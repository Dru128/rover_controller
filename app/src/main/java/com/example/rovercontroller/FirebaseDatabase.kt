package com.example.rovercontroller


import android.content.Context
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class FirebaseDatabase
{
    val database = Firebase.database

    fun setCommand(path: String, data: String)
    {
        database.getReference(path).setValue(data)
    }

    fun stopRover(context: Context)
    {
        database.apply {
            getReference(context.getString(R.string.left_forward)).setValue("false")
            getReference(context.getString(R.string.left_back)).setValue("false")
            getReference(context.getString(R.string.right_forward)).setValue("false")
            getReference(context.getString(R.string.right_back)).setValue("false")
        }
    }
}