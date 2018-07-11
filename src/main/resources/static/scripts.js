function checkFields(className, buttonId) {
  var inputs = document.getElementsByClassName(className);
  var button = document.getElementById(buttonId);
  var x = 0;

  for (var i = 0; i < inputs.length ; i++) {
    if (!inputs[i].value.trim() == "") {
        x += 1;
    }
  }
  if (x == inputs.length) {
    button.disabled = false;
  } else {
    button.disabled = true;
  }
}