<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�ȵ��Ʒ�б�" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	document.location.href = '../admin/addHotProduct.jsp';
}

function press()
{
	if (window.common.getEvent().keyCode == 13)
	{
		querys();
	}
}

function load()
{
	loadForm();
}

function del(id)
{
	if (window.confirm('ȷ��ɾ���ȵ��Ʒ?'))
	{
		$('method').value = 'delHotProduct';
		$('id').value = id;
		formEntry.submit();
	}
}

function queryStat()
{
	document.location.href = '../admin/product.do?method=queryStatHotProduct&load=1';
}

</script>

</head>
<body class="body_class" onload="load()"
	onkeypress="tooltip.bingEsc(event)">
<form name="formEntry" action="../admin/product.do"><input
	type="hidden" name="id" value=""> <input type="hidden"
	name="method" value=""> <p:navigation height="22">
	<td width="550" class="navigation">�ȵ��Ʒ���� &gt;&gt; �ȵ��Ʒ�б�</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>�ȵ��Ʒ�б�</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center" class="td_class" onclick="tableSort(this, true)"
					width="10%"><strong>����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="30%"><strong>��Ʒ����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="20%"><strong>��Ʒ����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="30%"><strong>ʱ��</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="10%"><strong>����</strong></td>
			</tr>

			<c:forEach items="${list}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center" onclick="hrefAndSelect(this)">
					${item.orders}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.productName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.productCode}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.logTime}</td>
					<td align="center" onclick="hrefAndSelect(this)"><c:if
						test="${user.role == 'STOCK'}">
						<a title="ɾ���ȵ�" href="javascript:del('${item.id}')"> <img
							src="../images/del.gif" border="0" height="15" width="15"></a>
					</c:if></td>
				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		<c:if test="${user.role == 'STOCK'}">
			<input type="button" class="button_class" name="adds"
				style="cursor: pointer" value="&nbsp;&nbsp;�����ȵ��Ʒ&nbsp;&nbsp;"
				onclick="addBean()">
		</c:if>
		&nbsp;&nbsp;
		<input type="button" class="button_class" name="adds"
			style="cursor: pointer" value="&nbsp;&nbsp;�ȵ��Ʒͳ��&nbsp;&nbsp;"
			onclick="queryStat()"></div>
	</p:button>

	<p:message />

</p:body></form>
</body>
</html>

