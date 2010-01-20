<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�ɹ���" />
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>

<script language="javascript">
function ask(id)
{
	document.location.href = '../stock/stock.do?method=preForSockAsk&id=${bean.id}&itemId=' + id;
}

function passTO()
{
	if (window.confirm('ȷ��ͨ���˲ɹ���?'))
	{
		$('pass').value = '1';
		formEntry.submit();
	}
}
</script>

</head>
<body class="body_class">
<form name="formEntry" action="../stock/stock.do" method="post">
<input type="hidden" name="method" value="updateStockStatus">
<input type="hidden" name="id" value="${bean.id}">
<input type="hidden" name="pass" value="1">
<p:navigation height="22">
	<td width="550" class="navigation">�ɹ�����ϸ</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>�ɹ�����Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:table cells="2">
			<p:cell title="����">
			${bean.id}
			</p:cell>

			<p:cell title="�ɹ���">
			${bean.userName}
			</p:cell>

			<p:cell title="����">
			${bean.locationName}
			</p:cell>

			<p:cell title="״̬">
			${my:get('stockStatus', bean.status)}
			</p:cell>

			<p:cell title="¼��ʱ��">
			${bean.logTime}
			</p:cell>

			<p:cell title="�赽��ʱ��">
			${bean.needTime}
			</p:cell>

			<p:cell title="����">
			${bean.flow}
			</p:cell>

			<p:cell title="�ܼƽ��">
			${my:formatNum(bean.total)}
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
				<td width="15%" align="center">�ɹ���Ʒ</td>
				<td width="10%" align="center">�ɹ�����</td>
				<td width="10%" align="center">��ǰ����</td>
				<td width="10%" align="center">�Ƿ�ѯ��</td>
				<td width="10%" align="center">�ο��۸�</td>
				<td width="10%" align="center">ʵ�ʼ۸�</td>
				<td width="20%" align="center">��Ӧ��</td>
				<td width="5%" align="center">�ϼƽ��</td>
				<td width="10%" align="center">ѯ ��</td>
			</tr>

			<c:forEach items="${bean.itemVO}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<td align="center">${item.productName}</td>

					<td align="center">${item.amount}</td>
					
					<td align="center">${item.productNum}</td>

					<td align="center">${item.status == 0 ? "<font color=red>��</font>" : "<font color=blue>��</font>"}</td>

					<td align="center">${my:formatNum(item.prePrice)}</td>

					<td align="center">${my:formatNum(item.price)}</td>

					<td align="center">${item.providerName}</td>

					<td align="center">${my:formatNum(item.total)}</td>

					<td align="center">
					<c:if test="${item.status == 0}">
					<a title="�ɹ�ѯ��"
						href="javascript:ask('${item.id}')">
					<img src="../images/change.gif" border="0" height="15" width="15"></a>
					</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			name="sub1" style="cursor: pointer"
			value="&nbsp;&nbsp;ͨ ��&nbsp;&nbsp;" onclick="passTO()">
			&nbsp;&nbsp;
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

