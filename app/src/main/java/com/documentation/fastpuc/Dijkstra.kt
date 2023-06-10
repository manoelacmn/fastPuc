package com.documentation.fastpuc

class Dijkstra<T> (private val graph: adjacencyList<T>) {



    private fun route(
        destination: vertex<T>, paths:
        HashMap<vertex<T>, Visit<T>>
    ): ArrayList<edge<T>> {
        var vertex = destination // 1
        val path = arrayListOf<edge<T>>() // 2
        loop@ while (true) {
            val visit = paths[vertex] ?: break
            when (visit.type) {
                VisitType.EDGE -> visit.edge?.let { // 3
                    path.add(it) // 4
                    vertex = it.source // 5
                }
                VisitType.START -> break@loop // 6
            }
        }
        return path

    }
    private fun distance(destination: vertex<T>, paths:
    HashMap<vertex<T>, Visit<T>>): Double {
        val path = route(destination, paths) // 1
        return path.sumOf { it.weight ?: 0.0 }
    }

//







//    fun shortestPath(start: vertex<T>) {
//        val paths: HashMap<vertex<T>, Visit<T>> = HashMap()
//        paths[start] = Visit(VisitType.START) // 1
//// 2
//        val distanceComparator = Comparator<vertex<T>> { first, second
//            ->
//            (distance(second, paths) - distance(first, paths)).toInt()
//        }
//// 3
//        val priorityQueue =
//            ComparatorPriorityQueueImpl(distanceComparator)
//// 4
//        priorityQueue.enqueue(start)
//        while (true) {
//            val vertex = priorityQueue.dequeue() ?: break // 1
//            val edges = graph.edges(vertex) // 2
//            edges.forEach {
//                val weight = it.weight ?: return@forEach // 3
//                if (paths[it.destination] == null
//                    || distance(vertex, paths) + weight < distance(it.destination, paths)) { //4
//                    paths[it.destination] = Visit(VisitType.EDGE, it)
//                    priorityQueue.enqueue(it.destination)
//                }
//            }
//        }
//
//        return paths
//    }
//    fun shortestPath(destination: vertex<T>, paths:
//    HashMap<vertex<T>,
//            Visit<T>>): ArrayList<edge<T>> {
//        return route(destination, paths)
//    }
//
//
//
//}

class Visit<T>(val type: VisitType, val edge: edge<T>? = null)
enum class VisitType {
    START, // 1
    EDGE // 2
}
}
