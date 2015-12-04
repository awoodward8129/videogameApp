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
     
                <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/additional-methods.js"></script>
        <Script src="validation.js">        </Script>
        <title>Add a Game</title>
    </head>
    <body>
         <div class="container">
                 
                
            <h2>Add a game</h2>
            
        <div class="row container">
            <div class="col-md-4">
                <form id="form" method="POST" action="VideogameController?action=add" role="form"  >
                    
            <div class="control-group">
            <label class="control-label" for="name">Title</label>
            <div class="controls">
                <input type="text" name="title" id="name" placeholder="Game Title">
            </div>
        </div>
          <div class="control-group">
            <label class="control-label" for="system">System</label>
            <div class="controls">
                <input type="text" name="system" id="system" placeholder="System Name">
            </div>
        </div>
         <div class="control-group">
            <label class="control-label" for="logDate">Log Date</label>
            <div class="controls">
                <input type="text" name="logDate" id="logDate" >
            </div>
        </div>
         <div class="control-group">
            <label class="control-label" for="price">Price</label>
            <div class="controls">
                <input type="text" name="price" id="price" placeholder="12.00">
            </div>
        </div>
         <div class="control-group">
            <label class="control-label" for="image">Image URL</label>
            <div class="controls">
                <input type="text" name="image" id="image" placeholder="www.myimage.com">
            </div>
        </div>
                     <div class="control-group">
            <label class="control-label" for="systemId">System Id</label>
            <div class="controls">
                <input type="text" name="systemId" id="systemId" placeholder="1">
            </div>
        </div>
        
                <button class="btn btn-success" type="submit">Submit Game</button>
        </form>
                
            </div>
            
        </div>
          </div>
    </body>

</html>
