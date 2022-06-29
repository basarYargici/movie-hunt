package com.basar.moviehunter.util

abstract class Mapper<T1, T2> {
    abstract fun map(value: T1): T2

    fun map(values: List<T1>): List<T2> {
        val items: MutableList<T2> = ArrayList(values.size)
        values.forEach { items.add(map(it)) }
        return items
    }
}