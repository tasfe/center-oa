<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@include file="./common.jsp"%>

<html>
<head>
<p:link title="��д�ⵥ" />
<script language="JavaScript" src="../js/prototype.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/addOut.js"></script>
<script language="JavaScript" src="../js/buffalo.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="javascript">

var oo;
var END_POINT="${pageContext.request.contextPath}/bfapp";

var buffalo = new Buffalo(END_POINT);
buffalo.onError = new Function();

var ids = '';
var amous = '';
var tsts;
var messk = '';
var locationId = '${currentLocationId}';
var currentLocationId = '${currentLocationId}';
function change()
{
	var obj = getOption($("customerId"));
	$("connector").value = obj.connector;
	$("phone").value = obj.phone;
	$("customerName").value = obj.cname;
}

function opens(obj)
{
	oo = obj;
	if ($('location'))
	{
		locationIdI = $F('location');
	}
	else
	{
		locationIdI = locationId;
	}

	window.common.modal('./product.do?method=rptInQueryProduct&firstLoad=1&locationInner='+ locationIdI);
}

function selectCustomer()
{
    window.common.modal("./out.do?method=queryCustomer");
}

//Ĭ�Ϻ�����
var BLACK_LEVEL = '90000000000000000000';


function getCustmeor(id, name, conn, phone, customercreditlevel)
{
	$("connector").value = conn;
	$("phone").value = phone;
	$("customerName").value = name;
	$("customerId").value = id;
	$("customercreditlevel").value = customercreditlevel;
	
	if (customercreditlevel == BLACK_LEVEL)
	{
	    removeAllItem($('reserve3'));
	    
	    setOption($('reserve3'), '1', '�����(�������ͻ�)');   
	}
	else
	{
	    removeAllItem($('reserve3'));
        
        setOption($('reserve3'), '2', '�ͻ����ú�ҵ��Ա���ö�ȵ���');  
        setOption($('reserve3'), '1', '�����(�������ͻ�)');  
	}
}

function total()
{
	var obj = document.getElementsByName("value");

	var total = 0;
	for (var i = 1; i < obj.length; i++)
	{
		if (obj[i].value != '')
		{
			total = add(total, parseFloat(obj[i].value));
		}
	}

	var ss =  document.getElementById("total");
	tsts = formatNum(total, 2);
	ss.innerHTML = '(�ܼ�:' + tsts + ')';
}

function titleChange()
{
	<c:if test="${ff}">
	if ($$('outType') == '1' || $$('outType') == '4')
	{
		$('outd').innerText = '�������ţ�';
	}
	else
	{
		$('outd').innerText = '��Ӧ�̣�';
	}

	if ($$('outType') == '1')
	{
		hides(false);
	}
	else
	{
		hides(true);
	}

	//����Ĵ���
	if ($$('outType') == '4')
	{
		showTr(true);
	}
	else
	{
		showTr(false);

		if ($('refOutFullId'))
		$('refOutFullId').value = '';
	}
	</c:if>
}

function load()
{
	titleChange();
	loadForm();

	hides(true);
	
	$$E(['productId']);
}

function hides(boo)
{
	$d('dirs', boo);
	$v('dirs', !boo);

	$d('dirs1', boo);
	$v('dirs1', !boo);

	$d('destinationId', boo);
	$v('destinationId', !boo);
}

function showTr(boo)
{
	$v('in_out', boo);
	$v('refOutFullId', boo);
}

function getOutId(id)
{
	$('refOutFullId').value = id;
}

