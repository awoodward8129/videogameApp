<%-- 
    Document   : editDeleteBook
    Created on : Sep 28, 2015, 11:13:55 AM
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
        <title>Edit/Delete System</title>
    </head>
    <body>
          <form method="POST" action="SystemController?action=delete">
            <table>
              
                <c:choose>
                    <c:when test="${not empty system}">
                        <tr>
                            <td style="background-color: black;color:white;" align="left">ID</td>
                            <td align="left"><input type="text" value="${system.systemId}" name="systemId" readonly/></td>
                        </tr>         
                    </c:when>
                </c:choose>
                        
                <tr>
                    <td style="background-color: black;color:white;" align="left">Title</td>
                    <td align="left"><input type="text" value="${system.systemName}" name="systemName" /></td>
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
        </form>
    </body>
</html>
