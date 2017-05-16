<%! import com.link_sharing.project.Visibility %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Link Sharing</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
  </head>
  <body>

    <content tag="nav">

        <li><a href="" data-toggle="modal" data-target="#createTopic"><span class="glyphicon glyphicon-comment"></span></a></li>
        <li><a href="" data-toggle="modal" data-target="#sendInvite"><span class="glyphicon glyphicon-envelope"></span></a></li>
        <li><a href="" data-toggle="modal" data-target="#createLink"><span class="glyphicon glyphicon-link"></span></a></li>
        <li><a href="" data-toggle="modal" data-target="#createDocument"><span class="glyphicon glyphicon-file"></span></a></li>
    </content>

    <div class="container-fluid">
      <div class="row" style="padding: 2px 15px;">
        <div class="col-md-5" >
          <div class="row" style="border: 1px solid #b2b2b2  ; border-radius: 5px; margin: 0">
            <div class="col-sm-4">
              <img src="http://lorempixel.com/130/135/city/" alt="" style="margin: 5px; border-radius: 5px;">
            </div>
            <div class="col-sm-8">
              <div class="row">
                <div class="col-sm-12">
                  <h3>${session.user.getFullName()}</h3>
                  <p>@${session.user.userName}</p>
                </div>
              </div>
              <div class="row">
                <div class="col-md-6">
                  <h4>Subsciption</h4>
                  <h5>${subscriptions}</h5>
                </div>
                <div class="col-md-6">
                  <h4>Topics</h4>
                  <h5>${}</h5>
                </div>
              </div>
            </div>
          </div>

          <br>
          <div class="row">
            <div class="col-md-12">
              <div class="panel panel-primary">
                <div class="panel-heading">
                  <h3 class="panel-title">Subscription</h3>
                </div>
                <div class="panel-body">
                  <ul class="list-unstyled trending-topics">
                      <g:each in="${userSubscriptions}" var="us">
                          <li>
                              <div class="row">
                                  <div class="col-sm-4">
                                      <img src="http://lorempixel.com/120/125/city/" alt="" class="trending-topics-profile-img">
                                  </div>
                                  <div class="col-sm-8">
                                      <div class="row">
                                          <div class="col-sm-12">
                                              <h3>${us.topicName}</h3>
                                              <p></p>
                                          </div>
                                      </div>
                                      <div class="row">
                                          <div class="col-sm-12">
                                              <h3>${us.fn}</h3>
                                              <p></p>
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
                                                                class="dropdown-toggle btn btn-default col-sm-8 subscription-select" optionKey="key" value="${us.topicSeriousness}" />
                                                  </g:form>
                                              </li>
                                              <li>
                                                  <g:form controller="topic" action="updateVisibility">
                                                      <g:field type="hidden" name="topicId" value="${us.topicID}"></g:field>
                                                      <g:select onChange="submit()" name="visibility" from="${com.link_sharing.project.Visibility.values()}"
                                                                class="dropdown-toggle btn btn-default col-sm-8 subscription-select" optionKey="key" value="${us.topicVisibility}" />
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
                          </li>
                      </g:each>
                  </ul>
                </div>
              </div>
            </div>
          </div> <!--subscription -->
          <br/>
          <div class="row">
            <div class="col-md-12">
              <div class="panel panel-primary">
                <div class="panel-heading">
                  <h3 class="panel-title">Trending Topics</h3>
                </div>
                <div class="panel-body">
                  <ul class="list-unstyled trending-topics">
                    <li>
                      <div class="row">
                        <div class="col-sm-4">
                          <img src="http://lorempixel.com/120/125/city/" alt="" class="trending-topics-profile-img">
                        </div>
                        <div class="col-sm-8">
                          <div class="row">
                            <div class="col-sm-12">
                              <h3>FName LName</h3>
                              <p>@firstName</p>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col-md-6">
                              <h4>Subsciption</h4>
                              <h5>50</h5>
                            </div>
                            <div class="col-md-6">
                              <h4>Topics</h4>
                              <h5>30</h5>
                            </div>
                          </div>
                        </div>
                      </div>
                    </li>
                    <li>
                      <div class="row">
                        <div class="col-sm-4">
                          <img src="http://lorempixel.com/120/125/city/" alt="" style="margin: 20px 5px; border-radius: 5px;">
                        </div>
                        <div class="col-sm-8">
                          <div class="row">
                            <div class="col-sm-12">
                              <h3>FName LName</h3>
                              <p>@firstName</p>
                            </div>
                          </div>
                          <div class="row">
                            <div class="col-md-6">
                              <h4>Subsciption</h4>
                              <h5>50</h5>
                            </div>
                            <div class="col-md-6">
                              <h4>Topics</h4>
                              <h5>30</h5>
                            </div>
                          </div>
                        </div>
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div> <!--row trending-topics-->

        </div>
        <div class="col-md-7">
          <div class="row inbox">
            <div class="panel panel-primary">
              <div class="panel-heading">
                Inbox
              </div>
              <div class="panel-body">
                  <g:each in="${unreadItems}" var="item">
                      <div class="row">
                          <div class="col-sm-3">
                              <img src="http://lorempixel.com/130/135/city/" alt="" style="margin: 5px; border-radius: 5px;">
                          </div>
                          <div class="col-sm-9">
                              <div class="row">
                                  <div class="col-sm-6">
                                      <h5>
                                          ${item.user.getFullName()} <span>@${item.user.userName}</span>
                                      </h5>
                                  </div>
                                  <div class="col-sm-6">
                                      <h5 class="text-right">${item.resource.topic.topicName}</h5>
                                  </div>
                              </div>
                              <div class="row">
                                  <p>
                                      ${item.resource.description}
                                  </p>
                              </div>
                              <div class="row">
                                  <div class="col-sm-4">
                                      <p>
                                          <span>f</span>
                                          <span>t</span>
                                          <span>g+</span>
                                      </p>
                                  </div>
                                  <div class="col-sm-8">
                                      <ul class="list-inline text-right">
                                          <li><dsh:showInboxLinkOrDownload resource="${item.resource}"></dsh:showInboxLinkOrDownload></li>
                                          <li><dsh:markAsReadUnRead item="${item}"></dsh:markAsReadUnRead></li>
                                          <li><a class="text-right" style="display: block" href="${createLink(controller: 'resource', action: 'show', params: [id: item.resource.id])}">view resource</a></li>
                                      </ul>
                                  </div>
                              </div>
                          </div>
                      </div> <!-- row -->
                  </g:each>
              </div> <!-- body-->
            </div>
          </div> <!-- row inbox-->


        </div>
      </div>
    </div>

    <g:render template="send-invite"></g:render>
    <g:render template="create-topic"></g:render>
    <g:render template="create-link"></g:render>
    <g:render template="create-document"></g:render>


  </body>
</html>
