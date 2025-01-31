package com.example.dishflow.models

data class CartItem(
    var foodName: String ?= null,
    var foodPrice: String ?= null,
    var foodDescription: String ?= null,
    var foodImage: String ?= null,
    var foodQuantity: Int ?= null
)
