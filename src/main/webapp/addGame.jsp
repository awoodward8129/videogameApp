<%-- 
    Document   : addGame
    Created on : Oct 12, 2015, 12:32:10 PM
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
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <title>Add a Game</title>
    </head>
    <body>
         <div class="container">
                 
                
            <h2>Add a game</h2>
             </div>
        <div class="row container">
            <div class="col-md-4">
                <form method="POST" action="VideogameController?action=add" role="form" >
                    
            <table>
                <tr>
                    <td>
                        Title <input class="form-control" type="text" name="title" value=""/>
                    </td>
                </tr>
                <tr>
                    <td>
            System <input class="form-control" type="text" name="system" value=""/>
                    </td>
            </tr>
       
             <tr>
                <td>
                    <!-- type can be date but need to figure out how to convert it to the right format  -->
            Date Logged <input class="form-control" type="date" name="logDate" value=""/>
                </td>
            </tr>
             <tr>
                <td>
                    
             Price <input type="text" class="form-control" name="price" value= "${price}"/>
                </td>
            </tr>
               <tr>
                <td>
                    
             Image <input type="text" class="form-control" name="image" value= "${image}"/>
                </td>
            </tr>
           
             </table>
                
                <button class="btn btn-primary" type="submit">submit</button>
        </form>
                
            </div>
            
        </div>
         
    </body>
</html>
