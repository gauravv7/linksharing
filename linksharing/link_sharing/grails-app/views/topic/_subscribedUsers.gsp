<div class="row">
    <div class="col-sm-4">
        <dsh:showProfilePic filepath="$item.photo" styleClasses="trending-topics-profile-img"></dsh:showProfilePic>
    </div>
    <div class="col-sm-8">
        <div class="row">
            <div class="col-sm-12">
                <h3>${item.firstName}</h3>
                <p><dsh:showUserNameLink id="${item.id}"></dsh:showUserNameLink></p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <h4>Subsciption</h4>
                <h5><dsh:subscriptionCount topicId="${topicid}"></dsh:subscriptionCount></h5>
            </div>
            <div class="col-md-6">
                <h4>Topics</h4>
                <h5><dsh:subscriptionCount topicId="${topicid}"></dsh:subscriptionCount></h5>
            </div>
        </div>
    </div>
</div>