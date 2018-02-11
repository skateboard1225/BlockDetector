package com.skateboard.blockdetector

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by skateboard on 2018/2/11.
 */
class SimpleItemDecoration(private var dividerHeight:Int,private var color:Int):RecyclerView.ItemDecoration()
{

    private lateinit var paint: Paint

    init
    {
        paint= Paint()
        paint.color=color

    }


    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?)
    {

        if(parent!=null && c!=null)
        {

            val childCount = parent.childCount

            val left = (parent.left + parent.paddingLeft).toFloat()

            val right = (parent.right-parent.paddingRight).toFloat()

            for (i in 0 until childCount)
            {

                val top: Float = parent.getChildAt(i).bottom.toFloat()

                val bottom: Float = top + dividerHeight

                c.drawRect(left, top, right, bottom, paint)
            }
        }

    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?)
    {
        outRect?.set(0,0,0,dividerHeight)
    }
}