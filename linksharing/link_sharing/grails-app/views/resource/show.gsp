<%! import com.link_sharing.project.Visibility %>
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
        <div class="col-md-7" >
            <div class="row">
                <div class="col-md-12 topic-box">
                    <div class="row">
                        <div class="col-sm-3">
                            <img src="http://lorempixel.com/130/135/city/" alt="" class="topic-profile-image">
                        </div>
                        <div class="col-sm-9">
                            <div class="row">
                                <div class="col-sm-6">
                                    <h3>${resource.createdBy.getFullName()}</h3>
                                    <p>@${resource.createdBy.userName}</p>
                                </div>
                                <div class="col-sm-6" class="topic-meta-box">
                                    <h5 class="text-right">${resource.topic.topicName}</h5>
                                    <h5 class="text-right" class="topic-meta-box__subheads">${resource.dateCreated.getDateTimeString() }</h5>
                                    <div class="text-right" class="topic-meta-box__subheads">
                                        <input data-url="${createLink(controller: 'resource', action: 'updateRating', params: [id: resource.id])}" id="input-1-ltr-star-xs" name="input-1-ltr-star-xs" class="kv-ltr-theme-default-star rating-loading" value="${ratingInfo.averageScore}" min="1" max="5" step="1" dir="ltr" data-size="xs">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <p>
                                ${resource.description}
                            </p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <ul class="list-inline">
                                <li>f</li>
                                <li>t</li>
                                <li>g+</li>
                            </ul>
                        </div>
                        <div class="col-md-6">
                            <ul class="list-inline pull-right">
                                <dsh:showIfAdminOrResourceCreatedBy resource="$resource.id"><li><a href="${createLink(controller: 'resource', action: 'delete', params: [id: resource.id])}">Delete</a></li></dsh:showIfAdminOrResourceCreatedBy>
                                <dsh:showIfAdminOrResourceCreatedBy resource="$resource.id"><li><a href="" data-toggle="modal" data-target="#editResource">Edit</a></li></dsh:showIfAdminOrResourceCreatedBy>
                                <li><dsh:showInboxLinkOrDownload resource="${resource}"></dsh:showInboxLinkOrDownload></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>


        </div>
        <div class="col-md-4 col-md-offset-1">
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Trending Topics</h3>
                        </div>
                        <div class="panel-body">
                            <ul class="list-unstyled trending-topics">
                                <g:each in="${trendingTopics}" var="item">
                                    <li>
                                        <g:render template="/topic/trendingTopics" model="[item: item]"></g:render>
                                    </li>
                                </g:each>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

    <g:render template="edit-resource" model="[rid: resource.id]"></g:render>
</body>
</html>
