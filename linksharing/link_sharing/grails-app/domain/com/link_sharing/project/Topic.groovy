package com.link_sharing.project

import com.link_sharing.project.User as User
import com.link_sharing.project.Subscription as Subscription
import com.link_sharing.project.Resource as Resource
import com.link_sharing.project.constants.Constants
import com.link_sharing.project.vo.TopicVO
import org.hibernate.criterion.CriteriaSpecification

class Topic {

    String topicName;
    User createdBy;
    Date dateCreated;
    Date lastUpdated;
    Visibility visibility

    static belongsTo = [User]

    static mappings = {
        sort "topicName" : 'asc'
    }

    static hasMany = [
            subscriptions: Subscription,
            resources: Resource,
    ]

    static constraints = {
        topicName(blank: false, unique: ['createdBy'])
        visibility(inList: Visibility.values().toList())
    }

    String toString() {
        return "${topicName}, ${createdBy}"
    }

    def afterInsert() {
        Topic.withNewSession {
            Subscription subscription = new Subscription(
                    createdBy: this.createdBy,
                    topic: this,
                    seriousness: Constants.SERIOUSNESS)
            if (subscription.validate() && subscription.save(flush: true)) {
                log.info "${subscription}-> ${this.createdBy} subscribed for ${this}"
            } else {
                log.error "Subscription failed: ${subscription.errors.allErrors}"
            }
        }
    }

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

    def subscribedUsers() {
        return Subscription.createCriteria().list {
            projections {
                property('createdBy')
            }
            eq('topic', this)
        }
    }
}
