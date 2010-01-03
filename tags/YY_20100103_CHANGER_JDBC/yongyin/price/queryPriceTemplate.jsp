<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="��վ�۸�ģ���б�" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/fanye.js"></script>
<script language="javascript">
function querys()
{
	formEntry.submit();
}

function press()
{
	if (window.common.getEvent().keyCode == 13)
	{
		querys();
	}
}

function addBean()
{
	document.location.href = '../admin/price.do?method=preForAddPriceTemplate';
}


function dels(id)
{
	if (window.confirm('ȷ��ɾ����վ�۸�ģ��'))
	document.location.href = '../admin/price.do?method=delPriceTemplate&productId=' + id;
}

function adds(id)
{
	document.location.href = '../admin/price.do?method=preForAddPrice&productId=' + id;
}

function resets()
{
	formEntry.reset();

	$('productName').value = '';
	$('productcode').value = '';
}

function load()
{
	loadForm();
	$f('productcode');
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../admin/price.do"><input
	type="hidden" name="method" value="queryPriceTemplate">
<input type="hidden" value="1" name="firstLoad"> 
<p:navigation
	height="22">
	<td width="550" class="navigation">��վ�۸�ģ����� &gt;&gt; ��վ�۸�ģ���б�</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center" >��Ʒ����</td>
				<td align="center" width="35%"><input type="text" name="productCode" value="${productCode}" onkeypress="press()"></td>
				<td align="center" >��Ʒ����</td>
				<td align="center" width="35%"><input type="text" name="productName" value="${productName}" onkeypress="press()"></td>
			</tr>
			
			<tr align=center class="content1">
				<td align="right" colspan="4"><input type="button" class="button_class"
				value="&nbsp;&nbsp;�� ѯ&nbsp;&nbsp;" onclick="querys()">&nbsp;&nbsp;
				<input type="button" class="button_class"
				value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onclick="resets()">
				</td>
			</tr>
		</table>

	</p:subBody>


	<p:title>
		<td class="caption"><strong>��վ�۸�ģ���б�</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0" id="result">
			<tr align=center class="content0">
				<td align="center" class="td_class" onclick="tableSort(this)" width="20%"><strong>��Ʒ����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)" width="20%"><strong>��Ʒ����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)" width="45%"><strong>��վ</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)" width="15%"><strong>����</strong></td>
			</tr>

			<c:forEach items="${list}" var="item" varStatus="vs">
				<tr class="${s.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center" onclick="hrefAndSelect(this)">${item.productName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.productCode}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.webs}</td>
					<td align="center" onclick="hrefAndSelect(this)">
					<c:if test="${user.role == 'TOP'}">
					<a title="ɾ����վ�۸�ģ��" href="javascript:dels('${item.productId}')">
					<img src="../images/del.gif" border="0" height="15"></a>
					</c:if>
					<c:if test="${user.role == 'PRICE'}">
					<a title="���Ӳ�Ʒ��վ�۸�" href="javascript:adds('${item.productId}')">
					<img src="../images/change.gif" border="0" height="15"></a>
					</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>

		<p:formTurning form="formEntry" method="queryPriceTemplate"></p:formTurning>
	</p:subBody>

	<p:line flag="1" />

	<c:if test="${user.role == 'TOP'}">
	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" name="adds"
			style="cursor: pointer" value="&nbsp;&nbsp;������վ�۸�ģ��&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>
	</c:if>

	<p:message />

</p:body></form>
</body>
</html>

