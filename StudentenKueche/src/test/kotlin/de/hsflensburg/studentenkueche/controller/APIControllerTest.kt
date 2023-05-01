package de.hsflensburg.studentenkueche.controller

import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class APIControllerTest {

    @Test
    fun apiLogin(@Autowired restTemplate : TestRestTemplate){
        var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        var json = JSONObject()
        json.put("username", "Nico420")
        json.put("password", "hanspwd")

        val request = HttpEntity(json.toString(), headers)
//        val username = restTemplate.postForEntity<Message>("/apiLogin", request)
//        assertEquals("Server reached!", username.body?.message)
    }

    @Test
    fun getRecipe(@Autowired restTemplate : TestRestTemplate){
        var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        var json = JSONObject()
        json.put("username", "Nico420")
        json.put("password", "hanspwd")

        val request = HttpEntity(json.toString(), headers)
//        val username = restTemplate.postForEntity<Message>("/apiLogin", request)
//        assertEquals("Server reached!", username.body?.message)
    }
}