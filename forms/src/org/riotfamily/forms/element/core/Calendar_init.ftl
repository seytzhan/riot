var inputField = document.getElementById('${element.id}');

var button = document.createElement('div');
button.className = 'calendar-button';
inputField.parentNode.insertBefore(button, inputField.nextSibling);

Calendar.setup({
	inputField: inputField,
	ifFormat  : '${element.jsFormatPattern}',
	button    : button
});