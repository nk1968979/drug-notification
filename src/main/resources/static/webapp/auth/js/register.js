function register(event,role){
    event.preventDefault();
                var registerData={
                                "fullName":document.registerForm.fullName.value,
                                "email":document.registerForm.email.value,
                                "phoneNum":document.registerForm.phoneNum.value,
                                "pass":document.registerForm.pass.value,
                                "role":role
                              }

                        $.ajax({
                              type: 'POST',
                              url: "/register",
                              data: JSON.stringify(registerData),
                              contentType: "application/json",
                              success: function(resultData) {
                                alert(resultData);
                               },
                              error: function(error){
                                alert(error.responseJSON.message);
                              }
                        });
}