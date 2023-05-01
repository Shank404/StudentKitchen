package de.hsflensburg.studentenkueche.repository

import de.hsflensburg.studentenkueche.entity.Customer
import de.hsflensburg.studentenkueche.entity.Recipe
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.domain.Sort

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class RecipeRepositoryTest{

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var recipeRepository: RecipeRepository

    var list: MutableList<Recipe> = mutableListOf()

    val path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\Images\\placeholder.jpg"

    @BeforeEach
    fun setup() {
        for (i in 1..5) {
            val recipe = Recipe(
                name = "Testcake-$i",
                ingredients = "Carrots, Testing-Spice, Flour",
                description = "Tasty testing-cake",
                price = 4.99 + i,
                duration = 35.0,
                difficulty = "Moderate",
                category = "cake-$i",
                images = "$path",
                preparation = "Mix it",
                author = "Mr.Test"
            )
            list.add(recipe)
            entityManager.persist(recipe)
        }
        entityManager.flush()
    }

    @AfterEach
    fun destruct() {
        list = mutableListOf()
    }

    @Test
    fun `findAll(sort=Sort)`() {
        sortReferenceListByDescending()
        val found = recipeRepository.findAll(Sort.by(Sort.Direction.DESC, "price"))
        Assertions.assertThat(found).isEqualTo(list)
    }
    @Test
    fun `findRecipesByCategory(category=String)`() {
        val found = recipeRepository.findRecipesByCategory("cake-1")
        val referenceList = mutableListOf(list[0])
        Assertions.assertThat(found).isEqualTo(referenceList)
    }
    @Test
    fun `findRecipesByCategory(category=String, sort=Sort)`() {
        val found = recipeRepository.findRecipesByCategory("cake-1",Sort.by(Sort.Direction.DESC, "price"))
        val referenceList = mutableListOf(list[0])
        Assertions.assertThat(found).isEqualTo(referenceList)
    }
    @Test
    fun `findRecipesById(id=Int)`() {
        val recipe = list[0]
        val found = recipeRepository.findRecipeById(recipe.id)
        Assertions.assertThat(found).isEqualTo(recipe)
    }
    @Test
    fun `findRecipesByLikesContainingAndCategory(user=Customer, category=String)`() {
        val testCustomer = createTestCustomer()
        val found = recipeRepository.findRecipesByLikesContainingAndCategory(testCustomer, "cake-1")
        val referenceList = mutableListOf(testCustomer.favouriteRecipes[0])
        Assertions.assertThat(found).isEqualTo(referenceList)
    }
    @Test
    fun `findRecipesByLikesContainingAndCategory(user=Customer, category=String, sort=Sort)`() {
        sortReferenceListByDescending()
        val testCustomer = createTestCustomer()
        val found = recipeRepository.findRecipesByLikesContainingAndCategory(testCustomer, "cake-1", Sort.by(Sort.Direction.DESC, "price"))
        val referenceList = mutableListOf(testCustomer.favouriteRecipes[4])
        Assertions.assertThat(found).isEqualTo(referenceList)
    }
    @Test
    fun `findRecipesByLikesContaining(user=Customer)`() {
        val testCustomer = createTestCustomer()
        val found = recipeRepository.findRecipesByLikesContaining(testCustomer)
        Assertions.assertThat(found).isEqualTo(list)
    }
    @Test
    fun `findRecipesByLikesContaining(user=Customer, sort=Sort)`() {
        sortReferenceListByDescending()
        val testCustomer = createTestCustomer()
        val found = recipeRepository.findRecipesByLikesContaining(testCustomer, Sort.by(Sort.Direction.DESC, "price"))
        Assertions.assertThat(found).isEqualTo(list)
    }
    @Test
    fun `findRecipesByAuthor(author=String)`() {
        val found = recipeRepository.findRecipesByAuthor(list[0].author)
        Assertions.assertThat(found).isEqualTo(list)
    }
    @Test
    fun `findRecipesByAuthor(author=String, order=Sort)`() {
        sortReferenceListByDescending()
        val found = recipeRepository.findRecipesByAuthor(list[0].author, Sort.by(Sort.Direction.DESC, "price"))
        Assertions.assertThat(found).isEqualTo(list)
    }

    fun sortReferenceListByDescending(){
        list.sortByDescending {
            it.price
        }
    }

    fun createTestCustomer() : Customer {
        val customer = Customer(
            name = "Hans Werner",
            username = "Hansi12",
            password = "SAFE",
            email = "hansi@hans.de",
            image = "Nix da",
            favouriteRecipes = list
        )
        entityManager.persistAndFlush(customer)
        return customer
    }
}