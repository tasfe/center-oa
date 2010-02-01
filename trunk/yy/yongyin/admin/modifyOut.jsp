<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>

<html>
<head>
<p:link title="�޸ĳ��ⵥ" />
<c:set var="ff" value='${out.type == 0 ? "��" : "��"}'/>
<c:set var="ff1" value='${out.type == 0}'/>
<script language="JavaScript" src="../js/prototype.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/addOut.js"></script>
<script language="JavaScript" src="../js/buffalo.js"></script>
<script language="JavaScript" src="../js/template.js"></script>
<script language="JavaScript" src="../js/cal.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">
var oo;
var END_POINT="${pageContext.request.contextPath}/bfapp";

var buffalo = new Buffalo(END_POINT);
buffalo.onError = new Function();

var ids = '';
var amous = '';
var tsts= '${out.total}';
var messk = '';
var locationId = '${currentLocationId}';
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

function getCustmeor(id, name, conn, phone)
{
	$("connector").value = conn;
	$("phone").value = phone;
	$("customerName").value = name;
	$("customerId").value = id;
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
		alert('��ѡ���������');
		return false;
	}
	
	if ($('customerName').value == '')
	{
		alert('��ѡ��ͻ�');
		return false;
	}
	
	<c:if test='${ff1}'>
	if ($$('reday') == '' || !isNumbers($$('reday')))
	{
		alert('������1��180֮�ڵ�����');
		$('reday').focus();
		return false;
	}
	
	if (parseInt($$('reday'), 10) > 180 || parseInt($$('reday'), 10) < 1)
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
	
	//isNumber
	for (var i = 1; i < proNames.length; i++)
	{
		if (proNames[i].value == '')
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
		
		if (!isFloat(prices[i].value))
		{
			alert('���ݴ���,��Ʒ����ֻ���Ǹ�����!');
			prices[i].focus();
			return false;
		}
		
		$('priceList').value = $('priceList').value + prices[i].value + '~';
	}
	
	var desList = document.getElementsByName('desciprt');
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

