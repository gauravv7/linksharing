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
        log.info "result from getRatingInfo $result"
        new RatingInfoVO(totalVotes: result[0], averageScore: result[1], totalScore: result[2])
    }

    static def getTopPosts() {

       return ResourceRating.createCriteria().list(max: 5) {
            resultTransformer CriteriaSpecification.ALIAS_TO_ENTITY_MAP
            projections {
                property('id', 'rrId')
                'resource'{
                    property('id', 'id')
                    property('description', 'description')
                    property('url', 'url')
                    property('filePath', 'filePath')
                    'topic' {
                        property('id', 'topicID')
                        property('topicName', 'tn')
                        eq('visibility', Visibility.PUBLIC)
                    }
                    'createdBy' {
                        property('id', 'userID')
                        property('userName', 'un')
                        property('firstName', 'fn')
                        property('lastName', 'ln')
                        property('photo','photo')
                    }
                    property('lastUpdated')
                }
            }
            groupProperty('id')
            count('id', 'totalVotes')
            order('totalVotes', 'desc')
        }
    }

    static def recentPosts() {
        return Resource.createCriteria().list(max: 5) {
            resultTransformer CriteriaSpecification.ALIAS_TO_ENTITY_MAP
            projections {
                property('id', 'id')
                property('description', 'description')
                property('url', 'url')
                property('filePath', 'filePath')
                'topic' {
                    property('id', 'topicID')
                    property('topicName', 'tn')
                    eq('visibility', Visibility.PUBLIC)
                }
                'createdBy' {
                    property('id', 'userID')
                    property('userName', 'un')
                    property('firstName', 'fn')
                    property('lastName', 'ln')
                    property('photo','photo')
                }
                property('lastUpdated', 'lu')
            }
            order('lastUpdated', 'desc')
        }
    }

    String toString() {
        return "${description}"
    }
}
