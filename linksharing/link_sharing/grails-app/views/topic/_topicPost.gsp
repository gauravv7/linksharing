<div class="row">
    <div class="col-sm-2">
        <dsh:showProfilePic filepath="$item.createdBy.photo" styleClasses="post-profile-img"></dsh:showProfilePic>
    </div>
    <div class="col-sm-10">
        <div class="row">
            <div class="col-sm-6 no-mar">
                <h5>
                    ${item.createdBy.getFullName()} <span>@<dsh:showUserNameLink id="${item.createdBy.id}"></dsh:showUserNameLink></span>
                </h5>
            </div>
            <div class="col-sm-6">
                <h5 class="text-right"><dsh:showTopicNameLink id="${item.topic.id}"></dsh:showTopicNameLink></h5>
            </div>
        </div>
        <div class="row">
            <p>
                ${item.description}
            </p>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <ul class="list-inline text-right">
                    <li><dsh:showInboxLinkOrDownload resource="${item}"></dsh:showInboxLinkOrDownload></li>
                    <g:if test="${session.user}">
                        <li><dsh:markAsReadUnRead item="${item}"></dsh:markAsReadUnRead></li>
                    </g:if>
                    <li><dsh:viewResource rid="${item.id}"></dsh:viewResource></li>
                </ul>
            </div>
        </div>
    </div>
</div> <!-- row -->