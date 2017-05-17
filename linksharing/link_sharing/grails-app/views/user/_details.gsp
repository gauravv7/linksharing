<div class="col-sm-4">
    <!--<img style="margin: 5px 0; border-radius: 5px;" src="${createLink(controller: 'user', action: 'getImage', params: [filepath: session.user.photo?: 'default-user.png' ])}"/>-->
    <dsh:showProfilePic filepath="$user.photo" styleClasses="trending-topics-profile-img"></dsh:showProfilePic>
</div>
<div class="col-sm-8">
    <div class="row">
        <div class="col-sm-12">
            <h5 class="no-mar">${user.getFullName()}</h5>
            <p class="no-mar">@${user.userName}</p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <p class="no-mar fs-14">Subsciption</p>
            <p class="no-mar fs-14"><dsh:subscriptionCount userId="${user.id}"></dsh:subscriptionCount></p>
        </div>
        <div class="col-md-6">
            <p class="no-mar fs-14">Topics</p>
            <p class="no-mar fs-14"><dsh:topicCount userId="${user.id}"></dsh:topicCount></p>
        </div>
    </div>
</div>