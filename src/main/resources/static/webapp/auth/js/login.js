function login(event,role){
            event.preventDefault();
            var loginData={
                            "username":document.loginForm.userID.value,
                            "password":document.loginForm.pass.value,
                             "role":role
                          }
            var login = $.ajax({
                          type: 'POST',
                          url: "/login",
                          data: JSON.stringify(loginData),
                          contentType: "application/json",
                          success: function(resultData) {
                            window.localStorage.setItem("token","Bearer "+resultData.token);
                            window.location.href="/home?token="+window.localStorage.getItem("token");
                           }
                    });
            login.error(function(result) {  alert(result)});
}