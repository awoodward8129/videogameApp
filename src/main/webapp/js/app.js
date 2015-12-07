/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function ($, window, document) {
    $(function () {
        //Private Properties
        var baseURL = "http://localhost:8080/videogameapp/api/v1/";
        var $tbody = $('tbody');

        findAllAuthors();

        function findAllSystems() {
            console.log('findAllGameSystems');
            $.ajax({
                type: 'GET',
                url: baseURL + "gameSystems",
                dataType: 'json',
                success: renderList
            });
        }

        function renderList(systemList) {
            $('tbody tr').remove();
            $.each(systemList, function (index, system) {
                $tbody.append('<tr><td>' + system.systemId + '</td><td> ' + system.title + '</td>'+
                        '<td><button class="deleteBtn" value="' + system.systemId + '">delete</button></td></tr>');
            });
        }

        $tbody.on('click', '.deleteBtn', function () {
            console.log('deleteSystem')
            $.ajax({
                type: 'DELETE',
                url: baseURL + 'system/' + $(this).val(),
                success: function (data, textStatus, jqXHR) {
                    alert('System deleted successfully');
                    findAllSystems();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert('delete author error');
                }
            });

        });
    });
}(window.jQuery, window, document));