package com.link_sharing.project.dto

import com.link_sharing.project.Resource
import com.link_sharing.project.Visibility
import org.hibernate.criterion.CriteriaSpecification

/**
 * Created by gaurav on 22/5/17.
 */
class ResourceDTO {

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
}
