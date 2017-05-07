package com.link_sharing.project

class ResourceController {

    def index() { }

    def delete(Long id) {

        Resource resource = Resource.load(id)

        log.info("resource ${resource}")

        if(resource) {
            resource.delete(flush:true)
            render "success"
        } else {
            render "Resource not found."
        }
    }
}
