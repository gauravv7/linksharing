package com.link_sharing.project

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Unroll

import spock.lang.Specification
import com.link_sharing.project.User as User
import com.link_sharing.project.Topic as Topic

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Topic)
@Mock([User, Subscription])
class TopicSpec extends Specification {

    @Unroll("Topic Constraints: #sno")
    void  "check topic validations"() {

        given:
        Topic topic = new Topic(topicName: topicName, createdBy: createdBy, visibility: visibility)

        when:
        Boolean result = topic.validate()

        then:
        result == valid

        where:
        sno | topicName | createdBy    | visibility       | valid
        1   | "t1"      | new User() | Visibility.PRIVATE | true
        2   | " "       | new User() | Visibility.PRIVATE | false
        3   | null      | new User() | Visibility.PRIVATE | false
        4   | "t4"      | null       | Visibility.PUBLIC  | false
        5   | "t5"      | new User() | null               | false
    }

    def "check topic-user uniqueness"() {

        given:
        String topicName = "t1"
        User creator = new User()
        Visibility visibility = Visibility.PRIVATE
        Topic topic = new Topic(topicName: topicName, createdBy: creator, visibility: visibility)

        when:
        topic.save()

        then:
        topic.count() == 1

        when:
        Topic newTopic = new Topic(topicName: topicName, createdBy: creator, visibility: visibility)
        newTopic.save()

        then:
        Topic.count() == 1
        newTopic.errors.allErrors.size() == 1
        newTopic.errors.getFieldErrorCount('topicName') == 1
    }

    def "check toString()"() {

        given:
        User user = new User(userName: 'u1')
        Topic topic = new Topic(topicName: name, createdBy: user)

        when:
        String topicName = topic.toString()

        then:
        topicName == result

        where:
        name            | result
        "topic1"        | "topic1, u1"
    }
}
