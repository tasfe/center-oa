<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="������" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">
function addBean()
{
	submit('ȷ�����Ӱ󶨹�ϵ?');
}

function load()
{
	loadForm();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../admin/common.do" method="post"><input
	type="hidden" name="method" value="bingProductTypeToCustmer">
	<input
	type="hidden" name="customerId" value="${bean.id}"><p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: hand"
		onclick="javascript:history.go(-1)">�ͻ�����</span> &gt;&gt; ������</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="80%">

	<p:title>
		<td class="caption"><strong>�����ͣ�</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="90%">
		<p:table cells="1">

			<p:cell title="�ͻ�">
			${bean.name}
			</p:cell>

			<p:cell title="��Ʒ����">
				<c:forEach items="${list}" var="item">
					<input type="checkbox" name="productTypeId" value="${item.id}" ${my:length(mapVS[item.id]) > 0 ? "checked=true" : ""}>${item.name}
					<br>
				</c:forEach>
			</p:cell>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			name="adds" style="cursor: pointer"
			value="&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;" onclick="addBean()">&nbsp;&nbsp;
		<input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
	</p:button>

	<tr>
		<td colspan='2' align="center"><FONT color="blue">${MESSAGE_INFO}</FONT><FONT
			color="red">${errorInfo}</FONT></td>
	</tr>
</p:body></form>
</body>
</html>

