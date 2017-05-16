<!-- Modal -->
<div class="modal fade" id="editResource" tabindex="-1" role="dialog" aria-labelledby="editResource">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Create Link</h4>
            </div>
            <div class="modal-body">
                <g:form name="resourceEdit" class="form-horizontal" controller="resource" action="edit" params="[id: rid]">
                    <div class="form-group">
                        <label for="description" class="col-sm-2 control-label">Description</label>
                        <div class="col-sm-10">
                            <g:textArea name="description" class="col-sm-8 form-control"/>
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