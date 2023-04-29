document.addEventListener("DOMContentLoaded", function() {
    var roleSelect = document.querySelector('select[name="role"]');
    var studentFields = document.querySelector('.student-fields');
    var professorFields = document.querySelector('.professor-fields');

    studentInputs = studentFields.querySelectorAll("input");
    professorInputs = professorFields.querySelectorAll("input");

  
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
        
          // enable only the student specific fields
          for (var i = 0; i< professorInputs.length; i++) {
            professorInputs[i].disabled = true;
          }
          for (var i = 0; i< studentInputs.length; i++) {
            studentInputs[i].disabled = false;
          }
      } 
      else if (roleSelect.value === 'PROFESSOR') {
          studentFields.style.display = 'none';
          professorFields.style.display = 'flex';

          // enable only the professor specific fields
          for (var i = 0; i< studentInputs.length; i++) {
            studentInputs[i].disabled = true;
          }
          for (var i = 0; i< professorInputs.length; i++) {
            professorInputs[i].disabled = false;
          }
      }else {
        studentFields.style.display = 'none';
        professorFields.style.display = 'none';
      }
    });
});