package com.documentation.fastpuc

data  class edge<T>(
    val source: vertex<T>,
    val destination: vertex<T>,
    val weight: Double? = null
)

