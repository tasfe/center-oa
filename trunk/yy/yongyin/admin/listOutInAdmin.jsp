<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="��ѯ���۵�" />
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
<script src="../js/title_div.js"></script>
<script src="../js/common.js"></script>
<script src="../js/cnchina.js"></script>
<script src="../js/public.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script language="javascript">
function detail()
{
	document.location.href = '../admin/out.do?method=findOut&outId=' + getRadioValue("fullId");
}

<c:set var="ad" value='${user.role == "ADMIN"}'/>
var dirs = {'ADMIN':3, 'FLOW': 7, 'MANAGER': 6};
var acc = {'ADMIN':7, 'FLOW': 6, 'MANAGER': 1};
var acc1 = {'ADMIN':6, 'MANAGER': 1};
var systemLocation = '${sessionScope.currentLocationId}';
function check()
{
	if (systemLocation == '0')
	{
		cckk = acc;
	}
	else
	{
		cckk = acc1;
	}

	if (getRadio('fullId').statuss == cckk['${user.role}'])
	{
		if (getRadio('fullId').statuss == 7 && getRadio('fullId').loc == 0)
		{
			document.location.href = '../admin/out.do?method=findOut&outId=' + getRadioValue("fullId") + '&fow=3';
			return;
		}

		var hi = '';
		if (getRadio('fullId').hasMap == "true")
		{
			hi = "ע��:��ǰҵ��Ա���г��ڵ����۵�  ";
		}

		if (window.confirm(hi + "ȷ�����ͨ�����۵�?"))
		{
			$Dbuttons(true);
			getObj('method').value = 'modifyOutStatus';

		 	getObj('statuss').value = dirs['${user.role}'];
		 	getObj('oldStatus').value = getRadio('fullId').statuss;

		 	getObj('outId').value = getRadioValue("fullId");

		 	getObj('radioIndex').value = $Index('fullId');

		 	//var sss = window.prompt('���������ͨ��ԭ��', '');

		 	getObj('reason').value = 'ͬ��';

		 	adminForm.submit();
		}
		else
		{
		    $Dbuttons(false);  
		}
	}
	else
	{
		alert('�����Բ���!');
	}
}

function cons()
{
	if (getRadio('fullId').con > 0)
	{
		document.location.href = '../admin/transport.do?method=findConsign&fullId=' + getRadioValue("fullId");
	}
	else
	{
		alert('�����Բ���!');
	}
}

