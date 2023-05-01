package de.hsflensburg.studentenkueche.repository

import de.hsflensburg.studentenkueche.entity.Recipe
import de.hsflensburg.studentenkueche.entity.Customer
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecipeRepository : JpaRepository<Recipe,Int> {

    override fun findAll(sort: Sort) : List<Recipe>

    fun findRecipesByCategory(category: String, sort: Sort): List<Recipe>

    fun findRecipesByCategory(category: String): List<Recipe>
    fun findRecipesByLikesContainingAndCategory(user: Customer, category: String, sort: Sort): List<Recipe>
    fun findRecipesByLikesContainingAndCategory(user: Customer, category: String): List<Recipe>
    fun findRecipesByLikesContaining(user: Customer): List<Recipe>
    fun findRecipesByLikesContaining(user: Customer, sort: Sort): List<Recipe>
    fun findRecipesByAuthor(author :String): List<Recipe>
    fun findRecipesByAuthor(author :String, order : Sort): List<Recipe>
    fun findRecipeById(id:Int): Recipe



}