package com.link_sharing.project.dto

import com.link_sharing.project.Resource
import com.link_sharing.project.Subscription
import com.link_sharing.project.Topic
import org.hibernate.criterion.CriteriaSpecification

/**
 * Created by gaurav on 22/5/17.
 */
class TopicDTO {

    static def getTrendingTopics(def params){
        return Resource.createCriteria().list {
            resultTransformer CriteriaSpecification.ALIAS_TO_ENTITY_MAP
            projections {
                groupProperty('topic.id')
                'topic'{
                    property('id', 'topicID')
                    property('topicName', 'topicName')
                    property('visibility', 'visibility')
                    property('createdBy', 'createdBy')
                    count('id', 'topicCount')
                }
            }
            order('topicCount', 'desc')
            order('topicName', 'asc')
            maxResults((params.max as int)?: 5)
            firstResult((params.offset as int)?: 0)
        }
    }

    static def subscribedUsers(Topic topic) {
        return Subscription.createCriteria().list {
            projections {
                property('createdBy')
            }
            eq('topic', topic)
        }
    }
}
