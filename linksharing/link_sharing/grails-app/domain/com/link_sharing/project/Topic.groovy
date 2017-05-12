package com.link_sharing.project

import com.link_sharing.project.User as User
import com.link_sharing.project.Subscription as Subscription
import com.link_sharing.project.Resource as Resource
import com.link_sharing.project.constants.Constants
import com.link_sharing.project.vo.TopicVO

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

    static List<TopicVO> getTrendingTopics() {
        List<TopicVO> trendingTopics = []
        Resource.createCriteria().list {
            projections {
                createAlias('topic', 't')
                groupProperty('t.id')
                property('t.topicname')
                property('t.visibility')
                count('t.id', 'topicCount')
                property('t.createdBy')
            }
            order('topicCount', 'desc')
            order('t.topicname', 'asc')
            maxResults(5)
        }?.each {
            trendingTopics.add(new TopicVO(id: it[0], name: it[1], visibility: it[2], count: it[3], createdBy: it[4]))
        }
        return trendingTopics
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