function check()
{
	if (!formCheck())
	{
		return false;
	}

	ids = '';
	amous = '';
	$('priceList').value = '';
	$('totalList').value = '';
	$('nameList').value = '';
	$('unitList').value = '';
	if (trim($('outTime').value) == '')
	{
		alert('��ѡ����������');
		return false;
	}

	if ($$('outType') == '')
	{
		alert('��ѡ��ⵥ����');
		return false;
	}

	if ($('customerId').value == '')
	{
		alert('��ѡ��ͻ�');
		return false;
	}

	if ($$('department') == '')
	{
		alert('��ѡ�����۲���');
		return false;
	}

	<c:if test='${!ff}'>
	if (!eCheck([$('reday')]))
	{
		alert('������1��180֮�ڵ�����');
		$('reday').focus();
		return false;
	}
	</c:if>


	var proNames = document.getElementsByName('productName');
	var units = document.getElementsByName('unit');
	var amounts = document.getElementsByName('amount');
	var prices = document.getElementsByName('price');
	var values = document.getElementsByName('value');


	//isNumbers
	for (var i = 1; i < proNames.length; i++)
	{
		if (proNames[i].value == '' || proNames[i].productId == '')
		{
			alert('���ݲ�����,��ѡ���Ʒ����!');
			return false;
		}

		ids = ids + proNames[i].productId + '~';

		$('nameList').value = $('nameList').value +  proNames[i].value + '~';

		$('idsList').value = ids;
	}

	for (var i = 1; i < amounts.length; i++)
	{
		if (trim(amounts[i].value) == '')
		{
			alert('���ݲ�����,����д��Ʒ����!');
			amounts[i].focus();
			return false;
		}

		if (!isNumbers(amounts[i].value))
		{
			alert('���ݴ���,��Ʒ���� ֻ��������!');
			amounts[i].focus();
			return false;
		}

		amous = amous + amounts[i].value + '~';

		$('amontList').value = amous;
	}

	for (var i = 1; i < prices.length; i++)
	{
		if (trim(prices[i].value) == '')
		{
			alert('���ݲ�����,����д��Ʒ�۸�!');
			prices[i].focus();
			return false;
		}
		
		if (parseInt(trim(prices[i].value)) == 0)
        {
            if (!window.confirm('������Ʒ���۲�Ҫ��0,�����ܲ�����,��ȷ��?'))
            {
                 prices[i].focus();
                 return false;
            }
        }

		if (!isFloat(prices[i].value))
		{
			alert('���ݴ���,��Ʒ����ֻ���Ǹ�����!');
			prices[i].focus();
			return false;
		}

		$('priceList').value = $('priceList').value + prices[i].value + '~';
	}

	var desList = document.getElementsByName('desciprt');
	for (var i = 1; i < desList.length; i++)
	{
		if (trim(desList[i].value) == '')
		{
		    alert('�ɱ��Ǳ���!');
            desList[i].focus();
            return false;
		}
		
		if (!isFloat(desList[i].value))
		{
			alert('��ʽ����,�ɱ�ֻ���Ǹ�����!');
			desList[i].focus();
			return false;
		}
	}
	for (var i = 1; i < values.length; i++)
	{
		$('totalList').value = $('totalList').value + values[i].value + '~';
		$('desList').value = $('desList').value + desList[i].value + '~';
	}

	for (var i = 1; i < units.length; i++)
	{
		$('unitList').value = $('unitList').value + units[i].value + '~';
	}

	$('totalss').value = tsts;

	return true;
}

function checkTotal()
{
	messk = '';
	var gh = $('nameList').value.split('~');
	var ghk = $('amontList').value.split('~');

	messk += '\r\n';
	for(var i = 0 ; i < gh.length - 1; i++)
	{
		messk += '\r\n' + '��Ʒ��' + gh[i] + '��   ����:' + ghk[i];
	}


	 if ($$('outType') == 1 && $$('type') == 1)
	 {
	 	 if (!window.confirm('����ǰ���������ǵ���������ʱҲ���������ӿ�棬�������ٿ�棬��ȷ����д�ĵ�������ʵ������?'))
	 	 {
	 	 	return;
	 	 }
	 }

	if ($('saves').value != '')
    {

	     if (window.confirm("ȷ������ⵥ?" + messk))
	     {
	     	$('id').value = '';
	     	disableAllButton();
	     	outForm.submit();
	     }

	     return;
    }

    if ($('location'))
    {
    	ccv = $F('location');
    }
    else
    {
    	ccv = locationId;
    }

    if (ccv == '')
    {
    	alert('��Ʒ����Ϊ�գ����ʵ');
    	return false;
    }

    //�ж�method
    if ($$('method') != 'addOut')
    {
    	alert('��ʾ���ύû�з����������µ�¼����');
    	return false;
    }

	buffalo.remoteCall("productDAO.check",[ids, amous, ${ff ? "1" : "0"}, ccv], function(reply) {
		        var result = reply.getResult();
		        if (result != 'true')
		        {
		        	alert(result);
		        	return;
		        }

	        	if (window.confirm("ȷ���ύ�ⵥ?" + messk))
		        {
		        	$('id').value = '';
		        	disableAllButton();
		        	outForm.submit();
		        }

		});
}

