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
        <Script src="validation.js">     </Script>
    </head>
    
      <body>
         <div class="container">
                 
                
            <h2>Update a game</h2>
            
        <div class="row container">
            <div class="col-md-4">
               
            
    
    
       <form method="POST" action="VideogameController?action=deleteOrEdit" role="form">
            <table>
                   <c:choose>
                    <c:when test="${not empty game}">
                        <tr>
                            <td style="background-color: black;color:white;" align="left">ID</td>
                            <td align="left"><input type="text" value="${game.videogameId}" name="gameId" id="gameId" readonly/></td>
                        </tr>         
                    </c:when>
                </c:choose>
                        
                 <c:choose>
                    <c:when test="${not empty game}">
                <tr>
                    <td>
                  Name <input type="text" name="title" value="${game.title}"/>
                    </td>.
                </tr>
                          <tr>
                <td>
                    
            Date Added <input type="text" name="logDate" value= "${game.logDate}"/>
                </td>
            </tr>
                     <tr>
                <td>
                    
            price <input type="text" name="price" value= "${game.price}"/>
                </td>
            </tr>
                     <tr>
                <td>
                    
            System <input type="text" name="system" value= "${game.system}"/>
                </td>
            </tr>
                   <tr>
                <td>
                    
            ImageUrl <input type="text" name="imageUrl" value= "${game.imageUrl}"/>
                </td>
            </tr>
               
                  </c:when>
                </c:choose>
              

                
                        <tr>
                    <td style="background-color: black;color:white;" align="left">Author</td>
                    <td align="left">
                    <select id="authorDropDown" name="systemId">
                    <c:choose>
                        <c:when test="${not empty game.systemId}">
                            
                            <c:forEach var="system" items="${systems}">                                       
                                <option value="${system.systemId}" <c:if test="${game.systemId.systemId == system.systemId}">selected</c:if>>${system.systemName}</option>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                        <option value="">None</option>
                         </c:otherwise>
                    </c:choose>
                    </select>
                </td>

                  
             
               
            <tr>
                <td>
                    <button id="submit" name="submit" value="delete" type="submit" >delete</button>
                </td>
                 <td>
                    <button id="submit" name="submit" value="update" type="submit" >update</button>
                </td>
            </tr>
             </table>
        </form>
    </body>
</html>
