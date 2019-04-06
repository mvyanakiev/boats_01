function validFormScript() {

    const ERROR_INVALID_NAME = "Name must be in range of 2 and 50 characters long.";




    $(`#inputBoatName`).on(`keyup keypress`, function () {
        let value = $(`#inputBoatName`).val().trim();
        if (value.length <= 2 || value.length > 50) {
            $(`#inputBoatName`).addClass("border border-danger");
            $(`#nameHelp`).text(ERROR_INVALID_NAME);
            $(`#nameHelp`).show();
        } else {
            $(`#inputBoatName`).removeClass("border border-danger");
            $(`#inputBoatName`).addClass("border border-success");
            $(`#nameHelp`).hide();
        }
    });



}