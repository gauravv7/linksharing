<!-- Modal -->
<div class="modal fade" id="createTopic" tabindex="-1" role="dialog" aria-labelledby="createTopic">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Create Topic</h4>
            </div>
            <div class="modal-body">
                <g:form class="form-horizontal" controller="topic" action="save" name="topicCreate">
                    <div class="form-group">
                        <label for="topicName" class="col-sm-2 control-label">Name</label>
                        <div class="col-sm-10">
                            <g:textField name="topicName" placeholder="Topic Name"
                                         class="form-control col-sm-8" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="visibility" class="col-sm-2 control-label">visibility</label>
                        <div class="col-sm-10">
                            <g:select name="visibility" from="${com.link_sharing.project.Visibility.values()}"
                                      class="dropdown-toggle btn btn-default col-sm-8" value="${com.link_sharing.project.Visibility}" optionKey="key" />
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-default">Save</button>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</div>