<%-- 
    Document   : editDeleteGame
    Created on : Oct 12, 2015, 1:19:29 PM
    Author     : Alex
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       
                <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/additional-methods.js"></script>
        <title>Edit/Delete Book</title>
               <Script>
     $(document).ready(function () {

$('#updateForm').validate({
    rules: {
        title: {
            minlength: 1,
            required: true
        },
        system: {
            minlength: 1,
            required: true
        },
        logDate: {
            required: true
        },
        price: {
            required: true,
            number: true
        },
        image: {
            required: true
        },

    },
    highlight: function (element) {
        $(element).closest('.control-group').removeClass('success').addClass('error');
    },
    success: function (element) {
        element.text('OK!').addClass('valid')
            .closest('.control-group').removeClass('error').addClass('success');
    }
     

});
});
        </Script>
    </head>
    <body>
        
        <div class="container">
                 
                
            <h2>Update a game</h2>
             </div>
        <div class="row container">
            <div class="col-md-4">
                <form id="updateForm" method="POST" action="VideogameController?action=delete" role="form" >
                    
         
                        <input class="form-control" type="hidden" name="gameId" value="${gameId}" />
                
                   <div class="control-group">
            <label class="control-label" for="name">Title</label>
            <div class="controls">
                <input type="text" name="title" id="name" value="${title}">
            </div>
        </div>
                 <div class="control-group">
            <label class="control-label" for="system">System</label>
            <div class="controls">
                <input type="text" name="system" id="system"value="${system}">
            </div>
        </div>
       <div class="control-group">
            <label class="control-label" for="logDate">Log Date</label>
            <div class="controls">
                <input type="date" name="logDate" id="logDate"  value="${logDate}">
            </div>
        </div>
            <div class="control-group">
            <label class="control-label" for="price">Price</label>
            <div class="controls">
                <input type="text" name="price" id="price"value="${price}">
            </div
               <div class="control-group">
            <label class="control-label" for="image">Image URL</label>
            <div class="controls">
                <input type="text" name="image" id="image" value="${image}">
            </div>
        </div>
              <button class="btn btn-danger" name="submit" value="delete" type="submit" >delete</button>
              <button class="btn btn-success" name="submit"  value="update" type="submit">Update Game</button>
        </form>
                
            </div>
            
        </div>
        
        
        
        
        
        
        <!--  <form method="POST" action="VideogameController?action=delete">
            <table>
                 <tr>
                    <td>
                  <input type="hidden" name="gameId" value="${gameId}" />
                    </td>
                </tr>
                
                <tr>
                    <td>
                   Title <input type="text" name="title" value="${title}"/>
                    </td>
                </tr>
                <tr>
                    <td>
            System <input type="text" name="system" value="${system}"/>
                    </td>
            </tr>
           
               <tr>
                <td>
                    
            Date Published <input type="date" name="logDate" value= "${logDate}"/>
                </td>
            </tr>
             <tr>
                <td>
                    
             Price <input type="text" name="price" value= "${price}"/>
                </td>
            </tr>
               <tr>
                <td>
                    
             Image <input type="text" name="image" value= "${image}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <button id="submit" name="submit" value="delete" type="submit" >delete</button>
                </td>
                 <td>
                    <button id="submit" name="submit" value="update" type="submit" >update</button>
                </td>
            </tr>
             </table>
        </form>-->
    </body>
</html>
