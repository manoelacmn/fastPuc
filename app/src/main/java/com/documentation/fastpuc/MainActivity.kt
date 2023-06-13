package com.documentation.fastpuc

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.documentation.fastpuc.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    private lateinit var binding:  ActivityMainBinding
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val graph = AdjacencyMatrix<String>()


        println(graph.toString())

        val points = mutableListOf<String>()
        val index  = hashMapOf<String, String>()

        db.collection("v1")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                Log.d("WHOLE DOCUMENT",document.data.toString())
                   Log.d("VERTICES LIST:",graph.vertices.toString())

                    index[document.data["name"] as String] = document.id

                    val alreadyExists = graph.vertices.find { it.data ==  document.id}
                    Log.d("ELEMENT FOUND", alreadyExists.toString())

                    if(alreadyExists==null){
                        val first = graph.createVertex(document.id)
                        for ((key, value) in document.data) {
                              Log.d("NEW DOCUMENT",value.toString())

                            if(key!="name"){
                                Log.d("DOCUMENT DATA KEYS",key)
                                Log.d("VERTICES LIST2:",graph.vertices.toString())

                                val alreadyExists1 = graph.vertices.find { it.data ==  key}
                                Log.d("ALREADY EXIST1:","${alreadyExists1.toString()} VALUE: ${value.toString()}")

                                if (alreadyExists1 == null){
                                    println(graph.toString())
                                    val others= graph.createVertex(key)
                                    graph.add(EdgeType.UNDIRECTED,first, others, value.toString().toDouble())
                                }else{
                                    graph.add(EdgeType.UNDIRECTED,first, alreadyExists1, value.toString().toDouble())
                                }

                            }

                        }
                    }else{
                        for ((key, value) in document.data) { 
                            Log.d("NEW DOCUMENT",value.toString())

                            if(key!="name"){
                                Log.d("DOCUMENT DATA KEYS",key)
                                Log.d("VERTICES LIST2:",graph.vertices.toString())

                                val alreadyExists1 = graph.vertices.find { it.data ==  key}
                                Log.d("ALREADY EXIST1:","${alreadyExists1.toString()} VALUE: ${value.toString()}")

                                if (alreadyExists1 == null){
                                    println(graph.toString())
                                    val others= graph.createVertex(key)
                                    graph.add(EdgeType.UNDIRECTED,alreadyExists, others, value.toString().toDouble())
                                }else{
                                    graph.add(EdgeType.UNDIRECTED,alreadyExists, alreadyExists1, value.toString().toDouble())
                                }


                            }

                        }
                    }


                    if (!points.contains(document.data["name"])){
                        points.add(document.data["name"] as String) 
                    }

                    Log.d("INDEX HASHMAP",index.toString())
                    for ((key, value) in index) {

                        println("MAP DATA:  $key: $value")

                    }
                }


            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        Log.d("FINAL LIST",points.toString())



        println("HERE")
        print(index.toString())


        val adapter1 = ArrayAdapter(this, R.layout.list_item, points)
        val adapter2 = ArrayAdapter(this, R.layout.list_item, points)
        binding.ACTStartPoint.setAdapter(adapter1)
        binding.ACTEndPoint.setAdapter(adapter2)

        binding.btnProcurar.setOnClickListener {
            Toast.makeText(this, binding.ACTStartPoint.text, Toast.LENGTH_SHORT).show()
            Log.d("STARTING POINT", binding.ACTStartPoint.text.toString())
            Log.d("ENDING POINT POINT", binding.ACTEndPoint.text.toString())

            val tempKey = index[binding.ACTStartPoint.text.toString()]

            val tempKey1 = index[binding.ACTEndPoint.text.toString()]

            val endVertex = graph.vertices.find { it.data ==  tempKey1.toString()}



            Log.d("TEMPKEY",tempKey.toString())

            val startVertex = graph.vertices.find { it.data ==  tempKey.toString()}!!


            val edgesOfA = graph.edges(startVertex)

            Log.d("Edge list", edgesOfA.toString())


            for (edge in edgesOfA) {
                Log.d("Edge", "From: ${edge.source.data} To: ${edge.destination} Weight: ${edge.weight}")

            }

            Log.d("SHORTEST PATH",graph.dijkstraShortestPath(endVertex!!).toString())

            val  smallestPaths = graph.dijkstraShortestPath(endVertex)

            for((vertex,weight) in smallestPaths){
                if (vertex.data == tempKey) {
                    val weightInSeconds = weight.toDouble() / 1.4
                    val hours = weightInSeconds / 3600
                    val minutes = (weightInSeconds % 3600) / 60
                    val seconds = weightInSeconds % 60

                    val formattedTime = String.format("%02d:%02d:%02d", hours.toInt(), minutes.toInt(), seconds.toInt())

                    println("Vertex: $vertex, Weight: $weight")
                    Log.d("Total time:", formattedTime)
                    binding.tvResult.text = formattedTime
                }
            }


            Log.d("VERTICES LIST:",graph.vertices.toString())


            Log.d("START VERTEX POINT:",startVertex.toString())

            Log.d("ENDING VERTEX POINT:",endVertex.toString())

            println(graph.toString())


        }


    }
}