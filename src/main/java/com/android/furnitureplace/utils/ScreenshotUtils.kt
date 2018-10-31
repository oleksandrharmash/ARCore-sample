
package com.android.furnitureplace.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.view.PixelCopy
import android.widget.Toast
import com.google.ar.sceneform.ux.ArFragment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object ScreenshotUtils {

    private fun generateScreenShotName(): String {
        val date = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        return "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}${File.separator}Sceneform${File.separator}${date}_screenshot.jpg"
    }

    private fun generateFilename(context: Context?): String {
        val date = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        return "${context?.filesDir}${File.separator}Sceneform${File.separator}${date}_screenshot.jpg"
    }

    @Throws(IOException::class)
    private fun saveBitmapToDisk(bitmap: Bitmap, filename: String, finish: () -> Unit) {

        val out = File(filename)
        if (!out.parentFile.exists()) {
            out.parentFile.mkdirs()
        }
        try {
            FileOutputStream(filename).use { outputStream ->
                ByteArrayOutputStream().use { outputData ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputData)
                    outputData.writeTo(outputStream)
                    outputStream.flush()
                    outputStream.close()
                }
                finish()
            }
        } catch (ex: IOException) {
            throw IOException("Failed to save bitmap to disk", ex)
        }
    }

    fun saveScreenshot(arFragmentAr: ArFragment?, finishSave: (filePath: String) -> Unit) {
        getImage(arFragmentAr, generateFilename(arFragmentAr?.context)) {
            finishSave(it)
        }
    }

    @SuppressLint("SetWorldReadable")
    fun takeScreenshot(arFragmentAr: ArFragment?, finishSave: (filePath: String) -> Unit) {
        getImage(arFragmentAr, generateScreenShotName()) {
            finishSave(it)
        }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun getImage(arFragmentAr: ArFragment?, fileName: String, finish: (filePath: String) -> Unit) {
        val view = arFragmentAr?.arSceneView
        val bitmap = Bitmap.createBitmap(view?.width ?: 0, view?.height
                ?: 0, Bitmap.Config.ARGB_8888)
        val handlerThread = HandlerThread("PixelCopier")
        handlerThread.start()
        PixelCopy.request(view, bitmap, { copyResult ->
            if (copyResult == PixelCopy.SUCCESS) {
                try {
                    saveBitmapToDisk(bitmap, fileName) {
                        finish(fileName)
                    }
                } catch (e: IOException) {
                    val toast = Toast.makeText(arFragmentAr?.context, e.toString(),
                            Toast.LENGTH_LONG)
                    toast.show()
                    return@request
                }
            } else {
                val toast = Toast.makeText(arFragmentAr?.context,
                        "Failed to copyPixels: $copyResult", Toast.LENGTH_LONG)
                toast.show()
            }
            handlerThread.quitSafely()
        }, Handler(handlerThread.looper))
    }

}