<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Notes</title>
    </head>
    <body>
        <h1>Enter a new password</h1>
        <form method="post" action="reset">
            <input type="password" name="newpassword"> <br>
            <input type="hidden" name="uuid" value=${uuid}>
            <input type="submit" value="submit">
        </form>
            <p>${user}</p>
    </body>
</html>