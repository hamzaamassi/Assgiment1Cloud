@file:Suppress("DEPRECATION")

package com.example.assignment1

import android.app.ProgressDialog
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
    private lateinit var progressDialog: ProgressDialog
    private var userName = ""
    private var userAge = ""
    private var userEmail = ""
    private var userAddress = ""
    private var userNumber = ""

    companion object {
        var userId: String? = ""

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        createUserBinding = ActivityCreateUserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(createUserBinding.root)
        firebaseFirestore = FirebaseFirestore.getInstance()

        createUserBinding.btnSave.setOnClickListener {
            showDialog()
            createUser()
        }

    }

    private fun createUser() {
        userName = createUserBinding.etUserName.text.toString()
        userAge = createUserBinding.etUserAge.text.toString()
        userEmail = createUserBinding.etUserEmail.text.toString()
        userAddress = createUserBinding.etUserAddress.text.toString()
        userNumber = createUserBinding.etUserNumber.text.toString()
        usersCollectionReference = firebaseFirestore.collection("users")
        if (userName.isEmpty()) {
            Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show()
        } else if (userAge.isEmpty()) {
            Toast.makeText(this, "Age required", Toast.LENGTH_SHORT).show()
        } else if (userEmail.isEmpty()) {
            Toast.makeText(this, "Email required", Toast.LENGTH_SHORT).show()
        }else if (userAddress.isEmpty()) {
            Toast.makeText(this, "Address required", Toast.LENGTH_SHORT).show()
        }else if (userNumber.isEmpty()) {
            Toast.makeText(this, "Number required", Toast.LENGTH_SHORT).show()
        } else {
            storeUserInDB()

        }
    }

    private fun storeUserInDB() {
        userId = usersCollectionReference!!.document().id
        val data = HashMap<String, String>()
        data["name"] = userName
        data["age"] = userAge
        data["email"] = userEmail
        data["address"] = userAddress
        data["number"] = userNumber
        data["userId"] = userId!!
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
                hideDialog()
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