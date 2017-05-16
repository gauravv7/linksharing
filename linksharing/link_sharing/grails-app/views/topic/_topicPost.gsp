<div class="row">
    <div class="col-sm-3">
        <dsh:showProfilePic filepath="$item.user.photo" styleClasses="trending-topics-profile-img"></dsh:showProfilePic>
    </div>
    <div class="col-sm-9">
        <div class="row">
            <div class="col-sm-6">
                <h5>
                    ${item.user.getFullName()} <span>@${item.user.userName}</span>
                </h5>
            </div>
            <div class="col-sm-6">
                <h5 class="text-right">${item.resource.topic.topicName}</h5>
            </div>
        </div>
        <div class="row">
            <p>
                ${item.resource.description}
            </p>
        </div>
        <div class="row">
            <div class="col-sm-4">
                <p>
                    <span>f</span>
                    <span>t</span>
                    <span>g+</span>
                </p>
            </div>
            <div class="col-sm-8">
                <ul class="list-inline text-right">
                    <g:if test="${session.user}">
                        <li><dsh:showInboxLinkOrDownload resource="${item.resource}"></dsh:showInboxLinkOrDownload></li>
                        <li><dsh:markAsReadUnRead item="${item}"></dsh:markAsReadUnRead></li>
                        <li><dsh:viewResource rid="${item.resource.id}"></dsh:viewResource></li>
                    </g:if>
                </ul>
            </div>
        </div>
    </div>
</div> <!-- row -->