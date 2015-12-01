<%-- 
    Document   : listAuthors
    Created on : Sep 21, 2015, 9:36:05 PM
    Author     : jlombardo
    Purpose    : display list of author records and (in the future) provide
                 a way to add/edit/delete records
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Author List (Ajax)</title>
        <link href="resources/css/app.css" rel="stylesheet" type="text/css"/>
    </head>
    <body class="gamesList">
        <h1>Author List (Ajax)</h1>
        <p style="width: 50%;">Note that only the table rows, after the header, are updated. There 
            is no complete page refresh because we're using Ajax to get the 
            data in JSON format from the server and then use client-side 
            JavaScript to take that data and append rows to the table.
        </p>

        <table id="authorListTable" style="width: 50%;" border="1" cellspacing="0" cellpadding="4">
            <thead>
                <tr style="background-color: black;color:white;">
                    <th align="left" class="tableHead">ID</th>
                    <th align="left" class="tableHead">Author Name</th>
                    <th align="right" class="tableHead">Date Added</th>
                    <th align="right" class="tableHead">Date Added</th>
                    <th align="right" class="tableHead">Date Added</th>
                    <th align="right" class="tableHead">Date Added</th>
                </tr>
            </thead>
            <tbody id="videogameTblBody">

            </tbody>
        </table>

        <table id="editDeleteGames" style="display:none;" width="500" border="1" cellspacing="0" cellpadding="4">
            <tr>
                <td style="background-color: black;color:white;" align="left">ID</td>
                <td align="left"><input type="text" value="" id="videogameId" name="videogameId" readonly/></td>
            </tr>         
            <tr>
                <td style="background-color: black;color:white;" align="left">title</td>
                <td align="left"><input type="text" value="" id="title" name="title" /></td>
            </tr>
            <tr>
                <td style="background-color: black;color:white;" align="left">system</td>
                <td align="left"><input type="text" value="" id="system" name="system" /></td>
            </tr>
            <tr>
                <td style="background-color: black;color:white;" align="left">price</td>
                <td align="left"><input type="text" value="" id="price" name="price" /></td>
            </tr>
            <tr>
                <td style="background-color: black;color:white;" align="left">log date</td>
                <td align="left"><input type="text" value="" id="logDate" name="logDate" readonly /></td>
            </tr>
            <tr>
                <td style="background-color: black;color:white;" align="left">image Url</td>
                <td align="left"><input type="text" value="" id="imageUrl" name="imageUrl" readonly /></td>
            </tr>   
            
        </table>

        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize>   


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" type="text/javascript"></script>
        <!-- <script src="resources/js/app.js" type="text/javascript"></script> -->
        <script>
            (function ($, window, document) {
                $(function () {
                    // ==================================================================
                    // Private properties
                    // ==================================================================

                    // normal properties
                    var curDate = new Date();
                    var authorsBaseUrl = "VideogameController";

                    // properties that cache JQuery selectors
                    var $document = $(document);
                    var $body = $('body');
                    var $videogameTblBody = $('#videogameTblBody');


                    // ==================================================================
                    // Private event handlers and functions
                    // ==================================================================

                    /*
                     * This is a JQuery-specific event that only fires after all HTML 
                     * is loaded, except images, and the DOM is ready. You must be careful 
                     * to only act on DOM elements from JavaScript AFTER they have been 
                     * loaded.
                     * 
                     * Gets a collection of author objects as a JSON array from the server.
                     */
                    $document.ready(function () {
                        console.log("document ready event fired!");

                        // Make sure we only do this on pages with an author list
                        if ($body.attr('class') === 'gamesList') {
                            $.ajax({
                                type: 'GET',
                                url: authorsBaseUrl + "?action=listAjax",
                                success: function (games) {
                                    displayGames(games);
                                },
                                error: function (jqXHR, textStatus, errorThrown) {
                                    alert("Could not get authors for this user due to: " + errorThrown.toString());
                                }
                            });
                        }
                    });
                    /*
                     * Loops over the authors collection returned by the server and 
                     * extracts individual author objects and their properties, then 
                     * builds table rows and columns using this data. Each row is 
                     * dynamically appended to the table body DOM element.
                     */
                    function displayGames(games) {
                        $.each(games, function (index, game) {
                            var row = '<tr class="gameListRow">' +
                                    '<td>' + game.videogameId + '</td>' +
                                    '<td>' + game.title + '</td>' +
                                    '<td>' + game.system + '</td>' +
                                    '<td>' + game.price + '</td>' +
                                    '<td>' + game.logDate + '</td>' +
                                    '<td>' + game.imageUrl + '</td>' +
                                    '</tr>';
                            $videogameTblBody.append(row);
                        });
                    }


                    $videogameTblBody.on('click', 'tr', function () {
                        console.log('row click event fired');
                        var videogameId = $(this).find("td").contents()[0].data;
                        console.log(videogameId);
                        $.ajax({
                            type: 'POST',
                            contentType: 'application/json',
                            url: authorsBaseUrl + "?action=findByIdAjax",
                            dataType: "json",
                            data: JSON.stringify({"videogameId": videogameId}),
                            success: function (game) {
                                $('#editDeleteGames').show();
                                $('#videogameId').val(game.videogameId);
                                $('#title').val(game.title);
                                $('#system').val(game.system);
                                $('#price').val(game.price);
                                $('#logDate').val(game.logDate);
                                $('#imageUrl').val(game.imageUrl);
                            },
                            error: function (jqXHR, textStatus, errorThrown) {
                                alert("Could not get author by id due to: " + errorThrown.toString());
                            }
                        });
                    });

                    $videogameTblBody.on('mouseover', 'tr', function () {
                        console.log('row mouseover event fired');
                        $(this).toggleClass('hover');
                    });

                    $videogameTblBody.on('mouseout', 'tr', function () {
                        console.log('row mouseout event fired');
                        $(this).toggleClass('hover');
                    });
                    
              
                    $('#editDeleteGames > tbody:last-child').append('<tr><td><button type="button" id="delete" value="' +$('#videogameId') +'"> </button> </td></tr>');
                    $('#delete').on('click', function () {
                        console.log('delete click event fired');
                        var videogameId = $('#delete').val();
                        console.log(videogameId);
                        $.ajax({
                            type: 'POST',
                            contentType: 'application/json, charset=utf-8"',
                            url: authorsBaseUrl + "?action=deleteAjax",
                            dataType: "json",
                            data: JSON.stringify({"videogameId": videogameId}),
                            success: function (game) {
                         
                                $('#videogameId').val(game.videogameId);
                                $('#title').val(game.title);
                                $('#system').val(game.system);
                                $('#price').val(game.price);
                                $('#logDate').val(game.logDate);
                                $('#imageUrl').val(game.imageUrl);
                            },
                                  
                            error: function (jqXHR, textStatus, errorThrown) {
                                alert("Could not get author by id due to: " + errorThrown.toString());
                            }
                        });
                    });

                });
            }(window.jQuery, window, document));</script>
    </body>
</html>
