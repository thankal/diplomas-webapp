document.addEventListener("DOMContentLoaded", function() {
    var roleSelect = document.querySelector('select[name="role"]');
    var studentFields = document.querySelector('.student-fields');
    var professorFields = document.querySelector('.professor-fields');
  
    // Hide student fields by default
    studentFields.style.display = 'none';
    professorFields.style.display = 'none';

    // in the roleSelect select the disabled option
    roleSelect.selectedIndex = 0;
  
    // Show/hide student fields when role changes
    roleSelect.addEventListener('change', function () {
      if (roleSelect.value === 'STUDENT') {
        studentFields.style.display = 'flex';
        professorFields.style.display = 'none';
      } 
      else if (roleSelect.value === 'PROFESSOR') {
          studentFields.style.display = 'none';
          professorFields.style.display = 'flex';
      }else {
        studentFields.style.display = 'none';
        professorFields.style.display = 'none';
      }
    });
});