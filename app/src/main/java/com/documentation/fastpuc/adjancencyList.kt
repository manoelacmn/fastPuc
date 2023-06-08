package com.documentation.fastpuc

import android.graphics.Canvas

class adjacencyList<T> : Graph<T> {

    private val adjacencies: HashMap<vertex<T>,ArrayList<edge<T>>> = HashMap()

    override fun createVertex(data: T): vertex<T> {
        val vertex = vertex(adjacencies.count(), data)
        adjacencies[vertex] = ArrayList()
        return vertex
    }
    override fun addDirectedEdge(source: vertex<T>, destination: vertex<T>, weight: Double?) {
        val edge = edge(source, destination, weight)
        adjacencies[source]?.add(edge)
    }

     override  fun  addUndirectionedEdge(source: vertex<T>, destination: vertex<T>, weight: Double?)
     {
         addDirectedEdge(source, destination, weight)
         addDirectedEdge(destination, source, weight)
     }

    override fun add(edge: EdgeType, source: vertex<T>, destination: vertex<T>, weight: Double?) {
            when (edge){
                EdgeType.DIRECTED -> addDirectedEdge(source,destination,weight)
                EdgeType.UNDIRECTED -> addUndirectionedEdge(source, destination, weight)
            }
        }

    override fun edges(source: vertex<T>): ArrayList<edge<T>> =  adjacencies[source] ?: arrayListOf()


    override fun weight(source: vertex<T>, destination: vertex<T>): Double? {

        return  edges(source).firstOrNull {it.destination == destination }?.weight
    }

    override fun toString(): String {
        return buildString { adjacencies.forEach{ (vertex,edges) ->
            val edgeString = edges.joinToString { it.destination.data.toString() }
            append("${vertex.data} ----> [$edgeString]\n")
        }
        }
    }
}


