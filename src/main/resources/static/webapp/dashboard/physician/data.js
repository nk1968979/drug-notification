function fillPatientData(){

    $.ajax({
               type: 'GET',
               url: "/allPatients?token="+window.localStorage.getItem("token"),
               contentType: "application/json",
               success: function(resultData) {
                            $('#username').text(resultData.username);
                            var body="";
                            $.each(resultData.patients, function(index,patient) {
                                var row="<tr><td>"+(index+1)+"</td><td>"+patient.fullName+"</td><td>"+patient.gender+"</td><td>"+patient.age+"</td><td>"+patient.height+"</td><td>"+patient.weight+"</td><td>"+patient.email+"</td><td>"+patient.phoneNum+"</td><td><ul class='action-list'><li><a href='#' data-tip='edit'><i class='fa fa-edit'></i></a></li><li><a href='#' data-tip='delete'><i class='fa fa-trash'></i></a></li></ul></td></tr>"
                                body=body+row;
                            });
                            $('#patient-list').html(body);

                        },

               error:function(error){
                          alert(error.responseJSON.message);
               }
     });
}
