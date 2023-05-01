package de.hsflensburg.studentenkueche.controller

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.io.BufferedReader
import java.io.File

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HTMLControllerTest {

    // Testet ob die HTML-Dokumente ausgeliefert werden !
    // Es wird das gefetchte HTML-Dokument mit dem original HTML-Dokument verglichen.
    // Zusätzlich wird der Status-Code überprüft.

    val path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\HTML\\"

    @Test
    fun getIndex(@Autowired restTemplate: TestRestTemplate) {
        val response : ResponseEntity<String> = restTemplate.getForEntity<String>("/")
        val bufferedReader: BufferedReader = File(path + "index.html").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(inputString, response.body)
    }

    @Test
    fun getLogin(@Autowired restTemplate: TestRestTemplate) {
        val response : ResponseEntity<String> = restTemplate.getForEntity<String>("/login")
        val bufferedReader: BufferedReader = File(path + "login.html").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(inputString, response.body)
    }

    @Test
    fun getLogout(@Autowired restTemplate: TestRestTemplate) {
        val response : ResponseEntity<String> = restTemplate.getForEntity<String>("/logout")
        val bufferedReader: BufferedReader = File(path + "index.html").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(inputString, response.body)
    }

    @Test
    fun getRegister(@Autowired restTemplate: TestRestTemplate) {
        val response : ResponseEntity<String> = restTemplate.getForEntity<String>("/register")
        val bufferedReader: BufferedReader = File(path + "register.html").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(inputString, response.body)
    }

    @Test
    fun getFavorites(@Autowired restTemplate: TestRestTemplate) {
        val response : ResponseEntity<String> = restTemplate.getForEntity<String>("/favorites")
        val bufferedReader: BufferedReader = File(path + "favorites.html").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(inputString, response.body)
    }

    @Test
    fun getMyRecipes(@Autowired restTemplate: TestRestTemplate) {
        val response : ResponseEntity<String> = restTemplate.getForEntity<String>("/myrecipes")
        val bufferedReader: BufferedReader = File(path + "myrecipes.html").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(inputString, response.body)
    }

    @Test
    fun getNewRecipe(@Autowired restTemplate: TestRestTemplate) {
        val response : ResponseEntity<String> = restTemplate.getForEntity<String>("/newrecipe")
        val bufferedReader: BufferedReader = File(path + "newrecipe.html").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(inputString, response.body)
    }

    @Test
    fun getProfile(@Autowired restTemplate: TestRestTemplate) {
        val response : ResponseEntity<String> = restTemplate.getForEntity<String>("/profile")
        val bufferedReader: BufferedReader = File(path + "profile.html").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(inputString, response.body)
    }

    @Test
    fun getRecipes(@Autowired restTemplate: TestRestTemplate) {
        val response : ResponseEntity<String> = restTemplate.getForEntity<String>("/recipes")
        val bufferedReader: BufferedReader = File(path + "recipes.html").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(inputString, response.body)
    }

    @Test
    fun getDetailRecipes(@Autowired restTemplate: TestRestTemplate) {
        val response : ResponseEntity<String> = restTemplate.getForEntity<String>("/detailrecipe")
        val bufferedReader: BufferedReader = File(path + "detailrecipe.html").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(inputString, response.body)
    }

    @Test
    fun getRecipeDetail(@Autowired restTemplate: TestRestTemplate) {
        val response : ResponseEntity<String> = restTemplate.getForEntity<String>("/recipedetail")
        val bufferedReader: BufferedReader = File(path + "recipedetail.html").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(inputString, response.body)
    }

    @Test
    fun getEditProfile(@Autowired restTemplate: TestRestTemplate) {
        val response : ResponseEntity<String> = restTemplate.getForEntity<String>("/editprofile")
        val bufferedReader: BufferedReader = File(path + "editprofile.html").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(inputString, response.body)
    }

    @Test
    fun getEditRecipe(@Autowired restTemplate: TestRestTemplate) {
        val response : ResponseEntity<String> = restTemplate.getForEntity<String>("/editrecipe")
        val bufferedReader: BufferedReader = File(path + "editrecipe.html").bufferedReader()
        val inputString = bufferedReader.use { it.readText() }

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(inputString, response.body)
    }
}