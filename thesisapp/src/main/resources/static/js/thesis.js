// using jQuery
$('table tbody tr').on('click', function() {
    console.log('Row clicked');
  const modalTitle = $('#detail-modal-title');
  modalTitle.text('Title of modal for ' + this.id);
  const modalBody = $('#detail-modal .modal-body');
  modalBody.html('Detailed content for ' + this.id);
  $('#detail-modal').modal('show');
});
