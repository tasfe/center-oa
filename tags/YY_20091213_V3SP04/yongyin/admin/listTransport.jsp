<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�����б�" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="javascript">
function addApplys()
{
	transportForm.submit();
}

function dels()
{
	if (getRadioValue('transports') == '')
	{
		alert('��ѡ������');
		return;
	}
	if (window.confirm('ȷ��ɾ�������䷽ʽ?'))
	document.location.href = '../admin/transport.do?method=delTransport&id=' + getRadioValue('transports');
}

</script>

</head>
<body class="body_class">
<form name="transportForm" action="../admin/transport.do"><input
	type="hidden" name="method" value="preForAddTransport"> <p:navigation
	height="22">
	<td width="550" class="navigation">�������  &gt;&gt; �������</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="90%">

	<p:title>
		<td class="caption"><strong>�����б�</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="85%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center" class="td_class">ѡ��</td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="35%"><strong>��������</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>��������</strong></td>
			</tr>

			<c:forEach items="${transportList}" var="item" varStatus="vs">
				<tr class="${s.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type="radio" name="transports"
						value="${item.id}" ${vs.index== 0 ? "checked" : ""}/></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.name}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.parent}</td>
				</tr>
			</c:forEach>
		</table>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="87%" rightWidth="13%">
		<div align="right"><input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;��������&nbsp;&nbsp;"
			onclick="addApplys()">&nbsp;&nbsp;<input type="button"
			class="button_class" value="&nbsp;&nbsp;ɾ������&nbsp;&nbsp;"
			onclick="dels()"></div>
	</p:button>

	<p:message />
	
</p:body></form>
</body>
</html>

