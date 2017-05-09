package com.link_sharing.project

import com.link_sharing.project.constants.Constants
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(LoginController)
@Mock([User])
class LoginControllerSpec extends Specification {

    def "check login for success"() {
        setup:
        session['user'] = new User()

        when:
        controller.index()

        then:
        response.forwardedUrl == "/user/index"
    }

    def "check login for error"() {
        when:
        controller.index()

        then:
        response.status==404
    }

    def "check logout"() {
        given:
        session.user = new User()

        when:
        controller.logout()

        then:
        session.user == null
        response.redirectedUrl == "/"
    }


    def "Check loginHandler for valid user"() {
        setup:
        User user = new User(userName: "u1", password: Constants.PASSWORD_NORMAL,
                firstName: "user1", lastName: "user1LastName", email: 'u1@u1.com', active: true, admin: true, confirmPassword: Constants.PASSWORD_NORMAL)
        user.save(flush: true)

        when:
        controller.loginHandler(user.userName, user.password)

        then:
        response.redirectedUrl == '/'
    }

    def "check loginHandler for inactive user"() {
        setup:
        User user = new User(userName: "u1", password: Constants.PASSWORD_NORMAL,
                firstName: "user1", lastName: "user1LastName", email: 'u1@u1.com', active: false, confirmPassword: Constants.PASSWORD_NORMAL)
        user.save(flush: true)

        when:
        controller.loginHandler(user.userName, user.password)

        then:
        flash.error == "User is not active"
    }

    def "check loginHandler for invalid user"() {
        setup:
        User user = new User(userName: "u1", password: Constants.PASSWORD_NORMAL,
                firstName: "user1", lastName: "user1LastName", email: 'u1@u1.com', confirmPassword: Constants.PASSWORD_NORMAL)

        when:
        controller.loginHandler(user.userName, user.password)

        then:
        flash.error == "User not found"
    }

}
