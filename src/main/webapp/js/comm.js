$(function () {
    //datepicker
    $(document).on("focus", "input[data-datepicker]", function () {
        var opts = {
            showOtherMonths: true
            , selectOtherMonths: true
        };
        $(this).datepicker(opts);

    });

    $(document).on("click", "button[data-datepicker]", function () {
        var altFieldVal = $(this).attr("data-datepicker");
        $("#" + altFieldVal).focus();

    });
});