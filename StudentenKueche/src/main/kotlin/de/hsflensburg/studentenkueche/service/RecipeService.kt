package de.hsflensburg.studentenkueche.service
import de.hsflensburg.studentenkueche.entity.Customer
import de.hsflensburg.studentenkueche.entity.Recipe
import de.hsflensburg.studentenkueche.methods.Base64Converter
import de.hsflensburg.studentenkueche.methods.ImageUploader
import de.hsflensburg.studentenkueche.repository.RecipeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import kotlin.random.Random


@Service
class RecipeService{

    @Autowired
    lateinit var recipeRepository: RecipeRepository

    var uploadPath: String = "StudentenKueche/src/main/resources/static/Images/Recipes/"
    var base64Converter : Base64Converter = Base64Converter()
    var imageUploader : ImageUploader = ImageUploader()

    fun createRecipe(recipe : Map<String,String>, image : MultipartFile) : ResponseEntity<Recipe> {
        var ingredients : String = getIngredients(recipe)

        var img : String = StringUtils.cleanPath(image.originalFilename!!)
        val savedRecipe : Recipe = recipeRepository.save(
            Recipe( name = recipe["name"].toString(),
                description = recipe["description"].toString(),
                price = recipe["price"]?.toDouble()!!,
                duration = recipe["duration"]?.toDouble()!!,
                difficulty = recipe["difficulty"].toString(),
                category = recipe["category"]!!,
                ingredients = ingredients,
                images = img,
                preparation = recipe["preparation"].toString(),
                author = recipe["author"].toString()
            )
        )

        imageUploader.saveImage(uploadPath+savedRecipe.id, img, image)
        return ResponseEntity.ok().body(recipeRepository.save(savedRecipe))
    }

    //TODO Die Methode getAllRecipes() + filter() + getMyRecipes() eventuell Generalisieren (Higher Order Function)
    fun getAllRecipes(order: String, category: String) : List<Recipe>{
        if (order == ""){
            return if(category == ""){
                replaceRecipeImagesToBase64(recipeRepository.findAll())
            }else{
                replaceRecipeImagesToBase64(recipeRepository.findRecipesByCategory(category))
            }
        }else{
            var direction = Sort.Direction.ASC
            when(order){
                "duration" -> direction = Sort.Direction.ASC
                "price" -> direction = Sort.Direction.ASC
                "rating" -> direction = Sort.Direction.DESC
            }
            return if (category == ""){
                replaceRecipeImagesToBase64(recipeRepository.findAll(Sort.by(direction, order)))
            }else{
                replaceRecipeImagesToBase64(recipeRepository.findRecipesByCategory(category, Sort.by(direction, order)))
            }
        }
    }

    fun getExampleRecipes(): ResponseEntity<List<Recipe>>{
        val allRecipes = replaceRecipeImagesToBase64(recipeRepository.findAll())
        val randomRecipes : MutableList<Recipe> = mutableListOf()
        for (i in 1..3){
            randomRecipes.add(allRecipes.get(Random.nextInt(0, allRecipes.size - 1)))
        }
        return ResponseEntity.ok().body(randomRecipes)
    }

    fun filter(user: Customer, category: String, order: String): List<Recipe>{
        if (order == ""){
            return if(category == ""){
                replaceRecipeImagesToBase64(recipeRepository.findRecipesByLikesContaining(user))
            }else{
                replaceRecipeImagesToBase64(recipeRepository.findRecipesByLikesContainingAndCategory(user,category))
            }
        }else{
            var direction = Sort.Direction.ASC
            when(order){
                "duration" -> direction = Sort.Direction.ASC
                "price" -> direction = Sort.Direction.ASC
                "rating" -> direction = Sort.Direction.DESC
            }
            return if (category == ""){
                replaceRecipeImagesToBase64(recipeRepository.findRecipesByLikesContaining(user,Sort.by(direction, order)))
            }else{
                replaceRecipeImagesToBase64(recipeRepository.findRecipesByLikesContainingAndCategory(user,category, Sort.by(direction, order)))
            }
        }
    }
    fun replaceRecipeImagesToBase64(recipes : List<Recipe>) : List<Recipe> {
        var existingList : MutableList<Recipe> = mutableListOf()
        recipes.forEach{
            existingList.add(it.copy(images = base64Converter.convertImage(uploadPath, it.images, it.id)))
        }
        return existingList
    }

    fun increaseViews(id : Int){
        val oldViews = recipeRepository.findRecipeById(id).views
        val recipe = recipeRepository.findRecipeById(id).copy(
            views = oldViews +1
        )
        recipeRepository.save(recipe)
    }

    fun deleteRecipe(id : Int) {
        return recipeRepository.deleteById(id)
    }

    fun editRecipe(recipeId : Int, recipe: Map<String, String>, image : MultipartFile) : ResponseEntity<Recipe> {
        return recipeRepository.findById(recipeId).map { existingRecipe ->
            var ingredients : String = getIngredients(recipe)
            var img : String = existingRecipe.images
            if(!image.isEmpty){
                img = StringUtils.cleanPath(image.originalFilename!!)
                imageUploader.saveImage(uploadPath+existingRecipe.id, img, image)
            }
            val updatedRecipe: Recipe = existingRecipe
                .copy(  name = recipe["name"].toString(),
                    description = recipe["description"].toString(),
                    price = recipe["price"]!!.toDouble(),
                    duration = recipe["duration"]!!.toDouble(),
                    difficulty = recipe["difficulty"].toString(),
                    category = recipe["category"].toString(),
                    ingredients = ingredients,
                    images = img,
                    preparation = recipe["preparation"].toString()
                )
            ResponseEntity.ok().body(recipeRepository.save(updatedRecipe))
        }.orElse(ResponseEntity.notFound().build())
    }

    fun getRecipe(id : Int) : ResponseEntity<Recipe> {
        return recipeRepository.findById(id).map { existingRecipe ->
            ResponseEntity.ok(existingRecipe.copy(images = base64Converter.convertImage(uploadPath, existingRecipe.images,existingRecipe.id)))
        }.orElse(ResponseEntity.notFound().build())
    }


    fun getMyRecipes(username : String, order : String) : ResponseEntity<List<Recipe>> {
        var collection : List<Recipe>? = null
        if(order == "null"){
            collection = replaceRecipeImagesToBase64(recipeRepository.findRecipesByAuthor(username))
        } else {
            var direction = Sort.Direction.ASC
            when(order){
                "rating" -> direction = Sort.Direction.DESC
                "views" -> direction = Sort.Direction.DESC
            }
            collection = replaceRecipeImagesToBase64(recipeRepository.findRecipesByAuthor(username, Sort.by(direction, order)))
        }
        return ResponseEntity.ok().body(collection)
    }

    fun getIngredients(recipe : Map<String, String>) : String{
        var ingredients : String = ""
        for(i in 0 until recipe["ingAmount"]!!.toInt()){
            if(recipe["tableIngredient$i"].toString() != "" && recipe["tableAmount$i"].toString() != ""){
                ingredients += recipe["tableIngredient$i"].toString() + "," + recipe["tableAmount$i"].toString() + ","
            }
        }
        return ingredients
    }

}