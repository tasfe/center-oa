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
	for (var i = 0; i < checkArr.length; i++)
	{
		var obj = checkArr[i];

		var index = obj.value;

		if (obj.checked)
		{
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
		}
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
            $d('hasAmount_' + index, false);
        }
        else
        {
            $('price_' + index).value = '';
            //$('customerName_' + index).value = '';
            //$('customerId_' + index).value = '';
            $d('qout_' + index);
            $d('price_' + index);
            $d('hasAmount_' + index);
        }
    }
}

function selectCustomer(index)
{
	cindex = index;
	window.common.modal("../admin/common.do?method=rptQueryCustmerInVS&load=1&productTypeId=${product.genre}");
}

function getCustmeor(id, name)
{
	if (cindex != -1)
	{
		$("customerName_" + cindex).value = name;
		$("customerId_" + cindex).value = id;
	}
}

function rejectBean()
{
	if (window.confirm('ȷ�����ش�ѯ�۵�?'))
	{
		var sss = window.prompt('�����벵��ѯ�۵�ԭ��', '');

		$('reason').value = sss;

		if (!(sss == null || sss == ''))
		{
			$('method').value = 'rejectPriceAsk';
			
			formEntry.submit();
		}
		else
		{
			alert('�����벵��ԭ��');
		}
	}
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../admin/price.do" method="post"><input
	type="hidden" name="method" value="processAskPrice">
	<input type="hidden" name="id" value="${bean.id}">
	<input type="hidden" name="customerId_0" value="${GProvider.id}">
	<input type="hidden" value="" name="reason">
	<input
	type="hidden" name="id" value="${bean.id}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">ѯ�۹���</span> &gt;&gt; ����ѯ��</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>ѯ����Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:table cells="2">
			<p:cell title="��Ʒ����">
			<a href="../admin/product.do?method=findProduct&productId=${bean.productId}&detail=1" title="�鿴��Ʒ��ϸ">
			${bean.productName}
			</a>
			</p:cell>

			<p:cell title="��Ʒ����">
			${bean.productCode}
			</p:cell>

			<p:cell title="��Ҫ����">
			${bean.amount}
			</p:cell>

			<p:cell title="�Ƿ�����">
			${bean.overTime == 0 ? "<font color=blue>δ����</font>" : "<font color=red>����</font>"}
			</p:cell>

			<p:cell title="ѯ����">
			${bean.userName}
			</p:cell>

			<p:cell title="ѯ������">
			${bean.locationName}
			</p:cell>

			<p:cell title="�����̶�">
			${my:get('priceAskInstancy', bean.instancy)}
			</p:cell>

			<p:cell title="����ʱ��">
			${bean.processTime}
			</p:cell>

			<p:cells id="selects" celspan="2" title="ѯ�۴���">
				<table id="mselect">
					<tr>
						<td>
							<input type="checkbox" name="check_init" value="0" onclick="init()" checked="checked" style="visibility: hidden;">
							��Ӧ��:<input
							type="text" name="customerName_0" value="${GProvider.name}" size="20" readonly="readonly">&nbsp;
							����:<input
							type="text" name="price_0" value="${my:formatNum(paskBean.price)}" size="6" oncheck="isFloat;">&nbsp;
							��������<input
                            type="text" name="supportAmount_0" value="${paskBean.supportAmount}" size="6" oncheck="isFloat;notNone">&nbsp;
							�����Ƿ�����:<input type="radio" name="hasAmount_0" value="0" ${paskBean.hasAmount == 0 ? 'checked=checked' : ''}>����
							&nbsp;&nbsp;<input type="radio" name="hasAmount_0" value="1" ${paskBean.hasAmount == 1 ? 'checked=checked' : ''}>������
							<br>
							����: <textarea name="description_0" cols="60" rows="3">${paskBean.description}</textarea>
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

