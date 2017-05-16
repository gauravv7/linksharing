<%@ page contentType="text/html;charset=UTF-8" %>
<html xmlns:g="http://www.w3.org/1999/html">
<head>
</head>

<body>

<g:if test="${session.user}">
    <content tag="nav">
        <li><a href="" data-toggle="modal" data-target="#createTopic"><span class="glyphicon glyphicon-comment"></span></a></li>
        <li><a href="" data-toggle="modal" data-target="#sendInvite"><span class="glyphicon glyphicon-envelope"></span></a></li>
        <li><a href="" data-toggle="modal" data-target="#createLink"><span class="glyphicon glyphicon-link"></span></a></li>
        <li><a href="" data-toggle="modal" data-target="#createDocument"><span class="glyphicon glyphicon-file"></span></a></li>
    </content>
</g:if>


<div class="container-fluid">
    <div class="row">
        <div class="col-md-4">
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Trending Topics</h3>
                        </div>
                        <div class="panel-body panel-body-overflow">
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
            </div> <!--row trending-topics-->
        </div>

        <div class="col-md-8">
            <div class="panel panel-default panel-primary">
                <div class="panel-heading panelHeaders">
                    Search Result
                </div>

                <div class="panel-body" style="overflow-y:scroll; height: auto; max-height: 500px">
                    <g:if test="${posts}">
                        <g:each in="${posts}" var="item">
                            <g:render template="post" model="[item: item]"></g:render>
                        </g:each>
                    </g:if>
                    <g:else>
                        No results to be shown.
                    </g:else>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
</html>