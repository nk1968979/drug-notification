function logout(){
    window.localStorage.removeItem("token");
    window.location.href="/signin";
}

$(document).ready(function(){
   $.expr[':'].contains = function(a, i, m) {
     return $(a).text().toUpperCase()
         .indexOf(m[3].toUpperCase()) >= 0;
   };
  // Search all columns
  $('#txt_searchall').keyup(function(){
    // Search Text
    var search = $(this).val();

    // Hide all table tbody rows
    $('#dataTable tbody tr').hide();

    // Count total search result
    var len = $('#dataTable tbody tr:not(.notfound) td:contains("'+search+'")').length;
    if(len > 0){
      // Searching text in columns and show match row
      $('#dataTable tbody tr:not(.notfound) td:contains("'+search+'")').each(function(){
        $(this).closest('tr').show();
      });
    }else{
      $('#dataTable .notfound').show();
    }

  });
})