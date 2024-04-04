package com.max.hsu.dartscoreboard.toolUtil

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T : Any> T.json(): String = Gson().toJson(this, T::class.java)
inline fun <reified T> Gson.fromJsonExtend(json: String): T =
    this.fromJson(json, object : TypeToken<T>() {}.type)

//convert a data class to a map
inline fun <reified T> T.serializeToMap(): MutableMap<String, Any> = convert()

//convert a map to a data class
inline fun <reified T> MutableMap<String, Any>.toDataClass(): T = convert()

//convert an object of type I to type O
inline fun <reified I, reified O> I.convert(): O = Gson().fromJsonExtend(this?.json() ?: "")
