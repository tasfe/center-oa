<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@include file="./common.jsp"%>

<html>

<head>
<p:link title="��ѯ���۵�" />
<script src="../js/CommonScriptMethod.js"></script>
<script src="../js/prototype.js"></script>
<script src="../js/title_div.js"></script>
<script src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/compatible.js"></script>
<script language="javascript">
function detail()
{
	document.location.href = '../admin/out.do?method=findOut&outId=' + getRadioValue("fullId");
}

function pagePrint()
{
	window.open('../admin/out.do?method=findOut&fow=4&outId=' + getRadioValue("fullId"));
	//document.location.href = ;
}

function exports()
{
	if (window.confirm("ȷ��������ǰ��ȫ����ѯ�Ŀⵥ?"))
	document.location.href = '../admin/out.do?method=export&flags=1';
}

function mark()
{
	if (window.confirm("ȷ����ǵ�ǰѡ��Ŀⵥ?"))
	{
		adminForm.action = '../admin/out.do';
		adminForm.method.value = 'mark';
		adminForm.outId.value = getRadioValue("fullId");

		adminForm.submit();
	}
}

function comp()
{
	var now = '${now}';

	var str1 = $('outTime').value;

	var str2 = $('outTime1').value;

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
			alert('��ѯ���ڿ�Ȳ��ܴ���3����(90��)!');
			return false;
		}

		$('outTime1').value = now;
	}

	if (str1 == '' && str2 != '')
	{
		if (!coo(now, str2))
		{
			alert('��ѯ���ڿ�Ȳ��ܴ���3����(90��)!');
			return false;
		}

		$('outTime').value = now;
	}

	if (str1 != '' && str2 != '')
	{
		if (!coo(str1, str2))
		{
			alert('��ѯ���ڿ�Ȳ��ܴ���3����(90��)!');
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

	return Math.abs((year2 - year1) * 365 + (month2 - month1) * 30 + (day2 - day1)) <= 90;
}

function modfiy()
{
	if (getRadio('fullId').statuss == '0' || getRadio('fullId').statuss == '2')
	{
		document.location.href = '../admin/out.do?method=findOut&outId=' + getRadioValue("fullId") + "&fow=1";
	}
	else
	{
		alert('ֻ�б���̬�Ͳ���̬�Ŀⵥ�����޸�!');
	}
}

function del()
{
	if (getRadio('fullId').statuss == '0' || getRadio('fullId').statuss == '2' || getRadio('fullId').tempType == '1')
	{
		 if (window.confirm("ȷ��ɾ�����۵�?"))
		document.location.href = '../admin/out.do?method=delOut&outId=' + getRadioValue("fullId");
	}
	else
	{
		alert('ֻ�б���̬�Ͳ���̬�Ŀⵥ����ɾ��!');
	}
}

function sub()
{
	if (getRadio('fullId').statuss == '0' || getRadio('fullId').statuss == '2')
	{
		 if (window.confirm("ȷ���ύ${ff ? "��" : "��"}�ⵥ?"))
		 {
		 	$('method').value = 'modifyOutStatus';
		 	$('oldStatus').value = getRadio('fullId').statuss;
		 	$('statuss').value = '1';
		 	$('outId').value = getRadioValue("fullId");
		 	
		 	disableAllButton();
		 	adminForm.submit();
		 }
	}
	else
	{
		alert('��״̬�����ύ!');
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
    window.common.modal("./out.do?method=queryCustomer&flagg=${GFlag}");
}

function getCustmeor(id, name, conn, phone)
{
	$("customerName").value = name;
	$("customerId").value = id;
}

function res()
{
	$('customerName').value = '';
	$("customerId").value = '';
	$("id").value = '';
}

var jmap = new Object();
<c:forEach items="${listOut1}" var="item">
	jmap['${item.fullId}'] = "${divMap[item.fullId]}";
</c:forEach>

function showDiv(id)
{
	tooltip.showTable(jmap[id]);
}

function load()
{
	loadForm();
	tooltip.init();
}

</script>

</head>
<body class="body_class" onkeypress="tooltip.bingEsc(event)" onload="load()">
<form action="./out.do" name="adminForm"><input type="hidden"
	value="queryOut" name="method"> <input type="hidden" value="1"
	name="firstLoad">
	<input type="hidden" value="${customerId}"
	name="customerId">
	<input type="hidden" value="${queryType}"
	name="queryType">
	<input type="hidden" value="${GFlag}"
	name="flagg">
	<input type="hidden" value=""
	name="outId">
	<input type="hidden" value=""
    name="oldStatus">
	<input type="hidden" value=""
	name="statuss">
<c:set var="fg" value='${ff ? "���" : "����"}'/>
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
				<td width="550" class="navigation">�ⵥ���� &gt;&gt; ��ѯ${fg}��</td>
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
<table width="100%" border="0" cellpadding="0" cellspacing="0"
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
						<td width="15%" align="center">${fg}��״̬</td>
						<td align="center">
						<c:if test="${!ff}">
						<select name="status" class="select_class" values="${status}">
							<option value="">--</option>
							<option value="0">����</option>
							<option value="1">�ύ</option>
							<option value="6">�ܾ���ͨ��</option>
							<option value="2">����</option>
							<option value="3">����</option>
						</select>
						</c:if>

						</td>
						<td width="15%" align="center">${ff ? "��Ӧ��" : "�ͻ�"}��</td>
						<td align="center"><input type="text" name="customerName" maxlength="14" value="${customerName}"
							onclick="selectCustomer()" style="cursor: hand"
							readonly="readonly"></td>
					</tr>

					<tr class="content1">
						<td width="15%" align="center">${fg}����</td>
						<td align="center">
						<c:if test="${!ff}">
						<select name="outType"
							class="select_class">
							<option value=""  ${outType==""?"selected" : ""}>--</option>
							<option value="0" ${outType=="0"?"selected" : ""}>���۳���</option>
							<option value="1" ${outType=="1"?"selected" : ""}>��������</option>
						</select>
						</c:if>

						<c:if test="${ff}">
						<select name="outType"
							class="select_class" values="${outType}">
								<option value=''>--</option>
								<option value="0">�ɹ����</option>
								<option value="1">����</option>
								<option value="4">����</option>
								<option value="2">�̿�����</option>
								<option value="3">��ӯ���</option>
								<option value="5">�˻������</option>
								<option value="6">���ϳ���</option>
						</select>
						</c:if>

						</td>
						<td width="15%" align="center">${fg}����</td>
						<td align="center"><input type="text" name="id" value="${id}"></td>
					</tr>

					<tr class="content2">
						<td width="15%" align="center">�Ƿ�ؿ�</td>
						<td align="center"><select name="pay" values="${pay}"
							class="select_class">
							<option value="">--</option>
							<option value="1">��</option>
							<option value="0">��</option>
						</select></td>

						<td width="15%" align="center">��;��ʽ</td>
						<td colspan="1" align="center">
						<select name="inway" values="${inway}"
							class="select_class">
							<option value="">--</option>
							<option value="1">��;</option>
							<option value="2">��;����</option>
							</select>
						</td>
					</tr>

					<tr class="content1">

						<td colspan="4" align="right"><input type="button" id="query_b"
							onclick="query()" class="button_class"
							value="&nbsp;&nbsp;�� ѯ&nbsp;&nbsp;">&nbsp;&nbsp;<input type="button" onclick="res()" class="button_class" value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></td>
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
								<td class="caption"><strong>���${fg}��:</strong><font color=blue>[��ǰ��ѯ����:${my:length(listOut1)}]</font></td>
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
						<td align="center" onclick="tableSort(this)" class="td_class">${ff ? "��Ӧ��" : "�ͻ�"}</td>
						<td align="center" onclick="tableSort(this)" class="td_class">״̬</td>
						<td align="center" onclick="tableSort(this)" class="td_class">${fg}����</td>
						<td align="center" onclick="tableSort(this)" class="td_class">${fg}ʱ��</td>
						<c:if test="${!ff}">
						<td align="center" onclick="tableSort(this)" class="td_class">�ؿ�����</td>
						</c:if>
						<td align="center" onclick="tableSort(this)" class="td_class">���</td>
						<td align="center" onclick="tableSort(this)" class="td_class">��;</td>
						<td align="center" onclick="tableSort(this)" class="td_class">����</td>
						<td align="center" onclick="tableSort(this)" class="td_class">${fg}��</td>
						<td align="center" onclick="tableSort(this)" class="td_class">������</td>
					</tr>

					<c:forEach items="${listOut1}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'
						>
							<td align="center"><input type="radio" name="fullId" tempType="${item.tempType}"
								statuss='${item.status}' value="${item.fullId}" ${vs.index== 0 ? "checked" : ""}/></td>
							<td align="center"
							onMouseOver="showDiv('${item.fullId}')" onmousemove="tooltip.move()" onmouseout="tooltip.hide()"><a onclick="hrefAndSelect(this)" href="../admin/out.do?method=findOut&outId=${item.fullId}">${item.mark ? "<font color=blue><B>" : ""}
							${item.fullId}${item.mark ? "</B></font>" : ""}</a></td>
							<td align="center">${item.customerName}</td>
							<td align="center">${my:status(item.status)}</td>
							<c:if test="${!ff}">
							<td align="center" onclick="hrefAndSelect(this)">${item.outType == 0 ? "���۳���" : "��������"}</td>
							</c:if>
							<td align="center" onclick="hrefAndSelect(this)">${item.outTime}</td>
							<c:if test="${!ff}">
							<c:if test="${item.pay == 0}">
							<td align="center" onclick="hrefAndSelect(this)"><font color=red>${item.redate}</font></td>
							</c:if>
							<c:if test="${item.pay == 1}">
							<td align="center" onclick="hrefAndSelect(this)"><font color=blue>${item.redate}</font></td>
							</c:if>
							</c:if>
							<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.total)}</td>
							<%
								String[] sss1 = new String[]{"" , "<font color=red>��;</font>", "��;����"};
								request.setAttribute("sss1", sss1);
							%>
							<td align="center" onclick="hrefAndSelect(this)">${my:getValue(item.inway, sss1)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.hadPay}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.stafferName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:get("reprotType", item.consign)}</td>
						</tr>
					</c:forEach>
				</table>

				<p:formTurning form="adminForm" method="queryOut"></p:formTurning>
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

	<c:if test="${my:length(listOut1) > 0}">
	<tr>
		<td width="100%">
		<div align="right">
		<c:set var="adm" value='${user.role == "ADMIN"}'/>
		<c:if test="${adm}">
		<input type="button"
			class="button_class" onclick="pagePrint()"
			value="&nbsp;&nbsp;���ƴ�ӡ&nbsp;&nbsp;">&nbsp;&nbsp;
		</c:if>
		<c:if test="${!ff}">
		<input type="button" class="button_class"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onClick="mark()">&nbsp;&nbsp;
		</c:if>
		<c:set var="ma" value='${user.role == "MANAGER"}'/>
		<c:set var="ma1" value='${user.role == "FLOW"}'/>

		<c:if test="${!ma && !ma1}">
		<input
			type="button" class="button_class"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onclick="modfiy()" />&nbsp;&nbsp;<input
			type="button" class="button_class"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onclick="sub()" />&nbsp;&nbsp; <input
			type="button" class="button_class"
			value="&nbsp;&nbsp;ɾ ��&nbsp;&nbsp;" onclick="del()" />&nbsp;&nbsp;
		</c:if>
			<input
			type="button" class="button_class"
			value="&nbsp;������ѯ���&nbsp;" onclick="exports()" /></div>
		</td>
		<td width="0%"></td>
	</tr>
	</c:if>

	<tr height="10">
		<td height="10" colspan='2'></td>
	</tr>

	<p:message></p:message>
</table>

</form>
</body>
</html>
