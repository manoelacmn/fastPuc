package com.documentation.fastpuc

class AdjacencyMatrix<T>: Graph<T> {
     val vertices = arrayListOf<vertex<T>>()
     val weights = arrayListOf<ArrayList<Double?>>()



//    override fun createVertex(data: T): vertex<T> {
//        val vertex = vertex(vertices.count(),data)
//        vertices.add(vertex)
//        weights.forEach(){
//            it.add(null)
//        }
//        weights.add(arrayListOf())
//        return vertex
//    }
    override fun createVertex(data: T): vertex<T> {
        val vertex = vertex(vertices.count(), data)
        vertices.add(vertex) // 1
        weights.forEach {
            it.add(null) // 2
        }
        val row = ArrayList<Double?>(vertices.count())
        repeat(vertices.count()) {
            row.add(null)
        }
        weights.add(row) // 3
        return vertex
    }

    override fun edges(source: vertex<T>): ArrayList<edge<T>> {
        val edges = arrayListOf<edge<T>>()
        (0 until weights.size).forEach { column ->
            val weight = weights[source.index][column]
            if (weight != null) {
                edges.add(edge(source, vertices[column], weight))
            }
        }
        return edges
    }

    override fun addDirectedEdge(source: vertex<T>, destination: vertex<T>, weight: Double?) {
        weights[source.index][destination.index] = weight
    }

    override fun addUndirectionedEdge(source: vertex<T>, destination: vertex<T>, weight: Double?) {
        addDirectedEdge(source,destination,weight)
        addDirectedEdge(destination,source,weight)
    }


    override fun weight(source: vertex<T>, destination: vertex<T>): Double? {
        return weights[source.index][destination.index]
    }

    override fun toString(): String {
// 1
        val verticesDescription = vertices
            .joinToString(separator = "\n") { "${it.index}: ${it.data}" }
// 2
                val grid = weights.map { row ->
                    buildString {
                        (0 until weights.size).forEach { columnIndex ->
                            val value = row[columnIndex]
                            if (value != null) {
                                append("$value\t")
                            } else {
                                append("ø\t\t")
                            }
                        }
                    }
                }
                val edgesDescription = grid.joinToString("\n")
// 3
                return "$verticesDescription\n\n$edgesDescription"
            }
}