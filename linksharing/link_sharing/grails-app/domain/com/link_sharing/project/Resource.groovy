package com.link_sharing.project

import com.link_sharing.project.User as User
import com.link_sharing.project.Topic as Topic
import com.link_sharing.project.ResourceRating as ResourceRating
import com.link_sharing.project.ReadingItem as ReadingItem
import com.link_sharing.project.co.ResourceSearchCO
import com.link_sharing.project.vo.RatingInfoVO

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
                if (resourceSearchCO.topicID) {
                    eq('topic.id', resourceSearchCO.topicID)
                }
                if (resourceSearchCO.visibility && resourceSearchCO.visibility == Visibility.PUBLIC) {
                    'topic' {
                        eq('visibility', Visibility.PUBLIC)
                    }
                }
                ilike('description', "%${resourceSearchCO.q}%")
            }

        }
    }

    RatingInfoVO getRatingInfo() {
        List result = ResourceRating.createCriteria().get {
            projections {
                count('id', 'totalVotes')
                avg('score')
                sum('score')
            }
            eq('resource', this)
            order('totalVotes', 'desc')
        }
        new RatingInfoVO(totalVotes: result[0], averageScore: result[1], totalScore: result[2])
    }

    String toString() {
        return "${description}"
    }
}
