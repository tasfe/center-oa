<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="������վ�۸�" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">
function addBean()
{
	submit('ȷ���������ϼ۸�?');
}

function load()
{
	loadForm();
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
<form name="addApply" action="../admin/price.do"><input
	type="hidden" name="method" value="addPrice"> <input
	type="hidden" name="productId" value="${wrap.productId}"><p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: hand"
		onclick="javascript:history.go(-1)">ѯ�۹���</span> &gt;&gt; ������վ�۸�</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>��վ�۸���Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:table cells="1">
			<p:cell title="��Ʒ����">${wrap.productName}</p:cell>

			<p:cell title="��Ʒ����">${wrap.productCode}</p:cell>

			<p:cell title="��վ�۸�">
				<c:forEach items="${wrap.items}" var="item">
					${item.name}���۸񡿣�<input type="text" name="price_${item.id}"
						oncheck="notNone;isFloat" head="${item.name}�۸�">
					<font color="#FF0000">*</font>
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

	<p:message />
</p:body></form>
</body>
</html>

