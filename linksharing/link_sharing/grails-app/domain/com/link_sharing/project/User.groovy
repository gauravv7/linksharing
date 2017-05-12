package com.link_sharing.project

import com.link_sharing.project.Topic as Topic
import com.link_sharing.project.Subscription as Subscription
import com.link_sharing.project.Resource as Resource
import com.link_sharing.project.ReadingItem as ReadingItem
import com.link_sharing.project.co.SearchCO
import com.link_sharing.project.vo.TopicVO

class User {

    String email;
    String userName;
    String password;
    String firstName;
    String lastName;

    Byte[] photo;
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
        photo(sqlType: 'longblob')
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
            }
            eq('createdBy.id', id)
        }.inject([:]){ result, k ->
            result << [(k[0]): k[1]+" by "+k[2]]

        }
        log.info("${topicNameList}")
        return topicNameList
    }

    static List<ReadingItem> getUnReadItems(User user, SearchCO searchCO){
        List list = []
        if(searchCO.q==null) {
            printf "at null"
            return null
        }

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
