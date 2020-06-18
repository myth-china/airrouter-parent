package com.myth.arch.airrouter.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.myth.arch.imodulea.AirServices_ModuleA
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            AirServices_ModuleA.getModuleARouter().startModuleAActivity(this@MainActivity)
        }
    }
}