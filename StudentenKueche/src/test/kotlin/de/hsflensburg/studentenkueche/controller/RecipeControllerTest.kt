package de.hsflensburg.studentenkueche.controller

import de.hsflensburg.studentenkueche.entity.Recipe
import de.hsflensburg.studentenkueche.service.RecipeRatingService
import de.hsflensburg.studentenkueche.service.RecipeService
import de.hsflensburg.studentenkueche.service.UserService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatcher
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

class RecipeControllerTest {

    @MockK
    lateinit var  recipeService: RecipeService
    @MockK
    lateinit var  userService: UserService
    @MockK
    lateinit var  recipeRatingService: RecipeRatingService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun postNewRecipe(){
        val mockRecipe = mockk<Recipe>()
        val multi = MockMultipartFile("images", ByteArray(1))

        every { recipeService.createRecipe(mapOf(), multi) } returns ResponseEntity.ok().body(mockRecipe)

        val result = recipeService.createRecipe(mapOf(), multi)
        assertEquals(ResponseEntity.ok().body(mockRecipe), result)
    }
}