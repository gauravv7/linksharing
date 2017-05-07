package com.link_sharing.project

import com.link_sharing.project.constants.Constants
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UserController)
@Mock([User])
class UserControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    def "check index"() {
        setup:
        User user = new User(userName: "u1", password: Constants.PASSWORD_NORMAL,
                firstName: "user1", lastName: "user1LastName", email: 'u1@u1.com', active: true, admin: true, confirmPassword: Constants.PASSWORD_NORMAL)
        user.save(flush: true)
        session.user = user

        when:
        controller.index()

        then:
        response.text == "user dashboard for user u1"
    }
}
