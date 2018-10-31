package com.android.furnitureplace.scene.ar


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.*
import android.widget.Toast
import com.android.furnitureplace.R
import com.android.furnitureplace.core.di.ModulesInstallable
import com.android.furnitureplace.core.fragment.BaseFragment
import com.android.furnitureplace.core.presentation.Presentable
import com.android.furnitureplace.database.FireBaseData
import com.android.furnitureplace.database.RealmData
import com.android.furnitureplace.di.ARModule
import com.android.furnitureplace.model.ItemStore
import com.android.furnitureplace.model.realm_model.Furniture
import com.android.furnitureplace.scene.dialogs.furniture.FurnitureBottomSheet
import com.android.furnitureplace.scene.dialogs.gallery.GalleryBottomSheet
import com.android.furnitureplace.utils.ScreenshotUtils
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.fragment_ar.*
import org.parceler.Parcels
import toothpick.Scope
import java.io.File
import javax.inject.Inject


private const val PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: Int = 14

class ARFragment : BaseFragment(), Presentable<ARScene.View, ARScene.Presenter>,
        ModulesInstallable, ARScene.View, View.OnClickListener {

    @Inject
    override lateinit var presenter: ARScene.Presenter
    private var arFragmentAr: ArFragment? = null
    private var realm: RealmData? = null
    private val dialog = FurnitureBottomSheet()

    private val furnitureData: ArrayList<ItemStore> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_ar, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arFragmentAr = childFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment
        addFurniture.setOnClickListener(this)
        share.setOnClickListener(this)
        save.setOnClickListener(this)
        profile.setOnClickListener(this)
        realm = RealmData(context)
    }

    override fun onResume() {
        if (realm == null)
            realm = RealmData(context)
        super.onResume()
    }

    override fun onPause() {
        realm = null
        super.onPause()
    }

    override fun onClick(view: View?) =
            when (view) {
                addFurniture -> {
                    dialog.setTargetFragment(this, 0)
                    dialog.show(fragmentManager, dialog.tag)
                }
                share -> {
                    checkPermission()
                }
                save -> {
                    if (!furnitureData.isEmpty()) {
                        setProgressState()
                        ScreenshotUtils.saveScreenshot(arFragmentAr) { path ->
                            val furniture = Furniture()
                            furniture.preview = path
                            furniture.listItem = presenter.getRealmModel(furnitureData)
                            realm?.add(furniture)
                            setProgressState()
                            furnitureData.clear()
                            arFragmentAr?.arSceneView?.session?.allAnchors?.forEach {
                                it.detach()
                            }
                        }
                    } else {
                        Toast.makeText(context, getString(R.string.empty_furniture_list), Toast.LENGTH_SHORT).show()
                    }
                }
                profile -> {
                    val data = realm?.getAll() ?: arrayListOf()
                    if (data.isNotEmpty()) {
                        GalleryBottomSheet().show(fragmentManager, dialog.tag)
                    } else {
                        Toast.makeText(context, "Empty" +
                                "", Toast.LENGTH_SHORT).show()
                    }
                }
                else -> Unit
            }

    override fun show(element: ItemStore) {
        dialog.dismissAllowingStateLoss()
        setProgressState()
        FireBaseData.downloadFile(File(context?.filesDir, "${element.title
                ?: ""}.sfb"), element.sourceAndroid ?: "") {
            ModelRenderable.builder()
                    .setSource(context, Uri.parse(File(context?.filesDir, "${element.title
                            ?: ""}.sfb").absolutePath))
                    .build()
                    .thenAccept { renderable ->
                        setProgressState()
                        addModelToScene(renderable, element)
                    }
                    .exceptionally { _ ->
                        null
                    }
        }
    }

    private fun addModelToScene(renderable: ModelRenderable?,
                                element: ItemStore) {
        arFragmentAr?.setOnTapArPlaneListener { hitResult: HitResult, _: Plane, _: MotionEvent ->
            if (renderable != null) {
                val anchor = hitResult.createAnchor()
                val anchorNode = AnchorNode(anchor)
                furnitureData.add(element)
                anchorNode.setParent(arFragmentAr?.arSceneView?.scene)
                val model = TransformableNode(arFragmentAr?.transformationSystem)
                model.setParent(anchorNode)
                model.renderable = renderable
                model.scaleController.isEnabled = false
                model.select()
            }
        }
    }

    private fun checkPermission() {
        activity?.let {
            if (ContextCompat.checkSelfPermission(it,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(it,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                } else {
                    ActivityCompat.requestPermissions(it,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
                }
            } else {
                setProgressState()
                shareFile()
            }
        }
    }

    private fun shareFile() {
        ScreenshotUtils.takeScreenshot(arFragmentAr) { path ->
            Intent().run {
                action = Intent.ACTION_SEND
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(Intent.EXTRA_STREAM, Uri.parse(path))
                type = "image/*"
                startActivity(Intent.createChooser(this, "Share images..."))
            }
            setProgressState()
            File(path).deleteOnExit()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    setProgressState()
                    shareFile()
                }
                return
            }
        }
    }

    private fun setProgressState() {
        activity?.runOnUiThread {
            progressLoad.visibility = if (progressLoad.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
    }

    override fun installModules(scope: Scope) {
        scope.installModules(ARModule(this))
    }

}
