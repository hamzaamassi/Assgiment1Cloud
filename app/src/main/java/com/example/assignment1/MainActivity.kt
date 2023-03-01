@file:Suppress("DEPRECATION")

package com.example.assignment1

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment1.databinding.ActivityMainBinding
import com.example.test2.adapter.UserAdapter
import com.example.test2.modle.User
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showDialog()
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
                    hideDialog()
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
    private fun showDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Creating ....")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    private fun hideDialog() {
        if (progressDialog.isShowing)
            progressDialog.dismiss()
    }
}