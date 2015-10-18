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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <title>Edit/Delete Book</title>
    </head>
    <body>
        
        <div class="container">
                 
                
            <h2>Update a game</h2>
             </div>
        <div class="row container">
            <div class="col-md-4">
                <form method="POST" action="VideogameController?action=delete" role="form" >
                    
            <table>
                
                 <tr>
                    <td>
                        <input class="form-control" type="hidden" name="gameId" value="${gameId}" />
                    </td>
                </tr>
                <tr>
                    <td>
                        Title <input class="form-control" type="text" name="title" value="${title}"/>
                    </td>
                </tr>
                <tr>
                    <td>
            System <input class="form-control" type="text" name="system" value="${system}"/>
                    </td>
            </tr>
       
             <tr>
                <td>
                    <!-- type can be date but need to figure out how to convert it to the right format  -->
            Date Logged <input class="form-control" type="date" name="logDate" value="${logDate}"/>
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
            
               <tr>
                <td>
                    <button class="btn btn-danger" id="submit" name="submit" value="delete" type="submit" >delete</button>
                </td>
                 <td>
                    <button class="btn btn-primary" id="submit" name="submit" value="update" type="submit" >update</button>
                </td>
            </tr>
           
             </table>
                
            
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
