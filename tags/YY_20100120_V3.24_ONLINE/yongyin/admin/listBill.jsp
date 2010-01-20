<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>

<head>
<p:link title="�ո����б�" />
<script src="../js/prototype.js"></script>
<script src="../js/common.js"></script>
<script src="../js/fanye.js"></script>
<script language="javascript">
function addBill()
{
	document.location.href = '../admin/bill.do?method=queryForAdd';
}

function chec()
{
	if (getRadioValue('bills') == '')
	{
		alert('��ѡ���ո��');
		return;
	}
	
	document.location.href = '../admin/bill.do?method=findBill&id=' + getRadioValue('bills') + '&mark=1';
}

function exprots()
{
	if (window.confirm('ȷ��������ǰ��ѯ���?'))
	{
		document.location.href = '../admin/bill.do?method=export';
	}	
}


function comp()
{
	var now = '${now}';
	
	var str1 = $('date').value;
	
	var str2 = $('date1').value;
	
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
		
		$('date1').value = now;
	}
	
	if (str1 == '' && str2 != '')
	{
		if (!coo(now, str2))
		{
			alert('��ѯ���ڿ�Ȳ��ܴ���3����(90��)!');
			return false;
		}
		
		$('date').value = now;
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

function load()
{
	loadForm();
	
	//initPage(15, $("result"), "tr", 1, 0, true);
}
</script>
</head>
<body class="body_class" onload="load()">
<form action="./bill.do" name="adminForm" onsubmit="return comp()"><input
	type="hidden" value="queryBill" name="method"> <input
	type="hidden" value="2" name="firstLoad">
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
				<td width="550" class="navigation">�ո������ &gt;&gt; ��ѯ�ո��</td>
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
						<td width="15%" align="center">��ʼʱ��:</td>
						<td align="center">
						<p:plugin name="date" size="20" value="${date}"/>
						</td>
						<td width="15%" align="center">����ʱ��:</td>
						<td align="center">
						<p:plugin name="date1" size="20" value="${date1}"/>
						</td>
					</tr>

					<tr class="content2">
						<td width="15%" align="center">��������:</td>
						<td align="center"><select name="type" class="select_class" values="${type}">
							<option value="" >--</option>
							<option value="0"}>�տ</option>
							<option value="1"}>���</option>
						</select></td>
						<td width="15%" align="center">�տ��ʻ�:</td>
						<td align="center"><select name="bank" class="select_class2" values="${bank}">
							<option value="">--</option>
							<c:forEach items="${bankList}" var="item">
								<option value="${item.id}">${item.name}</option>
							</c:forEach>
						</select></td>
					</tr>

					<tr class="content1">
						<td width="15%" align="center">Ʊ�ݱ��:</td>
						<td align="center"><input type="text" name="receipt" 
							value="${receipt}" class="input_class" maxlength="20" size="20">
						</td>
						<td width="15%" align="center">�ⵥ���:</td>
						<td align="center"><input type="text" name="outId"
							value="${outId}"></td>
					</tr>
					
					<tr class="content2">
						<td width="15%" align="center">������:</td>
						<td align="center"><input name="stafferName" value="${stafferName}" type="text">
						</td>
						<td width="15%" align="center">�˶�:</td>
						<td align="center"><select name="mark" class="select_class" values="${mark}">
							<option value="" >--</option>
							<option value="0" >δ�˶�</option>
							<option value="1" >�Ѻ˶�</option>
						</select></td>
					</tr>
					
					<tr class="content1">
						<td width="15%" align="center">��;��ʽ</td>
						<td colspan="1" align="center">
						<select name="inway" values="${inway}"
							class="select_class">
							<option value="">--</option>
							<option value="1">��;</option>
							<option value="2">��;����</option>
							</select>
						</td>
						<td width="15%" align="center"></td>
						<td align="center"></td>
					</tr>

					<tr class="content2">
						<td colspan="4" align="right"><input type="submit"
							class="button_class" value="&nbsp;&nbsp;�� ѯ&nbsp;&nbsp;"></td>
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
								<td class="caption"><strong>�ո��:</strong><font color=blue>[��ǰ��ѯ����:${my:length(billList)}]</font></td>
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
						<td align="center" onclick="tableSort(this)" class="td_class">ѡ��</td>
						<td align="center" onclick="tableSort(this)" class="td_class">���</td>
						<td align="center" onclick="tableSort(this)" class="td_class">�ͻ�</td>
						<td align="center" onclick="tableSort(this)" class="td_class">�ʻ�</td>
						<td align="center" onclick="tableSort(this)" class="td_class">�ⵥ���</td>
						<td align="center" onclick="tableSort(this)" class="td_class">��������</td>
						<td align="center" onclick="tableSort(this, true)" class="td_class">���</td>
						<td align="center" onclick="tableSort(this)" class="td_class">������</td>
						<td align="center" onclick="tableSort(this)" class="td_class">��;</td>
						<td align="center" onclick="tableSort(this)" class="td_class">�˶�</td>
					</tr>

					<c:forEach items="${billList}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
							<td align="center"><input type="radio" name="bills"
								value="${item.id}" ${vs.index== 0 ? "checked" : ""}/></td>
							<td align="center"><a onclick="hrefAndSelect(this)"
								href="../admin/bill.do?method=findBill&id=${item.id}">${item.id}</a></td>
							<td align="center" onclick="hrefAndSelect(this)">${item.customerName}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.bank}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.outId}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.type == 0 ? "�տ" : "���"}</td>
							<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.money)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.stafferName}</td>
							<%
								String[] sss1 = new String[]{"" , "<font color=red>��;</font>", "��;����"};
								request.setAttribute("sss1", sss1);
							%>
							<td align="center" onclick="hrefAndSelect(this)">${my:getValue(item.inway, sss1)}</td>
							<td align="center" onclick="hrefAndSelect(this)">${item.mark == "" ? "δ�˶�" : "<font
								color=blue><B>�˶�</B></font>"}</td>
						</tr>
					</c:forEach>
				</table>
				<p:formTurning form="adminForm" method="queryBill"></p:formTurning>
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
		<td width="100%">
		<div align="right"><input type="button" class="button_class"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onClick="chec()">&nbsp;&nbsp;<input
			type="button" class="button_class"
			value="&nbsp;&nbsp;�����ո��&nbsp;&nbsp;" onClick="addBill()">&nbsp;&nbsp;<input
			type="button" class="button_class"
			value="&nbsp;&nbsp;������ѯ���&nbsp;&nbsp;" onClick="exprots()"></div>
		</td>
		<td width="0%"></td>
	</tr>

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
