package com.link_sharing.project

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.link_sharing.project.User as User
import com.link_sharing.project.Topic as Topic
import com.link_sharing.project.Resource as Resource
import com.link_sharing.project.DocumentResource as DocumentResource
import com.link_sharing.project.LinkResource as LinkResource
import com.link_sharing.project.ReadingItem as ReadingItem

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(ReadingItem)
class ReadingItemSpec extends Specification {

    def "constraintsOfResourceItemExcludingUserUniqueness"() {

        given:
        ReadingItem resourceItemObj = new ReadingItem(resource: resource, user: user, isRead: isRead)

        when:
        boolean result = resourceItemObj.validate()

        then:
        result == excepted

        where:
        sno | resource           | user       | isRead | excepted
        1   | new LinkResource() | new User() | true   | true
        2   | null               | new User() | true   | false
        3   | new LinkResource() | null       | true   | false
        4   | new LinkResource() | new User() | null   | false
    }

    def "validatingUniqueReadingItem"() {

        given:
        //Resource resource = new LinkResource(url:"https://github.com/",description: "cdsjdjb",new User()).save()
        //User user = new User().save()

        User user1 = new User(email:"u1@gmail.com", userName: "u1", password: "123456", firstName: "user1FirstName",
                lastName: "user1LastName")

        Topic topic = new Topic(name: "Topic1", visibility: Visibility.PRIVATE, createdBy: user1)

        Resource resource = new LinkResource(url: "https://github.com/", description: "topic1", createdBy: user1, topic: topic)

        User user=new User(email:"u2@gmail.com", userName: "u2", password: "123456", firstName: "user2FirstName", lastName: "user2LastName", photo: null,
                admin: true, active: false)

        ReadingItem readingItem = new ReadingItem(resource: resource, user: user, isRead: true)
        ReadingItem newReadingItem = new ReadingItem(resource: resource, user: user, isRead: false)

        when:
        readingItem.save(flush: true)
        newReadingItem.save(flush: true)

        then:

        ReadingItem.count() == 1
        newReadingItem.errors.allErrors.size() == 1
        newReadingItem.errors.getFieldErrorCount('resource') == 1

    }


    def "toStringCheck"() {

        setup:
        User user = new User(userName: userName)
        Resource resource = new DocumentResource(description: description)
        ReadingItem readingItem = new ReadingItem(user: user, resource: resource, isRead: isRead)

        when:
        result == readingItem.toString()

        then:
        noExceptionThrown()

        where:
        userName    | description | isRead | result
        "u1"        | "topic1"    | true   | "u1 read the topic1: true"
    }
}
