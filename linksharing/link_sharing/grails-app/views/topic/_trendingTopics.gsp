<div class="row">
    <div class="col-sm-4">
        <dsh:showProfilePic filepath="$item.createdBy.photo" styleClasses="trending-topics-profile-img"></dsh:showProfilePic>
    </div>
    <div class="col-sm-8">
        <div class="row">
            <div class="col-sm-12">
                <h3><dsh:showTopicNameLink id="${item.topicID}"></dsh:showTopicNameLink></h3>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <h4>@${item.createdBy.userName}</h4>
                <g:if test="${session.user}">
                    <h5><dsh:subscribed userId="${session.user.id}" topicId="${item.topicID}"></dsh:subscribed></h5>
                </g:if>
            </div>
            <div class="col-md-6">
                <h4>Subsciption</h4>
                <h5><dsh:subscriptionCount topicId="${item.topicID}"></dsh:subscriptionCount></h5>
            </div>
            <div class="col-md-6">
                <h4>Topics</h4>
                <h5><dsh:subscriptionCount topicId="${item.topicID}"></dsh:subscriptionCount></h5>
            </div>
        </div>
    </div>
</div>