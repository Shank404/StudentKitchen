package de.hsflensburg.studentenkueche.controller

import de.hsflensburg.studentenkueche.entity.Recipe
import de.hsflensburg.studentenkueche.service.RecipeRatingService
import de.hsflensburg.studentenkueche.service.RecipeService
import de.hsflensburg.studentenkueche.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api")
class RecipeController {

    @Autowired
    lateinit var recipeService: RecipeService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var recipeRatingService: RecipeRatingService

    @PostMapping(value = ["/newrecipe"], consumes = ["multipart/form-data"])
    fun postNewRecipe(
        @RequestParam recipe: Map<String, String>,
        @RequestParam("images") image: MultipartFile
    ): ResponseEntity<Recipe> {
        return recipeService.createRecipe(recipe, image)
    }

    @PostMapping(value = ["/recipes/{id}"], consumes = ["multipart/form-data"])
    fun postEditRecipe(@PathVariable id : Int, @RequestParam recipe : Map<String, String>,@RequestParam("images") image : MultipartFile): ResponseEntity<Recipe> {
        return recipeService.editRecipe(id, recipe, image)
    }

    @GetMapping("/recipes/{id}")
    // @RequestBody
    fun getRecipeByID(@PathVariable(value = "id") id: Int): ResponseEntity<Recipe> {
        return recipeService.getRecipe(id)
    }

    @GetMapping("/allrecipes")
    fun getAllCategoryRecipes(@RequestParam parameters: Map<String, String>): ResponseEntity<List<Recipe>> {
        val category = parameters["cate"]
        val order = parameters["sort"]
        // println(category)
        // println(order)
        val recipe = recipeService.getAllRecipes(order!!, category!!)
        return ResponseEntity.ok().body(recipe)
    }

    @GetMapping("/examplerecipes")
    fun getExampleRecipes(): ResponseEntity<List<Recipe>> {
        return recipeService.getExampleRecipes()
    }

    @PostMapping("/updaterecipeviews/{id}")
    fun postViews(@PathVariable(value = "id") id: Int, @RequestParam recipe: Map<String, String>) {
        recipeService.increaseViews(id)
    }

    @DeleteMapping("/recipes/{id}")
    fun deleteRecipe(@PathVariable(value = "id") id: Int) {
        return recipeService.deleteRecipe(id)
    }

    @GetMapping("/favouriterecipes")
    fun getAllFavouriteRecipes(@RequestParam parameters: Map<String, String>): ResponseEntity<List<Recipe>> {
        val user = parameters["user"]
        val category = parameters["cate"]
        val order = parameters["sort"]
        val existingUser = userService.getUser(user!!)
        val filteredRecipes = recipeService.filter(existingUser,category!!,order!!)
        return ResponseEntity.ok().body(recipeService.replaceRecipeImagesToBase64(filteredRecipes))
    }

    @GetMapping("/favouriteIds/{username}")
    fun getFavouriteRecipeIds(@PathVariable username: String): ResponseEntity<List<Recipe>> {
        return ResponseEntity.ok().body(userService.getUser(username).favouriteRecipes)
    }

    @PostMapping("/updatereciperating/{recipeId}/{username}/{vote}")
    fun updateRecipeRating(@PathVariable recipeId : Int, @PathVariable username : String, @PathVariable vote : Int) {
        recipeRatingService.rateRecipe(recipeId, username, vote)
    }

    @GetMapping("/myrecipes/{username}/{sortBy}")
    fun getMyRecipes(@PathVariable username : String, @PathVariable sortBy : String): ResponseEntity<List<Recipe>> {
        println(sortBy)
        return recipeService.getMyRecipes(username, sortBy)
    }

}