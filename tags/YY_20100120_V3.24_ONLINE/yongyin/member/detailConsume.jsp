<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="��Ա������ϸ" />
<script language="JavaScript" src="../js/key.js"></script>

<script language="javascript">

</script>

</head>
<body class="body_class">
<form><p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: hand"
		onclick="javascript:history.go(-1)">��Ա���ѹ���</span> &gt;&gt; ��Ա������ϸ</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="80%">

	<p:title>
		<td class="caption"><strong>��Ա������Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="85%">
		<p:table cells="1">
			<p:cell title="��Ա">${bean.cardNo}</p:cell>
			<p:cell title="�ͻ�">${bean.memberName}</p:cell>
			<p:cell title="��Ʒ">${bean.productName}</p:cell>
			<p:cell title="��Ʒ����">${bean.amount}</p:cell>
			<p:cell title="��Ʒ����">${bean.price}</p:cell>
			<p:cell title="ԭʼ���">${bean.precost}</p:cell>
			<p:cell title="�ۿ�">${bean.rebate}</p:cell>
			<p:cell title="ʵ�����ѽ��">${bean.cost}</p:cell>
			<p:cell title="��ע">${bean.description}</p:cell>
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="87%" rightWidth="13%">
		<div align="right"><input type="button" class="button_class"
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

