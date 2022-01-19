package com.fabledt5.moviescleanarchitecture.domain.util

fun <T> List<T>.getFirst(elements: Int): List<T> {
    if (this.size < elements) return this
    return this.subList(0, elements - 1)
}