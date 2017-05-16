<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Link Sharing"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <asset:stylesheet src="application.css"/>

    <g:layoutHead/>
</head>
<body>
    <header>
        <nav class="navbar navbar-default">
          <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Menu</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="#">Link Sharing</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse pull-right" id="bs-example-navbar-collapse-1">
                <g:form class="navbar-form navbar-left" method="get" controller="resource" action="search">
                    <div class="form-group">
                        <input type="text" class="form-control" name="q" placeholder="Search">
                    </div>
                    <g:hiddenField name="visibility" id="visibility" class="visibility"
                                   value="PUBLIC"/>
                </g:form>

                <ul class="nav navbar-nav navbar-right">
                    <g:if test="${session.user}">
                        <g:pageProperty name="page.nav" />
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span>${session.user.userName}<span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Profile</a></li>
                                <li><a href="/logout">Logout</a></li>
                            </ul>
                        </li>
                    </g:if>
                </ul>

            </div><!-- /.navbar-collapse -->
          </div><!-- /.container-fluid -->
        </nav>
    </header>

    <div class="container-fluid">
        <g:if test="${flash.error}">
            <div class="alert alert-danger">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <p>${flash.error}</p>
            </div>
        </g:if>
        <g:if test="${flash.message}">
            <div class="alert alert-success" style="display: block">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <p>${flash.message}</p>
            </div>
        </g:if>
    </div>

    <g:layoutBody/>

    <footer>
        <p>linksharing</p>
    </footer>

    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>

    <asset:javascript src="application.js"/>

</body>
</html>
