/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

    $(document).ready(function () {

$('#form').validate({
    rules: {
        title: {
            minlength: 1,
            required: true
        },
        system: {
            minlength: 1,
            required: true
        },
        logDate: {
            required: true
        },
        price: {
            required: true,
            number: true
        },
        image: {
            required: true
        },

    },
    highlight: function (element) {
        $(element).closest('.control-group').removeClass('success').addClass('error');
    },
    success: function (element) {
        element.text('OK!').addClass('valid')
            .closest('.control-group').removeClass('error').addClass('success');
    }
     

});
});
