<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�̵��б�" />
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="javascript">

</script>

</head>
<body class="body_class">
<form> <p:navigation
	height="22">
	<td width="550" class="navigation">�̵���� &gt;&gt; ��־�б�</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>��־�б�</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>��ˮ��</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>��λ</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>��Ʒ</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>ǰ����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>�춯����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>������</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>��������</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>������Ա</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>ʱ��</strong></td>
			</tr>

			<c:forEach items="${listStorageLog}" var="item"
				varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center" onclick="hrefAndSelect(this)">${item.serializeId}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.depotpartName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.storageName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.productName}</td>
					
					<td align="center" onclick="hrefAndSelect(this)">${item.preAmount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.changeAmount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.afterAmount}</td>
					<%
					    String[] sss = new String[] {"��ʼ��", "�޸�", "����ͨ��", "���ͨ��", "����ת��", "��Ʒ�ϳ�/�ֲ�"};
					    request.setAttribute("sss", sss);
					%>
					<td align="center" onclick="hrefAndSelect(this)">${my:getValue(item.type, sss)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.user}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.description}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.logTime}</td>
				</tr>
			</c:forEach>
		</table>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="97%" rightWidth="3%">
		<div align="right"><input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;">
		</div>
	</p:button>

	<p:message />

</p:body></form>
</body>
</html>

