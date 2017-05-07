package com.link_sharing.project

import com.link_sharing.project.constants.Constants
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(TopicController)
@Mock([User, Topic, Subscription])
class TopicControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    // not working due to sessionHandle cannot find session bean, might be grails 3 bug
    def "check Show topic"() {
        setup:
        User user = new User(userName: "u1", active: true, password: Constants.PASSWORD_NORMAL, firstName: "user1", lastName: "user1Lastname", email: "u1@u1.com", confirmPassword: Constants.PASSWORD_NORMAL)
        user.save(flush: true)

        print user.errors.allErrors.collect().join(', ')
        Topic topic = new Topic(topicName: 'groovy', visibility: visible, createdBy: user)
        session.user = user
        topic.save(flush: true)
        print topic.errors.allErrors.collect().join(', ')

        when:
        println("topic id :" + topic.id)
        controller.show(topic.id)

        then:
        response.text == result

        where:
        visible            | result
        Visibility.PUBLIC  | "success public"
        Visibility.PRIVATE | "success private"
    }

    def "Check show topic for private topic for which user is not subscribed"() {
        setup:
        User user = new User(userName: "u1", active: true, password: Constants.PASSWORD_NORMAL, firstName: "user1", lastName: "user1LastName", email: "u1@u1.com", confirmPassword: Constants.PASSWORD_NORMAL)
        user.save(flush: true)

        User user2 = new User(userName: "u2", active: true, password: Constants.PASSWORD_NORMAL, firstName: "user2", lastName: "user2Lname", email: "u2@u2.com", confirmPassword: Constants.PASSWORD_NORMAL)
        user2.save(flush: true)

        Topic topic = new Topic(topicName: 'groovy', visibility: Visibility.PRIVATE, createdBy: user)
        topic.save(flush: true)
        controller.session.user = user2

        when:
        controller.show(topic.id)

        then:
        response.redirectedUrl == "/"
    }
}
