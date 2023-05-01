package de.hsflensburg.studentenkueche.controller

import de.hsflensburg.studentenkueche.entity.Recipe
import de.hsflensburg.studentenkueche.methods.Ingredient
import de.hsflensburg.studentenkueche.service.RecipeService
import de.hsflensburg.studentenkueche.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class HTMLController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var recipeService: RecipeService

    @GetMapping("/")
    fun getIndex(model: Model): String{
        //var recipes = recipeService.getExampleRecipes().body
        //model.addAttribute("recipes", recipes)
        return "/HTML/index"
    }

    @GetMapping("/login")
    fun getLogin(): String{
        return "HTML/login.html"
    }

    @GetMapping("/logout")
    fun logout(model : Model) : String {
        var recipes = recipeService.getExampleRecipes().body
        model.addAttribute("recipes", recipes)
        return "/HTML/index"
    }

    @GetMapping("/register")
    fun getRegister(): String{
        return "/HTML/register.html"
    }

    @GetMapping("/favorites/{username}/{sortBy}/{category}")
    fun getFavorites(@PathVariable username : String, @PathVariable sortBy : String, @PathVariable category : String, model : Model): String{
        var existingUser = userService.getUser(username)
        var filteredRecipes = listOf<Recipe>()

        if(sortBy != "noSort" && category != "noCategory"){
            filteredRecipes = recipeService.filter(existingUser!!,category!!,sortBy!!)
        } else if (sortBy != "noSort"){
            filteredRecipes = recipeService.filter(existingUser!!,"",sortBy!!)
        } else if (category != "noCategory"){
            filteredRecipes = recipeService.filter(existingUser!!,category!!,"")
        } else {
            filteredRecipes = recipeService.filter(existingUser!!,"","")
        }
        model.addAttribute("recipes", filteredRecipes)
        model.addAttribute("currentSort", sortBy)
        model.addAttribute("currentCategory", category)
        return "/HTML/favorites"
    }

    @GetMapping("/myrecipes/{username}/{sortBy}")
    fun getMyRecipes(@PathVariable username : String, @PathVariable sortBy : String, model : Model): String{
        var myRecipes = listOf<Recipe>()
        if(sortBy != "noSort"){
            myRecipes = recipeService.getMyRecipes(username, sortBy).body!!
        } else {
            myRecipes = recipeService.getMyRecipes(username, "null").body!!
        }
        model.addAttribute("myRecipes", myRecipes)
        model.addAttribute("currentSort", sortBy)
        return "/HTML/myrecipes"
    }

    @GetMapping("/newrecipe")
    fun getNewRecipe(): String{
        return "/HTML/newrecipe.html"
    }

    @GetMapping("/profile/{username}")
    fun getProfile(@PathVariable username : String, model : Model): String{
        var user = userService.getUser(username)
        model.addAttribute("user", user)
        return "/HTML/profile"
    }

    @GetMapping("/recipes/{sortBy}/{category}")
    fun getRecipes(@PathVariable sortBy : String, @PathVariable category : String, model : Model): String{
        var recipes = listOf<Recipe>()
        if(sortBy != "noSort" && category != "noCategory"){
            recipes = recipeService.getAllRecipes(sortBy, category)
        } else if (sortBy != "noSort"){
            recipes = recipeService.getAllRecipes(sortBy, "")
        } else if (category != "noCategory"){
            recipes = recipeService.getAllRecipes("", category)
        } else {
            recipes = recipeService.getAllRecipes("", "")
        }
        model.addAttribute("recipes", recipes)
        model.addAttribute("currentSort", sortBy)
        model.addAttribute("currentCategory", category)
        return "/HTML/recipes"
    }

    @GetMapping("/detailrecipe/{id}")
    fun getDetailRecipe(@PathVariable id : Int, model : Model): String{
        var recipe = recipeService.getRecipe(id).body
        var ingredientsList : MutableList<Ingredient> = mutableListOf()
        var ingSplit = recipe?.ingredients?.split(",")
        for(i in 0 until (ingSplit!!.size-1) step 2){
            ingredientsList.add(Ingredient(ingSplit[i], ingSplit[i+1]))
        }
        model.addAttribute("recipe", recipe)
        model.addAttribute("ingredients", ingredientsList)
        return "/HTML/detailrecipe"
    }

    /*
    @GetMapping("/recipedetail")
    fun getRecipeDetail(): String{
        return "/HTML/recipedetail"
    }*/

    @GetMapping("/editprofile/{username}")
    fun getEditProfile(@PathVariable username : String, model : Model): String{
        var user = userService.getUser(username)
        model.addAttribute("user", user)
        return "/HTML/editprofile"
    }

    @GetMapping("/editrecipe/{id}")
    fun getEditRecipe(@PathVariable id : Int, model : Model): String {
        var recipe = recipeService.getRecipe(id).body
        var ingredientsList : MutableList<Ingredient> = mutableListOf()
        var ingSplit = recipe?.ingredients?.split(",")
        for(i in 0 until (ingSplit!!.size-1) step 2){
            ingredientsList.add(Ingredient(ingSplit[i], ingSplit[i+1]))
        }
        model.addAttribute("recipe", recipe)
        model.addAttribute("ingredients", ingredientsList)
        return "/HTML/editrecipe"
    }
}