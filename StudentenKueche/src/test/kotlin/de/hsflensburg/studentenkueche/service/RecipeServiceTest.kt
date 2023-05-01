package de.hsflensburg.studentenkueche.service

import de.hsflensburg.studentenkueche.entity.Recipe
import de.hsflensburg.studentenkueche.repository.RecipeRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.util.AssertionErrors.assertEquals
import org.springframework.web.multipart.MultipartFile
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecipeServiceTest {

    @RelaxedMockK
    lateinit var recipeRepository: RecipeRepository

    @InjectMockKs
    lateinit var recipeService: RecipeService

    private var list: MutableList<Recipe> = mutableListOf()

    @BeforeAll
    fun setUp() {
        MockKAnnotations.init(this)
        for (i in 1..5) {
            val recipe = Recipe(
                name = "Testcake-$i",
                ingredients = "Carrots, Testing-Spice, Flour",
                description = "Tasty testing-cake",
                price = 4.99 + i,
                duration = 35.0,
                difficulty = "Moderate",
                category = "cake-$i",
                preparation = "Mix it",
                author = "Mr.Test"
            )
            list.add(recipe)
        }
    }

    @Test
    fun `editRecipe(recipeId=Int, recipe=Map, image=MultipartFile)`() {
//        val recipe = list[0]
//        val updatedRecipe = list[0].copy(name = "Hans Werner")
//        val map = mutableMapOf<String, String>()
//        val optional = Optional.of(recipe)
//        val reference = ResponseEntity.ok().body(updatedRecipe)
//        val image = MockMultipartFile("images", ByteArray(1))
//
//        map["name"] = updatedRecipe.name
////        map["description"] = updatedRecipe.description
////        map["price"] = updatedRecipe.price.toString()
////        map["duration"] = updatedRecipe.duration.toString()
////        map["difficulty"] = updatedRecipe.price.toString()
//
//        every { recipeRepository.findById(recipe.id) } returns optional
//        every { recipeRepository.save(updatedRecipe) } returns updatedRecipe
//
//        val result = recipeService.editRecipe(recipe.id, map, image)
//
//        verify(exactly = 1) { recipeRepository.findById(recipe.id) }
//        assertEquals("", reference, result)
    }

    @Test
    fun `getRecipe(id=Int)`() {
        val recipe = list[0]
        val optional = Optional.of(recipe)
        val reference = ResponseEntity.ok().body(recipe)

        every { recipeRepository.findById(recipe.id) } returns optional

        val result = recipeService.getRecipe(recipe.id)

        verify(exactly = 1) { recipeRepository.findById(recipe.id) }
        assertEquals("", reference, result)
    }

    @Test
    fun `getMyRecipes(username=String, order=String)`() {
        every { recipeRepository.findRecipesByAuthor("Mr.Test") } returns list

        val result = recipeService.getMyRecipes("Mr.Test", "null")

        verify(exactly = 1) { recipeRepository.findRecipesByAuthor("Mr.Test") }
        assertEquals("Not the same!", list, result)
    }

    @Test
    fun `getIngredients(recipe=Map)`() {
        val map = mutableMapOf<String, String>()
        map["ingAmount"] = "6"
        for (i in 0..5) {
            map["tableIngredient${i}"] = "Zutat${i}"
            map["tableAmount${i}"] = "${i}-mal"
        }
        val result = recipeService.getIngredients(map)
        assertEquals("", "Zutat0,0-mal,Zutat1,1-mal,Zutat2,2-mal,Zutat3,3-mal,Zutat4,4-mal,Zutat5,5-mal,", result)
    }
}