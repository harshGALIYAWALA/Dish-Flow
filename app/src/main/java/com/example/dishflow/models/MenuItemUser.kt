package com.example.dishflow.models

data class MenuItemUser(
    val foodName: String ?= null,
    val foodPrice: String ?= null,
    val foodDescription: String ?= null,
    val foodImageUrl: String ?= null,
    val foodIntredient: String ?= null,
)
