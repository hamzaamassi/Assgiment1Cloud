package com.example.assignment1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.assignment1.databinding.ActivityCreateUserBinding
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("DEPRECATION")
class CreateUserActivity : AppCompatActivity() {
    private lateinit var createUserBinding: ActivityCreateUserBinding
    private lateinit var firebaseFirestore: FirebaseFirestore
    private var usersCollectionReference: CollectionReference? = null
    private var userName = ""
    private var userNumber = ""
    private var userAddress = ""

    companion object {
        var userId: String? = ""

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        createUserBinding = ActivityCreateUserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(createUserBinding.root)
        firebaseFirestore = FirebaseFirestore.getInstance()

        createUserBinding.btnSave.setOnClickListener {
            createUser()
        }

    }

    private fun createUser() {
        userName = createUserBinding.etUserName.text.toString()
        userNumber = createUserBinding.etUserNumber.text.toString()
        userAddress = createUserBinding.etUserAddress.text.toString()
        usersCollectionReference = firebaseFirestore.collection("users")
        if (userName.isEmpty()) {
            Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show()
        } else if (userNumber.isEmpty()) {
            Toast.makeText(this, "Number required", Toast.LENGTH_SHORT).show()
        } else if (userAddress.isEmpty()) {
            Toast.makeText(this, "Address required", Toast.LENGTH_SHORT).show()
        } else {
            storeUserInDB()

        }
    }

    private fun storeUserInDB() {
        userId = usersCollectionReference!!.document().id
        val data = HashMap<String, String>()
        data["name"] = userName
        data["number"] = userNumber
        data["address"] = userAddress
        data["userId"] = userId!!
//        val db = Firebase.firestore
//        db.collection("users").document(userId!!).set(data)
        usersCollectionReference!!.document(userId!!).set(data)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Created Successfully", Toast.LENGTH_LONG)
                        .show()
                    this.onBackPressed()
                } else {
                    Toast.makeText(
                        this,
                        "Something error, Please try again later",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }
}