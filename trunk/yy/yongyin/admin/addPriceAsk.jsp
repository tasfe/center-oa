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
	init();
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

function init()
{
    var ss = $O('instancy');
    removeAllItem(ss);
    if ($$('type') == '0')
    {
        setOption(ss, '0', 'һ��(2Сʱ)');
        setOption(ss, '1', '����(1Сʱ)');
        setOption(ss, '2', '�ǳ�����(30����)');
    }
    
    if ($$('type') == '1')
    {
        setOption(ss, '3', '����ѯ��(����11�����)');
        setOption(ss, '4', '����ѯ��(����2�����)');
        setOption(ss, '5', '����ѯ��(����6�����)');
        setOption(ss, '6', '����ѯ��(���23�����)');
    }
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

	<p:subBody width="98%">
		<p:class value="com.china.centet.yongyin.bean.PriceAskBean" />

		<p:table cells="1">
			<p:pro field="productId" innerString="size=60">&nbsp;&nbsp;<input type="button"
					value="&nbsp;...&nbsp;" name="qout" class="button_class"
					onclick="selectProduct()">
			</p:pro>

			<p:pro field="amount" />
			
			<p:pro field="type" innerString="onchange=init()">
                <option value="0">�ڲ�ѯ��</option>
                <option value="1">����ѯ��</option>
            </p:pro>

			<p:pro field="instancy" innerString="style='width: 240px'">
				<option value="0">һ��(2Сʱ)</option>
				<option value="1">����(1Сʱ)</option>
				<option value="2">�ǳ�����(30����)</option>
				<option value="3">����ѯ��(����11�����)</option>
				<option value="4">����ѯ��(����2�����)</option>
				<option value="5">����ѯ��(����6�����)</option>
				<option value="6">����ѯ��(���23�����)</option>
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

	<p:message/>
</p:body></form>
</body>
</html>

