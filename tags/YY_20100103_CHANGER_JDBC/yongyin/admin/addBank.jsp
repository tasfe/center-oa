<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="��������" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<c:if test="${modify}">
<c:set var="dis" value="�޸�"/>
</c:if>
<c:if test="${!modify}">
<c:set var="dis" value="����"/>
</c:if>
<script language="javascript">
function addApplys()
{
	submit('ȷ��${dis}����[' + $$('name') + ']');
}

function load()
{
	loadForm();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../admin/common.do">
<input type="hidden" name="id" value="${bank.id}">
<c:if test="${modify}">
	<input type="hidden" name="method" value="modfiyBank">
</c:if> 
<c:if test="${!modify}">
	<input type="hidden" name="method" value="addBank">
</c:if> 
<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: hand"
		onclick="javascript:history.go(-1)">���й���</span> &gt;&gt; ${dis}����</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="80%">

	<p:title>
		<td class="caption"><strong>������Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="75%">
		<p:class value="com.china.centet.yongyin.bean.BankBean" />

		<p:table cells="1">

			<p:pro field="name" value="${bank.name}" innerString='${modify ? "readonly=readonly" : ""}'/>

			<p:pro field="locationId" value="${bank.locationId}">
				<option value="">--</option>
				<c:forEach items="${locationList}" var="item">
					<option value="${item.id}">${item.locationName}</option>
				</c:forEach>
			</p:pro>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="87%" rightWidth="13%">
		<div align="right"><input type="button" class="button_class" name="adds"
			style="cursor: pointer" value="&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;"
			onclick="addApplys()">&nbsp;&nbsp; <input type="button"
			class="button_class" onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
	</p:button>

	<p:message/>
</p:body></form>
</body>
</html>

