<g:each in="${unreadItems}" var="item">
    <div class="row" id="listTemplateDivId">

        <div class="col-sm-2">
            <dsh:showProfilePic filepath="$item.user.photo" styleClasses="post-profile-img"></dsh:showProfilePic>
        </div>
        <div class="col-sm-10">
            <div class="row">
                <div class="col-sm-6 no-mar">
                    <h5>

                        ${item.user.getFullName()} <span>@<dsh:showUserNameLink id="${item.user.id}"></dsh:showUserNameLink></span>
                    </h5>
                </div>
                <div class="col-sm-6">
                    <h5 class="text-right"><dsh:showTopicNameLink id="${item.resource.topic.id}"></dsh:showTopicNameLink></h5>
                </div>
            </div>
            <div class="row">
                <p>
                    ${item.resource.description}
                </p>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <ul class="list-inline text-right">
                        <li><dsh:showInboxLinkOrDownload resource="${item.resource}"></dsh:showInboxLinkOrDownload></li>
                        <g:if test="${session.user}">
                            <li><dsh:markAsReadUnRead item="${item}"></dsh:markAsReadUnRead></li>
                        </g:if>
                        <li><dsh:viewResource rid="${item.resource.id}"></dsh:viewResource></li>
                    </ul>
                </div>
            </div>
        </div>

    </div> <!-- row -->
</g:each>

