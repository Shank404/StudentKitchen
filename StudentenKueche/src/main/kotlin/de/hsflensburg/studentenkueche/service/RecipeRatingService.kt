package de.hsflensburg.studentenkueche.service

import de.hsflensburg.studentenkueche.entity.RecipeRating
import de.hsflensburg.studentenkueche.repository.RecipeRatingRepository
import de.hsflensburg.studentenkueche.repository.RecipeRepository
import de.hsflensburg.studentenkueche.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RecipeRatingService {

    @Autowired
    lateinit var recipeRepository: RecipeRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var recipeRatingRepository: RecipeRatingRepository

    fun rateRecipe(recipeId : Int, username : String, rating : Int){
        val existingRecipeRatingList = recipeRatingRepository.findAllByRecipeId(recipeId)
        var existingRecipeRating = RecipeRating()
        val existingUser = userRepository.getUserByUsername(username)
        val newRecipeRating = RecipeRating(recipeId = recipeId, userId = existingUser.id, recipeRating = rating)
        var ratingExists = false


        existingRecipeRatingList.forEach {
            if(it.userId == existingUser.id){
                ratingExists = true
                existingRecipeRating = it
            }
        }

        if(ratingExists){
            recipeRatingRepository.save(existingRecipeRating.copy(recipeId = recipeId,userId = existingUser.id, recipeRating = rating))
        } else {
            recipeRatingRepository.save(newRecipeRating)
        }
        editRecipeRating(recipeId)
    }

    fun editRecipeRating(recipeId : Int) {
        val existingRecipeRatingList = recipeRatingRepository.findAllByRecipeId(recipeId)
        val existingRecipe = recipeRepository.findRecipeById(recipeId)
        var rating = 0.0
        val ratingVotes = existingRecipeRatingList.size
        existingRecipeRatingList.forEach {
            rating += it.recipeRating
        }
        recipeRepository.save(existingRecipe.copy(rating = rating, ratingvotes = ratingVotes))
    }
}