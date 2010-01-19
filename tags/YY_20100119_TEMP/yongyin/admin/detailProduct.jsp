<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="��Ʒ��ϸ" />
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/common.js"></script>

<script language="javascript">

</script>

</head>
<body class="body_class">
<form><p:navigation height="22">
	<td width="550" class="navigation">��Ʒ��ϸ</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>��Ʒ��Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:table cells="1">
			<p:cell title="��Ʒ����">
			${bean.name}
			</p:cell>

			<p:cell title="��Ʒ����">
			${bean.code}
			</p:cell>

			<p:cell title="��Ʒ����">
				<select name="genre" values="${bean.genre}" readonly="true">
					<option value="">--</option>
					<c:forEach items="${list}" var="item">
						<option value="${item.id}">${item.name}</option>
					</c:forEach>
				</select>
			</p:cell>

			<p:cell title="�̵����">
				<select name="type" oncheck="notNone;" values="${bean.type}"
					readonly="true">
					<option value="">--</option>
					<option value="0">ÿ���̵�</option>
					<option value="1">����</option>
				</select>
			</p:cell>

			<p:cell title="��ƷͼƬ">
				<img src="${rootUrl}${bean.picPath}?${random}">
			</p:cell>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
	</p:button>
</p:body></form>
</body>
</html>

