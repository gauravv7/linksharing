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
                <!--<img style="margin: 5px 0; border-radius: 5px;" src="${createLink(controller: 'user', action: 'getImage', params: [filepath: session.user.photo?: 'default-user.png' ])}"/>-->
                <dsh:showProfilePic filepath="$session.user.photo" styleClasses="trending-topics-profile-img"></dsh:showProfilePic>
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
                  <h5>${topicCount}</h5>
                </div>
              </div>
            </div>
          </div>

        </div>
        <div class="col-md-7">
          <div class="row inbox">
            <div class="panel panel-primary">
              <div class="panel-heading">
                Profile
              </div>
              <div class="panel-body">
                  <g:uploadForm class="form-horizontal" controller="user" action="updateProfile">
                      <div class="form-group">
                          <label for="firstName" class="col-sm-2 control-label">First Name</label>
                          <div class="col-sm-10">
                              <input type="text" class="form-control" name="firstName" id="firstName" value="${session.user.firstName}">
                          </div>
                      </div>
                      <div class="form-group">
                          <label for="lastName" class="col-sm-2 control-label">Last Name</label>
                          <div class="col-sm-10">
                              <input type="text" class="form-control" name="lastName" id="lastName" value="${session.user.lastName}">
                          </div>
                      </div>
                      <div class="form-group">
                          <label for="userName" class="col-sm-2 control-label">Username</label>
                          <div class="col-sm-10">
                              <input type="text" class="form-control" name="userName" id="userName" value="${session.user.userName}">
                          </div>
                      </div>
                      <div class="form-group">
                          <label for="photograph" class="col-sm-2 control-label">Photograph</label>
                          <div class="col-sm-10">
                              <input type="file" class="form-control" id="photograph" name="photograph">
                          </div>
                      </div>
                      <div class="form-group">
                          <div class="col-sm-offset-2 col-sm-10">
                              <button type="submit" class="btn btn-default">update</button>
                          </div>
                      </div>
                  </g:uploadForm>
              </div> <!-- body-->
            </div>
          </div> <!-- row inbox-->

            <div class="row inbox">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Change Password
                    </div>
                    <div class="panel-body">
                        <g:uploadForm class="form-horizontal" controller="user" action="updatePassword" name="change-password">
                            <div class="form-group">
                                <label for="password" class="col-sm-2 control-label">Password</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" name="password" id="password" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="confirmPassword" class="col-sm-2 control-label">Confirm Password</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" >
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="submit" class="btn btn-default">update</button>
                                </div>
                            </div>
                        </g:uploadForm>
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
