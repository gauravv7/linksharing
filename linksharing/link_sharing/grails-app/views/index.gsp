<!doctype html>
<html>
<head>
</head>
<body>

    <div class="container-fluid">
        <div class="row" style="padding: 2px 15px;">
            <div class="col-md-7" >
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Recent Shares
                    </div>
                    <div class="panel-body panel-body-overflow">
                        <g:each in="${recentShares}" var="rs">
                            <div class="row">
                                <div class="col-sm-3">
                                    <img src="http://lorempixel.com/130/135/city/" alt="" style="margin: 5px; border-radius: 5px;">
                                </div>
                                <div class="col-sm-9">
                                    <div class="row">
                                        <div class="col-sm-6">
                                            <h5>
                                                ${rs.ln} <span>@${rs.un}</span>
                                            </h5>
                                        </div>
                                        <div class="col-sm-6">
                                            <h5 class="text-right">${rs.tn}</h5>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <p>
                                            ${rs.description}
                                        </p>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-6">
                                            <p>
                                                <span>f</span>
                                                <span>t</span>
                                                <span>g+</span>
                                            </p>
                                        </div>
                                        <div class="col-sm-6">
                                            <dsh:viewResource rid="${rs.id}"></dsh:viewResource>
                                        </div>
                                    </div>
                                </div>
                            </div> <!-- row -->
                        </g:each>

                    </div> <!-- body-->
                </div>

                <br/>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        Top Posts
                    </div>
                    <div class="panel-body panel-body-overflow">
                        <g:each in="${topPosts}" var="post">
                            <div class="row">
                                <div class="col-sm-3">
                                    <img src="http://lorempixel.com/130/135/city/" alt="" style="margin: 5px; border-radius: 5px;">
                                </div>
                                <div class="col-sm-9">
                                    <div class="row">
                                        <div class="col-sm-6">
                                            <h5>
                                                ${post.fn} <span>@${post.un}</span>
                                            </h5>
                                        </div>
                                        <div class="col-sm-6">
                                            <h5 class="text-right">${post.tn}</h5>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <p>${post.description}</p>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-6">
                                            <p>
                                                <span>f</span>
                                                <span>t</span>
                                                <span>g+</span>
                                            </p>
                                        </div>
                                        <div class="col-sm-6">
                                            <dsh:viewResource rid="${post.id}"></dsh:viewResource>
                                        </div>
                                    </div>
                                </div>
                            </div> <!-- row -->
                        </g:each>
                    </div> <!-- body-->
                </div>
            </div>
            <div class="col-md-5">
                <g:render template="/auth/login" />
                <g:render template="/auth/register" />

            </div>
        </div>
    </div>

</body>
</html>
