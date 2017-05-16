<%@ page contentType="text/html;charset=UTF-8" %>
<html xmlns:g="http://www.w3.org/1999/html">
<head>
</head>

<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-4">
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

        <div class="col-md-8">
            <div class="panel panel-default panel-primary">
                <div class="panel-heading panelHeaders">
                    Search Result
                </div>

                <div class="panel-body" style="overflow-y:scroll; height: 800px;">
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