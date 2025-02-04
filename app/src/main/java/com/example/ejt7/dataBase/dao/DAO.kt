package com.example.ejt7.dataBase.dao

import android.content.Context

interface DAO <T>{
    fun findAll(context: Context?): List<T>
    fun save(context: Context?, t:T)
    fun update(context: Context?, t:T)
    fun delete(context: Context?, id:Int)
}