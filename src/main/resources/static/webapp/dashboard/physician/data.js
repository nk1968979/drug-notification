function fillPatientData(){

    $.ajax({
               type: 'GET',
               url: "/allPatients?token="+window.localStorage.getItem("token"),
               contentType: "application/json",
               success: function(resultData) {
                            $('#username').text(resultData.username);
                             var body="<tr class='notfound'><td colspan='10'>No record found</td></tr>";
                            $.each(resultData.patients, function(index,patient) {
                                var row="<tr><td>"+patient.mrn+"</td><td>"+patient.fullName+"</td><td>"+patient.gender+"</td><td>"+patient.age+"</td><td>"+patient.height+"</td><td>"+patient.weight+"</td><td>"+patient.email+"</td><td>"+patient.phoneNum+"</td><td><a href='javascript:showPrescriptions("+patient.id+")' data-tip='Prescriptions' title='Prescriptions'> Show Prescriptions <i class='fa fa-plus-square-o'></i></a></td></tr>"
                                body=body+row;
                            });
                            $('#patient-list').html(body);

                        },

               error:function(error){
                          alert(error.responseJSON.message);
               }
     });
}

function showPrescriptions(id){
     window.location.href="/webapp/dashboard/patient/home.html?id="+id;
}
