package com.link_sharing.project

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.link_sharing.project.User as User
import com.link_sharing.project.Topic as Topic
import com.link_sharing.project.DocumentResource as DocumentResource

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(DocumentResource)
class DocumentResourceSpec extends Specification {

    def "validateDocumentResource"() {

        setup:
        User user = Mock(User)
        Topic topic = Mock(Topic)

        DocumentResource documentResource = new DocumentResource(filePath: filePath, description: description,
                createdBy: user, topic: topic)

        when:
        Boolean result = documentResource.validate()

        then:
        result == valid

        where:
        description   | filePath                | valid
        "description" | '/ some/file/path'      | true
        " "           | '/some/file/path'       | false
        null          | '/some/file/path'       | false
        "description" | ' '                     | false
        "description" | null                    | false
    }

    def "tostringCheck"() {
        setup:
        DocumentResource documentResource = new DocumentResource(filePath: filePath)

        when:
        result == documentResource.toString()

        then:
        noExceptionThrown()

        where:
        filePath          | result
        "/some/file/path" | "/some/file/path"
        ""                | ""
        null              | null
    }
}
