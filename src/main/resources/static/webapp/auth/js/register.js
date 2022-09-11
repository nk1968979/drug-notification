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
                 var isValidated=validateData(registerData,role);
                 if(!isValidated){
                    return;
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

function validateData(registerData,role){
    var emailPattern = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;
    if(!isNotEmpty(registerData.fullName)){
        alert("Please Enter Full Name");
        return false;
    }else if(!isNotEmpty(registerData.email)){
        alert("Please Enter Email");
        return false;
    }else if(!isNotEmpty(registerData.phoneNum)){
        alert("Please Enter Phone Number");
        return false;
    }else if(!isNotEmpty(registerData.pass)){
        alert("Please Enter Password");
        return false;
    }else if(!registerData.email.match(emailPattern)){
        alert("Please Enter valid Email");
        return false;
    }else if(isNaN(registerData.phoneNum) || registerData.phoneNum.length<10 || !['9','8','7','6'].includes(registerData.phoneNum.toString().charAt(0))){
        alert("Please Enter valid Phone Number");
        return false;
    }else if(registerData.pass!==document.registerForm.passConf.value){
        alert("Passwords are not matching");
        return false;
    }

    if(role==="patient"){
        if(!isNotEmpty(registerData.age)){
            alert("Please Enter Age");
            return false;
        }else if(!isNotEmpty(registerData.gender)){
            alert("Please Enter Gender");
            return false;
        }else if(!isNotEmpty(registerData.weight)){
            alert("Please Enter Weight");
            return false;
        }else if(!isNotEmpty(registerData.height)){
            alert("Please Enter Height");
            return false;
        }else if(isNaN(registerData.age)){
            alert("Please Enter Valid Age in Numerical");
            return false;
        }
        else if(isNaN(registerData.height)){
                alert("Please Enter Valid Height in Numerical");
                return false;
        }
        else if(isNaN(registerData.weight)){
                alert("Please Enter Valid Weight in Numerical");
                return false;
        }

    }
    return true;

}

function isNotEmpty(field){
    return field && field.length>0;
}