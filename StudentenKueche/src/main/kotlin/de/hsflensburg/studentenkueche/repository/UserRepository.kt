package de.hsflensburg.studentenkueche.repository

import de.hsflensburg.studentenkueche.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<Customer, Int> {

    fun existsUserByUsername(username: String): Boolean
    fun getUserByUsername(username: String): Customer
    // TODO BESPRECHEN: getUserByUsernameOrNull wäre hier besser für die Tests!

}