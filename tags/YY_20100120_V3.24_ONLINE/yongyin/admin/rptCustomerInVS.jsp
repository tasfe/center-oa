<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@include file="./common.jsp"%>
<html>

<head>
<p:link title="ѡ��ͻ�" />
<base target="_self">
<script src="../js/prototype.js"></script>
<script src="../js/public.js"></script>
<script src="../js/common.js"></script>
<script language="javascript">

function add()
{
	var opener = window.common.opener();
	var oo = getRadio("customer");

	if (oo == null)
	{
		alert('��ѡ��Ӧ��');
		return;
	}

    opener.getCustmeor(oo.value, oo.customername);
    opener = null;
    window.close();
}

function query()
{
	adminForm.submit();
}
</script>
</head>
<body class="body_class">
<form action="../admin/common.do" name="adminForm"><input type="hidden"
	value="rptQueryCustmerInVS" name="method">
	<input type="hidden" value="${productTypeId}" name="productTypeId">
	<input type="hidden" value="1" name="firstLoad">
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
				<td width="550" class="navigation">��Ӧ���б�</td>
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
		<td align='center' colspan='2'>
		<table width="85%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr class="content1">
						<td width="15%" align="center">����</td>
						<td align="center"><input type="text" name="name" style="ime-mode:active"
							value="${name}"></td>
						<td width="15%" align="center">����</td>
						<td align="center"><input type="text" name="code"
							value="${code}"></td>
					</tr>

					<tr class="content2">
						<td colspan="4" align="right"><input type="submit"
							onclick="query()" class="button_class"
							value="&nbsp;&nbsp;�� ѯ&nbsp;&nbsp;"></td>
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
								<td class="caption"><strong>�����Ӧ��:</strong><b>��˫��������ֱ��ȷ����</b></td>
								<td align="right"><input name="sure1"
									type="button" class="button_class" value="&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;"
									onClick="add()"></td>
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
		<table width="85%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr align="center" class="content0">
						<td align="center" width="8%" align="center">ѡ��</td>
						<td align="center" onclick="tableSort(this)" class="td_class">����</td>
						<td align="center" onclick="tableSort(this)" class="td_class">����</td>
						<td align="center" onclick="tableSort(this)" class="td_class">��ϵ��</td>
					</tr>

					<c:forEach items="${list}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
							<td align="center"><input type="radio" name="customer" id="customer_${vs.index}"
								customername="${item.name}" value="${item.id}" ${vs.index== 0 ? "checked" : ""}/></td>
							<td align="center" onclick="hrefAndSelect(this)" ondblclick="add()">${item.name}</td>
							<td align="center" onclick="hrefAndSelect(this)" ondblclick="add()">${item.code}</td>
							<td align="center" onclick="hrefAndSelect(this)" ondblclick="add()">${item.connector}</td>
						</tr>
					</c:forEach>
				</table>

				<p:formTurning form="adminForm" method="rptQueryCustmerInVS"></p:formTurning>
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

	<c:if test="${my:length(list) > 0}">
	<tr>
		<td width="94%">
		<div align="right"><input type="button" class="button_class" name="sure2"
			value="&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;" onClick="add()">&nbsp;&nbsp;</div>
		</td>
		<td width="6%"></td>
	</tr>
	</c:if>
</table>

</form>
</body>
</html>
