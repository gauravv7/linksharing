<div class="row">
    <div class="col-sm-3">
        <dsh:showProfilePic filepath="$item.createdBy.photo" styleClasses="trending-topics-profile-img"></dsh:showProfilePic>
    </div>
    <div class="col-sm-9">
        <div class="row">
            <div class="col-sm-6">
                <h5>
                    ${item.createdBy.getFullName()} <span>@${item.createdBy.userName}</span>
                </h5>
            </div>
            <div class="col-sm-6">
                <h5 class="text-right">${item.topic.topicName}</h5>
            </div>
        </div>
        <div class="row">
            <p>
                ${item.description}
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