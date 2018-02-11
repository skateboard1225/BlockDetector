package com.skateboard.blockdetector

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_reset.*

/**
 * Created by skateboard on 2018/2/9.
 */
class ResetActivity:AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)
        confirmBtn.setOnClickListener {

            val text=limitTime.editableText.toString()
            resetLimitTime(text.toLong())


        }
    }

    private fun resetLimitTime(limitTime:Long)
    {

        BlockDetector.resetLimitTime(limitTime)

    }

}