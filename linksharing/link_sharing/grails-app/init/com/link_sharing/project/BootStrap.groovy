package com.link_sharing.project

import grails.config.Config

class BootStrap {

    def grailsApplication

    def init = { servletContext ->
        // Get configuration from GrailsApplication.
        final Config configuration = grailsApplication.config

        // Get the value for sample.config.
        final String sampleConfigValue = configuration.getProperty('grails.app.context')

        log.info("Value for sample.config configuration property = $sampleConfigValue")

        createUser()
    }
    def destroy = {
    }

    void createUser() {


        User normalUser = new User(userName: "user", firstName: "normal", lastName: "normalLastName", email: "user@ttn.com",
                password: "normal1", admin: false, active: false)
        User adminUser = new User(userName: "admin", firstName: "admin", lastName: "adminLastName", email: "admin@ttnd.com",
                password: "admin1", admin: true,active: true)

        Integer countUsers = User.count()

        if (!countUsers) {

            log.info "Creating new users "
            if (normalUser.save(flush:true,failOnError:true)) {
                log.info "${normalUser} saved"
            } else {
                log.error "${normalUser} cannot be saved--- ${normalUser.errors.allErrors}"
            }
            if (adminUser.save(flush:true,failOnError:true)) {
                log.info "${adminUser} saved"
            } else {
                log.error "${adminUser} cannot be saved--- ${adminUser.errors.allErrors}"
            }
        } else {
            log.info "Users exists in the system "
        }



    }
}
