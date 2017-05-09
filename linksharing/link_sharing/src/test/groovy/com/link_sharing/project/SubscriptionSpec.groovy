package com.link_sharing.project

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll
import com.link_sharing.project.User as User
import com.link_sharing.project.Topic as Topic
import com.link_sharing.project.Subscription as Subscription
/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Subscription)
@Unroll
class SubscriptionSpec extends Specification {

    def "validatingSubscription"() {
        given:
        Subscription subscription = new Subscription(topic: topic, createdBy: user, seriousness: seriousness)

        when:
        Boolean valid = subscription.validate()

        then:
        valid == result

        where:
        topic       | user       | seriousness        | result
        new Topic() | new User() | null               | false
        new Topic() | new User() | Seriousness.CASUAL | true
        new Topic() | null       | Seriousness.CASUAL | false
        null        | new User() | Seriousness.SERIOUS| false
    }

    def "validatingDuplicateSubscription"() {

        given:
        Topic topic = new Topic()
        User user = new User()
        Subscription subscriptionObj = new Subscription(topic: topic, createdBy: user, seriousness: Seriousness.SERIOUS)

        when:
        subscriptionObj.save()

        then:
        Subscription.count() == 1

        when:
        subscriptionObj = new Subscription(topic: topic, createdBy: user, seriousness: Seriousness.SERIOUS)
        subscriptionObj.save()

        then:
        Subscription.count() == 1
        subscriptionObj.errors.allErrors.size() == 1
    }

    def "toStringCheck"() {

        given:
        User user = new User(userName: userName)
        Topic topic = new Topic(topicName: topicName)
        Subscription subscription = new Subscription(topic: topic, createdBy: user, seriousness: Seriousness.CASUAL)

        when:
        result == subscription.toString()

        then:
        noExceptionThrown()

        where:
        userName  | topicName | result
        "u1"      | "topic1"  | "u1 subscribed topic1"
    }
}
