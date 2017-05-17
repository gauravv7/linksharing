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
              <g:render template="details" model="['user': session.user]"></g:render>
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
                      <div id="subscriptionsDiv">
                          <g:render template="subscriptions" ></g:render>
                      </div>
                  </ul>
                </div>
                  <div class="panel-footer">
                      <util:remotePaginate controller="user" action="filterForSubscriptions" total="${subscriptionCount}"
                                           update="subscriptionsDiv" prev="prev" max="2" params="${params}"/>
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
                      <div id="trendingTopicsDiv">
                          <g:each in="${trendingTopics}" var="item">
                              <li>
                                  <g:render template="/topic/trendingTopics" model="[item: item]"></g:render>
                              </li>
                          </g:each>
                      </div>
                  </ul>
                </div>
                  <div class="panel-footer">
                      <util:remotePaginate controller="user" action="filterForTrendingTopics" total="${trendingTopicsCount}"
                                           update="trendingTopicsDiv" prev="prev" max="5" params="${params}"/>
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
                      <div  id="inboxDiv">
                          <g:render template="/resource/post" model="[item: $unreadItems]"></g:render>
                      </div>
                </div> <!-- body-->
                <div class="panel-footer">
                    <util:remotePaginate controller="user" action="filterForInbox" total="${unreadItemsCount}"
                                     update="inboxDiv" prev="prev" max="5" params="${params}"/>
                </div>
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
