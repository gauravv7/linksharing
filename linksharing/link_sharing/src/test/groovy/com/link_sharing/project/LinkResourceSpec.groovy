package com.link_sharing.project

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.link_sharing.project.User as User
import com.link_sharing.project.Topic as Topic
import com.link_sharing.project.LinkResource as LinkResource

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(LinkResource)
class LinkResourceSpec extends Specification {

    def "validateLinkResource"() {

        setup:
        User user = Mock(User)
        Topic topic = Mock(Topic)

        LinkResource linkResource = new LinkResource(url: url, description: description, createdBy: user, topic: topic)

        when:
        Boolean result = linkResource.validate()

        then:
        result == valid

        where:
        description   | url                                | valid
        "description" | 'http://www.testLinkResource.com/' | true
        " "           | 'http://www.testLinkResource.com/' | false
        null          | 'http://www.testLinkResource.com/' | false
        "description" | '://www.testLinkResource.com/'     | false
        "description" | ' '                                | false
        "description" | null                               | false
    }


    def "tostringCheck"() {

        setup:
        LinkResource linkResource = new LinkResource(url: url)

        when:
        result == linkResource.toString()

        then:
        noExceptionThrown()

        where:
        url                               | result
        "http://www.testLinkResource.com" | "http://www.testLinkResource.com"
        ""                                | ""
        null                              | null
    }
}
