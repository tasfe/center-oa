<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�����쳣����" />
<script language="JavaScript" src="../js/key.js"></script>

<script language="javascript">

</script>

</head>
<body class="body_class">
<form><p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: hand"
		onclick="javascript:history.go(-1)">�����쳣�������</span> &gt;&gt; �����쳣������ϸ</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="80%">

	<p:title>
		<td class="caption"><strong>�����쳣������Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="75%">
		<p:class value="com.china.centet.yongyin.bean.ProctExceptionBean" />

		<p:table cells="1">

			<p:cell title="��Ʒ����">${bean.productName}</p:cell>
			<p:cell title="��Ʒ����">${bean.amount}</p:cell>
			<p:cell title="������">${bean.applyer}</p:cell>
			<%
			    String[] sss1 = new String[] {"����", "�ύ", "����", "ͨ��"};
			    request.setAttribute("sss1", sss1);
			%>
			<p:cell title="����״̬">${my:getValue(bean.status, sss1)}</p:cell>
			<p:cell title="����ԭ��">${bean.description}</p:cell>
			<p:cell title="����">
				<a title="������ظ���"
					href="../admin/proException.do?method=downProductException&id=${bean.id}">${bean.fileName}</a>
			</p:cell>
			<p:cell title="�������">${bean.apply}</p:cell>

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

