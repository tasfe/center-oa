<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�ⵥ" />
<c:set var="ff" value='${out.type == 0 ? "����" : "���"}'/>
<script language="JavaScript" src="../js/prototype.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">
function sub()
{
	if (formCheck())
	{
		if (window.confirm('ȷ�����ͨ���˿ⵥ?'))
		{
			outForm.submit();
		}
	}
}

</script>
</head>
<body class="body_class">
<form name="outForm" method=post action="./out.do?"><input
	type=hidden name="method" value="modifyOutStatus" /> <input
	type=hidden name="outId" value="${out.fullId}" /> <input type="hidden"
	value="3" name="statuss">
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
				<td width="550" class="navigation">�˶Կⵥ</td>
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

<table width="80%" border="0" cellpadding="0" cellspacing="0"
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
								<td class="caption"><strong>${ff}����Ϣ:</strong></td>
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
		<table width="85%" border="0" cellpadding="0" cellspacing="0"
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
							<c:if test="${out.pay == 0}">
								<td>�Ƿ�ؿ</td>
								<td><input type="checkbox" name="pay" value="1"></td>
							</c:if>
							<c:if test="${out.pay == 1}">
								<td>�Ƿ�ؿ</td>
								<td>��</td>
							</c:if>
						</tr>
					</c:if>

					<tr class="content1">
						<td>��Ʒ����</td>
						<td colspan="1">${out.locationName}</td>
						<td>�������ڣ�</td>
						<td>${out.arriveDate}</td>
					</tr>

					<tr class="content2">
						<td>${ff}��������</td>
						<td colspan="3"><textarea rows="3" cols="55"
							readonly="readonly">${out.description}</textarea></td>
					</tr>

					<tr class="content1">
						<td>�����</td>
						<td colspan="3"><textarea rows="3" cols="55" name="reason" oncheck="notNone"></textarea></td>
					</tr>

					<tr class="content2">
						<td>ѡ������䣺</td>
						<td colspan="3"><select name="depotpartId" oncheck="notNone" style="width: 240px">
								<option value="">--</option>
							<c:forEach items="${depotpartList}" var="item">
								<option value="${item.id}">${item.name}</option>
							</c:forEach>
						</select><font color="blue">(��ѡ����Ʒ��)</font></td>
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
		<table width="85%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="tables">
					<tr align="center" class="content0">
						<td width="5%" align="center">ѡ��</td>
						<td width="20%" align="center">Ʒ��</td>
						<td width="5%" align="center">��λ</td>
						<td width="10%" align="center">����</td>
						<td width="15%" align="center">����</td>
						<td width="20%" align="left">���(�ܼ�:<span id="total">${my:formatNum(out.total)}</span>)</td>
						<td width="25%" align="center">�ɱ�</td>
					</tr>

					<c:forEach items="${baseList}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
							<td width="5%" align="center"><input type="checkbox"
								name="product" value="${item.id}"></td>
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

	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td width="92%">
		<div align="right"><input type="button" class="button_class"
			onclick="sub()" value="&nbsp;&nbsp;���ͨ��&nbsp;&nbsp;">&nbsp;&nbsp;
		<input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
		</td>
		<td width="8%"></td>
	</tr>

</table>
</form>
</body>
</html>

