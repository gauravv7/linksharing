<%! import com.link_sharing.project.Visibility %>
<!DOCTYPE html>
<html>
<head>
</head>
<body>

<content tag="nav">
    <form class="navbar-form navbar-left">
        <div class="form-group">
            <input type="text" class="form-control" placeholder="Search">
        </div>
    </form>
    <ul class="nav navbar-nav navbar-right">
        <li><a href="" data-toggle="modal" data-target="#createTopic"><span class="glyphicon glyphicon-comment"></span></a></li>
        <li><a href="" data-toggle="modal" data-target="#sendInvite"><span class="glyphicon glyphicon-envelope"></span></a></li>
        <li><a href="" data-toggle="modal" data-target="#createLink"><span class="glyphicon glyphicon-link"></span></a></li>
        <li><a href="" data-toggle="modal" data-target="#createDocument"><span class="glyphicon glyphicon-file"></span></a></li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span>UserName <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="#">Profile</a></li>
                <li><a href="#">Logout</a></li>
            </ul>
        </li>
    </ul>
</content>

<div class="container-fluid">
    <div class="row row-topic-desc">
        <div class="col-md-7" >
            <div class="row">
                <div class="col-md-12 topic-box">
                    <div class="row">
                        <div class="col-sm-3">
                            <img src="http://lorempixel.com/130/135/people/" alt="" class="topic-profile-image">
                        </div>
                        <div class="col-sm-9">
                            <div class="row">
                                <div class="col-sm-6">
                                    <h3>FName LName</h3>
                                    <p>@firstName</p>
                                </div>
                                <div class="col-sm-6" class="topic-meta-box">
                                    <h5 class="text-right">Grails</h5>
                                    <h5 class="text-right" class="topic-meta-box__subheads">2:45 PM 22 FEB, 2014</h5>
                                    <h5 class="text-right" class="topic-meta-box__subheads">
                                        <span class="glyphicon glyphicon-star" class="topic-rating"></span>
                                        <span class="glyphicon glyphicon-star" class="topic-rating"></span>
                                        <span class="glyphicon glyphicon-star" class="topic-rating"></span>
                                        <span class="glyphicon glyphicon-star-empty" class="topic-rating"></span>
                                        <span class="glyphicon glyphicon-star-empty" class="topic-rating"></span>
                                    </h5>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <p>
                                Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
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
                                <li><a href="">Delete</a></li>
                                <li><a href="">Edit</a></li>
                                <li><a href="">Download</a></li>
                                <li><a href="">View Full Site</a></li>
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
                                <li>
                                    <div class="row">
                                        <div class="col-sm-4">
                                            <img src="http://lorempixel.com/120/125/people/" alt="" class="trending-topics-profile-img">
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
                                            <img src="http://lorempixel.com/120/125/people/" alt="" style="margin: 20px 5px; border-radius: 5px;">
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
            </div>
        </div>
    </div>
</div>


</body>
</html>
