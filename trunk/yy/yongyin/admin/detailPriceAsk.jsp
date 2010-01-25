<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="����ѯ��" />
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/common.js"></script>

<script language="javascript">

</script>

</head>
<body class="body_class">
<form><p:navigation height="22">
	<td width="550" class="navigation">����ѯ����ϸ</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>����ѯ����Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="2">
			<p:cell title="��Ʒ����">
			${bean.productName}
			</p:cell>

			<p:cell title="��Ʒ����">
			${bean.productCode}
			</p:cell>

			<p:cell title="��Ҫ����">
			${bean.amount}
			</p:cell>

			<p:cell title="�Ƿ�����">
			${bean.overTime == 0 ? "<font color=blue>δ����</font>" : "<font color=red>����</font>"}
			</p:cell>

			<p:cell title="ѯ����">
			${bean.userName}
			</p:cell>

			<p:cell title="ѯ������">
			${bean.locationName}
			</p:cell>

			<p:cell title="�����̶�">
			${my:get('priceAskInstancy', bean.instancy)}
			</p:cell>

			<p:cell title="ѯ�۴���ʱ��">
			${bean.endTime}
			</p:cell>
			
			<p:cells title="״̬" celspan="1">
			${my:get('priceAskStatus', bean.status)}
			</p:cells>
			
			<p:cell title="���ռ۸�">
			<c:if test="${user.role != 'NETASK'}">
			${bean.price}
			</c:if>
			</p:cell>
			
			<p:cells title="����ԭ��" celspan="2">
			${bean.reason}
			</p:cells>
		</p:table>
	</p:subBody>

	<p:tr/>

	<p:subBody width="98%">
		<table width="100%" border="0" cellspacing='1' id="tables">
			<tr align="center" class="content0">
				<c:if test="${user.role != 'PRICE'}">
				<td width="10%" align="center">��Ӧ��</td>
				</c:if>
				<c:if test="${user.role == 'PRICE'}">
				<td width="10%" align="center">����Ա</td>
				</c:if>
				<td width="10%" align="center" onclick="tableSort(this, true)" class="td_class">�۸�</td>
				<td width="10%" align="center">����</td>
				<td width="10%" align="center">��������</td>
				<td width="5%" align="center">����</td>
				<td width="30%" align="center">��ע</td>
				<td width="10%" align="center" onclick="tableSort(this)" class="td_class">ʱ��</td>
			</tr>

			<c:forEach items="${bean.itemVO}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<c:if test="${user.role != 'PRICE'}">
					<td align="center">${item.providerName}</td>
					</c:if>
					<c:if test="${user.role == 'PRICE'}">
					<td align="center">${item.userName}</td>
					</c:if>

					<td  align="center">${item.price}</td>
					<td  align="center">${item.supportAmount}</td>

					<td  align="center">${item.hasAmount == 0 ? "����" : "<font color=red>������</font>"}</td>
					
					<td  align="center">${my:get('priceAskType', item.type)}</td>
					<td  align="center">${item.description}</td>
					<td  align="center">${item.logTime}</td>
				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
	</p:button>

	<tr>
		<td colspan='2' align="center"><FONT color="blue">${MESSAGE_INFO}</FONT><FONT
			color="red">${errorInfo}</FONT></td>
	</tr>
</p:body></form>
</body>
</html>

