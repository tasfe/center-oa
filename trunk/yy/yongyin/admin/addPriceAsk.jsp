<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="���ӽ���ѯ��" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">
function addBean()
{
	submit('ȷ�����ӽ���ѯ��?');
}

function load()
{
	//alert('load');
}

function selectProduct()
{
	//��ѡ
	window.common.modal("../admin/product.do?method=rptInQueryProduct3&firstLoad=1&type=2");
}

function getProduct(oo)
{
	var obj = oo;
	
	$('productId').value = obj.value;
	$('productName').value = obj.productname;
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../admin/price.do"><input
	type="hidden" name="method" value="addPriceAsk"> <input
	type="hidden" name="productId" value=""><p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: hand"
		onclick="javascript:history.go(-1)">ѯ�۹���</span> &gt;&gt; ���ӽ���ѯ��</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>����ѯ����Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.centet.yongyin.bean.PriceAskBean" />

		<p:table cells="1">
			<p:pro field="productId">&nbsp;&nbsp;<input type="button"
					value="&nbsp;...&nbsp;" name="qout" class="button_class"
					onclick="selectProduct()">
			</p:pro>

			<p:pro field="amount" />

			<p:pro field="instancy">
				<option value="0">һ��</option>
				<option value="1">����</option>
				<option value="2">�ǳ�����</option>
			</p:pro>
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

	<tr>
		<td colspan='2' align="center"><FONT color="blue">${MESSAGE_INFO}</FONT><FONT
			color="red">${errorInfo}</FONT></td>
	</tr>
</p:body></form>
</body>
</html>

