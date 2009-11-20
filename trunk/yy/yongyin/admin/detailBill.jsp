<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>

<p:link title="�����ո���" />
<script language="JavaScript" src="../js/CommonScriptMethod.js"></script>
<script language="JavaScript" src="../js/prototype.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">
function marks()
{
	if (formCheck())
	{
		if (window.confirm('ȷ���˶�?'))
		{
			addBill.submit();
		}
	}
}

function load()
{
	var ff = '${bill.billType}';
	var type = '${bill.type}';
	
	var text = ({0:({0:'�����տ�', 1:'Ԥ�տ�', 2:'������ת��', 3: '����������'}), 1:({0:'��ͨ����', 1:'����', 2:'ת��', 4: '��������'})})[type][ff];
	
	if (text)
	{
		$('spec').innerHTML = text;
	}
	else
	{
		$('spec').innerHTML = type;
	}
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="addBill" action="../admin/bill.do"><input
	type="hidden" name="method" value="mark"> <input type="hidden"
	name="id" value="${bill.id}">
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
				<td width="550" class="navigation">���ϸ</td>
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
<table width="85%" border="0" cellpadding="0" cellspacing="0"
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
								<td width="15">&nbsp;</td>
								<td width="6"><img src="../images/dot_r.gif" width="6"
									height="6"></td>
								<td class="caption"><strong>������Ϣ:</strong></td>
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
		<td colspan='2' align='center'>
		<table width="80%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr class="content2">
						<td width="15%">����:</td>
						<td width="35%">${bill.id}</td>
						<td width="15%">�տ��ʻ�:</td>
						<td width="35%">${bill.bank}</td>
					</tr>

					<tr class="content1">
						<td width="15%">��������:</td>
						<td width="35%">${bill.type == 0 ? "�տ" : "���"}</td>

						<td width="15%">����:</td>
						<td width="35%" id='spec'></td>
					</tr>

					<tr class="content2">
						<td width="15%">���ⵥ��:</td>
						<td width="35%">${bill.outId}</td>

						<td width="15%">Ŀ������</td>
						<td width="35%">${bill.destBank}</td>
					</tr>

					<tr class="content1">
						<td width="15%" id="inout">�տ���:</td>
						<td width="35%">${my:formatNum(bill.moneys)}</td>

						<td width="15%" id="cust">�ͻ�:</td>
						<td width="35%">${bill.customerName}</td>
					</tr>

					<tr class="content2">
						<td width="15%" id="inout">������:</td>
						<td width="35%">${bill.stafferName}</td>
						<td width="15%" id="mm">�տ�����:</td>
						<td width="35%">${bill.temp}</td>
					</tr>

					<tr class="content1">

						<td width="15%" id="cust">��ע:</td>
						<td width="35%" colspan="3">${bill.description}</td>
					</tr>

					<tr class="content2">

						<td width="15%" id="cust">��;��ʽ:</td>
						<%
						    String[] sss1 = new String[] {"", "<font color=red>��;</font>", "��;����"};
						    request.setAttribute("sss1", sss1);
						%>
						<td width="35%">${my:getValue(bill.inway, sss1)}</td>
						<td width="15%" id="cust">��ص���:</td>
						<td width="35%"><a
							href="../admin/bill.do?method=findBill&id=${bill.refBillId}">${bill.refBillId}</a></td>
					</tr>


					<c:if test="${fff}">
						<tr class="content2">
							<td width="15%" id="cust">�˶�:</td>
							<td width="35%" colspan="3"><textarea rows="3" cols="55"
								oncheck="notNone;maxLength(254)" name="mark">${bill.mark}</textarea></td>
						</tr>
					</c:if>

					<c:if test="${!fff}">
						<tr class="content2">
							<td width="15%" id="cust">�˶�:</td>
							<td width="35%" colspan="3">${bill.mark}</td>
						</tr>
					</c:if>
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
		<td width="90%">
		<div align="right"><c:if test="${fff}">
			<input type="button" class="button_class" onclick="marks()"
				value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;">
		</c:if> <input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
		</td>
		<td width="10%"></td>
	</tr>

</table>


</form>
</body>
</html>

