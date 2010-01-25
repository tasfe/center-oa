<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="����ѯ��" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">

var cindex = -1;
function addBean()
{
	submit('ȷ������ѯ��?', null, lverify);
}

function lverify()
{
	var checkArr = document.getElementsByName('check_init');

	var isSelect = false;

	var imap = {};

	var count = 0;
	for (var i = 0; i < checkArr.length; i++)
	{
		var obj = checkArr[i];

		var index = obj.value;

		if (obj.checked)
		{
			count++;
			isSelect = true;

			if ($('customerName_' + i).value == '' || $('customerId_' + i).value == '' )
			{
				alert('��Ӧ�̲���Ϊ��');
				return false;
			}

			if ($$('hasAmount_' + i)  == null)
			{
				alert('��ѡ��Ӧ���Ƿ���������Ҫ��');
				return false;
			}

			if (imap[$('customerId_' + i).value] == $('customerId_' + i).value)
			{
				alert('ѡ��Ĺ�Ӧ�̲����ظ�');
				return false;
			}

			imap[$('customerId_' + i).value] = $('customerId_' + i).value;
		}
	}

	if(count < 3)
	{
		alert('ѡ��ѯ�۹�Ӧ�̱������2��');
		return false;
	}

	if (!isSelect)
	{
		alert('��ѡ��ѯ�۹�Ӧ��');
		return false;
	}

	return true;
}
function load()
{
	loadForm();

	init();
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
			$('customerName_' + index).value = '';
			$('customerId_' + index).value = '';
			$d('qout_' + index);
			$d('price_' + index);
			$d('amount_' + index);
		}
	}
}

function selectCustomer(index)
{
	cindex = index;
	
	window.common.modal("../stock/stock.do?method=rptInQueryPriceAskProvider&firstLoad=1&productId=${bean.productId}&userId=${stock.userId}");
}


function getPriceAskProvider(oo)
{
    if (cindex != -1)
    {
        $("customerName_" + cindex).value = oo.pproviderName;
        $("customerId_" + cindex).value = oo.pprovideriId;
        $("price_" + cindex).value = oo.pp;
        $("price_" + cindex).value = oo.pp;
        $("netaskId_" + cindex).value = oo.ppid;
    }
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../stock/stock.do" method="post"><input
	type="hidden" name="method" value="stockItemAskPrice">
	<input type="hidden" name="customerId_0" value="">
	<input type="hidden" name="customerId_1" value="">
	<input type="hidden" name="customerId_2" value="">
	<input type="hidden" name="customerId_3" value="">
	<input type="hidden" name="customerId_4" value="">
	
	<input type="hidden" name="netaskId_0" value="">
    <input type="hidden" name="netaskId_1" value="">
    <input type="hidden" name="netaskId_2" value="">
    <input type="hidden" name="netaskId_3" value="">
    <input type="hidden" name="netaskId_4" value="">
    
	<input type="hidden" name="stockId" value="${id}">
	<input
	type="hidden" name="id" value="${bean.id}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: hand"
		onclick="javascript:history.go(-1)">ѯ�۹���</span> &gt;&gt; ����ɹ�ѯ��</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>ѯ����Ϣ��ѡ������ѯ�۽��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:table cells="2">
			<p:cell title="�ɹ�����">
			${bean.stockId}
			</p:cell>

			<p:cell title="��Ʒ����">
			${bean.productName}
			</p:cell>

			<p:cell title="��Ʒ����">
			${bean.productCode}
			</p:cell>

			<p:cell title="�ɹ�����">
			${bean.amount}
			</p:cell>

			<p:cells celspan="2" title="�ο��۸�">
			${bean.prePrice}
			</p:cells>


			<p:cells id="selects" celspan="2" title="ѯ�۴���">
				<table id="mselect">
					<tr>
						<td>
							<input type="checkbox" name="check_init" value="0" onclick="init()" id="check_init_0">��Ӧ��һ��<input type="button"
								value="&nbsp;ѡ ��&nbsp;" name="qout_0" class="button_class"
								onclick="selectCustomer(0)">&nbsp;
							��Ӧ��:<input
							type="text" name="customerName_0" value="" size="20" readonly="readonly">&nbsp;
							�۸�:<input
							type="text" name="price_0" value="" size="6" oncheck="isFloat;">&nbsp;
							����:<input
                            type="text" name="amount_0" value="" size="6" oncheck="isNumber;">&nbsp;

							</td>
					</tr>

					<tr>
						<td><input type="checkbox" name="check_init" value="1" onclick="init()" id="check_init_1">��Ӧ�̶���<input type="button"
								value="&nbsp;ѡ ��&nbsp;" name="qout_1" class="button_class"
								onclick="selectCustomer(1)">&nbsp;
							��Ӧ��:<input
							type="text" name="customerName_1" value="" size="20" readonly="readonly">&nbsp;
							�۸�:<input
							type="text" name="price_1" value="" size="6" oncheck="isFloat;">&nbsp;
							����:<input
                            type="text" name="amount_1" value="" size="6" oncheck="isNumber;">&nbsp;

						</td>
					</tr>

					<tr>
						<td><input type="checkbox" name="check_init" value="2" onclick="init()" id="check_init_2">��Ӧ������<input type="button"
								value="&nbsp;ѡ ��&nbsp;" name="qout_2" class="button_class"
								onclick="selectCustomer(2)">&nbsp;
							��Ӧ��:<input
							type="text" name="customerName_2" value="" size="20" readonly="readonly">&nbsp;
							�۸�:<input
							type="text" name="price_2" value="" size="6" oncheck="isFloat;">&nbsp;
							����:<input
                            type="text" name="amount_2" value="" size="6" oncheck="isNumber;">&nbsp;
							</td>
					</tr>

					<tr>
						<td><input type="checkbox" name="check_init" value="3" onclick="init()" id="check_init_03">��Ӧ���ģ�<input type="button"
								value="&nbsp;ѡ ��&nbsp;" name="qout_3" class="button_class"
								onclick="selectCustomer(3)">&nbsp;
							��Ӧ��:<input
							type="text" name="customerName_3" value="" size="20" readonly="readonly">&nbsp;
							�۸�:<input
							type="text" name="price_3" value="" size="6" oncheck="isFloat;">&nbsp;
							����:<input
                            type="text" name="amount_3" value="" size="6" oncheck="isNumber;">&nbsp;
							</td>
					</tr>

					<tr>
						<td><input type="checkbox" name="check_init" value="4" onclick="init()" id="check_init_4">��Ӧ���壺<input type="button"
								value="&nbsp;ѡ ��&nbsp;" name="qout_4" class="button_class"
								onclick="selectCustomer(4)">&nbsp;
							��Ӧ��:<input
							type="text" name="customerName_4" value="" size="20" readonly="readonly">&nbsp;
							�۸�:<input
							type="text" name="price_4" value="" size="6" oncheck="isFloat;">&nbsp;
							����:<input
                            type="text" name="amount_4" value="" size="6" oncheck="isNumber;">&nbsp;
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
			value="&nbsp;&nbsp;ȷ��ѯ��&nbsp;&nbsp;" onclick="addBean()">&nbsp;&nbsp;
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

