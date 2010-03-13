<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>

<html>

<head>
<p:link title="��ѯ" />
<c:if test='${flag == "3"}'>
<base target="_self">
</c:if>
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
<script src="../js/CommonScriptMethod.js"></script>
<script src="../js/prototype.js"></script>
<script src="../js/common.js"></script>
<script src="../js/fanye.js"></script>
<script src="../js/public.js"></script>
<script src="../js/title_div.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script language="javascript">
function detail()
{
	var index = $Index('fullId');
	document.location.href = '../admin/out.do?method=findOut&outId=' + getRadioValue("fullId") + '&fow=2&index=' + index + "&flag=${flag}";
}

function sures()
{
	var opener = window.common.opener();
	var oo = getRadio("fullId");
	
	if (oo == null)
	{
		alert('��ѡ��ⵥ');
		return;
	}
	
    opener.getOutId(oo.value);
    //opener.getProduct(oo.value, oo.productName, oo.num);
    opener = null;
    window.close();
}

function res()
{
	$O('customerId').value= '';
	$O('customerName').value= '';
	$O('id').value= '';
	$O('type').selectedIndex = 0;
	$O('checks').selectedIndex = 0;
	$O('stafferName').selectedIndex = 0;
	$O('pay').selectedIndex = 0;
	$O('reCom').selectedIndex = 0;
	$O('redate').value= '';
}

function comp()
{
	var now = '${now}';
	
	var str1 = $O('outTime').value;
	
	var str2 = $O('outTime1').value;
	
	//����Ҫ�п�ʼ�ͽ���ʱ��һ��
	if (str1 == '' && str2 == '')
	{
		alert('����Ҫ�п�ʼ�ͽ���ʱ��һ��');
		return false;
	}
	
	if (str1 != '' && str2 == '')
	{
		if (!coo(str1, now))
		{
			alert('��ѯ���ڿ�Ȳ��ܴ���30����(900��)!');
			return false;
		}
		
		$O('outTime1').value = now;
	}
	
	if (str1 == '' && str2 != '')
	{
		if (!coo(now, str2))
		{
			alert('��ѯ���ڿ�Ȳ��ܴ���30����(900��)!');
			return false;
		}
		
		$O('outTime').value = now;
	}
	
	if (str1 != '' && str2 != '')
	{
		if (!coo(str1, str2))
		{
			alert('��ѯ���ڿ�Ȳ��ܴ���30����(900��)!');
			return false;
		}
	}
	
	return true;
}

function coo(str1, str2)
{
	var s1 = str1.split('-');
	var s2 = str2.split('-');
	
	var year1 = parseInt(s1[0], 10);
	
	var year2 = parseInt(s2[0], 10);
	
	var month1 = parseInt(s1[1], 10);
	
	var month2 = parseInt(s2[1], 10);
	
	var day1 = parseInt(s1[2], 10);
	
	var day2 = parseInt(s2[2], 10);
	
	return Math.abs((year2 - year1) * 365 + (month2 - month1) * 30 + (day2 - day1)) <= 900;
}

function modfiy()
{
	if (getRadio('fullId').statuss == '0' || getRadio('fullId').statuss == '2')
	{
		document.location.href = '../admin/out.do?method=findOut&outId=' + getRadioValue("fullId") + "&fow=1";
	}
	else
	{
		alert('ֻ�б���̬�Ͳ���̬�ĳ��ⵥ�����޸�!');
	}
}

function del()
{
	if (getRadio('fullId').statuss == '0' || getRadio('fullId').statuss == '2')
	{
		 if (window.confirm("ȷ��ɾ�����ⵥ?"))
		document.location.href = '../admin/out.do?method=delOut&outId=' + getRadioValue("fullId");
	}
	else
	{
		alert('ֻ�б���̬�Ͳ���̬�ĳ��ⵥ����ɾ��!');
	}
}

function query()
{
	if (comp())
	{
		adminForm.submit();
	}
}

function selectCustomer()
{
        
    window.common.modal("./out.do?method=queryAllCustomer&flagg=${GFlag}");
}

function getCustmeor(id, name, conn, phone)
{
	$O("customerName").value = name;
	$O("customerId").value = id;
}

var jmap = new Object();
<c:forEach items="${listOut3}" var="item">
	jmap['${item.fullId}'] = "${divMap[item.fullId]}";
</c:forEach>

function showDiv(id, obj)
{
	obj.focus();
	tooltip.showTable(jmap[id]);
}

