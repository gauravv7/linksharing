package com.link_sharing.project

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.link_sharing.project.User as User
import com.link_sharing.project.ResourceRating as ResourceRating
import com.link_sharing.project.Resource as Resource
import com.link_sharing.project.LinkResource as LinkResource
import com.link_sharing.project.DocumentResource as DocumentResource

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(ResourceRating)
class ResourceRatingSpec extends Specification {

    def "checkResourceRating"() {

        given:
        ResourceRating resourceRating = new ResourceRating(resource: resource, createdBy: user, score: score)

        when:
        Boolean valid = resourceRating.validate()

        then:
        valid == result

        where:
        resource                                        | user            | score | result
        new DocumentResource()                          | new User()      | 3     | true
        null                                            | new User()      | 3     | false
        new DocumentResource()                          | null            | 3     | false
        new DocumentResource()                          | new User()      | 10    | false
        new DocumentResource()                          | new User()      | 0     | false

    }

    def "validateUniqueResourceRating"() {

        given:
        Resource resource = Mock(LinkResource)
        User user = Mock(User)
        ResourceRating resourceRating = new ResourceRating(resource: resource, createdBy: user, score: 3)
        ResourceRating newResourceRating = new ResourceRating(resource: resource, createdBy: user, score: 4)

        when:
        resourceRating.save(flush: true)
        newResourceRating.save(flush: true)

        then:
        !resourceRating.errors.allErrors.size()
        newResourceRating.errors.allErrors.size()
        newResourceRating.errors.getFieldError('resource')
    }

    def "tostringCheck"() {
        setup:

        User user = new User(userName: userName)
        Resource resource = new DocumentResource(description: description)
        ResourceRating resourceRating = new ResourceRating(createdBy: user, resource: resource, score: score)

        when:
        result == resourceRating.toString()

        then:
        noExceptionThrown()

        where:
        userName    | description  | score  | result
        "u1"        | "topic1"     | 5      | "u1 rated topic1 by 5"
    }
}
