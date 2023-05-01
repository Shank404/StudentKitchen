package de.hsflensburg.studentenkueche

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

@SpringBootApplication
class StudentenKuecheApplication

fun main(args: Array<String>) {
	runApplication<StudentenKuecheApplication>(*args)
}
