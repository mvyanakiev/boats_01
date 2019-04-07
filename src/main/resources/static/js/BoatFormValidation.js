function validFormScript() {

    const ERROR_INVALID_NAME = "Name must be in range of 2 and 50 characters long.";
    const ERROR_INVALID_PRODUCER = "Producer name must be in range of 2 and 50 characters long.";
    const ERROR_INVALID_MODEL = "Model must be in range of 2 and 50 characters long.";
    const ERROR_INVALID_DATE = "Date required";
    const ERROR_INVALID_PRICE = "Price must be bigger than 0.01";

    $(`#inputBoatName`).on(`keyup keypress`, function () {

        let field = $(`#inputBoatName`);
        let helpText = $(`#nameHelp`);
        let value = field.val().trim();

        if (value.length < 2 || value.length > 50) {
            showHelpText(field, helpText, ERROR_INVALID_NAME);
        } else {
            removeHelpText(field, helpText);
        }
    });

    $(`#inputBoatProducer`).on(`keyup keypress`, function () {

        let field = $(`#inputBoatProducer`);
        let helpText = $(`#producerHelp`);
        let value = field.val().trim();

        if (value.length < 2 || value.length > 50) {
            showHelpText(field, helpText, ERROR_INVALID_PRODUCER);
        } else {
            removeHelpText(field, helpText);
        }
    });

    $(`#inputBoatModel`).on(`keyup keypress`, function () {

        let field = $(`#inputBoatModel`);
        let helpText = $(`#modelHelp`);
        let value = field.val().trim();

        if (value.length < 2 || value.length > 50) {
            showHelpText(field, helpText, ERROR_INVALID_MODEL);
        } else {
            removeHelpText(field, helpText);
        }
    });

    $(`#inputLastCheckedDate`).on(`keyup keypress`, function () {

        let field = $(`#inputLastCheckedDate`);
        let helpText = $(`#dateHelp`);

        let value = field.val().trim();
        if (value.length < 1 || value.length > 50 || value == null || value === "") {
            showHelpText(field, helpText, ERROR_INVALID_DATE);
        } else {
            removeHelpText(field, helpText);
        }
    });

    $(`#inputBoatPrice`).on(`keyup keypress`, function () {

        let field = $(`#inputBoatPrice`);
        let helpText = $(`#priceHelp`);

        let value = field.val().trim();
        if (parseFloat(value) < 0.01 || value == null || value === "") {
            showHelpText(field, helpText, ERROR_INVALID_PRICE);
        } else {
            removeHelpText(field, helpText);
        }
    });

    function removeHelpText(field, helpText) {
        field.removeClass("border border-danger");
        field.addClass("border border-success");
        helpText.hide();
    }

    function showHelpText(field, helpText, message){
        field.addClass("border border-danger");
        helpText.text(message);
        helpText.show();
    }

//    // Validates that the input string is a valid date formatted as "mm/dd/yyyy"
// function isValidDate(dateString)
// {
//     // First check for the pattern
//     if(!/^\d{1,2}\/\d{1,2}\/\d{4}$/.test(dateString))
//         return false;
//
//     // Parse the date parts to integers
//     var parts = dateString.split("/");
//     var day = parseInt(parts[1], 10);
//     var month = parseInt(parts[0], 10);
//     var year = parseInt(parts[2], 10);
//
//     // Check the ranges of month and year
//     if(year < 1000 || year > 3000 || month == 0 || month > 12)
//         return false;
//
//     var monthLength = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
//
//     // Adjust for leap years
//     if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
//         monthLength[1] = 29;
//
//     // Check the range of the day
//     return day > 0 && day <= monthLength[month - 1];
// };
}