package com.link_sharing.project

import com.link_sharing.project.co.DocumentResourceCO
import com.link_sharing.project.constants.Constants
import com.link_sharing.project.utils.QueryUtils

class DocumentResourceController {

    def fileUploadService

    def save(DocumentResourceCO documentResourceCO) {
        log.info "${documentResourceCO}"
        log.info "file name is ${documentResourceCO.document.getOriginalFilename()}"

        String finalFileName = fileUploadService.getUniqueFileName(documentResourceCO.document)

        if( fileUploadService.uploadFile(documentResourceCO.document, finalFileName, Constants.LOC_DOCUMENT_RESOURCE) ){
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

    def download(Long id) {

        if(session.user) {
            DocumentResource documentResource = (DocumentResource) Resource.get(id)
            User user = session.user
            def file
            if (documentResource && QueryUtils.ifTopicCanbeViewdBy(documentResource.topic, user)) {
                def temp = new File(Constants.LOC_DOCUMENT_RESOURCE+"/"+documentResource.filePath)
                if (temp.exists()) {
                    file = temp
                } else {
                    file = null
                }
            }
            log.info "file is: $documentResource.filePath"
            log.info "file is: $file"
            if (file) {
                response.setHeader("Content-disposition", "attachment;filename=\"${documentResource.filePath}\"")
                response.outputStream << file.bytes
            } else {
                flash.error = "resource not found"
            }
        } else {
            flash.error = "please login to continue"
        }
        redirect(url: request.getHeader("referer"))
    }

}
