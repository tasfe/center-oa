<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="���ӻ�Ա" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>

<script language="javascript">
function addBean()
{
	if (parseInt($$('costpoint')) > ${bean.usepoint})
	{
		alert('���ֲ���,���ܶһ�');

		return;
	}

	if (parseInt($$('costpoint')) <= 0)
	{
		alert('����ʹ�û���');

		return;
	}

	submit('ȷ�����ӻ�Ա���ֶһ���Ϣ?');
}

function load()
{
	loadForm();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../member/member.do">
	<input type="hidden" name="method" value="addExchange">
	<input type="hidden" name="memberId" value="${bean.id}">
<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: hand"
		onclick="javascript:history.go(-1)">��Ա����</span> &gt;&gt; ���ֶһ�</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>���ֶһ���Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="80%">
		<p:class value="com.china.centet.yongyin.bean.ExchangeBean" />

		<p:table cells="1">
			<p:cell title="��Ա">${bean.cardNo}</p:cell>
			<p:cell title="�ɶһ�����">${bean.usepoint}</p:cell>
			<p:pro field="entity" />
			<p:pro field="costpoint"/>
			<p:pro field="description" innerString="cols=80 rows=3" cell="2"/>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="87%" rightWidth="13%">
		<div align="right"><input type="button" class="button_class" name="adds"
			style="cursor: pointer" value="&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;"
			onclick="addBean()">&nbsp;&nbsp; <input type="button"
			class="button_class" onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
	</p:button>
</p:body></form>
</body>
</html>

