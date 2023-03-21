package com.example.assignment1.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment1.databinding.ItemUserBinding
import com.example.assignment1.modle.User
import com.google.firebase.firestore.FirebaseFirestore



class UserAdapter(
    private var activity: AppCompatActivity,
    private var data: ArrayList<User>
) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    private lateinit var firebaseFirestore: FirebaseFirestore


    class MyViewHolder(var binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        firebaseFirestore = FirebaseFirestore.getInstance()
        val binding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = data[position]
            holder.binding.root.setOnLongClickListener {
                val alertDialog = AlertDialog.Builder(activity)
                alertDialog.setTitle("Delete User")
                alertDialog.setMessage("Are you sure to delete User ?")
//                alertDialog.setIcon(R.drawable.delete)
                alertDialog.setPositiveButton("Yes") { _, _ ->
                    currentUser.userId?.let { it1 ->
                        firebaseFirestore
                            .collection("users")
                            .document(it1)
                            .delete()
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(
                                        activity,
                                        "Deleted Successfully",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                } else {
                                    Toast.makeText(activity, "Something Error ", Toast.LENGTH_LONG)
                                        .show()
                                }


                            }
                    }

                }
                alertDialog.setNegativeButton("No") { _, _ ->
                }
                alertDialog.create().show()

                true

            }

        holder.binding.tvUserName.text = "Name: ${currentUser.name}"
        holder.binding.tvUserAge.text = "Age: ${currentUser.age}"
        holder.binding.tvUserEmail.text = "Email: ${currentUser.email}"
        holder.binding.tvUserNumber.text = "Number: ${currentUser.number}"
        holder.binding.tvUserAddress.text = "Address: ${currentUser.address}"
        }
    }

