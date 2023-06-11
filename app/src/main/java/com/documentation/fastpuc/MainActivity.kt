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


class MainActivity : AppCompatActivity() {

    private lateinit var binding:  ActivityMainBinding
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val points = listOf("H0", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "H14", "H15")
//        val points = listOf<String>()


        val graph = AdjacencyMatrix<String>()

//        val h0 = graph.createVertex("H0")
//        val h1 = graph.createVertex("H1")
//        val h2 = graph.createVertex("H2")
//        val h3 = graph.createVertex("H3")
//        val h4 = graph.createVertex("H4")
//
//
//        graph.addUndirectionedEdge(h0, h4, 1000.00)

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
                        for ((key, value) in document.data) { // acess the others values within a document
                              Log.d("NEW DOCUMENT",value.toString())

                            if(key!="name"){
                                Log.d("DOCUMENT DATA KEYS",key)
                                Log.d("VERTICES LIST2:",graph.vertices.toString())

                                val alreadyExists1 = graph.vertices.find { it.data ==  key}
                                Log.d("ALREADY EXIST1:","${alreadyExists1.toString()} VALUE: ${value.toString()}")

                                if (alreadyExists1 == null){ // if not exists then create a vertice and its edge
                                    println(graph.toString())
                                    val others= graph.createVertex(key)
                                    graph.add(EdgeType.UNDIRECTED,first, others, value.toString().toDouble())
                                }else{  // if exists only create edge
                                    graph.add(EdgeType.UNDIRECTED,first, alreadyExists1, value.toString().toDouble())
                                }

                               // val others= graph.createVertex(key)
                                // acess the others values within a document
                                 // create edges with the others
                            }

                        }
                    }else{
                        for ((key, value) in document.data) { // acess the others values within a document
                            Log.d("NEW DOCUMENT",value.toString())

                            if(key!="name"){
                                Log.d("DOCUMENT DATA KEYS",key)
                                Log.d("VERTICES LIST2:",graph.vertices.toString())

                                val alreadyExists1 = graph.vertices.find { it.data ==  key}
                                Log.d("ALREADY EXIST1:","${alreadyExists1.toString()} VALUE: ${value.toString()}")

                                if (alreadyExists1 == null){ // if not exists then create a vertice and its edge
                                    println(graph.toString())
                                    val others= graph.createVertex(key)
                                    graph.add(EdgeType.UNDIRECTED,alreadyExists, others, value.toString().toDouble())
                                }else{  // if exists only create edge
                                    graph.add(EdgeType.UNDIRECTED,alreadyExists, alreadyExists1, value.toString().toDouble())
                                }

                                // val others= graph.createVertex(key)
                                // acess the others values within a document
                                // create edges with the others
                            }

                        }
                    }


                    if (!points.contains(document.data["name"])){
                        points.add(document.data["name"] as String) // verify if vertice name is already contained if not it adds
                        // points.add(key)
                    }
//                    print(index.toString())
                    Log.d("INDEX HASHMAP",index.toString())
                    for ((key, value) in index) {
                        //  Log.d("NEW DOCUMENT","DOCUMENT")
                        println("MAP DATA:  $key: $value")

                    }
                }
                println(graph.toString())
                Log.d("GRAPH VALUES",graph.toString())


            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

//        Log.d("FINAL LIST",points.toString())

//        println(graph.toString())

//
//        println("HERE")

        println("HERE")
        print(index.toString())


        val adapter = ArrayAdapter(this, R.layout.list_item, points)
        binding.ACTStartPoint.setAdapter(adapter)
        binding.ACTEndPoint.setAdapter(adapter)

        binding.btnProcurar.setOnClickListener {
            Toast.makeText(this, binding.ACTStartPoint.text, Toast.LENGTH_SHORT).show()
            println(graph.toString())

        }


    }
}