package de.hsflensburg.studentenkueche.service

import de.hsflensburg.studentenkueche.entity.Recipe
import de.hsflensburg.studentenkueche.methods.Base64Converter
import de.hsflensburg.studentenkueche.methods.ImageUploader
import de.hsflensburg.studentenkueche.entity.Customer
import de.hsflensburg.studentenkueche.repository.RecipeRepository
import de.hsflensburg.studentenkueche.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.net.URI

@Service
class UserService{

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var recipeRepository: RecipeRepository

    var base64Converter : Base64Converter = Base64Converter()
    val uploadPath: String = "StudentenKueche/src/main/resources/static/Images/Users/"
    var imageUploader : ImageUploader = ImageUploader()

    fun existUser(username : String) : Boolean{
        return userRepository.existsUserByUsername(username)
    }

    fun getUser(username : String) : Customer {
        var existingUser = userRepository.getUserByUsername(username)
        if(existingUser.image == ""){
            return existingUser
        }
        return existingUser.copy(image = base64Converter.convertImage(uploadPath, existingUser.image, existingUser.id))
    }

    fun createUser(user : Map<String, String>) : ResponseEntity<Customer> {
        var response : ResponseEntity<Customer>  = ResponseEntity.badRequest().body(Customer(username = "ERROR"))
        if (!existUser(user["username"]!!)){
            val savedUser = userRepository.save(Customer(username = user["username"]!!, password = user["password"]!!))
            response = ResponseEntity.created(URI("/createUser")).body(savedUser)
        }
        return response
    }
    fun checkLoginCredentials(@RequestParam user : Map<String, String>) : ResponseEntity<Customer> {
        if(existUser(user["username"]!!)){
            val fetchedUser : Customer = getUser(user["username"]!!)
            if (user["password"] == fetchedUser.password){
                return ResponseEntity.ok().body(fetchedUser)
            }
        }
        return ResponseEntity.badRequest().body(Customer(username = "ERROR"))
    }

    fun editUser(username : String, body : Map<String, String>, image : MultipartFile) : ResponseEntity<Customer>{
        return userRepository.findById(getUser(username).id).map { existingUser ->
            var img : String = existingUser.image
            if(!image.isEmpty){
                img = StringUtils.cleanPath(image.originalFilename!!)
                imageUploader.saveImage(uploadPath+existingUser.id, img, image)
            }

            val updatedUser: Customer = existingUser
                .copy(  name = body["name"]!!,
                    username = body["username"]!!,
                    email = body["email"]!!,
                    password = body["password"]!!,
                    image = img
                )
            ResponseEntity.ok().body(userRepository.save(updatedUser))
        }.orElse(ResponseEntity.notFound().build())
    }

    fun addFavourite(username : String, recipeId : Int) : ResponseEntity<Customer>{
        val existingUser = userRepository.getUserByUsername(username)
        val recipe = recipeRepository.findRecipeById(recipeId)

        val existingFavouriteRecipes : MutableList<Recipe> = existingUser.favouriteRecipes
        if(existingFavouriteRecipes.contains(recipe)){
            existingFavouriteRecipes.remove(recipe)
        } else {
            existingFavouriteRecipes.add(recipe)
        }
        val updatedUser : Customer = existingUser.copy(favouriteRecipes = existingFavouriteRecipes)

        return ResponseEntity.ok().body(userRepository.save(updatedUser))
    }

    fun alterRecipeAmount(username : String, number : Int): ResponseEntity<Customer> {
        val existingUser = userRepository.getUserByUsername(username)
        val updatedUser : Customer = existingUser.copy(amountRecipes = existingUser.amountRecipes + number)
        return ResponseEntity.ok().body(userRepository.save(updatedUser))
    }

}