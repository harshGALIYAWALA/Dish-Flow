package com.example.dishflow.models

data class AllMenu(
    val foodName : String ?= null,
    val foodPrice : String ?= null,
    val foodDescription : String ?= null,
    val foodImage : String ?= null,
    val foodIngredients : String ?= null
)
