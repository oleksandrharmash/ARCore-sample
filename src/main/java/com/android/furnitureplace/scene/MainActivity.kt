package com.android.furnitureplace.scene

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.furnitureplace.R
import com.android.furnitureplace.core.anatation.ActivityLayout
import com.android.furnitureplace.core.di.ModulesInstallable
import com.android.furnitureplace.scene.ar.ARFragment

@ActivityLayout(R.layout.activity_main)
class MainActivity : AppCompatActivity(), ModulesInstallable {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ARFragment())
                .commit()
    }
}