function reject()
{
    if (getRadio('fullId').statuss == 1 && getRadio('fullId').lreserve3 == 1 && getRadio('fullId').pay == 0)
    {
        $.messager.prompt('����', '�����벵��ԭ��', '', function(r){
                if (r)
                {
                    $Dbuttons(true);
                    getObj('method').value = 'modifyOutStatus';
                    getObj('statuss').value = '2';
                    getObj('oldStatus').value = getRadio('fullId').statuss;
                    getObj('outId').value = getRadioValue("fullId");
        
                    getObj('radioIndex').value = $Index('fullId');
        
                    var sss = r;
        
                    getObj('reason').value = r;
        
                    if (!(sss == null || sss == ''))
                    {
                        adminForm.submit();
                    }
                    else
                    {
                        $Dbuttons(false);
                    }
                }
            });
    }
    else
    {
        alert('�����Բ���!');
    }
}

</script>
<title>���ⵥ�б�</title>

</head>
<body class="body_class" onkeypress="tooltip.bingEsc(event)">
<form action="./out.do" name="adminForm"><input type="hidden"
	value="queryOut3" name="method"> <input type="hidden" value="1"
	name="firstLoad">
	<input type="hidden"
    name="reason">
	<input type="hidden" value="${customerId}"
	name="customerId">
	<input type="hidden" value=""
	name="outId">
	<input type="hidden" value="${flag}"
	name="flag">
	<input type="hidden" value=""
    name="radioIndex">
    <input type="hidden" value=""
    name="oldStatus">
    <input type="hidden" value=""
    name="statuss">
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
				<td width="550" class="navigation">��ѯ�ⵥ</td>
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

