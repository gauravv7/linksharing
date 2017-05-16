package com.link_sharing.project

import com.link_sharing.project.Topic as Topic
import com.link_sharing.project.Subscription as Subscription
import com.link_sharing.project.Resource as Resource
import com.link_sharing.project.ReadingItem as ReadingItem
import com.link_sharing.project.co.SearchCO
import com.link_sharing.project.vo.TopicVO
import org.hibernate.criterion.CriteriaSpecification

class User {

    String email;
    String userName;
    String password;
    String firstName;
    String lastName;

    String photo;
    Boolean admin;
    Boolean active;
    Date dateCreated;
    Date lastUpdated;
    String confirmPassword

    static hasMany = [
            topics : Topic,
            subscriptions: Subscription,
            resources: Resource,
            readingItems: ReadingItem
    ]

    static transients = ['fullName', 'confirmPassword']

    static mapping = {
        id(sort: 'desc')
    }

    static constraints = {

        email(unique: true, email: true, blank: false)
        userName(unique: true, blank: false)
        password(minSize: 5, blank: false, validator: {password, obj ->
            def password2 = obj.confirmPassword
            password2 == password ? true : ['password.mismatch']
        })
        firstName(blank: false)
        lastName(blank: false)
        photo(nullable: true)
        admin(nullable: true)
        active(nullable: true)
        confirmPassword(bindable: true, nullable: true, blank: true)

    }


    def userSubscriptions() {

        return Subscription.createCriteria().list() {
            resultTransformer CriteriaSpecification.ALIAS_TO_ENTITY_MAP
            projections {
                groupProperty('id')
                property('id', 'id')
                property('seriousness', 'topicSeriousness')
                'topic' {
                    property('id', 'topicID')
                    property('createdBy.id', 'topicCreatedBy')
                    property('topicName', 'topicName')
                    property('visibility', 'topicVisibility')
                }
                'createdBy' {
                    property('id', 'userID')
                    property('userName', 'userName')
                    property('lastName', 'ln')
                    property('firstName', 'fn')
                    property('photo', 'pic')
                }
            }
        }
    }

    Map getSubscribedTopics() {

        Map topicNameList = Subscription.createCriteria().list {
            projections {
                'topic' {
                    property('id')
                    property('topicName')
                    createdBy {
                        property('userName')
                    }
                }
                'createdBy'{
                    eq('id', id)
                }
            }
        }.inject([:]){ result, k ->
            result << [(k[0]): k[1]+" by "+k[2]]

        }
        log.info("${topicNameList}")
        return topicNameList
    }

    Map getPrivatelySubscribedTopics() {

        Map topicNameList = Subscription.createCriteria().list {
            projections {
                'topic' {
                    property('id')
                    property('topicName')
                    createdBy {
                        property('userName')
                    }
                    eq('visibility', Visibility.PRIVATE)
                }
                'createdBy'{
                    eq('id', id)
                }
            }
        }.inject([:]){ result, k ->
            result << [(k[0]): k[1]+" by "+k[2]]

        }
        log.info("${topicNameList}")
        return topicNameList
    }

    static List<ReadingItem> getUnReadItems(User user, SearchCO searchCO){
        List list = []

        if (searchCO.q) {
            list = User.createCriteria().list {
                projections {
                    readingItems {
                        property('resource')
                    }
                }
                readingItems {
                    eq('isRead', false)
                    resource {
                        ilike('description', "%"+searchCO.q+"%")
                    }
                }

                eq("id", user.id)

                maxResults searchCO.max?: 5
                firstResult searchCO.offset?: 0
            }

        } else {
            list = User.createCriteria().list {
                projections {
                    readingItems {
                        property('resource')
                    }
                }
                readingItems {
                    eq('isRead', false)
                }
                eq('id', user.id)
                maxResults searchCO.max?: 0
                firstResult searchCO.offset?: 5
            }

        }
        printf list.collect().join(", ")
        return list
    }

    String getFullName() {
        [firstName, lastName].findAll { it }.join(' ')

    }

    String toString() {
        return this.userName
    }
}
