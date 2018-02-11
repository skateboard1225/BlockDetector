package com.skateboard.blockdetector

import android.content.Context
import android.os.Looper
import java.io.FileNotFoundException
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * Created by skateboard on 2018/2/11.
 */
object SaveUtil
{

    private var stackTraceMap: MutableMap<String, String> = mutableMapOf()


    val STACKTRACEMAP_FILE="stacktraemap_file"


    fun saveStackTrace(context: Context,key: String, stackTrace: String)
    {
        var map= getSavedMap(context)

        map=map?: mutableMapOf()

        map[key]=stackTrace

        saveMap(context,map)
    }


    fun getSavedMap(context: Context):MutableMap<String, String>?
    {

        var savedMap:MutableMap<String,String>?=null
        try
        {
            val inputStream=context.openFileInput(STACKTRACEMAP_FILE)
            val objInput=ObjectInputStream(inputStream)
            savedMap=objInput.readObject() as? MutableMap<String, String>
        }
        catch(e:FileNotFoundException)
        {
            e.printStackTrace()
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }
        catch(e:ClassNotFoundException)
        {
            e.printStackTrace()
        }

        return savedMap

    }

    fun saveMap(context: Context,map: MutableMap<String,String>)
    {

        try
        {
            val outputStream=context.openFileOutput(STACKTRACEMAP_FILE,Context.MODE_PRIVATE)
            val objOutput=ObjectOutputStream(outputStream)
            objOutput.writeObject(map)
        }
        catch(e:FileNotFoundException)
        {
            e.printStackTrace()
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }
        catch(e:ClassNotFoundException)
        {
            e.printStackTrace()
        }


    }




    fun buildStackTrace(): String
    {
        val stackTrace = Looper.getMainLooper().thread.stackTrace

        val strBuilder = StringBuilder()

        for (element in stackTrace)
        {

            strBuilder.append("${element.className} ${element.methodName} (line:${element.lineNumber})")

            strBuilder.append("\n")
        }

        return strBuilder.toString()
    }


}