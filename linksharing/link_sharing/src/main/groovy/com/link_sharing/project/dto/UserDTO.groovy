package com.link_sharing.project.dto

import com.link_sharing.project.ReadingItem
import com.link_sharing.project.Subscription
import com.link_sharing.project.User
import com.link_sharing.project.Visibility
import com.link_sharing.project.co.SearchCO
import org.hibernate.criterion.CriteriaSpecification

/**
 * Created by gaurav on 22/5/17.
 */
class UserDTO {

    static def userSubscriptions(Long userID, Map params) {

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
                    eq('id', userID)

                }
            }
            maxResults((params.max as int)?: 2)
            firstResult((params.offset as int)?: 0)
        }
    }

    Map getSubscribedTopicList(Long userID) {

        List topicNameList = Subscription.createCriteria().list {
            projections {
                'topic' {
                    property('id')
                    property('topicName')
                    createdBy {
                        property('userName')
                    }
                }
                'createdBy'{
                    eq('id', userID)
                }
            }
        }
        return topicNameList
    }

    Map getSubscribedTopics(Long userID) {

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
                    eq('id', userID)
                }
            }
        }.inject([:]){ result, k ->
            result << [(k[0]): k[1]+" by "+k[2]]

        }
        return topicNameList
    }

    Map getPrivatelySubscribedTopics(Long userID) {

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
                    eq('id', userID)
                }
            }
        }.inject([:]){ result, k ->
            result << [(k[0]): k[1]+" by "+k[2]]

        }
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
}
