/* 
 * This is the client-side logic for connecting to RESTful Web Services on 
 * the server-side via the HotelRepository class.
 * 
 * This code is heavily dependent on JQuery 2.x. Despite this fact all client-
 * side code is language and server framework agnostic. Any server-side
 * framework that can deliver RESTful, non-HATEOUS style web services can be used.
 * 
 * References:
 * - Wikipedia: http://en.wikipedia.org/wiki/HATEOS
 */

// The root URL for the RESTful services
// NOTE: you MUST use https to make this secure! And the port number is
// specific to Glassfish developer mode. In production this would be 443
var rootURL = "https://localhost:8181/videogameApp/api/v1/systems";
var currentHotel;

// Retrieve hotel list when application starts
findAll();


function findAll() {
    console.log('findAll');
          $.ajax({
                    type: 'GET',
                    url: rootURL,
                    success: function (embedded) {
                        displaySystems(embedded._embedded.systems);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert("Could not get systems for this user due to: " + errorThrown.toString());
                    }
                });
    
  
  }
  

      function displaySystems(systems) {
            $.each(systems, function (index, system) {
                var row = '<tr class="authorListRow">' +
                        '<td></td>' +
                        '<td>' + system.systemName + '</td>' +
                      
                        '</tr>';
                $('#hotelList').append(row);
            });
        }

/**function renderList(data) {
    // JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
    var list = data == null ? [] : (data instanceof Array ? data : [data]);

    $('#hotelList li').remove();
    $.each(list, function(index, obj) {
        $('#hotelList').append('<li><a href="#" data-identity="' + rootURL + '/' + obj.systemId +'">' + obj.systemName + '</a></li>');
    });
}
**/
