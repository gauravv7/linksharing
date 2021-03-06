package com.link_sharing.project

import com.link_sharing.project.constants.Constants
import grails.config.Config
import org.springframework.mock.web.MockMultipartFile

class BootStrap {

    def grailsApplication
    def assetResourceLocator
    def fileUploadService

    def init = { servletContext ->
        // Get configuration from GrailsApplication.
        final Config configuration = grailsApplication.config

        // Get the value for sample.config.
        final String sampleConfigValue = configuration.getProperty('grails.app.context')

        log.info("Value for sample.config configuration property = $sampleConfigValue")

        createUser()
        createTopics()
        createResources()
        subscribeTopics()
        createReadingItems()
        createResourceRatings()
    }
    def destroy = {
    }

    void createUser() {

        User normalUser = new User(userName: "user", firstName: "normal", lastName: "normalLastName", email: "user@ttn.com",
                password: Constants.PASSWORD_NORMAL, confirmPassword: Constants.PASSWORD_NORMAL, admin: false, active: true)
        User adminUser = new User(userName: "admin", firstName: "admin", lastName: "adminLastName", email: "admin@ttnd.com",
                password: Constants.PASSWORD_ADMIN, confirmPassword: Constants.PASSWORD_ADMIN, admin: true,active: true)

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

    void createTopics(){
        if(Topic.count==0){
            User.list().each{
                user-> (1..5).each {
                    it ->
                        new Topic(topicName: "t$user.id-$it", createdBy: user, visibility: Visibility.PUBLIC).save()
                }
            }
        }
    }

    void createResources() {

        Topic.list().each { Topic topic ->
            Integer countResources = Resource.countByTopic(topic)
            MockMultipartFile document = new MockMultipartFile("test", new FileInputStream('grails-app/assets/resources/test.pdf'))
            String finalFileName = Constants.DEFAULT_DOCUMENT

            fileUploadService.uploadFile(document, finalFileName, Constants.LOC_DOCUMENT_RESOURCE)
            if (!countResources) {
                2.times {
                    Resource documentResource = new DocumentResource(description: "topic ${topic} doc", createdBy: topic
                            .createdBy, filePath: Constants.DEFAULT_DOCUMENT, topic: topic)

                    Resource linkResource = new LinkResource(description: "topic ${topic} link", createdBy: topic
                            .createdBy, url: "https://www.google.co.in", topic: topic)

                    if (documentResource.save(flush:true)) {
                        log.info "${documentResource} saved by ${topic.createdBy} for ${topic}"
                        topic.addToResources(documentResource)
                        topic.createdBy.addToResources(documentResource)
                    } else {
                        log.error "${documentResource} not saved--- ${documentResource.errors.allErrors}"
                    }

                    if (linkResource.save(flush:true)) {
                        log.info "${linkResource} saved by ${topic.createdBy} for ${topic}"
                        topic.addToResources(linkResource)
                        topic.createdBy.addToResources(linkResource)
                    } else {
                        log.error "${linkResource} not saved--- ${linkResource.errors.allErrors}"
                    }
                } // 2.times

            } // countResources
        } // topics each
    }

    void subscribeTopics() {

        User.list().each { User user ->
            Topic.list().each { Topic topic ->

                if (Subscription.findByCreatedByAndTopic(user, topic) == null) {
                    Subscription subscription = new Subscription(createdBy: user, topic: topic, seriousness: Constants.SERIOUSNESS)

                    if (subscription.save(flush:true)) {
                        log.info "${subscription}-> ${user} subscribed for ${topic}"
                        topic.addToSubscriptions(subscription)
                        user.addToSubscriptions(subscription)
                    } else {
                        log.error "Subscription failed: ${subscription.errors.allErrors}"
                    }
                } else {
                    log.info "User ${user} already subscribed to Topic ${topic}"
                }
            } //topic list
        } // user list
    }

    void createReadingItems() {

        User.list().each { User user ->
            Topic.list().each { Topic topic ->

                if (Subscription.findByCreatedByAndTopic(user, topic)) {
                    topic.resources.each { resource ->
                        if (resource.createdBy != user && !user.readingItems?.contains(resource))
                        {
                            ReadingItem readingItem = new ReadingItem(user: user, resource: resource, isRead: false)
                            if (readingItem.save(flush:true))
                            {
                                log.info "${readingItem} saved in ${user}'s list"

                                resource.addToReadingItems(readingItem)
                                user.addToReadingItems(readingItem)
                            }
                            else {
                                log.error "${readingItem} is not saved in ${user}'s list--- ${readingItem.errors.allErrors}"
                            }
                        }
                        else
                        {
                            ReadingItem readingItem = new ReadingItem(user: user, resource: resource, isRead: true)
                            if (readingItem.save(flush:true))
                            {
                                log.info "${readingItem} saved in ${user}'s list"

                                resource.addToReadingItems(readingItem)
                                user.addToReadingItems(readingItem)
                            }
                            else {
                                log.error "${readingItem} is not saved in ${user}'s list--- ${readingItem.errors.allErrors}"
                            }
                        }
                    } // topic.resources
                } // if subscription
            } // topic list
        } // user list
    }

    void createResourceRatings() {

        User.list().each { User user ->
            user.readingItems?.each { ReadingItem readingItem ->

                if (!readingItem.isRead) {
                    ResourceRating resourceRating = new ResourceRating(score: Constants.RATING, createdBy: readingItem.user,
                            resource: readingItem.resource)

                    if (resourceRating.save(flush:true)) {
                        log.info "${resourceRating} rating for ${readingItem.resource} by ${readingItem.user}"
                        readingItem.resource.addToRatings(resourceRating)
                    } else {
                        log.error "${resourceRating} rating not set for ${readingItem.resource} by ${readingItem.user} " +
                                " ${resourceRating.errors.allErrors}"
                    }
                } else {
                    log.info "${readingItem.user} cannot rate"
                }
            } // user.readingItems
        } // user list
    }
}
