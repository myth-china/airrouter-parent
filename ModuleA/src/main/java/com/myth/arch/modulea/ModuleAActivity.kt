package com.myth.arch.modulea

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myth.arch.imoduleb.AirServices_ModuleB
import kotlinx.android.synthetic.main.activity_module_a.*

class ModuleAActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_a)

        btn.setOnClickListener {
            AirServices_ModuleB.getModuleBServiceImpl()
            AirServices_ModuleB.getModuleBRouter().startModuleBActivity(this)
        }
    }
}