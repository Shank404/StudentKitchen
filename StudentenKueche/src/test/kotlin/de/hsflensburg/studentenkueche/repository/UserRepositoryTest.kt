package de.hsflensburg.studentenkueche.repository

import de.hsflensburg.studentenkueche.entity.Customer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var userRepository: UserRepository

    private var user : Customer? = null

    @BeforeEach
    fun setup(){
        user = Customer(
            name = "Mr.Test",
            username = "TestItTillItBreaks",
            password = "qwerty",
            email = "test@testitut.de"
        )

        entityManager.persist(user)
        entityManager.flush()
    }

    @Test
    fun `When getUserByUsername then return User`() {
        val found = userRepository.getUserByUsername("TestItTillItBreaks")
        assertThat(found).isEqualTo(user)
    }

    @Test
    fun `When existsUserByUsername then return true`(){
        val found = userRepository.existsUserByUsername("TestItTillItBreaks")
        assertThat(found).isEqualTo(true)
    }

    @Test
    fun `When existsUserByUsername = null then return false`(){
        val found = userRepository.existsUserByUsername("NopeNeverExisted")
        assertThat(found).isEqualTo(false)
    }
}