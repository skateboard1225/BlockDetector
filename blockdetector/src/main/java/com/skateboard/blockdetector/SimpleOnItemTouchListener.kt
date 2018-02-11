package com.skateboard.blockdetector

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent

/**
 * Created by skateboard on 2018/2/11.
 */
class SimpleOnItemTouchListener(private var recyclerView: RecyclerView):RecyclerView.SimpleOnItemTouchListener()
{

    interface OnClickEventListener
    {
        fun onClick(position:Int)

        fun onLongClick(position: Int)
    }

    private lateinit var gestureDetector: GestureDetector

    var clickEventListener:OnClickEventListener?=null


    private var gestureListener= object : GestureDetector.SimpleOnGestureListener()
    {
        override fun onSingleTapUp(e: MotionEvent?): Boolean
        {
            if(e!=null)
            {
                val childView = recyclerView.findChildViewUnder(e.x, e.y)
                clickEventListener?.onClick(recyclerView.getChildAdapterPosition(childView))
            }
            return super.onSingleTapUp(e)
        }

        override fun onLongPress(e: MotionEvent?)
        {
            super.onLongPress(e)
            if(e!=null)
            {
                val childView = recyclerView.findChildViewUnder(e.x, e.y)
                clickEventListener?.onLongClick(recyclerView.getChildAdapterPosition(childView))
            }
        }
    }


    init
    {
        gestureDetector= GestureDetector(recyclerView.context,gestureListener)
    }



    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean
    {
        return gestureDetector.onTouchEvent(e)
    }


}