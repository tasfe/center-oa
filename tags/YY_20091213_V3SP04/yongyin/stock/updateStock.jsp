<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="����ɹ�" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="javascript">

var cindex = -1;
function addBean()
{
	submit('ȷ�������Ʒ�ɹ�?', null, lverify);
}

var maxItem = ${maxItem};

function lverify()
{
	var checkArr = document.getElementsByName('check_init');

	var isSelect = false;

	var imap = {};

	for (var i = 0; i < checkArr.length; i++)
	{
		var obj = checkArr[i];

		var index = obj.value;

		if (obj.checked)
		{
			isSelect = true;
			if ($('productName_' + i).value == '' || $('productId_' + i).value == '' )
			{
				alert('��Ʒ����Ϊ��');
				return false;
			}

			if ($$('amount_' + i)  == null)
			{
				alert('��ѡ���Ʒ�Ƿ���������Ҫ��');
				return false;
			}

			if (imap[$('productId_' + i).value] == $('productId_' + i).value)
			{
				alert('ѡ��Ĳ�Ʒ�����ظ�');
				return false;
			}

			imap[$('productId_' + i).value] = $('productId_' + i).value;
		}
	}

	if (!isSelect)
	{
		alert('��ѡ��ɹ���Ʒ');
		return false;
	}

	return true;
}
function load()
{
	loadForm();

	isel();

	init();
}

function isel()
{
	var checkArr = document.getElementsByName('check_init');

	for (var i = 0; i < checkArr.length; i++)
	{
		if (i < maxItem)
		{
			checkArr[i].checked = true;
		}
	}
}

function init()
{
	var checkArr = document.getElementsByName('check_init');

	for (var i = 0; i < checkArr.length; i++)
	{
		var obj = checkArr[i];

		var index = obj.value;

		if (obj.checked)
		{
			$d('qout_' + index, false);
			$d('price_' + index, false);
			$d('amount_' + index, false);
		}
		else
		{
			$('price_' + index).value = '';
			$('productName_' + index).value = '';
			$('productId_' + index).value = '';
			$d('qout_' + index);
			$d('price_' + index);
			$d('amount_' + index);
		}
	}
}

function selectProduct(index)
{
	cindex = index;
	window.common.modal("../admin/product.do?method=rptInQueryProduct3&firstLoad=1");
}

function getProduct(oo)
{
	if (cindex != -1)
	{
		$("productName_" + cindex).value = oo.productname;
		$("productId_" + cindex).value = oo.value;
	}
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../stock/stock.do" method="post"><input
	type="hidden" name="method" value="updateStock">
	<input type="hidden" name="productId_0" value="${bean.itemVO[0].productId}">
	<input type="hidden" name="productId_1" value="${bean.itemVO[1].productId}">
	<input type="hidden" name="productId_2" value="${bean.itemVO[2].productId}">
	<input type="hidden" name="productId_3" value="${bean.itemVO[3].productId}">
	<input type="hidden" name="productId_4" value="${bean.itemVO[4].productId}">
	<input
	type="hidden" name="id" value="${bean.id}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: hand"
		onclick="javascript:history.go(-1)">�ɹ�����</span> &gt;&gt; ���²ɹ�</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>�ɹ���Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.centet.yongyin.bean.StockBean" />

		<p:table cells="1">
			<p:pro field="needTime" value="${bean.needTime}"/>
			
			<p:pro field="flow" innerString="quick='true'" outString="֧�ּ�ƴѡ��" value="${bean.flow}">
			<option value="">--</option>
			<c:forEach items="${departementList}" var="item">
			<option value="${item}">${item}</option>
			</c:forEach>
			</p:pro>

			<p:pro field="description"  innerString="cols=80 rows=3" value="${bean.description}" />


			<p:cells id="selects" celspan="2" title="�ɹ�����">
				<table id="mselect">
					<tr>
						<td>
							<input type="checkbox" name="check_init" value="0" onclick="init()">��Ʒһ��<input type="button"
								value="&nbsp;ѡ ��&nbsp;" name="qout_0" class="button_class"
								onclick="selectProduct(0)">&nbsp;
							��Ʒ:<input
							type="text" name="productName_0" value="${bean.itemVO[0].productName}" size="20" readonly="readonly">&nbsp;
							�ο��۸�:<input
							type="text" name="price_0" value="${bean.itemVO[0].prePrice}" size="6" oncheck="notNone;isFloat;">&nbsp;
							����:<input
							type="text" name="amount_0" value="${bean.itemVO[0].amount}" size="6" oncheck="notNone;isNumber;">

							</td>
					</tr>

					<tr>
						<td><input type="checkbox" name="check_init" value="1" onclick="init()">��Ʒ����<input type="button"
								value="&nbsp;ѡ ��&nbsp;" name="qout_1" class="button_class"
								onclick="selectProduct(1)">&nbsp;
							��Ʒ:<input
							type="text" name="productName_1" value="${bean.itemVO[1].productName}" size="20" readonly="readonly">&nbsp;
							�ο��۸�:<input
							type="text" name="price_1" value="${bean.itemVO[1].prePrice}" size="6" oncheck="notNone;isFloat;">&nbsp;
							����:<input
							type="text" name="amount_1" value="${bean.itemVO[1].amount}" size="6" oncheck="notNone;isNumber;">

						</td>
					</tr>

					<tr>
						<td><input type="checkbox" name="check_init" value="2" onclick="init()">��Ʒ����<input type="button"
								value="&nbsp;ѡ ��&nbsp;" name="qout_2" class="button_class"
								onclick="selectProduct(2)">&nbsp;
							��Ʒ:<input
							type="text" name="productName_2" value="${bean.itemVO[2].productName}" size="20" readonly="readonly">&nbsp;
							�ο��۸�:<input
							type="text" name="price_2" value="${bean.itemVO[2].prePrice}" size="6" oncheck="notNone;isFloat;">&nbsp;
							����:<input
							type="text" name="amount_2" value="${bean.itemVO[2].amount}" size="6" oncheck="notNone;isNumber;">
							</td>
					</tr>

					<tr>
						<td><input type="checkbox" name="check_init" value="3" onclick="init()">��Ʒ�ģ�<input type="button"
								value="&nbsp;ѡ ��&nbsp;" name="qout_3" class="button_class"
								onclick="selectProduct(3)">&nbsp;
							��Ʒ:<input
							type="text" name="productName_3" value="${bean.itemVO[3].productName}" size="20" readonly="readonly">&nbsp;
							�ο��۸�:<input
							type="text" name="price_3" value="${bean.itemVO[3].prePrice}" size="6" oncheck="notNone;isFloat;">&nbsp;
							����:<input
							type="text" name="amount_3" value="${bean.itemVO[3].amount}" size="6" oncheck="notNone;isNumber;">
							</td>
					</tr>

					<tr>
						<td><input type="checkbox" name="check_init" value="4" onclick="init()">��Ʒ�壺<input type="button"
								value="&nbsp;ѡ ��&nbsp;" name="qout_4" class="button_class"
								onclick="selectProduct(4)">&nbsp;
							��Ʒ:<input
							type="text" name="productName_4" value="${bean.itemVO[4].productName}" size="20" readonly="readonly">&nbsp;
							�ο��۸�:<input
							type="text" name="price_4" value="${bean.itemVO[4].prePrice}" size="6" oncheck="notNone;isFloat;">&nbsp;
							����:<input
							type="text" name="amount_4" value="${bean.itemVO[4].amount}" size="6" oncheck="notNone;isNumber;">
							</td>
					</tr>
				</table>
			</p:cells>

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

