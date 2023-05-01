package de.hsflensburg.studentenkueche.methods


import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class ImageUploader {

    fun saveImage(uploadPath : String, filename : String, image : MultipartFile){
        val uploadPath : Path = Paths.get(uploadPath)

        if(uploadPath.toString() != uploadPath.toString() + "TEST"){
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath)
            }
            try {
                val inputStream : InputStream = image.inputStream
                val filePath : Path = uploadPath.resolve(filename)
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)
            } catch ( e : IOException) {
                throw IOException("Could not save image file: $filename", e)
            }
        }
    }
}