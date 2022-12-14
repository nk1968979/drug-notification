function login(event,role){
            event.preventDefault();
            var loginData={
                            "username":document.loginForm.userID.value,
                            "password":document.loginForm.pass.value,
                             "role":role
                          }
             $.ajax({
                          type: 'POST',
                          url: "/login",
                          data: JSON.stringify(loginData),
                          contentType: "application/json",
                          success: function(resultData) {
                            window.localStorage.setItem("token","Bearer "+resultData.token);
                            var redirectURL="/webapp/dashboard/"+role+"/home.html";
                            if(role==="patient"){
                                redirectURL=redirectURL+"?id="+resultData.id;
                            }
                            window.location.href=redirectURL;
                           },
                          error:function(error){
                            alert(error.responseJSON.message);
                          }
                    });
}