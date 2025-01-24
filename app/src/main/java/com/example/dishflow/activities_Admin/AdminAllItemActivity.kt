package com.example.dishflow.activities_Admin

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishflow.R
import com.example.dishflow.adaptar.MenuItemAdapter_Admin
import com.example.dishflow.databinding.ActivityAdminAllItemBinding
import com.example.dishflow.models.AllMenu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminAllItemActivity : AppCompatActivity() {

    private lateinit var databaseRefrence: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private  var menuItem: ArrayList<AllMenu> = ArrayList()

    private val binding: ActivityAdminAllItemBinding by lazy {
        ActivityAdminAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        databaseRefrence = FirebaseDatabase.getInstance().reference
        retrieveMenuItem()


        binding.backBtn.setOnClickListener{
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("menu")

        //fetch data from database
        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear the list before adding new data
                menuItem.clear()
                for (foodSnapshot in snapshot.children) {
                    val food = foodSnapshot.getValue(AllMenu::class.java)
                    food?.let {
                        menuItem.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Database error", "Failed to read value.", error.toException())
            }
        })
    }

    private fun setAdapter() {
        val adapter = MenuItemAdapter_Admin(this, menuItem, databaseRefrence)
        binding.MenuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.MenuRecyclerView.adapter = adapter
    }
}