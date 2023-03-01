package com.example.assignment1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment1.databinding.ActivityMainBinding
import com.example.test2.adapter.UserAdapter
import com.example.test2.modle.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseFirestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        firebaseFirestore = FirebaseFirestore.getInstance()
        fun addUserActivity() {
            val intent = Intent(this, CreateUserActivity::class.java)
            startActivity(intent)
        }
        readeData()
        binding.fabAddCategory.setOnClickListener {
            addUserActivity()
        }

    }


    private fun readeData() {
        firebaseFirestore.collection("users")
            .get()
            .addOnCompleteListener { it ->
                if (it.isSuccessful && !it.result.isEmpty) {
                    val cats = it.result.map {
                        it.toObject(User::class.java)
                    }
                    val userAdapter = UserAdapter(
                        this,
                        cats as ArrayList<User>
                    )
                    binding.rvCategory.layoutManager =
                        LinearLayoutManager(this)
                    binding.rvCategory.adapter = userAdapter
                }

            }
    }

}