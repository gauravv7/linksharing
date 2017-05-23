package com.link_sharing.project.dto

import com.link_sharing.project.Resource
import com.link_sharing.project.ResourceRating
import com.link_sharing.project.Visibility
import com.link_sharing.project.vo.RatingInfoVO
import org.hibernate.criterion.CriteriaSpecification

/**
 * Created by gaurav on 22/5/17.
 */
class ResourceRatingDTO {

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

    static RatingInfoVO getRatingInfo(Long resourceId) {
        List result = ResourceRating.createCriteria().get {
            projections {
                count('id', 'totalVotes')
                avg('score')
                sum('score')
            }
            eq('resource.id', resourceId)
            order('totalVotes', 'desc')
        }
        new RatingInfoVO(totalVotes: result[0], averageScore: result[1], totalScore: result[2])
    }

}
