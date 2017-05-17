<g:each in="${userSubscriptions}" var="us">
    <li>
        <div class="row">
            <div class="col-sm-4">
                <dsh:showProfilePic filepath="$us.pic" styleClasses="trending-topics-profile-img"></dsh:showProfilePic>
            </div>
            <div class="col-sm-8">
                <div class="row">
                    <div class="col-sm-12">
                        <h3><dsh:showTopicNameLink id="${us.topicID}"></dsh:showTopicNameLink></h3>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <h3><dsh:showUserNameLink id="${us.topicCreatedBy}"></dsh:showUserNameLink></h3>
                        <br/>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <h4>@${us.userName}</h4>
                        <h5><dsh:subscribed userId="${session.user.id}" topicId="${us.topicID}"></dsh:subscribed></h5>
                    </div>
                    <div class="col-md-4">
                        <h4>Subsciption</h4>
                        <h5><dsh:subscriptionCount topicId="${us.topicID}"></dsh:subscriptionCount></h5>
                    </div>
                    <div class="col-md-4">
                        <h4>Posts</h4>
                        <h5><dsh:postCount topicId="${us.topicID}"></dsh:postCount ></h5>
                    </div>
                </div>
                <dsh:ifSubscribed topicId="${us.topicID}" userId="${session.user.id}">
                    <div class="row">
                        <div class="col-md-12">
                            <ul class="list-inline">
                                <li>
                                    <g:form controller="subscription" action="updateSeriousness">
                                        <g:field type="hidden" name="sid" value="${us.id}"></g:field>
                                        <g:select onChange="submit()" name="seriousness" from="${com.link_sharing.project.Seriousness.values()}"
                                                  class="dropdown-toggle btn btn-default col-sm-8 subscription-select form-control" optionKey="key" value="${us.topicSeriousness}" />
                                    </g:form>
                                </li>
                                <li>
                                    <g:form controller="topic" action="updateVisibility">
                                        <g:field type="hidden" name="topicId" value="${us.topicID}"></g:field>
                                        <g:select onChange="submit()" name="visibility" from="${com.link_sharing.project.Visibility.values()}"
                                                  class="dropdown-toggle btn btn-default col-sm-8 subscription-select form-control" optionKey="key" value="${us.topicVisibility}" />
                                    </g:form>
                                </li>
                                <g:if test="${us.topicVisibility==com.link_sharing.project.Visibility.PRIVATE}">
                                    <li>
                                        <a type="button" class="subscription-invite-btn" data-toggle="modal" data-target="#sendInvite" data-topicid="${us.topicID}"><span class="glyphicon glyphicon-envelope"></span></a>
                                    </li>
                                </g:if>
                                <g:if test="${session.user.id==us.topicCreatedBy}">
                                    <li>
                                        <a type="button" class="subscription-invite-btn" href="${createLink(controller: 'topic', action: 'remove', params: [topicId: us.topicID])}"><span class="glyphicon glyphicon-trash"></span></a>
                                    </li>
                                </g:if>
                            </ul>
                        </div>
                    </div>
                </dsh:ifSubscribed>
            </div>
        </div>

    </li>
</g:each>