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
      <!--  <h1>Session Demo</h1>
        <form method="POST" action="VideogameController?action=session">
            Enter page background color (per user): <input name="color" value="" /> <br>
            Enter font color (per application): <input name="fontColor" value="" /> <br>
            <input name="submit" value="Submit" type="submit">
        </form>
      <!--  <p><a href="page2.jsp">Click here</a> to go to Page 2</p>
        <p><a href="testsession.jsp">Click here</a> for Session Status</p>
        <h3 style='color: ${fontColor};'>For comparison, this font color comes from application scope</h3>-->
      
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
               <a class="btn btn-primary" href="VideogameController?action=addButton">Add a Game</a>
              <a class="btn btn-primary" href="VideogameController?action=redirect">Redirect Test</a> 
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
        <c:forEach var="b" items="${games}" varStatus="rowCount">
          
            <td align="left">${b.gameId}</td>
            <td align="left">${b.title}</td>
            <td align="left">${b.system}</td>
            
            <td align="left">
                <fmt:formatDate pattern="dd/MM/yyyy" value="${b.logDate}"></fmt:formatDate>
                
            </td>
            
            
             <td align="left">${b.price}</td>
            <td align="left"><img src =${b.image}/></td>
            <td><a class="btn btn-primary" href="VideogameController?action=editDeleteButton&gameId=${b.gameId}&title=${b.title}&system=${b.system}&logDate=${b.logDate}&price=${b.price}&image=${b.image}">Edit or Delete</a></td>
            
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
