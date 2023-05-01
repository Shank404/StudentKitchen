package de.hsflensburg.studentenkueche.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class RecipeRating(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Int = 0,

    val recipeId : Int = 0,
    val userId : Int = 0,
    val recipeRating : Int = 0

)