function save()
{
	$('saves').value = 'saves';
	if (check())
	{
		checkTotal();
	}
}

function sub()
{
	$('saves').value = '';
	if (check())
	{
		checkTotal();
	}
}

function managerChange()
{
	if ($$('outType') == 0)
	{
		$('customerName').value = '';
		//$('arriveDate').value = '';
		$('customerId').value = '';
		$('customerName').disabled  = false;
		//$('arriveDate').disabled  = false;
		$('reday').value = '';
		$('reday').readOnly = false;
	}
	else
	{
		$('customerName').value = '��������';
		//$('arriveDate').value = '��������';
		$('customerId').value = '0';
		$('customerName').disabled  = true;
		//$('arriveDate').disabled  = true;
		$('reday').value = '30';
		$('reday').readOnly = true;
	}
}

function selectOut()
{
	window.common.modal('../admin/out.do?method=queryOut3&load=1&flag=4');
}

</script>
</head>
<body class="body_class" onload="load()">
<form name="outForm" method=post action="./out.do?"><input
	type=hidden name="method" value="addOut" /><input type=hidden
	name="nameList" /> <input type=hidden name="idsList" /> <input
	type=hidden name="unitList" /> <input type=hidden name="amontList" />
<input type=hidden name="priceList" /> <input type=hidden
	name="totalList" /> <input type=hidden name="totalss" /> <input
	type=hidden name="customerId" /> <input type=hidden name="type"
	value='${ff ? "1" : "0"}' /> <input type=hidden name="saves" value="" />
