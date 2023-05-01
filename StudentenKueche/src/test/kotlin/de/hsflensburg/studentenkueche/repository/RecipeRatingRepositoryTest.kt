package de.hsflensburg.studentenkueche.repository

import de.hsflensburg.studentenkueche.entity.Recipe
import de.hsflensburg.studentenkueche.entity.RecipeRating
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.domain.Sort

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class RecipeRatingRepositoryTest {

    @Autowired
    lateinit var entityManager: TestEntityManager
    @Autowired
    lateinit var recipeRatingRepository: RecipeRatingRepository

    @Test
    fun `findAllByRecipeId(id=Int)`() {
        var list : MutableList<RecipeRating> = mutableListOf()
        for(i in 1..5){
            val rating = RecipeRating(recipeId=120, userId=22+i, recipeRating=233+i)
            list.add(rating)
            entityManager.persistAndFlush(rating)
        }
        val found = recipeRatingRepository.findAllByRecipeId(120)
        Assertions.assertThat(found).isEqualTo(list)
    }

}