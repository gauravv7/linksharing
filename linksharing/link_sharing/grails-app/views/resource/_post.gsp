<div class="row">
    <div class="col-sm-3">
        <img src="http://lorempixel.com/130/135/city/" alt="" style="margin: 5px; border-radius: 5px;">
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
                    <li><dsh:showInboxLinkOrDownload resource="${item.resource}"></dsh:showInboxLinkOrDownload></li>
                    <li><dsh:markAsReadUnRead item="${item}"></dsh:markAsReadUnRead></li>
                    <li><a class="text-right" style="display: block" href="${createLink(controller: 'resource', action: 'show', params: [id: item.resource.id])}">view resource</a></li>
                </ul>
            </div>
        </div>
    </div>
</div> <!-- row -->