package de.hsflensburg.studentenkueche.entity

import javax.persistence.*

@Entity
data class Customer(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,
        val name: String = "",
        val username: String = "",
        val password: String = "",
        val email: String = "",
        val image: String = "",
        val amountRecipes: Int = 0,

        @ManyToMany
        @JoinTable(
                name = "favourite_recipes",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "recipe_id")]
        )
        val favouriteRecipes : MutableList<Recipe> = mutableListOf()
) {
}

