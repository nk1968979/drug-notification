var isEdit=false;
var id=undefined;
var patientId=undefined;
function getPrescriptionData(){
    $("#add-prescriptions").hide();
     patientId=  new URLSearchParams(window.location.search).get('id');
    $.ajax({
               type: 'GET',
               url: "/getPrescriptions?id="+patientId+"&token="+window.localStorage.getItem("token"),
               contentType: "application/json",
               success: function(resultData) {
                            $('#username').text(resultData.username);
                            $('#patientName').text(resultData.patientName);
                            var body="";
                            $.each(resultData.prescriptions, function(index,prescription) {
                                var row="<tr><td>"+(index+1)+"</td><td>"+prescription.drugName+"</td><td>"+prescription.dosage+"</td><td>"+prescription.morning+"</td><td>"+prescription.noon+"</td><td>"+prescription.night+"</td><td>"+prescription.notes+"</td><td>"+prescription.prescribedBy+"</td><td>"+new Date(prescription.prescriptionDate).toLocaleString()+"</td>";
                                if(resultData.role==="physician"){
                                    row=row+"<td><ul class='action-list'><li><a href='javascript:edit("+JSON.stringify(prescription)+")' data-tip='edit'><i class='fa fa-edit'></i></a></li><li><a href='javascript:deletePrescription("+prescription.id+")' data-tip='delete'><i class='fa fa-trash'></i></a></li></ul></td>";
                                }
                                body=body+row+"</tr>";
                            });
                            if(resultData.role==="patient"){
                                $("#add-button").hide();
                                 $("#action").hide();
                            }
                            $('#prescription-list').html(body);

                        },

               error:function(error){
                          alert(error.responseJSON.message);
               }
     });
}

function goBack(){
    window.location.href="/webapp/dashboard/physician/home.html";
}

function addPrescription(event){
    event.preventDefault();
    $('#prescription-list').html("");
    var prescription={
            "drugName":document.prescriptionForm.drugName.value,
            "dosage":document.prescriptionForm.dosage.value,
            "notes":document.prescriptionForm.notes.value,
            "morning":document.prescriptionForm.morning.value,
            "noon":document.prescriptionForm.noon.value,
            "night":document.prescriptionForm.night.value,
            "patientId":patientId
        }
        if(isEdit){
            prescription["id"]=id;
        }
                            $.ajax({
                                  type: 'POST',
                                  url: "/add-edit-prescription",
                                  headers: {"Authorization": window.localStorage.getItem("token")},
                                  data: JSON.stringify(prescription),
                                  contentType: "application/json",
                                  success: function(resultData) {
                                        if(isEdit){
                                            alert("Prescription Updated Successfully");
                                        }else{
                                            alert("Prescription Added Successfully");
                                        }
                                        getPrescriptionData();
                                   },
                                  error: function(error){
                                    alert(error.responseJSON.message);
                                  }
                            });


}

function add(){
    isEdit=false;
    id=undefined;
    $("#add-prescriptions").show();
    $("#add-edit-title").text("Add Prescription");
    $("#edit-save").text("Save");
    clearPrescription();
}
function cancel(){
    isEdit=false;
    id=undefined;
    $("#add-prescriptions").hide();
    clearPrescription();
}

function edit(prescription){
    isEdit=true;
    id=prescription.id;
    $("#add-prescriptions").show();
    $("#edit-save").text("Edit");
    $("#add-edit-title").text("Edit Prescription");
    document.prescriptionForm.drugName.value=prescription.drugName;
    document.prescriptionForm.dosage.value=prescription.dosage;
    document.prescriptionForm.notes.value=prescription.notes;
    document.prescriptionForm.morning.value=prescription.morning;
    document.prescriptionForm.noon.value=prescription.noon;
    document.prescriptionForm.night.value=prescription.night;
}

function clearPrescription(){
    document.prescriptionForm.drugName.value="";
    document.prescriptionForm.dosage.value="";
    document.prescriptionForm.notes.value="";
    document.prescriptionForm.morning.value="";
    document.prescriptionForm.noon.value="";
    document.prescriptionForm.night.value="";
}

function deletePrescription(id){
    $('#prescription-list').html("");
                            $.ajax({
                                      type: 'POST',
                                      url: "/delete-prescription?id="+id,
                                      headers: {"Authorization": window.localStorage.getItem("token")},
                                      contentType: "application/json",
                                      success: function(resultData) {
                                            alert("Prescription Deleted Successfully");
                                            getPrescriptionData();
                                       },
                                      error: function(error){
                                        alert(error.responseJSON.message);
                                      }
                                });
}