<input type=hidden name="desList" value="" />
<input type=hidden name="customercreditlevel" value="" />
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td height="22" valign="bottom">
		<table width="100%" height="22" border="0" cellpadding="0"
			cellspacing="0">
			<tr valign="middle">
				<td width="8"></td>
				<td width="30">
				<div align="center"><img src="../images/dot_a.gif" width="9"
					height="9"></div>
				</td>
				<td width="550" class="navigation">�ⵥ���� &gt;&gt; ${ff ? "��д��ⵥ"
				: "��д���۵�"}</td>
				<td width="85"></td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td height="6" valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<!--DWLayoutTable-->
			<tr>
				<td width="8" height="6"
					background="../images/index_sp_welcome_center_10.gif"><img
					src="../images/index_sp_welcome_center_07.gif" width="8" height="6"></td>
				<td width="190"
					background="../images/index_sp_welcome_center_08.gif"></td>
				<td width="486"
					background="../images/index_sp_welcome_center_10.gif"></td>
				<td align="right"
					background="../images/index_sp_welcome_center_10.gif">
				<div align="right"><img
					src="../images/index_sp_welcome_center_12.gif" width="23"
					height="6"></div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<table width="95%" border="0" cellpadding="0" cellspacing="0"
	align="center">
	<tr>
		<td valign="top" colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<!--DWLayoutTable-->
			<tr>
				<td width="784" height="6"></td>
			</tr>
			<tr>
				<td align="center" valign="top">
				<div align="left">
				<table width="90%" border="0" cellspacing="2">
					<tr>
						<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="10">
							<tr>
								<td width="35">&nbsp;</td>
								<td width="6"><img src="../images/dot_r.gif" width="6"
									height="6"></td>
								<td class="caption"><strong>��д���۵���Ϣ:<font color=red>${hasOver}</font> �������ö�Ȼ�ʣ��:${credit}</strong>
								<c:if test="${!ff}">
								<font color="blue">��Ʒ����</font>
								<select name="location" class="select_class" values="${currentLocationId}">
									<c:forEach items='${locationList}' var="item">
										<option value="${item.id}">${item.locationName}</option>
									</c:forEach>
								</select>
								</c:if>
								</td>
							</tr>
						</table>
						</td>
					</tr>


				</table>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>


	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td colspan='2' align='center'>
		<table width="95%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr class="content2">
						<td width="15%" align="right">${ff ? "���" : "����"}���ڣ�</td>

						<td width="35%"><input type="text" name="outTime"
							value="${current}" maxlength="20" size="20"
							readonly="readonly"><font color="#FF0000">*</font></td>

						<c:if test="${!ff}">
							<td width="15%" align="right">�������ͣ�</td>
							<td width="35%"><select name="outType" class="select_class" onchange="managerChange()">
								<option value="0">���۳���</option>
								<option value="1">��������</option>
							</select><font color="#FF0000">*</font></td>
						</c:if>

						<c:if test="${ff}">
							<td width="15%" align="right">������ͣ�</td>
							<td width="35%">
							<select name="outType" class="select_class" onchange="titleChange()">
								<option value=''>--</option>
								<option value="0">�ɹ����</option>
								<option value="1">����</option>
								<option value="4">����</option>
								<option value="2">�̿�����</option>
								<option value="3">��ӯ���</option>
							</select><font color="#FF0000">*</font></td>
						</c:if>
					</tr>

					<tr class="content1">
						<td align="right" id="outd">${ff ? "��Ӧ��" : "�ͻ�"}��</td>
						<td><input type="text" name="customerName" maxlength="14" value=""
							onclick="selectCustomer()" style="cursor: pointer;"
							readonly="readonly"><font color="#FF0000">*</font></td>
						<td align="right">���۲��ţ�</td>
						<td><select name="department" class="select_class">
							<option value=''>--</option>

							<c:forEach items='${departementList}' var="item">
								<option value="${item}">${item}</option>
							</c:forEach>
						</select><font color="#FF0000">*</font></td>
					</tr>
					<tr class="content2">
						<td align="right">��ϵ�ˣ�</td>
						<td><input type="text" name="connector" maxlength="14"
							readonly="readonly"></td>
						<td align="right">��ϵ�绰��</td>
						<td><input type="text" name="phone" maxlength="20" readonly></td>
					</tr>
					<tr class="content1">
						<td align="right">�����ˣ�</td>
						<td><input type="text" name="stafferName" maxlength="14"
							value="${user.stafferName}" readonly="readonly"></td>
						<td align="right">���ݺ��룺</td>
						<td><input type="text" name="id" maxlength="20"
							value="ϵͳ�Զ�����" readonly></td>
					</tr>

					<c:if test='${!ff}'>
					<tr class="content2">
						<td align="right">�ؿ�������</td>
						<td colspan="1"><input type="text" name="reday" maxlength="4" oncheck="notNone;isInt;range(1, 180)"
							value="" title="������1��180֮�ڵ�����"><font color="#FF0000">*</font></td>

						<td align="right">�������ڣ�</td>
						<td><p:plugin name="arriveDate"  size="20" oncheck="notNone;cnow('30')"/><font color="#FF0000">*</font></td>
					</tr>
					</c:if>

					<c:if test='${ff && user.locationID == "0"}'>
					<tr class="content2">
						<td align="right">ѡ�������</td>
						<td colspan="1"><select name="depotpartId" class="select_class">
								<option value=''>--</option>
								<c:forEach items="${depotpartList}" var="item">
								<option value='${item.id}'>${item.name}</option>
								</c:forEach>
							</select><font color="#FF0000">*</font></td>
							<td align="right"><div id="dirs1">Ŀ������</div></td>
						<td colspan="1"><div id="dirs"><select name="destinationId" class="select_class" oncheck="notNone;noEquals('${currentLocationId}')"
						message="����Ϊ�ջ���ѡ��ǰ����">
								<option value=''>--</option>
								<c:forEach items="${locationList}" var="item">
								<option value='${item.id}'>${item.locationName}</option>
								</c:forEach>
							</select><font color="#FF0000">*</font></div></td>
					</tr>
					</c:if>

					<c:if test='${ff}'>
					<tr class="content2" id="in_out">
						<td align="right">�����ⵥ��</td>
						<td colspan="1"><input type="text" name="refOutFullId" maxlength="40" oncheck="notNone;" readonly="readonly"
							value="">&nbsp;&nbsp;<input
							type="button" value="&nbsp;...&nbsp;" name="qout" class="button_class" onclick="selectOut()">
							<font color="#FF0000">*</font></td>

						<td align="right"></td>
						<td></td>
					</tr>
					</c:if>
					
					<tr class="content2">
                        <td align="right">���ʽ��</td>
                        <td colspan="3">
                        <select name="reserve3" class="select_class" oncheck="notNone;" head="���ʽ" style="width: 240px">
                            <option value='2'>�ͻ����ú�ҵ��Ա���ö�ȵ���</option>
                            <option value='1'>�����(�������ͻ�)</option>
                        </select>
                        <font color="#FF0000">*</font></td>
                    </tr>

					<tr class="content1">
						<td align="right">${ff ? "��ⵥ" : "���۵�"}��ע��</td>
						<td colspan="3"><textarea rows="3" cols="55" oncheck="notNone;"
							name="description"></textarea>
							<font color="#FF0000">*</font>
							<b>(����д�����۵Ĳ�Ʒ,��Ϊ���������ᷢ�ʹ����ݸ���������)</b></td>
					</tr>

				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>


	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td colspan='2' align='center'>
		<table width="95%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="tables">
					<tr align="center" class="content0">
						<td width="20%" align="center">Ʒ��</td>
						<td width="5%" align="center">��λ</td>
						<td width="10%" align="center">����</td>
						<td width="15%" align="center">����</td>
						<td width="15%" align="left">���<span id="total"></span></td>
						<td width="25%" align="center">�ɱ�</td>
						<td width="15%" align="center"><input type="button"
							value="����" class="button_class" onclick="addTr()"></td>
					</tr>

					<tr class="content1" id="trCopy" style="display: none;">
						<td width="20%"><input type="text" name="productName"
							onclick="opens(this)" productId="" readonly="readonly"
							style="width: 100%; cursor: hand"></td>

						<td width="5%"><select name="unit" style="WIDTH: 50px;">
							<option value="��">��</option>
							<option value="ö">ö</option>
							<option value="��">��</option>
							<option value="��">��</option>
						</select></td>

						<td width="10%" align="center"><input type="text"
							style="width: 100%" maxlength="6" onkeyup="cc(this)"
							name="amount"></td>

						<td width="15%" align="center"><input type="text"
							style="width: 100%" maxlength="8" onkeyup="cc(this)"
							onblur="blu(this)" name="price"></td>

						<td width="20%" align="center"><input type="text"
							value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

						<td width="35%" align="center"><input type="text"
							style="width: 100%" name="desciprt"></td>

						<td width="15%" align="center"></td>
					</tr>

					<tr class="content2">
						<td width="20%"><input type="text" name="productName" id="unProductName"
							onclick="opens(this)" productId="" readonly="readonly"
							style="width: 100%; cursor: pointer"></td>

						<td width="5%"><select name="unit" style="WIDTH: 50px;">
							<option value="��">��</option>
							<option value="ö">ö</option>
							<option value="��">��</option>
							<option value="��">��</option>
						</select></td>

						<td width="10%" align="center"><input type="text" style="width: 100%" id="unAmount"
							maxlength="6" onkeyup="cc(this)" name="amount"></td>

						<td width="15%" align="center"><input type="text" style="width: 100%" id="unPrice"
							maxlength="8" onkeyup="cc(this)" onblur="blu(this)" name="price"></td>

						<td width="20%" align="center"><input type="text"
							value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

						<td width="25%" align="center"><input type="text" id="unDesciprt"
							style="width: 100%" name="desciprt"></td>

						<td width="15%" align="center"><input type=button value="���"  class="button_class" onclick="clears()"></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td width="92%">
		<div align="right"><input type="button" class="button_class"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onClick="save()" />&nbsp;&nbsp;<input
			type="button" class="button_class" id="sub_b"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onClick="sub()" /></div>
		</td>
		<td width="8%"></td>
	</tr>

</table>
</form>
</body>
</html>

