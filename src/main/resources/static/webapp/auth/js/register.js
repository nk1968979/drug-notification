function register(event,role){
    event.preventDefault();
                 var URL="/register-physician";
                 registerData={
                                "fullName":document.registerForm.fullName.value,
                                "email":document.registerForm.email.value,
                                "phoneNum":document.registerForm.phoneNum.value,
                                "pass":document.registerForm.pass.value,
                                "role":role
                 }
                 if(role==='patient'){
                       registerData['age']=document.registerForm.age.value;
                       registerData['gender']=document.registerForm.gender.value;
                       registerData['height']=document.registerForm.height.value;
                       registerData['weight']=document.registerForm.weight.value;
                       URL="/register-patient";

                 }

                        $.ajax({
                              type: 'POST',
                              url: URL,
                              data: JSON.stringify(registerData),
                              contentType: "application/json",
                              success: function(resultData) {
                                    alert(resultData);
                                    window.location.href="/webapp/auth/login-"+role+"/index.html";

                               },
                              error: function(error){
                                alert(error.responseJSON.message);
                              }
                        });
}