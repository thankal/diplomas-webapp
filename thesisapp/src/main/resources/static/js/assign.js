document.addEventListener("DOMContentLoaded", function() {
    var strategySelect = document.querySelector('select[name="strategy"]');
    var thresholdFields = document.querySelector('.threshold-fields');

    thresholdInputs = thresholdFields.querySelectorAll("input");

  
    // Hide student fields by default
    thresholdFields.style.display = 'none';


    // in the roleSelect select the disabled option
    strategySelect.selectedIndex = 0;
  
    // Show/hide student fields when role changes
    strategySelect.addEventListener('change', function () {
      if (strategySelect.value === 'threshold') {
        thresholdFields.style.display = 'block';
        
          for (var i = 0; i< thresholdInputs.length; i++) {
            thresholdInputs[i].disabled = false;
          }

      }else {
        thresholdFields.style.display = 'none';
          for (var i = 0; i< thresholdInputs.length; i++) {
            thresholdInputs[i].disabled = true;
          }
      }
    });
});