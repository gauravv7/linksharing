package com.link_sharing.project

import com.link_sharing.project.constants.Constants
import grails.test.mixin.TestFor
import spock.lang.Specification
import com.link_sharing.project.User as MyUser
import spock.lang.Unroll

import java.awt.Container

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(MyUser)
class UserSpec extends Specification {

    void "test one"(){
        expect:
        true
    }

    @Unroll("Executing #sno")
    void "testUserValidations"() {
        setup:
        MyUser user = new MyUser(email: email, userName: userName, password: password, firstName: fname, lastName: lname, photo: photo,
                admin: admin, active: active, confirmPassword: password)
        when:
        Boolean result = user.validate()

        then:
        result == valid
        where:
        sno | email               | userName    | password | fname     | lname     | photo | admin  | active  | valid
        1   | "u1"                |  "u1"       | ""       | null      | ""        | null  | true   | false   | false
        1   | "u2@gmail.com"      |  "u2"       | "1234"   | ""        | "xyz"     | null  | null   | null    | false
        1   | "u3@gmail.com"      |  "u3"       | "098765" | "user3"   | "acb"     | null  | true   | true    | true
    }


    def "EmailAddressAndUserNameOfUserShouldBeUnique"() {
        setup:
        String email = "u1@gmail.com"
        String userName = "u1"
        String password = 'password'
        MyUser user = new MyUser(email:email, userName: userName, password: Constants.PASSWORD_NORMAL, firstName: "user1", lastName: "user1LastName", photo: null,
                admin: true, active: true, confirmPassword: Constants.PASSWORD_NORMAL )

        when:
        user.save()

        then:
        user.count() == 1

        when:
        MyUser newUser = new MyUser(email:email, userName: userName, password: Constants.PASSWORD_NORMAL, firstName: "user2", lastName: "user2LastName", photo: null,
                admin: true, active: false, confirmPassword: Constants.PASSWORD_NORMAL)
        newUser.save()

        then:
        MyUser.count() == 1
        newUser.errors.allErrors.size() == 2
        newUser.errors.getFieldErrorCount('email') == 1
        newUser.errors.getFieldErrorCount('userName') == 1
    }

    def "getUserFullName"() {
        expect:
        new MyUser(firstName: firstName, lastName: lastName).fullName == name

        where:
        firstName | lastName          | name
        null      | null              | ""
        "user1"   | "user1LastName"   | "user1 user1LastName"
        ""        | "user2LastName"   | "user2LastName"
        null      | "user3LastName"   | "user3LastName"
        "user4"   | ""                | "user4"
        "user5"   | null              | "user5"
    }

    def "tostringCheck"() {

        given:
        MyUser user = new MyUser(userName: userName)

        when:
        result == user.toString()

        then:
        noExceptionThrown()

        where:
        userName    | result
        "user1"     | "user1"
    }
}
