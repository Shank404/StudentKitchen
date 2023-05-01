package de.hsflensburg.studentenkueche.controller

import de.hsflensburg.studentenkueche.entity.Customer
import de.hsflensburg.studentenkueche.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api")
class UserController {

    @Autowired
    lateinit var userService: UserService


    @PostMapping("/register")
    fun postRegister(@RequestParam user: Map<String, String>): ResponseEntity<Customer> {
        return userService.createUser(user)
    }

    @PostMapping("/login")
    fun postLogin(@RequestParam user: Map<String, String>): ResponseEntity<Customer> {
        println("login"+user)
        return userService.checkLoginCredentials(user)
    }

    @GetMapping("/user/{username}")
    fun getUser(@PathVariable(value = "username") username: String): ResponseEntity<Customer> {
        return ResponseEntity.ok().body(userService.getUser(username))
    }

    @PostMapping("/user/{username}")
    fun editUser(@PathVariable(value = "username") username: String, @RequestParam body: Map<String, String>, @RequestParam("images") image: MultipartFile): ResponseEntity<Customer> {
        return userService.editUser(username, body, image)
    }

    @PostMapping("/addfavouriterecipe/{username}/{recipeId}")
    fun addFavouriteRecipe(@PathVariable username : String, @PathVariable recipeId : Int) : ResponseEntity<Customer> {
        return userService.addFavourite(username, recipeId)
    }

    @PostMapping("/usersrecipesamount/{username}/{amount}")
    fun postAlterRecipesAmount(@PathVariable username : String, @PathVariable amount : Int) : ResponseEntity<Customer>{
        return userService.alterRecipeAmount(username, amount)
    }
}