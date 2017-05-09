package com.link_sharing.project

import com.link_sharing.project.constants.Constants
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification


@TestFor(SubscriptionController)
@Mock([Topic, User, Subscription])
class SubscriptionControllerSpec extends Specification {


    def "test save when topic not found"() {

        when:
        controller.save(1.toLong())

        then:
        flash.error == "Topic Does not Exist"
    }

    def "test save when topic found"() {
        given:
        User user = new User(firstName: "user", lastName: "userLastName", userName: "u0",
                email: "u0@u0.com", password: Constants.PASSWORD_NORMAL,
                admin: false, active: true, photo: "u0".bytes,
                confirmPassword: Constants.PASSWORD_NORMAL)
        user.save(flush: true)

        User user1 = new User(firstName: "user1", lastName: "user1LastName", userName: "u1",
                email: "u1@u1.com", password: Constants.PASSWORD_NORMAL,
                admin: false, active: true, photo: "u1".bytes,
                confirmPassword: Constants.PASSWORD_NORMAL)
        session.user = user1
        Topic topic = new Topic(topicName: "topic1", visibility: Visibility.PUBLIC, createdBy: user)
        user.addToTopics(topic)
        topic.save(flush: true)

        when:
        controller.save(topic.id)
        then:
        flash.message == "Subscription saved successfully"
    }

    def "check update subscription when user and topic doesnot match"() {

        given:
        User user = new User(firstName: "user", lastName: "userLastName", userName: "u0",
                email: "u0@u0.com", password: Constants.PASSWORD_NORMAL,
                admin: false, active: true, photo: "u0".bytes,
                confirmPassword: Constants.PASSWORD_NORMAL)
        user.save(flush: true)
        Topic topic = new Topic(topicName: "topic1", visibility: Visibility.PUBLIC, createdBy: user)
        topic.save(flush: true)

        User user1 = new User(firstName: "user1", lastName: "user1LastName", userName: "u1",
                email: "u1@u1.com", password: Constants.PASSWORD_NORMAL,
                admin: false, active: true, photo: "u1".bytes,
                confirmPassword: Constants.PASSWORD_NORMAL)
        session['user'] = user1

        when:
        controller.update(topic.id, "SERIOUS")

        then:
        flash.error.toLowerCase() == "subscription not found"
    }

    def "check update subscription when user and topic match in"() {
        given:

        User user = new User(firstName: "user", lastName: "userLastName", userName: "u0",
                email: "u0@u0.com", password: Constants.PASSWORD_NORMAL,
                admin: false, active: true, photo: "u0".bytes,
                confirmPassword: Constants.PASSWORD_NORMAL)
        user.save(flush: true)
        Topic topic = new Topic(topicName: "topic1", visibility: Visibility.PUBLIC, createdBy: user)
        topic.save(flush: true)
        session['user'] = user

        when:
        controller.update(topic.id, "SERIOUS")

        then:
        flash.message.toLowerCase() == "subscription saved successfully"
    }

    def "check delete subscription when topic and user does not match in subscription"() {
        given:
        User user = new User(firstName: "user", lastName: "userLastName", userName: "u0",
                email: "u0@u0.com", password: Constants.PASSWORD_NORMAL,
                admin: false, active: true, photo: "u0".bytes,
                confirmPassword: Constants.PASSWORD_NORMAL)
        user.save(flush: true)
        Topic topic = new Topic(topicName: "topic1", visibility: Visibility.PUBLIC, createdBy: user)
        topic.save(flush: true)
        User user1 = new User(firstName: "user1", lastName: "user1LastName", userName: "u1",
                email: "u1@u1.com", password: Constants.PASSWORD_NORMAL,
                admin: false, active: true, photo: "u1".bytes,
                confirmPassword: Constants.PASSWORD_NORMAL)
        session.user = user1

        when:
        controller.delete(topic.id)

        then:
        flash.error == "Subscription not found"
    }

    def "check delete subscription when topic and user does match in subscription"() {
        given:
        User user = new User(firstName: "user1", lastName: "user1LastName", userName: "u1",
                email: "u1@u1.com", password: Constants.PASSWORD_NORMAL,
                admin: false, active: true, photo: "u1".bytes,
                confirmPassword: Constants.PASSWORD_NORMAL)
        user.save(flush: true)
        Topic topic = new Topic(topicName: "topic1", visibility: Visibility.PUBLIC, createdBy: user)
        topic.save(flush: true)
        session['user'] = user

        when:
        controller.delete(topic.id)

        then:
        flash.message == "Subscription deleted"
    }
}
