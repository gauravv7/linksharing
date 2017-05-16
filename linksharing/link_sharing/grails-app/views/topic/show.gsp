<!DOCTYPE html>
<html>
<head>
</head>
<body>

<content tag="nav">

    <li><a href="" data-toggle="modal" data-target="#createTopic"><span class="glyphicon glyphicon-comment"></span></a></li>
    <li><a href="" data-toggle="modal" data-target="#sendInvite"><span class="glyphicon glyphicon-envelope"></span></a></li>
    <li><a href="" data-toggle="modal" data-target="#createLink"><span class="glyphicon glyphicon-link"></span></a></li>
    <li><a href="" data-toggle="modal" data-target="#createDocument"><span class="glyphicon glyphicon-file"></span></a></li>
</content>

<div class="container-fluid">
    <div class="row row-topic-desc">
        <div class="col-md-5" >
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Topic</h3>
                        </div>
                        <div class="panel-body panel-body-overflow">
                            <ul class="list-unstyled trending-topics">
                                <li>
                                    <div class="row">
                                        <div class="col-sm-4">
                                            <dsh:showProfilePic filepath="$topic.createdBy.photo" styleClasses="trending-topics-profile-img"></dsh:showProfilePic>
                                        </div>
                                        <div class="col-sm-8">
                                            <div class="row">
                                                <div class="col-sm-12">
                                                    <h3>${topic.topicName} <span>(${topic.visibility})</span> </h3>
                                                    <p></p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <h4>@${topic.createdBy.userName}</h4>
                                                    <g:if test="${session.user}">
                                                        <h5><dsh:subscribed userId="${session.user.id}" topicId="${topic.id}"></dsh:subscribed></h5>
                                                    </g:if>
                                                </div>
                                                <div class="col-md-4">
                                                    <h4>Subsciption</h4>
                                                    <h5><dsh:subscriptionCount topicId="${topic.id}"></dsh:subscriptionCount></h5>
                                                </div>
                                                <div class="col-md-4">
                                                    <h4>Posts</h4>
                                                    <h5><dsh:postCount topicId="${topic.id}"></dsh:postCount ></h5>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <dsh:ifSubscribed topicId="${topic.id}" userId="${topic.createdBy.id}">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <ul class="list-inline">
                                                    <li>
                                                        <g:if test="${session.user!=null && session.user?.id == subscription.createdBy.id}">
                                                            <g:form controller="subscription" action="updateSeriousness">
                                                                <g:field type="hidden" name="sid" value="${subscription.id}"></g:field>
                                                                <g:select onChange="submit()" name="seriousness" from="${com.link_sharing.project.Seriousness.values()}"
                                                                          class="dropdown-toggle btn btn-default col-sm-8 subscription-select" optionKey="key" value="${subscription.seriousness}" />
                                                            </g:form>
                                                        </g:if>
                                                    </li>
                                                    <g:if test="${topic.visibility == com.link_sharing.project.Visibility.PRIVATE}">
                                                        <li>
                                                            <a type="button" class="subscription-invite-btn" data-toggle="modal" data-target="#sendInvite" data-topicid="${topic.id}"><span class="glyphicon glyphicon-envelope"></span></a>
                                                        </li>
                                                    </g:if>
                                                </ul>
                                            </div>
                                        </div>
                                    </dsh:ifSubscribed>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div> <!--topic: createdBy -->

            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Users</h3>
                        </div>
                        <div class="panel-body panel-body-overflow">
                            <ul class="list-unstyled trending-topics">
                                <g:each in="${subscribedUsers}" var="item">
                                    <li>
                                        <g:render template="subscribedUsers" model="[item: item, topicid: topic.id]"></g:render>
                                    </li>
                                </g:each>
                            </ul>
                        </div>
                    </div>
                </div>
            </div> <!--row trending-topics-->

        </div>
        <div class="col-md-7">
            <div class="row row-noMargin">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Posts
                    </div>
                    <div class="panel-body">
                        <g:each in="${posts}" var="item">
                            <g:render template="topicPost" model="[item: item]"></g:render>
                        </g:each>
                    </div> <!-- body-->
                </div>
            </div> <!-- row inbox-->
        </div>
    </div>
</div>

</body>
</html>
