<%@ page language="java" contentType="text/html;charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTDHTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Hello World!</title>

</head>

<body>

	  <form method="POST" enctype="multipart/form-data" action="/upload">
        <p>
            文件：<input type="file" name="file" />
        </p>
        <p>
            <input type="submit" value="上传" />
        </p>
    </form>



</body>

</html>