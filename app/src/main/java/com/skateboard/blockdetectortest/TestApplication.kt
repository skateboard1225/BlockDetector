package com.skateboard.blockdetectortest

import android.app.Application
import com.skateboard.blockdetector.BlockDetector

/**
 * Created by skateboard on 2018/2/9.
 */
class TestApplication: Application()
{
    override fun onCreate()
    {
        super.onCreate()
        BlockDetector.install(this)
    }
}