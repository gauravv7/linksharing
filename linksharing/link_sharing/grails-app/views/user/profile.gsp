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
            <g:render template="details" model="['user': user]"></g:render>
          </div>

          <br>
          <div class="row">
            <div class="col-md-12">
              <div class="panel panel-primary">
                <div class="panel-heading">
                  <h3 class="panel-title">Topics</h3>
                </div>
                <div class="panel-body panel-body-overflow">
                  <ul class="list-unstyled trending-topics">
                      <g:each in="${topics}" var="us">
                          <li>
                              <div class="row">
                                  <div class="col-sm-12">
                                      <div class="row">
                                          <div class="col-sm-12">
                                              <h3><dsh:showTopicNameLink id="${us.id}"></dsh:showTopicNameLink></h3>
                                              <p></p>
                                          </div>
                                      </div>
                                      <div class="row">
                                          <div class="col-sm-12">
                                              <h3><dsh:showUserNameLink id="${us.createdBy.id}"></dsh:showUserNameLink></h3>
                                              <p></p>
                                          </div>
                                      </div>
                                      <div class="row">
                                          <div class="col-md-4">
                                              <h4>@${us.createdBy.userName}</h4>
                                              <g:if test="$session.user">
                                                  <h5><dsh:subscribed userId="${session.user.id}" topicId="${us.id}"></dsh:subscribed></h5>
                                              </g:if>
                                          </div>
                                          <div class="col-md-4">
                                              <h4>Subsciption</h4>
                                              <h5><dsh:subscriptionCount topicId="${us.id}"></dsh:subscriptionCount></h5>
                                          </div>
                                          <div class="col-md-4">
                                              <h4>Posts</h4>
                                              <h5><dsh:postCount topicId="${us.id}"></dsh:postCount ></h5>
                                          </div>
                                      </div>
                                  </div>
                              </div>
                          </li>
                      </g:each>
                  </ul>
                </div>
              </div>
            </div>
          </div> <!--topics -->
          <br/>
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Subscriptions</h3>
                        </div>
                        <div class="panel-body panel-body-overflow">
                            <ul class="list-unstyled trending-topics">
                                <g:each in="${subscribedTopics}" var="us">
                                    <li>
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <h3>${us.topic.topicName}</h3>
                                                        <p></p>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <h3>${us.createdBy.firstName}</h3>
                                                        <p></p>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-4">
                                                        <h4>@${us.createdBy.userName}</h4>
                                                        <g:if test="$session.user">
                                                            <h5><dsh:subscribed userId="${session.user.id}" topicId="${us.topic.id}"></dsh:subscribed></h5>
                                                        </g:if>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <h4>Subsciption</h4>
                                                        <h5><dsh:subscriptionCount topicId="${us.topic.id}"></dsh:subscriptionCount></h5>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <h4>Posts</h4>
                                                        <h5><dsh:postCount topicId="${us.topic.id}"></dsh:postCount ></h5>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                </g:each>
                            </ul>
                        </div>
                    </div>
                </div>
            </div> <!--subscription -->

        </div>
        <div class="col-md-7">
          <div class="row inbox">
            <div class="panel panel-primary">
              <div class="panel-heading">
                Posts
              </div>
              <div class="panel-body">
                  <g:each in="${posts}" var="item">
                      <g:render template="/topic/topicPost" model="[item: item]"></g:render>
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
