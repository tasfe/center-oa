<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="���ϼ۸��б�" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/fanye.js"></script>
<script language="javascript">
function querys()
{
	formEntry.submit();
}

function addBean()
{
	document.location.href = '../admin/price.do?method=preForAddPrice';
}

function press()
{
	if (window.common.getEvent().keyCode == 13)
	{
		querys();
	}
}

function resets()
{
	formEntry.reset();

	$('productName').value = '';
	$('productId').value = '';
	setSelectIndex($('status'), 0);
}

function load()
{
	loadForm();
}

function dels(id)
{
	if (window.confirm('ȷ��ɾ�����ϼ۸�?'))
	{
		$('method').value = 'delPrice';
		$('id').value = id;
		formEntry.submit();
	}
}

function reject(id)
{
	if (window.confirm('ȷ�����ش����ϼ۸�?'))
	{
		$('method').value = 'rejectPrice';
		$('id').value = id;
		formEntry.submit();
	}
}

function selectProduct()
{
	//��ѡ
	window.common.modal("../admin/product.do?method=rptInQueryProduct2&firstLoad=1&type=2");
}

function getProduct(oo)
{
	var obj = oo[0];

	$('productId').value = obj.value;
	$('productName').value = obj.productName;
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../admin/price.do"><input
	type="hidden" name="method" value="queryPrice">
	<input
	type="hidden" name="id" value="">
	<input
	type="hidden" name="productId" value="${productId}"><input
	type="hidden" value="1" name="firstLoad"> <p:navigation
	height="22">
	<td width="550" class="navigation">ѯ�۹��� &gt;&gt; ���ϼ۸��б�</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center" >��ʼʱ��</td>
				<td align="center" width="35%"><p:plugin name="alogTime" value="${alogTime}" /></td>
				<td align="center" >����ʱ��</td>
				<td align="center" width="35%"><p:plugin name="blogTime" value="${blogTime}" /></td>
			</tr>

			<tr align=center class="content1">
				<td align="center">��Ʒ����</td>
				<td align="center" width="35%"><input type="text" readonly="readonly"
					name="productName" value="${productName}">&nbsp;&nbsp;<input
					type="button" value="&nbsp;...&nbsp;" name="qout"
					class="button_class" onclick="selectProduct()"></td>
				<td align="center">�۸�״̬</td>
				<td align="center" width="35%"><select name="status" readonly="${readonly}"
					class="select_class" values="${status}">
					<option value="">--</option>
					<option value="0">����</option>
					<option value="1">����</option>
				</select></td>
			</tr>

			<tr align=center class="content0">
				<td align="right" colspan="4"><input type="button"
					class="button_class" value="&nbsp;&nbsp;�� ѯ&nbsp;&nbsp;"
					onclick="querys()">&nbsp;&nbsp; <input type="button"
					class="button_class" value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"
					onclick="resets()"></td>
			</tr>
		</table>

	</p:subBody>


	<p:title>
		<td class="caption"><strong>���ϼ۸��б�</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center" class="td_class" onclick="tableSort(this)" width="15%"><strong>��Ʒ</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)" width="15%"><strong>����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)" width="10%"><strong>�۶�</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)" width="10%"><strong>��Դ��վ</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)" width="10%"><strong>¼��ʱ��</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)" width="10%"><strong>״̬</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)" width="10%"><strong>����</strong></td>
			</tr>

			<c:forEach items="${list}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center" onclick="hrefAndSelect(this)">
					${item.productName}
					</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.productCode}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.price}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.priceWebName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.logTime}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('priceStatus', item.status)}</td>
					<td align="center" onclick="hrefAndSelect(this)">

					<c:if test="${user.role == 'REPRICE'}">
					<a
						title="�������ϼ۸�"
						href="javascript:reject('${item.id}')">
					<img src="../images/begin.gif" border="0" height="15" width="15"></a>
					</c:if>

					<c:if test="${item.status == 1}">
					<a
						title="ɾ�����ϼ۸�"
						href="javascript:dels('${item.id}')">
					<img src="../images/del.gif" border="0" height="15" width="15"></a>
					</c:if>

					</td>
				</tr>
			</c:forEach>
		</table>

		<p:formTurning form="formEntry" method="queryPrice"></p:formTurning>
	</p:subBody>

	<p:line flag="1" />

	<p:message />

</p:body></form>
</body>
</html>

