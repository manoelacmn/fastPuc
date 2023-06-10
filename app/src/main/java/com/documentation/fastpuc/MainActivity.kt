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


        db.collection("v1")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
//                    Log.d(TAG, "${document.id} => ${document.data} & NAME: ${document.data["name"]}")
//                    points.add(document.data["name"] as String)

                        for ((key, value) in document.data) {
                            Log.d("NEW DOCUMENT","DOCUMENT")
                            println("$key: $value")
                            if (!points.contains(key)){
                                points.add(key)
                            }
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        Log.d("FINAL LIST",points.toString())

        println(graph.toString())


        val adapter = ArrayAdapter(this, R.layout.list_item, points)
        binding.ACTStartPoint.setAdapter(adapter)
        binding.ACTEndPoint.setAdapter(adapter)

        binding.btnProcurar.setOnClickListener {
            Toast.makeText(this, binding.ACTStartPoint.text, Toast.LENGTH_SHORT).show()
            println(graph.toString())

        }


    }
}