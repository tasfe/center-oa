<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<HTML>
<HEAD>
<p:link title="ϵͳ���ò˵�" cal="false"/>
<style type="text/css">
</style>
</HEAD>
<BODY class="tree_class">
<table>
	<tr height="10">
		<td colspan="2"></td>
	</tr>

	<tr height="10">
		<td width="15"></td>
		<td><font color="blue"><B>[${GLocationName}]
		${user.stafferName} ��½[3.0ʹ�ð�]</B></font></td>
	</tr>

	<tr height="10">
		<td colspan="2"></td>
	</tr>

	<tr>
		<td width="15"></td>
		<td><font color="blue"><B>���죺${GTime}</B></font></td>
	</tr>



	<tr height="10">
		<td colspan="2"></td>
	</tr>

	<c:forEach items="${menuItemList}" var="item">
		<tr>
			<td width="15"></td>
			<td><a href="${item.url}" target="main">${item.menuItemName}</a></td>
		</tr>

		<tr height="10">
			<td colspan="2"></td>
		</tr>
	</c:forEach>

	<tr>
		<td width="15"></td>
		<td><a href="../admin/modifyPassword.jsp" target="main">�޸�����</a></td>
	</tr>

	<tr height="10">
		<td colspan="2"></td>
	</tr>

	<tr>
		<td width="15"></td>
		<td><a href="../admin/logout.do" target="_parent">�˳���½</a></td>
	</tr>

	<tr height="10">
		<td colspan="2"></td>
	</tr>

</table>
</BODY>
</HTML>