var productLocation = '${out.location}';
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
		 
	     if (window.confirm("ȷ������${ff}�ⵥ?" + messk))
	     {
	     	$d('outType', false);
	     	disableAllButton();
	     	outForm.submit();
	     }
	     
	     return;
    }
    
	buffalo.remoteCall("productDAO.check",[ids, amous, ${out.type}, productLocation], function(reply) {	
		        var result = reply.getResult();  
		        if (result != 'true')
		        {
		        	alert(result);
		        	return;
		        }
		        
		        if (window.confirm("ȷ���ύ${ff}�ⵥ?" + messk))
		        {
		        	$d('outType', false);
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

function titleChange()
{
	if ($$('outType') == '1' || $$('outType') == '4')
	{
		$('outd').innerText = '�������ţ�';
	}
	else
	{
		$('outd').innerText = '��Ӧ�̣�';
	}
	
	<c:if test="${out.type == 1}">
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
	}
	</c:if>
	
	<c:if test="${out.type == 0}">
	managerChange();
	</c:if>
}

function selectOut()
{
	window.common.modal('../admin/out.do?method=queryOut3&load=1&flag=4');
}

function getOutId(id)
{
	$('refOutFullId').value = id;
}

function hides(boo)
{
	$d('dirs', boo);
	$v('dirs', !boo);
	
	$d('dirs1', boo);
	$v('dirs1', !boo);
}

function showTr(boo)
{
	$v('in_out', boo);
	$v('refOutFullId', boo);
}


function managerChange()
{
	if ($$('outType') == 0)
	{
		
	}
	else
	{
		$('customerName').value = '��������';
		$('customerId').value = '0';
		$('customerName').disabled  = true;
		$('reday').value = '7';
		$('reday').readOnly = true;
	}
}

function load()
{
	titleChange();
	
	loadForm();
	
	if ($$('outType') == '1')
	{
		hides(false);
	}
	else
	{
		hides(true);
	}
	
	titleChange();
	
	$$E(['productId']);
}

function locationChange()
{
	productLocation = $$('location');
}
</script>
</head>
<body class="body_class" onload="load()">
<form name="outForm" method=post action="./out.do?"><input
	type=hidden name="method" value="addOut" /> <input type=hidden
	name="nameList" /> <input type=hidden name="idsList" /> <input
	type=hidden name="unitList" /> <input type=hidden name="amontList" />
<input type=hidden name="priceList" /> <input type=hidden
	name="totalList" /> <input type=hidden name="totalss" value="${out.total}" /> <input
	type=hidden name="customerId" value="${out.customerId}" /> <input type=hidden name="saves"
	value="" />
	<input type=hidden name="id" value="${out.id}" />
	<input type=hidden name="desList" value="" />
	<input type=hidden name="type"
	value='${out.type}' />
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
				<td width="550" class="navigation">�ⵥ���� &gt;&gt; <span
					style="cursor:hand" onclick="javascript:history.go(-1)">��ѯ${ff}�ⵥ</span>
				&gt;&gt; �޸�${ff}�ⵥ</td>
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
								<td class="caption"><strong>�޸�${ff}�ⵥ��Ϣ:�������ö�Ȼ�ʣ��:${credit}</strong>
								<c:if test="${out.type == 0}">
								<font color="blue">��Ʒ����</font>
								<select name="location" class="select_class" values="${out.location}" onchange="locationChange()" readonly="true">
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
						<td width="15%" align="right">${ff}�����ڣ�</td>
						<td width="35%"><input type="text" name="outTime"
							value="${out.outTime}" onKeypress="calDate(this)"
							class="time_input" onclick="calDate(this)" title="���ѡ��ͨʱ��"
							maxlength="20" size="20" readonly="readonly"><font
							color="#FF0000">*</font></td>
						<td width="15%" align="right">${ff}�����ͣ�</td>
						<td width="35%">
						
						<c:if test="${out.type == 0}">
						<select name="outType" class="select_class">
							<c:if test="${out.outType == 0}">
							<option value="0">���۳���</option>
							</c:if>
							<c:if test="${out.outType == 1}">
							<option value="1">��������</option>
							</c:if>
						</select>
						</c:if>
						
						<c:if test="${out.type == 1}">
							<select name="outType" class="select_class"  onchange="titleChange()" values="${out.outType}" readonly=true>
								<option value="0">�ɹ����</option>
								<option value="1">����</option>
								<option value="4">����</option>
								<option value="2">�̿�����</option>
								<option value="3">��ӯ���</option> 
							</select>
						</c:if>
						
						<font color="#FF0000">*</font></td>

					</tr>
					<tr class="content1">
						<td align="right" id="outd">${out.type == 1 ? "��Ӧ��" : "�ͻ�"}��</td>
						<td><input type="text" name="customerName" maxlength="14" value="${out.customerName}"
							readonly="readonly"><font color="#FF0000">*</font></td>
						<td align="right">��${ff}���ţ�</td>
						<td><select name="department" class="select_class">
							<c:forEach items="${departementList}" var="item">
								<option value="${item}" ${out.department==item?"selected" : ""}>${item}</option>
							</c:forEach>
						</select><font color="#FF0000">*</font></td>
					</tr>
					<tr class="content2">
						<td align="right">��ϵ�ˣ�</td>
						<td><input type="text" name="connector" maxlength="14"
							value="${out.connector}" readonly="readonly"></td>
						<td align="right">��ϵ�绰��</td>
						<td><input type="text" name="phone" maxlength="20" readonly
							value="${out.phone}"></td>
					</tr>
					<tr class="content1">
						<td align="right">�����ˣ�</td>
						<td><input type="text" name="stafferName" maxlength="14"
							value="${out.stafferName}" readonly="readonly"></td>
						<td align="right">���ݺ��룺</td>
						<td><input type="text" name="fullId" maxlength="20" readonly
							value="${out.fullId}"></td>
					</tr>
					
					<c:if test='${ff1}'>
					<tr class="content2"> 
						<td align="right">�ؿ�������</td>  
						<td colspan="1"><input type="text" name="reday" maxlength="4"
							value="${out.reday}" title="������1��180֮�ڵ�����"><font color="#FF0000">*</font></td>
							
							<td align="right">�������ڣ�</td> 
						<td><input type="text" name="arriveDate"
							onKeypress="calDate(this)" class="time_input" oncheck="notNone;cnow('>')"  value="${out.arriveDate}"
							onclick="calDate(this)"  maxlength="20" size="20"
							readonly="readonly"><font color="#FF0000">*</font></td>
					</tr>
					</c:if>
					
					<c:if test='${!ff1 && user.locationID == "0"}'>
					<tr class="content2"> 
						<td align="right">ѡ�������</td>  
						<td colspan="1"><select name="depotpartId" class="select_class" values="${out.depotpartId}"> 
								<option value=''>--</option>
								<c:forEach items="${depotpartList}" var="item">
								<option value='${item.id}'>${item.name}</option>
								</c:forEach>
							</select><font color="#FF0000">*</font></td>
							<td align="right"><div id="dirs1">Ŀ������</div></td>  
						<td colspan="1"><div id="dirs"><select name="destinationId" class="select_class" 
						oncheck="notNone;noEquals('${currentLocationId}')"
						message="����Ϊ�ջ���ѡ��ǰ����"
						values="${out.destinationId}"> 
								<option value=''>--</option>
								<c:forEach items="${locationList}" var="item">
								<option value='${item.id}'>${item.locationName}</option>
								</c:forEach>
							</select><font color="#FF0000">*</font></div></td>
					</tr>
					</c:if>
					
					<c:if test='${!ff1}'>
					<tr class="content2" id="in_out"> 
						<td align="right">�����ⵥ��</td>  
						<td colspan="1"><input type="text" name="refOutFullId" maxlength="40" oncheck="notNone;" readonly="readonly"
							value="${out.refOutFullId}">&nbsp;&nbsp;<input
							type="button" value="&nbsp;...&nbsp;" name="qout" class="button_class" onclick="selectOut()">
							<font color="#FF0000">*</font></td>
						
						<td align="right"></td> 
						<td></td>
					</tr>
					</c:if>
					
					<tr class="content2">
                        <td align="right">���ʽ��</td>
                        <td colspan="3">
                        <select name="reserve3" class="select_class" oncheck="notNone;" head="���ʽ" values="${out.reserve3}" readonly="true"
                        style="width: 240px">
                            <option value='2'>�ͻ����ú�ҵ��Ա���ö�ȵ���</option>
                            <option value='1'>�����(�������ͻ�)</option>
                        </select>
                        <font color="#FF0000">*</font></td>
                    </tr>
					
					
					<tr class="content1">
						<td align="right">��ע��</td>
						<td colspan="3"><textarea name="description" rows="3"
							cols="55">${out.description}</textarea></td>
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
						<td width="10%" align="center">����</td>
						<td width="20%" align="left">���<span id="total">(�ܼ�:${my:formatNum(out.total)})</span></td>
						<td width="25%" align="center">�ɱ�</td>
						<td width="15%" align="center"><input type="button"
							value="����" class="button_class" onclick="addTr()"></td>
					</tr>

					<tr class="content1" id="trCopy" style="display: none;">
						<td width="20%"><input type="text" name="productName"
							onclick="opens(this)" productId="" readonly="readonly" style="width: 100%; cursor: hand">
							</td>

						<td width="5%"><select name="unit" style="WIDTH: 50px;">
							<option value="��">��</option>
							<option value="ö">ö</option>
							<option value="��">��</option>
							<option value="��">��</option>
						</select></td>

						<td width="10%" align="center"><input type="text" style="width: 100%" maxlength="6"
							onkeyup="cc(this)" name="amount"></td>

						<td width="10%" align="center"><input type="text" style="width: 100%"
							onkeyup="cc(this)" onblur="blu(this)" name="price"></td>

						<td width="20%" align="center"><input type="text" value="0.00"
							readonly="readonly" style="width: 100%" name="value"></td>
							
						<td width="25%" align="center"><input type="text"
							 style="width: 100%" name="desciprt"></td>

						<td width="15%" align="center"></td>
					</tr>

					<c:forEach items="${baseList}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content2" : "content1"}'>
							<td width="20%"><input type="text" name="productName"
								value="${item.productName}" onclick="opens(this)"
								productId="${item.productId}" readonly="readonly" style="width: 100%"
								style="cursor: hand"></td>

							<td width="10%"><select name="unit" style="WIDTH: 50px;">
								<option value="��" ${item.unit=="��" ? "selected" : ""}>��</option>
								<option value="ö" ${item.unit=="ö" ? "selected" : ""}>ö</option>
								<option value="��" ${item.unit=="��" ? "selected" : ""}>��</option>
								<option value="��" ${item.unit=="��" ? "selected" : ""}>��</option>

							</select></td>

							<td width="10%" align="center"><input type="text" style="width: 100%"
								value="${item.amount}" onkeyup="cc(this)" name="amount"></td>

							<td width="10%" align="center"><input type="text" style="width: 100%"
								value="${my:formatNum(item.price)}" onkeyup="cc(this)" onblur="blu(this)"
								name="price"></td>

							<td width="20%" align="center"><input type="text"
								value="${my:formatNum(item.value)}" readonly="readonly" style="width: 100%"
								name="value"></td>
							
							<td width="25%" align="center"><input type="text" value="${item.description}"
							 style="width: 100%" name="desciprt"></td>
							 
							<c:if test="${vs.first}">
							<td width="15%" align="center"></td>
							</c:if>
							
							<c:if test="${!vs.first}">
							<td width="15%" align="center"><input type=button value="&nbsp;ɾ ��&nbsp;" class=button_class onclick="removeTr(this)"></td>
							</c:if>
						</tr>
					</c:forEach>
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
		<td width="97%">
		<div align="right"><input type="button" class="button_class"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onClick="save()" />&nbsp;&nbsp;<input
			type="button" class="button_class"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onClick="sub()" />&nbsp;&nbsp;<input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;">
			</div>
		</td>
		<td width="3%"></td>
	</tr>

</table>
</form>
</body>
</html>

