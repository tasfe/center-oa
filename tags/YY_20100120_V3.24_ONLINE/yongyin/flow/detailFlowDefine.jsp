<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="���̶���" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>

<script language="javascript">
function load()
{
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry">
<p:navigation height="22">
	<td width="550" class="navigation">���̶�����ϸ</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>���̶�����Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:table cells="2">
			<p:cell title="��������">
			${bean.name}
			</p:cell>

			<p:cell title="����ʱ��">
			${bean.logTime}
			</p:cell>

			<p:cell title="������">
			${bean.userName}
			</p:cell>

			<p:cell title="״̬">
			${my:get('flowDefineStatus', bean.status)}
			</p:cell>

			<p:cells celspan="2" title="��ע">
			${bean.description}
			</p:cells>
		</p:table>
	</p:subBody>

	<p:tr />

	<p:subBody width="100%">
		<table width="100%" border="0" cellspacing='1' id="tables">
			<tr align="center" class="content0">
				<td width="10%" align="center">˳��</td>
				<td width="30%" align="center">��������</td>
				<td width="30%" align="center">������</td>
				<td width="30%" align="center">����ʽ</td>
			</tr>

			<c:forEach items="${bean.tokensVO}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<td align="center">${item.orders + 1}</td>

					<td align="center">${item.name}</td>

					<td align="center">${item.processerName}</td>

					<td align="center">${my:get('tokenType', item.type)}</td>
				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
	</p:button>

	<p:message/>
</p:body></form>
</body>
</html>

