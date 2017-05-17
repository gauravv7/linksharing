// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//
//= require jquery-2.2.0.min
//= require bootstrap
//= require star-rating.min.js
//= require jquery.validate.min.js
//= require_tree .
//= require_self

if (typeof jQuery !== 'undefined') {
    (function($) {
        $(document).ajaxStart(function() {
            $('#spinner').fadeIn();
        }).ajaxStop(function() {
            $('#spinner').fadeOut();
        });
    })(jQuery);
}

$(document).ready(function(){
    $('#sendInvite').on('show.bs.modal', function (event) {
      var button = $(event.relatedTarget) // Button that triggered the modal
      var tid = parseInt(button.data('topicid')) // Extract info from data-* attributes

      var modal = $(this)
      if(tid){
        modal.find('.modal-body select#topic').val(tid)
      }
    })

    $('.kv-ltr-theme-default-star').rating({
        hoverOnClear: false,
        showCaption: false,
        showClear: false,
        min: 1,
        max: 5,
        step: 1,
        containerClass: 'is-star'
    }).on("rating.change", function(event, value, caption) {
        $.ajax({
            url: $(event.target).data('url')+"?score="+value,
            type: 'GET',
            success: function(data){
                console.log(data)
                alert(data);
            }
        })
    });

    // Setup form validation on the #register-form element
    $("#register-form").validate({

        // Specify the validation rules
        rules: {
            firstName: "required",
            lastName: "required",
            userName: "required",
            email: {
                required: true,
                email: true
            },
            password: {
                required: true,
                minlength: 5,
                equalTo: "#confirmPassword"
            },
            confirmPassword: "required",
            photograph: "required"
        },

        // Specify the validation error messages
        messages: {
            firstName: "Please enter your first name",
            lastName: "Please enter your last name",
            password: {
                required: "Please provide a password",
                minlength: "Your password must be at least 5 characters long",
                equalTo: "Password mismatch"
            },
            email: "Please enter a valid email address",
            photograph: "Please upload a photograph"
        },

        submitHandler: function(form) {
            form.submit();
        }
    });

    // Setup form validation on the #login-form element
    $("#login-form").validate({

        // Specify the validation rules
        rules: {
            username: "required",
            password: "required",
        },

        // Specify the validation error messages
        messages: {
            username: "Please enter your username",
            password: "Please enter your password",
        },

        submitHandler: function(form) {
            form.submit();
        }
    });
    // Setup form validation on the #login-form element
    $("#change-password").validate({

        // Specify the validation rules
        rules: {
            password: {
                required: true,
                minlength: 5,
                equalTo: "#confirmPassword"
            },
            confirmPassword: "required"
        },

        // Specify the validation error messages
        messages: {
            password: {
                required: "Please provide a password",
                minlength: "Your password must be at least 5 characters long",
                equalTo: "Password mismatch"
            }
        },

        submitHandler: function(form) {
            form.submit();
        }
    });
})