function reject()
{
	if (systemLocation == '0')
	{
		cckk = acc;
	}
	else
	{
		cckk = acc1;
	}

	if (getRadio('fullId').statuss == cckk['${user.role}'])
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

function exports()
{
	if (window.confirm("ȷ��������ǰ��ȫ����ѯ�Ŀⵥ?"))
	document.location.href = '../admin/out.do?method=export&flags=2';
}

function prints()
{
	document.location.href = '../admin/out.do?method=exportWord&outId=' + $$('fullId');
}

function query()
{
    getObj('method').value = 'queryOut2';
	adminForm.submit();
}

function selectCustomer()
{
    window.common.modal("./out.do?method=queryAllCustomer&flagg=0");
}

function getCustmeor(id, name, conn, phone)
{
	getObj("customerName").value = name;
	getObj("customerId").value = id;
}

var bs = '${user.role}';
var jmap = new Object();
<c:forEach items="${listOut2}" var="item">
	jmap['${item.fullId}'] = "${divMap[item.fullId]}";
</c:forEach>

function showDiv(id)
{
	tooltip.showTable(jmap[id]);
}

function load()
{
	if (bs == 'THR')
	{
		$d('bu1');
		$v('bu1');

		$d('bu2');
		$v('bu2');
	}
	
	tooltip.init ();
}


</script>
<title>�б�</title>
</head>

<body class="body_class" onkeypress="tooltip.bingEsc(event)" onload="load()">
<form action="./out.do" name="adminForm" method="post"><input type="hidden"
	value="queryOut2" name="method"> <input type="hidden" value="1"
	name="firstLoad"><input type="hidden"
	name="reason"><input type="hidden" value="${customerId}"
	name="customerId">
	<input type="hidden" value=""
	name="outId">
	<input type="hidden" value=""
	name="statuss">
	<input type="hidden" value=""
    name="oldStatus">
	<input type="hidden" value=""
	name="radioIndex">
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
				<td width="550" class="navigation">�ⵥ���� &gt;&gt; ��ѯ���۵�</td>
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
				<table width="100%" border="0" cellspacing='1'>
					<tr class="content1">
						<td width="15%" align="center">��ʼʱ��</td>
						<td align="center" width="35%"><p:plugin name="outTime" size="20" value="${outTime}"/></td>
						<td width="15%" align="center">����ʱ��</td>
						<td align="center"><p:plugin name="outTime1" size="20" value="${outTime1}"/>
						</td>
					</tr>

					<tr class="content2">
						<td width="15%" align="center">���۵�״̬</td>
						<td align="center"><select name="status" class="select_class" values="${status}">
							<option value="" >--</option>
							<option value="0">����</option>
							<option value="1">�ύ</option>
							<option value="6">�ܾ���ͨ��</option>
							<option value="7">����ͨ��</option>
							<option value="2">����</option>
							<option value="3">����</option>
						</select></td>
						<td width="15%" align="center">�ͻ�</td>
						<td align="center"><input type="text" name="customerName"
							maxlength="14" value="${customerName}" onclick="selectCustomer()"
							style="cursor: hand" readonly="readonly"></td>
					</tr>

					<tr class="content1">
						<td width="15%" align="center">��������</td>
						<td align="center"><select name="outType" values="${outType}"
							class="select_class">
							<option value="" >--</option>
							<option value="0">���۳���</option>
						</select></td>
						<td width="15%" align="center">���۵���</td>
						<td align="center"><input type="text" name="id" value="${id}" onkeypress="window.common.enter(query)"></td>
					</tr>

					<tr class="content2">
						<td width="15%" align="center">������</td>
						<td align="center"><select name="stafferName" values="${stafferName}" quick="true"
							class="select_class">
							<option value="" >--</option>
							<c:forEach items="${staffers}" var="item">
								<option value="${item}">${item}</option>
							</c:forEach>
						</select></td>
						<td width="15%" align="center">�ؿ�����</td>
						<td align="center">
						<select name="reCom" style="WIDTH: 75px" values="${reCom}">
							<option value="">--</option>
							<option value="=">����</option>
							<option value="&gt;=">���ڵ���</option>
							<option value="&lt;=">С�ڵ���</option>
						</select>
						<p:plugin name="redate" size="20" value="${redate}"/>
						</td>
					</tr>

					<tr class="content1">
						<td width="15%" align="center">�Ƿ�ؿ�</td>
						<td align="center"><select name="pay" values="${pay}"
							class="select_class">
							<option value="" >--</option>
							<option value="1">��</option>
							<option value="0">��</option>
							<option value="2">����</option>
						</select></td>

						<td colspan="2" align="right"><input type="button"
							onclick="query()" class="button_class"
							value="&nbsp;&nbsp;�� ѯ&nbsp;&nbsp;">&nbsp;&nbsp;</td>
					</tr>
				</table>
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
								<td class="caption"><strong>������۵�:</strong></td>
								<td class="caption" align="right"><strong>���ϼ�:${my:formatNum(total)}</strong></td>
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
				<table width="100%" border="0" cellspacing='1'>
					<tr align="center" class="content0">
						<td align="center" width="5%" align="center">ѡ��</td>
						<td align="center" onclick="tableSort(this)" class="td_class">���ݱ��</td>
						<td align="center" onclick="tableSort(this)" class="td_class">�ͻ�</td>
						<td align="center" onclick="tableSort(this)" class="td_class">״̬</td>
						<td align="center" onclick="tableSort(this)" class="td_class">��������</td>
						<td align="center" onclick="tableSort(this)" class="td_class">��������</td>
						<td align="center" onclick="tableSort(this)" class="td_class">�ؿ�����</td>
						<td align="center" onclick="tableSort(this)" class="td_class">����(��)</td>
						<td align="center" onclick="tableSort(this)" class="td_class">��������</td>
						<td align="center" onclick="tableSort(this, true)" class="td_class">���</td>
						<td align="center" onclick="tableSort(this, true)" class="td_class">����</td>
						<td align="center" onclick="tableSort(this)" class="td_class">������</td>
						<td align="center" onclick="tableSort(this)" class="td_class">����</td>
						<td align="center" onclick="tableSort(this)" class="td_class">������</td>
					</tr>

					<c:forEach items="${listOut2}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'
						>
							<td align="center"><input type="radio" name="fullId" temp="${item.tempType}" con="${item.consign}" loc="${item.location}"
							hasMap="${hasMap[item.fullId]}"	statuss='${item.status}' value="${item.fullId}" index="${radioIndex}"/></td>
							<td align="center"
							onMouseOver="showDiv('${item.fullId}')" onmousemove="tooltip.move()" onmouseout="tooltip.hide()"><a
							onclick="hrefAndSelect(this)" href="../admin/out.do?method=findOut&outId=${item.fullId}">${item.fullId}</a></td>
							<td align="center" onclick="hrefAndSelect(this)">${item.customerName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:status(item.status)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.outType == 0 ? "���۳���" : "��������"}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.outTime}</td>
							<c:if test="${item.pay == 0}">
							<td align="center" onclick="hrefAndSelect(this)"><font color=red>${item.redate}</font></td>
							</c:if>
							<c:if test="${item.pay == 1}">
							<td align="center" onclick="hrefAndSelect(this)"><font color=blue>${item.redate}</font></td>
							</c:if>
							<td align="center" onclick="hrefAndSelect(this)">${overDayMap[item.fullId]}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.arriveDate}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.total)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.hadPay}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.stafferName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:get('outCredit', item.reserve2)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.consign == 1 ? "δͨ��" : ""}${item.consign == 2 ? "<font color=blue><b>ͨ��</b></font>" : ""}</td>
						</tr>
					</c:forEach>
				</table>

				<p:formTurning form="adminForm" method="queryOut2"></p:formTurning>
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

	<c:if test="${my:length(listOut2) > 0}">
		<tr>
			<td width="100%">
			<div align="right">
				<c:if test='${user.role == "FLOW" || user.role == "THR"}'>
				<input type="button" class="button_class"
				value="&nbsp;��������&nbsp;" onClick="cons()">&nbsp;&nbsp;
				</c:if>
				<input type="button" class="button_class"
				value="&nbsp;&nbsp;�� ϸ&nbsp;&nbsp;" onClick="detail()">&nbsp;&nbsp;<input name="bu1"
				type="button" class="button_class" value="&nbsp;���ͨ��&nbsp;"
				onclick="check()" />&nbsp;&nbsp;<input type="button" name="bu2"
				class="button_class" value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"
				onclick="reject()" />&nbsp;&nbsp;<input type="button"
				class="button_class" value="&nbsp;������ѯ���&nbsp;" onclick="exports()" />
			</div>
			</td>
			<td width="0%"></td>
		</tr>
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
