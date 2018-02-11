package com.skateboard.blockdetector

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_showlist.*

/**
 * Created by skateboard on 2018/2/10.
 */
class ShowListActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showlist)
        var stackTraceMap = getStackTraceMap()
        if (stackTraceMap != null)
        {
            prepareShowList(stackTraceMap)
        }
    }


    private fun getStackTraceMap(): Map<String, String>?
    {

        return SaveUtil.getSavedMap(this.applicationContext)

    }


    private fun prepareShowList(stackTraceMap: Map<String, String>)
    {
        showList.layoutManager = LinearLayoutManager(this)

        showList.addItemDecoration(SimpleItemDecoration(10, Color.WHITE))

        val stackTraceList=stackTraceMap.toList()

        showList.adapter=StackTraceAdapter(stackTraceList)

        val onItemTouchListener=SimpleOnItemTouchListener(showList)

        onItemTouchListener.clickEventListener= object : SimpleOnClickEventListener()
        {
            override fun onClick(position: Int)
            {
                jumpToShow(stackTraceList[position])
            }
        }

        showList.addOnItemTouchListener(onItemTouchListener)

    }

    private fun jumpToShow(pairData:Pair<String,String>)
    {
        val intent=Intent(this,ShowActivity::class.java)

        intent.putExtra(NotificationUtil.KEY_CONTENT,pairData.second)

        startActivity(intent)
    }





    companion object
    {

        class StackTraceItemViewHolder(view: View) : RecyclerView.ViewHolder(view)
        {
            lateinit var title: TextView

            lateinit var des: TextView

            init
            {
                title = itemView.findViewById(R.id.title)

                des = itemView.findViewById(R.id.des)
            }
        }


        class StackTraceAdapter(private var stackTraceList: List<Pair<String, String>>) : RecyclerView.Adapter<StackTraceItemViewHolder>()
        {



            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): StackTraceItemViewHolder
            {
                val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.stacktrace_item_layout, parent, false)

                return StackTraceItemViewHolder(itemView)
            }

            override fun getItemCount(): Int
            {
                return stackTraceList.size
            }

            override fun onBindViewHolder(holder: StackTraceItemViewHolder?, position: Int)
            {
                val pair = stackTraceList[position]

                holder?.title?.text = pair.first

                holder?.des?.text = pair.second

            }

        }


    }


}