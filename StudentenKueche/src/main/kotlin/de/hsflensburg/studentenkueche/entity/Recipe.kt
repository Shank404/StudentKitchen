package de.hsflensburg.studentenkueche.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class Recipe (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val name: String = "",
    val ingredients: String = "",
    val rating: Double = 0.0,
    val description: String = "",
    val price: Double = 0.0,
    val duration: Double = 0.0,
    val difficulty: String = "",
    val category: String = "",
    val images: String = "",
    val preparation: String = "",
    val views: Int = 0,
    val ratingvotes: Int = 0,
    val author: String = "",

    @ManyToMany(mappedBy = "favouriteRecipes")
        @JsonIgnore
        val likes : List<Customer> = mutableListOf()
)
