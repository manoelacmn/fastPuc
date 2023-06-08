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

        val points = listOf("H0", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "H14", "H15")


        val graph = AdjacencyMatrix<String>()
        for (i in points){
            graph.createVertex(i);
        }





        db.collection("v1")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        val adapter = ArrayAdapter(this, R.layout.list_item, points)
        binding.ACTStartPoint.setAdapter(adapter)
        binding.ACTEndPoint.setAdapter(adapter)

        binding.btnProcurar.setOnClickListener { Toast.makeText(this, binding.ACTStartPoint.text, Toast.LENGTH_SHORT).show() }


    }
}