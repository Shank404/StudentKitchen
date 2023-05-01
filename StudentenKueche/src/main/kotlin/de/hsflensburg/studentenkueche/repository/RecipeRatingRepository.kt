package de.hsflensburg.studentenkueche.repository

import de.hsflensburg.studentenkueche.entity.RecipeRating
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface RecipeRatingRepository : JpaRepository<RecipeRating, Int> {
    fun findAllByRecipeId(id: Int): MutableList<RecipeRating>

}