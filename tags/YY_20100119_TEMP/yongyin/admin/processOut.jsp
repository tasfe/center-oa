<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�ⵥ" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">

function invokeOther()
{
	if ($$('location') == '')
	{
		alert('��ѡ��ת��������(����������)');
		return false;
	}
	
	if ($$('location') == '0')
	{
		alert('��ѡ��ת��������(����������)');
		return false;
	}
	
	if ($$('location') == $$('productLocationId'))
	{
		alert('ת���������ǲ�Ʒ��������');
		return false;
	}
	
	if (window.confirm('ȷ���Ѵ˵����ⵥת����--' + getOptionText('location')))
	{
		document.location.href = '../admin/out.do?method=processInvoke&outId=${out.fullId}&flag=2&changeLocationId=' + $$('location');
	}
}

function recives()
{
	if ($('depotpartId'))
	{
		if ($$('depotpartId') == '')
		{
			alert('��ѡ���Զ��ӿڵ���Ĳ���');
			return false;
		}
		
		if (window.confirm('ȷ��ȫ�����ܴ˵����Ŀⵥ?'))
		document.location.href = '../admin/out.do?method=processInvoke&outId=${out.fullId}&flag=1&depotpartId=' + $$('depotpartId');
	}
	else
	{
		if (window.confirm('ȷ��ȫ�����ܴ˵����Ŀⵥ?'))
		document.location.href = '../admin/out.do?method=processInvoke&outId=${out.fullId}&flag=1';
	}
}

function rejects()
{
	if (window.confirm('ȷ�����ش˵����Ŀⵥ?'))
	{
		document.location.href = '../admin/out.do?method=processInvoke&outId=${out.fullId}&flag=3';
	}
}

function load()
{
}
</script>
</head>
<body class="body_class" onload="load()">
<input type="hidden" name="productLocationId" value="${out.location}">
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="na">
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
				<td width="550" class="navigation">�ⵥ���� &gt;&gt; ���������ⵥ</td>
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

<table width="90%" border="0" cellpadding="0" cellspacing="0"
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
								<td class="caption"><strong>��ⵥ��Ϣ:</strong></td>
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
		<table width="90%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr class="content2">
						<td width="15%">${ff}���ڣ�</td>
						<td width="35%">${out.outTime}</td>
						<td width="15%">${ff}���ͣ�</td>
						<c:if test="${out.type == 0}">
							<td width="35%">${out.outType == 0 ? "���۳���" : "��������"}</td>
						</c:if>

						<c:if test="${out.type == 1}">
							<td width="35%">${my:get('outType', out.outType)}</td>
						</c:if>

					</tr>
					<tr class="content1">

						<c:if test="${out.outType == 1 && out.type == 1}">
							<td>�������ţ�</td>
						</c:if>

						<c:if test="${out.outType != 1 || out.type != 1}">
							<td>��Ӧ��(�ͻ�)��</td>
						</c:if>
						<td>${out.customerName}</td>
						<td>��${ff}���ţ�</td>
						<td>${out.department}</td>
					</tr>
					<tr class="content2">
						<td>��ϵ�ˣ�</td>
						<td>${out.connector}</td>
						<td>��ϵ�绰��</td>
						<td>${out.phone}</td>
					</tr>
					<tr class="content1">
						<td>�����ˣ�</td>
						<td>${out.stafferName}</td>
						<td>���ݺ��룺</td>
						<td>${out.fullId}</td>
					</tr>
					
					<tr class="content1">
						<td>��ؿⵥ��</td>
						<td><a href="../admin/out.do?method=findOut&outId=${out.refOutFullId}">${out.refOutFullId}</a></td>
						<td>���䵥�ţ�</td>
						<td>${out.tranNo}</td>
					</tr>

					<tr class="content2">
						<td>״̬��</td>
						<td colspan="1">${my:status(out.status)}</td>
						<td>�ؿ�������</td>
						<td>${out.reday}</td>
					</tr>

					<c:if test="${out.type == 0}">
						<tr class="content1">
							<td>�ؿ����ڣ�</td>
							<td colspan="1">${out.redate}</td>
							<td>�Ƿ�ؿ</td>
							<td>${out.pay == 0 ? "��" : "��"}</td>
						</tr>
					</c:if>

					<tr class="content2">
						<td>��Ʒ����</td>
						<td colspan="1">${out.locationName}</td>
						<td>�������ڣ�</td>
						<td>${out.arriveDate}</td>
					</tr>
					
					<tr class="content1">
						<td>����Ŀ������</td>
						<td colspan="1">${out.destinationName}</td>
						<td>��;��ʽ��</td>
						<%
								String[] sss1 = new String[]{"" , "��;", "��;����"};
								request.setAttribute("sss1", sss1);
						%>
						<td>${my:getValue(out.inway, sss1)}</td>
					</tr>

					<tr class="content2">
						<td>${ff}��������</td>
						<td colspan="3">${out.description}</td>
					</tr>

					<tr class="content1">
						<td>${ff}���˶ԣ�</td>
						<td colspan="3">${out.checks}</td>
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
		<table width="90%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="tables">
					<tr align="center" class="content0">
						<td width="20%" align="center">Ʒ��</td>
						<td width="5%" align="center">��λ</td>
						<td width="10%" align="center">����</td>
						<td width="15%" align="center">����</td>
						<td width="20%" align="left">���(�ܼ�:<span id="total">${my:formatNum(out.total)}</span>)</td>
						<td width="25%" align="center">�ɱ�</td>
					</tr>

					<c:forEach items="${baseList}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
							<td width="20%" align="center">${item.productName}</td>

							<td width="5%" align="center">${item.unit}</td>

							<td width="10%" align="center">${item.amount}</td>

							<td width="15%" align="center">${my:formatNum(item.price)}</td>

							<td width="15%" align="center">${my:formatNum(item.value)}</td>

							<td width="25%" align="center">${item.description}</td>

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

	
	<c:if test="${isSystem}">
	<tr id="locations">
		<td height="10" colspan='2' align="center">����
			<select name="depotpartId" class="select_class">
				<option value="">--</option>
			<c:forEach items='${depotpartList}' var="item">
				<option value="${item.id}">${item.name}</option>
			</c:forEach>
		</select>
		&nbsp;&nbsp;ת������
			<select name="location" class="select_class" values="${currentLocationId}">
				<option value="">--</option>
			<c:forEach items='${locationList}' var="item">
				<option value="${item.id}">${item.locationName}</option>
			</c:forEach>
		</select>
		</td>
	</tr>
	
	<tr>
		<td height="10" colspan='2'></td>
	</tr>
	</c:if>
	
	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td width="92%">
		<div align="right"><input type="button" 
			class="button_class" onclick="recives()"
			value="&nbsp;&nbsp;ȫ������&nbsp;&nbsp;">&nbsp;&nbsp;
			<c:if test="${isSystem}">
			<input type="button" 
			class="button_class" onclick="invokeOther()"
			value="&nbsp;ת����������&nbsp;">&nbsp;&nbsp;
			</c:if>
			<input type="button" 
			class="button_class" onclick="rejects()"
			value="&nbsp;&nbsp;ȫ������&nbsp;&nbsp;">&nbsp;&nbsp;<input
			type="button" name="ba" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
		</td>
		<td width="8%"></td>
	</tr>

</table>
</body>
</html>

