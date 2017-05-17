<!-- Modal -->
<div class="modal fade" id="createLink" tabindex="-1" role="dialog" aria-labelledby="createLink">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Create Link</h4>
            </div>
            <div class="modal-body">
                <g:form name="linkResourceSave" class="form-horizontal" controller="linkResource" action="save">
                    <div class="form-group">
                        <label for="link" class="col-sm-2 control-label">Link</label>
                        <div class="col-sm-10">
                            <g:field type="url" class="form-control col-sm-8" name="url"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="description" class="col-sm-2 control-label">Description</label>
                        <div class="col-sm-10">
                            <g:textArea name="description" class="col-sm-8 form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="topic" class="col-sm-2 control-label">Topic</label>
                        <div class="col-sm-10">
                            <g:select class="btn dropdown-toggle col-sm-8 form-control" name="topic" from="${session.user?.getSubscribedTopics()}" optionKey="key" optionValue="value" />
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