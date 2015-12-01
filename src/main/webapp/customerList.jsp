<%-- 
    Document   : gameList
    Created on : Oct 12, 2015, 12:04:38 PM
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
            <link rel="stylesheet" href="sitewide.css">  
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <title>Games List</title>
    </head>
    <body >
       
      
      <div class="text-right">
          <a href="#" >
            Number of Logged in Users:
              <span class="badge">${count}</span> 
        </a>
      </div>
   
       
       
      <!--  <div><a href="VideogameController?action=redirect">Redirect Test</a></div> -->
        
        <div class="container">
            <h1 class="text-center">Games List</h1>
     
              <div class="text-center" >
               <a class="btn btn-primary" href="CustomerController?action=addButton">Add a Game</a>
              <a class="btn btn-primary" href="CustomerController?action=redirect">Redirect Test</a> 
              </div>
            <hr>
            <div class="text-center">
                     <a href="#" >
            Number of Records Added
              <span class="badge" >${counter}</span> 
                  </a>
            </div>
                     

        <table class="table table-striped">
            <tr >
                <th align="left" class="tableHead">ID</th>
                <th align="left" class="tableHead">Video Game Title</th>
                <th align="left" class="tableHead">System</th>
              
                <th align="right" class="tableHead">Date Logged</th>
                <th align="left" class="tableHead">Price</th>
                <th align="left" class="tableHead">Image</th>
                <th></th>
            </tr>
        <c:forEach var="b" items="${customers}" varStatus="rowCount">
          
            <td align="left">${b.firstName}</td>
            <td align="left">${b.lastName}</td>
            <td align="left">${b.address}</td>
            
            <td align="left">
               ${b.city}
                
            </td>
            
            
             <td align="left">${b.state}</td>
            
           
            <td align="left"><img src ="${b.zip}"></img></td>
          
        </tr>
        </c:forEach>
        </table>
         </div>
            
        <c:if test="${errMsg != null}">
            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                ${errMsg}</p>
        </c:if>
    </body>
</html>
