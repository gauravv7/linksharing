package com.link_sharing.project

import com.link_sharing.project.User as User
import com.link_sharing.project.Topic as Topic
import com.link_sharing.project.ResourceRating as ResourceRating
import com.link_sharing.project.ReadingItem as ReadingItem
import com.link_sharing.project.co.ResourceSearchCO
import com.link_sharing.project.vo.PostVO
import com.link_sharing.project.vo.RatingInfoVO
import org.hibernate.criterion.CriteriaSpecification

abstract class Resource {

    String description
    User createdBy
    Topic topic
    Date dateCreated
    Date lastUpdated

    static transients = ['ratingInfo']

    static hasMany = [
            ratings : ResourceRating,
            readingItems: ReadingItem
    ]

    static mapping = {
        description(type: 'text')
    }

    static belongsTo = [topic: Topic]

    static constraints = {
        description(blank: false)
    }

    static namedQueries = {
        search { ResourceSearchCO resourceSearchCO ->
            if (resourceSearchCO.q) {
                or{
                    ilike('description', "%${resourceSearchCO.q}%")
                    topic{
                        ilike('topicName', "%${resourceSearchCO.q}%")
                    }
                }
            }
        }
    }

    String toString() {
        return "${description}"
    }
}
