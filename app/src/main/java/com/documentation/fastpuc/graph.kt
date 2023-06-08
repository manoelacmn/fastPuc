package com.documentation.fastpuc

interface  Graph<T> {
    fun createVertex(data: T): vertex<T>

    fun addDirectedEdge(
        source: vertex<T>,
        destination: vertex<T>,
        weight: Double?
    )

    fun addUndirectionedEdge(
        source: vertex<T>,
        destination: vertex<T>,
        weight: Double?
    ) {

        addDirectedEdge(source, destination, weight)
        addDirectedEdge(destination, source, weight)
    }

    fun add(
        edge: EdgeType,
        source: vertex<T>,
        destination: vertex<T>,
        weight: Double?
    ) {
        when (edge) {
            EdgeType.DIRECTED -> addDirectedEdge(source,destination,weight)
            EdgeType.UNDIRECTED -> addUndirectionedEdge(source, destination, weight)
        }
    }

    fun edges(source: vertex<T>): ArrayList<edge<T>>

    fun weight(source: vertex<T>, destination: vertex<T>): Double?


}

enum class EdgeType {
    DIRECTED,
    UNDIRECTED
}