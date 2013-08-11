<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="WebForm1.aspx.cs" Inherits="WebService.WebForm1" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
</head>
<body>
    <form id="form1" method="post" enctype="multipart/form-data" action="http://localhost/webservice/resources.svc/setusericon?uid=29" runat="server" >
    <div>
        <input type="file" name="file" runat="server"/>
        <input type="submit" runat="server" title="submit"/>
    </div>
    </form>
</body>
</html>
