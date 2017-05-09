package com.link_sharing.project.vo

/**
 * Created by gaurav on 9/5/17.
 */
class RatingInfoVO {

    Integer totalVotes
    Integer averageScore
    Integer totalScore

    @Override
    String toString() {
        return "${totalVotes}, ${averageScore}, ${totalScore}"
    }
}
