package com.link_sharing.project

import com.link_sharing.project.co.DocumentResourceCO

class DocumentResourceController {

    def fileUploadService

    def save(DocumentResourceCO documentResourceCO) {
        log.info "${documentResourceCO}"
        log.info "file name is ${documentResourceCO.document.getOriginalFilename()}"

        String[] fileFrags = documentResourceCO.document.getOriginalFilename().split("\\.")
        String extension = fileFrags[fileFrags.length-1]
        String fileName  = documentResourceCO.document.getOriginalFilename().substring(0,documentResourceCO.document.getOriginalFilename().length()-extension.length()-1)
        String finalFileName = fileName+'-'+session.user.id+"."+extension

        if( fileUploadService.uploadFile(documentResourceCO.document, finalFileName) ){
            DocumentResource documentResource = new DocumentResource(filePath: finalFileName,
                    description: documentResourceCO.description, topic: Topic.get(documentResourceCO.topic), createdBy: session.user)
            if (documentResource.validate()) {
                if (documentResource) {
                    List<User> subscribedUsers = documentResource.topic.subscribedUsers()
                    subscribedUsers.each {
                        if (it.id != session.user.id) {
                            documentResource.addToReadingItems(new ReadingItem(
                                    user: it,
                                    resource: documentResource,
                                    isRead: false)
                            )
                        } else{
                            documentResource.addToReadingItems(new ReadingItem(
                                    user: it,
                                    resource: documentResource,
                                    isRead: true)
                            )
                        }
                    }
                    documentResource.save(flush: true)
                    flash.message = "resource saved"
                } else {
                    flash.error = "resource not saved"
                }
            } else{
                flash.error = "Error occured"+documentResource.errors.allErrors.collect().join(", ")
            }
        } else {
            flash.error = "Error uploading File, please try again"
        }





        redirect(url: request.getHeader("referer"))
    }

}
