<%-- 
    Document   : addBook
    Created on : Sep 25, 2015, 3:19:20 PM
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
        <title>Add a Book</title>
    </head>
    <body>
        <h1>Add a System</h1>
        
         <form method="POST" action="SystemController?action=add">
            <table>
                <tr>
                    <td>
                   Name <input type="text" name="systemName" value=""/>
                    </td>
                </tr>
           
            <tr>
                <td>
                      <button type="submit">submit</button>
                </td>
            </tr>
             </table>
        </form>
    </body>
</html>
