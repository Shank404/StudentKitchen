package de.hsflensburg.studentenkueche.methods

import java.io.File
import java.util.*

class Base64Converter {

    //Konvertiert das angegebene Image in ein Base64 String und gibt diesen zurück
    fun convertImage(uploadPath : String, imageName : String, id : Int) : String {
        if(!imageName.isEmpty()){
            var fileContent: ByteArray = File("$uploadPath$id/$imageName").readBytes()
            return Base64.getEncoder().encodeToString(fileContent)
        }
        return ""
    }
}