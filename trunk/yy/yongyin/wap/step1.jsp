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
<BODY bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
    marginheight="0">
<FORM name=adminLoginForm action="../wap/out.do" method=post><input
	type="hidden" name="method" value="step2" />
<table align="center">
	<tr>
		<td align="center"><b><font color="blue">������Ϣ</font></b>
		</td>
	</tr>
	
    <tr height="5%">
        <td align="center"></td>
    </tr>

	<tr>
		<td>
		<table width="100% border="0" cellpadding="0"
			cellspacing="0">
			<tr>
                <td align="right">
                  ��������
                </td>
                <td align="left">
                <c:forEach items="${allOutLocation}" var="item" varStatus="vs">
                <input type="radio" name="location" value="${item.id}" 
                ${gwapmap.location == item.id ? "checked=checked" : ""}
                ${(gwapmap.location == nil) && vs.first ? "checked=checked" : ""}
                />${item.locationName}
                </c:forEach>
                </td>
            </tr>
            
            <tr height="20">
                <td align="right" />
                <td align="left"></td>
            </tr>
			
			<tr>
                <td align="right">
                  ��������
                </td>
                <td align="left">
                <input type="radio" name="outType" value="0" ${gwapmap.outType == '0' ? "checked=checked" : ""} ${gwapmap.outType == nil ? "checked=checked" : ""}>���۳���
                <input type="radio" name="outType" value="1" ${gwapmap.outType == '1' ? "checked=checked" : ""}>��������
                </td>
            </tr>
            
            <tr height="20">
                <td align="right" />
                <td align="left"></td>
            </tr>

			<tr>
				<td align="right">
				<div align=left class="STYLE1">�ؿ�����</div>
				</td>
				<td align="left"><input name="reday" type="text" style="-wap-input-format: '*N'" value="${gwapmap.reday}"></td>
			</tr>

			<tr height="20">
				<td align="right" />
				<td align="left"></td>
			</tr>

			<tr>
				<td align="right">
				<div align="left" class="STYLE1">��������</div>
				</td>
				<td align="left"><input name="arriveDate" value="${gwapmap.arriveDate}" style="-wap-input-format: '8N'"
					type="text" class="input">(yyyyMMdd)</td>
			</tr>

			<tr height="20">
				<td align="right" />
				<td align="left"></td>
			</tr>

			<tr>
				<td align="right">
				<div align="left" class="STYLE1">��ע</div>
				</td>
				<td align="left">
				<textarea name="description" rows="4"><c:out value="${gwapmap.description}"/></textarea></td>
			</tr>

			<tr height="20">
				<td align="right" />
				<td align="left"></td>
			</tr>

			<tr height="20">
				<td align="right" />
				<td align="left"></td>
			</tr>

			<tr>
				<td align="left"></td>
				<td align="left"><input name="logins" type="submit" value="(1)��һ��"></td>
			</tr>
			
			<tr height="20">
                <td align="right" />
                <td align="left"></td>
            </tr>
            
            <tr>
                <td align="left" colspan='2'><font color=red>${errorInfo}</font></td>
                <c:remove var="errorInfo" scope="session" />
            </tr>
            
            <tr>
                <td align="left" colspan="2">
                <a href="./menu.jsp">�� ҳ</a>&nbsp;&nbsp;<a href="../wap/checkuser.do?method=logout">�� ��</a>
                </td>
            </tr>
			
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
