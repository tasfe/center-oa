<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<html>
<head>
<title>-=�ֻ�����[V1.0]=-</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
</HEAD>
<BODY>
<FORM method=post>
<table align="center">
    <tr>
        <td align="center"><b><font color="blue">�� �� ϵ ͳ</font></b></td>
    </tr>

    <tr height="5%">
        <td align="center"></td>
    </tr>

    <tr height="35%">
        <td>
        <table width="100% border="0"  cellpadding="0" cellspacing="0">
            
            <c:if test="${user.role == 'COMMON'}">
            <tr>
                <td align="left">
                <a href="./step1.jsp">���߿���</a>
                </td>
            </tr>

            <tr height="20">
                <td align="right" />
            </tr>
            
            <tr>
                <td align="left">
                <a href="./listOut.jsp">�ҵĵ���</a>
                </td>
            </tr>
            </c:if>
            
            <tr height="20">
                <td align="right" />
            </tr>
            
            <tr>
                <td align="left">
                <a href="./menu.jsp">�� ҳ</a>&nbsp;&nbsp;<a href="../wap/checkuser.do?method=logout">�� ��</a>
                </td>
            </tr>

        </table>
        </td>
    </tr>

    <tr height="30%">
    </tr>
</table>
</form>
</body>
</html>