<br>
<table width="98%" border="0" cellpadding="0" cellspacing="0"
	align="center">
	<tr>
		<td align='center' colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<c:if test='${flag != "4"}'>
				<table width="100%" border="0" cellspacing='1'>
					<tr class="content1">
						<td width="15%" align="center">��ʼʱ��</td>
						<td align="center">
						<p:plugin name="outTime" size="20" value="${out3Map.outTime}"/>
						</td>
						<td width="15%" align="center">����ʱ��</td>
						<td align="center">
						<p:plugin name="outTime1" size="20" value="${out3Map.outTime1}"/>
						</td>
					</tr>

					<tr class="content2">
						<td width="15%" align="center">����</td>
						<td align="center"><input type="text" name="id" value="${out3Map.id}"></td>
						
						<td width="15%" align="center">�ͻ���</td>
						<td align="center"><input type="text" name="customerName" maxlength="14" value="${out3Map.customerName}"
							onclick="selectCustomer()" style="cursor: hand"
							readonly="readonly"></td>
					</tr>

					<tr class="content1">
						<td width="15%" align="center">�ⵥ����:</td>
						<td align="center" ><select name="type" values="${out3Map.type}"
							class="select_class">
							<option value="">--</option>
							<option value="0">���۵�</option>
							<option value="1">��ⵥ</option>
						</select></td>
						
						<td width="15%" align="center">�Ƿ�˶�:</td>
						<td align="center" ><select name="checks"  values="${out3Map.checks}"
							class="select_class">
							<option value=""  >--</option>
							<option value="0">δ�˶�</option>
							<option value="1">�Ѻ˶�</option>
						</select></td>
					</tr>
					
					<tr class="content2">
						<td width="15%" align="center">������</td>
						<td align="center"><input name="stafferName" value="${out3Map.stafferName}" type="text">
							</td>
						<td width="15%" align="center">�ؿ�����</td>
						<td align="center">
						<select name="reCom" style="WIDTH: 75px" values="${out3Map.reCom}">
							<option value="">--</option>
							<option value="=" }>����</option>
							<option value="&gt;=" >���ڵ���</option>
							<option value="&lt;=" >С�ڵ���</option>
						</select>
						<p:plugin name="redate" size="20" value="${out3Map.redate}"/>
						</td>
					</tr>
					
					<tr class="content1">
						<td width="15%" align="center">�Ƿ�ؿ�</td>
						<td align="center"><select name="pay"
							class="select_class">
							<option value="" ${out3Map.pay==""?"selected" : ""}>--</option>
							<option value="1" ${out3Map.pay=="1"?"selected" : ""}>��</option>
							<option value="0" ${out3Map.pay=="0"?"selected" : ""}>��</option>
						</select></td>
						
						<td colspan="2" align="right"><input type="button"
							onclick="query()" class="button_class"
							value="&nbsp;&nbsp;�� ѯ&nbsp;&nbsp;">&nbsp;&nbsp;<input type="button"
							onclick="res()" class="button_class"
							value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></td>
					</tr>
				</table>
				</c:if>
				</td>
			</tr>
		</table>
		</td>
	</tr>

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
								<td class="caption"><strong>���${fg}�ⵥ:</strong><font color=blue>[��ǰ��ѯ����:${TATOL}]</font></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
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
		<td align='center' colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id=result>
					<tr align="center" class="content0">
						<td align="center" width="5%" align="center">ѡ��</td>
						<td align="center" onclick="tableSort(this)" class="td_class">���ݱ��</td>
						<td align="center" onclick="tableSort(this)" class="td_class">�ͻ�</td>
						<td align="center" onclick="tableSort(this)" class="td_class">״̬</td>
						<td align="center" onclick="tableSort(this)" class="td_class">�ⵥ����</td>
						<td align="center" onclick="tableSort(this)" class="td_class">��дʱ��</td>
						<td align="center" onclick="tableSort(this)" class="td_class">�ؿ�����</td>
						<td align="center" onclick="tableSort(this, true)" class="td_class">���</td>
						<td align="center" onclick="tableSort(this)" class="td_class">��д��</td>
						<td align="center" onclick="tableSort(this)" class="td_class">����</td>
						<td align="center" onclick="tableSort(this)" class="td_class">�˶�</td>
					</tr>

					<c:set var="ind" value="${out3Map.rIndex > my:length(listOut3) ? 0 : out3Map.rIndex}"/>
					<c:set var="hr" value='${flag != "3"}'/>
					<c:forEach items="${listOut3}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
							<td align="center"><input type="radio" name="fullId" lreserve3=${item.reserve3} lpay=${item.pay} 
								statuss='${item.status}' value="${item.fullId}" ${vs.index == ind ? "checked" : ""}/></td>
							<td align="center"
							onMouseOver="showDiv('${item.fullId}', this)" onmousemove="tooltip.move()" onmouseout="tooltip.hide()"><a 
							onclick="hrefAndSelect(this)" 
							<c:if test='${hr}'>
							href="../admin/out.do?method=findOut&outId=${item.fullId}"
							</c:if>
							>${item.fullId}</a></td>
							<td align="center" onclick="hrefAndSelect(this)">${item.customerName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:status(item.status)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.type == 0 ? "���۵�" : "��ⵥ"}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.outTime}</td>
							<c:if test="${item.pay == 0}">
							<td align="center" onclick="hrefAndSelect(this)"><font color=red>${item.redate}</font></td>
							</c:if>
							<c:if test="${item.pay == 1}">
							<td align="center" onclick="hrefAndSelect(this)"><font color=blue>${item.redate}</font></td>
							</c:if>
							<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.total)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.stafferName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.hadPay}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.checks == "" ? "δ�˶�" : "<font color=blue><B>�Ѻ˶�</B></font>"}</td>
						</tr>
					</c:forEach>
				</table>
				<c:if test='${hr}'>
				<p:turning url="../admin/out.do?method=queryOut3&flag=${flag}"></p:turning>
				</c:if>
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
	<c:set var='dis' value='${flag eq "2" ? "����" : "�ܲ����"}'/>
	<c:if test="${my:length(listOut3) > 0}">
	<c:if test='${flag != "3" && flag != "4"}'>
	<tr>
		<td width="100%">
		<div align="right"><input type="button" class="button_class"
			value="&nbsp;&nbsp;${dis}�˶�&nbsp;&nbsp;" onClick="detail()">&nbsp;&nbsp;
			<input type="button" name="bu2"
                class="button_class" value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"
                onclick="reject()" /></div>
		</td>
		<td width="0%"></td>
	</tr>
	</c:if>
	<c:if test='${flag == "3" || flag == "4"}'>
	<tr>
		<td width="100%">
		<div align="right"><input type="button" class="button_class"
			value="&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;" onClick="sures()">&nbsp;&nbsp;</div>
		</td>
		<td width="0%"></td>
	</tr>
	</c:if>
	</c:if>

	<tr height="10">
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td colspan='2' align="center"><FONT color="blue">${MESSAGE_INFO}</FONT><FONT
			color="red">${errorInfo}</FONT> <c:remove var="MESSAGE_INFO"
			scope="session" /><c:remove var="errorInfo" scope="session" /></td>
	</tr>
</table>

</form>
</body>
</html>
