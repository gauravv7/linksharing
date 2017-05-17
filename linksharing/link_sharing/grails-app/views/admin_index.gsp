<!doctype html>
<html>
<head>
</head>
<body>

<div class="container-fluid">
    <div class="row" style="padding: 2px 15px;">
        <div class="col-md-12" >
            <table class="table table-striped table-hover ">
                <thead>
                <tr>
                    <th>User Name</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>email</th>
                    <th>isAdmin</th>
                    <th>isActive</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>

                <g:each in="${users}" var="user">
                    <tr>
                        <th>${user.userName}</th>
                        <th>${user.firstName}</th>
                        <th>${user.lastName}</th>
                        <th>${user.email}</th>
                        <th>${user.admin?"yes": "no"}</th>
                        <th>${user.active?"yes": "no"}</th>
                        <th>
                            <dsh:showActivateDeactiveAction id="${user.id}"></dsh:showActivateDeactiveAction>
                        </th>
                    </tr>
                </g:each>
                <g:paginate next="Forward" prev="Back"
                            maxsteps="0" controller="user"
                            action="all" total="${userCount}" />
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
