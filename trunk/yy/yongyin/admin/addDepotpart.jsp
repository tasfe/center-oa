<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="���Ӳ���" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<c:if test='${param.add != "1"}'>
	<c:set var="dis" value="�޸�" />
	<c:set var="red" value="readonly=true" />
</c:if>
<c:if test='${param.add == "1"}'>
	<c:set var="dis" value="����" />
	<c:set var="red" value="" />
</c:if>
<script language="javascript">
function addApplys()
{
	submit('ȷ��${dis}����[' + $$('name') + ']');
}

function load()
{
	loadForm();
	$f($('name'));
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../admin/das.do"><input
	type="hidden" name="id" value="${depotpart.id}"> <c:if
	test='${param.add != "1"}'>
	<input type="hidden" name="method" value="updateDepotpart">
</c:if> <c:if test='${param.add == "1"}'>
	<input type="hidden" name="method" value="addDepotpart">
</c:if> <p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: hand"
		onclick="javascript:history.go(-1)">��������</span> &gt;&gt; ${dis}����</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="80%">

	<p:title>
		<td class="caption"><strong>������Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="75%">
	
		<p:class value="com.china.centet.yongyin.bean.DepotpartBean" />

		<p:table cells="1">

			<p:pro field="name" value="${depotpart.name}" />
			
			<p:pro field="type" value="${depotpart.type}" innerString="${red}">
				<option value="1">��Ʒ��</option>
				<option value="0">��Ʒ��</option>
				<option value="2">���ϲ�</option>
			</p:pro>

			<p:pro field="description" value="${depotpart.description}" />

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="87%" rightWidth="13%">
		<div align="right"><input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;"
			onclick="addApplys()">&nbsp;&nbsp; <input type="button"
			class="button_class" onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
	</p:button>

	<tr>
		<td colspan='2' align="center"><FONT color="blue">${MESSAGE_INFO}</FONT><FONT
			color="red">${errorInfo}</FONT></td>
	</tr>
</p:body></form>
</body>
</html>

