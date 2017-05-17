package com.link_sharing.project

import com.link_sharing.project.utils.QueryUtils
import grails.transaction.Transactional
import grails.util.Environment
import grails.web.context.ServletContextHolder
import org.springframework.web.multipart.MultipartFile

@Transactional
class FileUploadService {

    def grailsApplication

    def String uploadFile(MultipartFile file, String name, String dir) {
        String storagePath = ""
        if (Environment.current == Environment.PRODUCTION) {
            storagePath = dir
        } else {
//            def servletContext = ServletContextHolder.servletContext
            storagePath = dir
        }
        log.info storagePath
        // Create storage path directory if it does not exist
        def storagePathDirectory = new File(storagePath)
        if (!storagePathDirectory.exists()) {
            print "CREATING DIRECTORY ${storagePath}: "
            if (storagePathDirectory.mkdirs()) {
                println "SUCCESS"
            } else {
                println "FAILED"
            }
        }

        // Store file
        if (!file.isEmpty()) {
            file.transferTo(new File("${storagePath}/${name}"))
            println "Saved file: ${storagePath}/${name}"
            return "${storagePath}/${name}"
        } else {
            println "File ${file.inspect()} was empty!"
            return null
        }
    }

    String getUniqueFileName(MultipartFile file){
        String[] fileFrags = file.getOriginalFilename().split("\\.")
        String extension = fileFrags[fileFrags.length-1]
        String fileName  = file.getOriginalFilename().substring(0,file.getOriginalFilename().length()-extension.length()-1)
        String finalFileName = fileName+'-'+QueryUtils.uniqueFileName().toString()+"."+extension
        return finalFileName
    }
}
