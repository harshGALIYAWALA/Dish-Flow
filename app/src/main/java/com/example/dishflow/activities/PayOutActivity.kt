package com.example.dishflow.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishflow.R
import com.example.dishflow.databinding.ActivityPayOutBinding
import com.example.dishflow.fragments.CongratBottomFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PayOutActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var name: String
    private lateinit var address: String
    private lateinit var phone: String
    private lateinit var paymentMethod: String
    private lateinit var totalAmount: String
    private lateinit var foodItemName: ArrayList<String>
    private lateinit var foodItemPrice: ArrayList<String>
    private lateinit var foodItemImage: ArrayList<String>
    private lateinit var foodItemDescription: ArrayList<String>
    private lateinit var foodItemIngredients: ArrayList<String>
    private lateinit var foodItemQuantity: ArrayList<Int>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userId: String


    private lateinit var binding: ActivityPayOutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding  = ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference()

        setUserData()

        //getting all the valurs from the intent
        val intent = intent
        foodItemName = intent.getStringArrayListExtra("foodItemName") as ArrayList<String>
        foodItemPrice = intent.getStringArrayListExtra("foodItemPrice") as ArrayList<String>
        foodItemImage = intent.getStringArrayListExtra("foodItemImage") as ArrayList<String>
        foodItemDescription = intent.getStringArrayListExtra("foodItemDescription") as ArrayList<String>
        foodItemIngredients = intent.getStringArrayListExtra("foodItemIngredients") as ArrayList<String>
        foodItemQuantity = intent.getIntegerArrayListExtra("foodItemQuantity") as ArrayList<Int>
        
        totalAmount = totalAmountPrice().toString() + "Rs"
//        binding.totalAmount.isEnabled = false
        binding.totalAmount.setText(totalAmount)


       binding.placeMyOderBtn.setOnClickListener{
           val congraBottomFragment = CongratBottomFragment()
           congraBottomFragment.show(supportFragmentManager, "CongratBottomFragment")
       }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun totalAmountPrice(): Int {
        var totalAmount = 0
        for (i in 0 until foodItemPrice.size) {
            val price = foodItemPrice[i]
            val lastChar = price.last()
            val priceIntVale = if(lastChar == '$'){
                price.dropLast(1).toInt()
            } else {
                price.toInt()
            }
            var quantity = foodItemQuantity[i]
            totalAmount += priceIntVale * quantity
        }
        return totalAmount
    }

    private fun setUserData() {
        val user = auth.currentUser
        if (user != null) {
            userId = user.uid
            val userRef = databaseReference.child("user").child(userId)

            userRef.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        val Name = snapshot.child("name").getValue(String::class.java)?:""
                        val Address = snapshot.child("address").getValue(String::class.java)?:""
                        val Phone = snapshot.child("phone").getValue(String::class.java)?:""
                        binding.apply {
                            name.setText(Name)
                            address.setText(Address)
                            phone.setText(Phone)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
//                    Toast.makeText(baseContext, "something went wrong", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}