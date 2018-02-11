package com.skateboard.blockdetector

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_show.*

/**
 * Created by skateboard on 2018/2/9.
 */
class ShowActivity:AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        showContent()

    }

    private fun showContent()
    {
        val text=intent.getStringExtra(NotificationUtil.KEY_CONTENT)

        content.text=text
    }

}