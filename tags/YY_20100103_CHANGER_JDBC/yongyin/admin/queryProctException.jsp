<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�����쳣�б�" />
<script language="JavaScript" src="../js/common.js"></script>
<script src="../js/prototype.js"></script>
<script language="javascript">
function addproException()
{
	document.location.href = '../admin/addProductException.jsp';
}

function updateproException()
{
	if (getRadioValue('proExceptions') == '')
	{
		alert('��ѡ������쳣');
		return;
	}
	
	if (getRadio('proExceptions').statuss == 0 || getRadio('proExceptions').statuss == 2)
	{
		document.location.href = '../admin/proException.do?method=findProductException&update=1&id=' + getRadioValue('proExceptions');
	}
	else
	{
		alert('���ܲ���!');
	}
}

function delproException()
{
	if (getRadioValue('proExceptions') == '')
	{
		alert('��ѡ������쳣');
		return;
	}
	
	if (getRadio('proExceptions').statuss == 0 || getRadio('proExceptions').statuss == 2)
	{
		if (window.confirm('ȷ��ɾ�������쳣?'))
		document.location.href = 
		'../admin/proException.do?method=delProctException&id=' + getRadioValue('proExceptions');
	}
	else
	{
		alert('���ܲ���!');
	}
}

function pass()
{
	if (getRadioValue('proExceptions') == '')
	{
		alert('��ѡ������쳣');
		return;
	}
	
	if (getRadio('proExceptions').statuss == 1)
	{
		if (window.confirm('ȷ��ͨ�������쳣?'))
		{
			var sss = window.prompt('������ͨ��ԭ��', '');
			
			if (sss == '' || sss == null)
			{
				return;
			}
		 
			document.location.href = 
				'../admin/proException.do?method=updateProctExceptionStatus&opr=1&id=' 
				+ getRadioValue('proExceptions') + '&apply=' + sss;
		}
	}
	else
	{
		alert('���ܲ���!');
	}
}

function reject()
{
	if (getRadioValue('proExceptions') == '')
	{
		alert('��ѡ������쳣');
		return;
	}
	
	if (getRadio('proExceptions').statuss == 1)
	{
		if (window.confirm('ȷ�����ػ����쳣?'))
		{
			var sss = window.prompt('�����벵��ԭ��', '');
			
			if (sss == '' || sss == null)
			{
				return;
			}
		 
			document.location.href = 
				'../admin/proException.do?method=updateProctExceptionStatus&opr=0&id=' 
				+ getRadioValue('proExceptions') + '&apply=' + sss;
		}
	}
	else
	{
		alert('���ܲ���!');
	}
}

function load()
{
	loadForm();
	$f('productName');
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="bankList" action="../admin/proException.do"><input
	type="hidden" name="method" value="queryProductException"> <p:navigation
	height="22">
	<td width="550" class="navigation">�����쳣���� &gt;&gt; �����쳣�б�</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="90%">

	<p:subBody width="95%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr class="content1">
				<td width="15%" align="center">��ʼʱ��</td>
				<td align="left">
				<p:plugin name="beginDate" size="20" value="${beginDate}"/>
				</td>
				<td width="15%" align="center">����ʱ��</td>
				<td align="left">
				<p:plugin name="endDate" size="20" value="${endDate}"/>
				</td>
			</tr>

			<tr class="content2">
				<td width="15%" align="center">��Ʒ����</td>
				<td align="left"><input type="text" name="productName"
					value="${productName}"></td>
				<td width="15%" align="center">����״̬</td>
				<td align="left"><select name="status" class="select_class"
					values="${status}">
					<option value="">--</option>
					<option value="0">����</option>
					<option value="1">�ύ</option>
					<option value="2">����</option>
					<option value="3">ͨ��</option>
				</select></td>
			</tr>

			<tr class="content1">
				<td colspan="4" align="right"><input type="submit"
					class="button_class" value="&nbsp;&nbsp;�� ѯ&nbsp;&nbsp;">&nbsp;&nbsp;<input
					type="reset" class="button_class"
					value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></td>
			</tr>
		</table>

	</p:subBody>

	<p:title>
		<td class="caption"><strong>�����쳣�б�</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="95%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center" class="td_class">ѡ��</td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>��Ʒ</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>������</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>״̬</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>ʱ��</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="25%"><strong>ԭ��</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>����</strong></td>
			</tr>

			<c:forEach items="${requestScope.proctExceptionList}" var="item"
				varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type="radio" name="proExceptions"
						statuss="${item.status}" value="${item.id}" ${vs.index== 0 ? "checked" : ""}/></td>
					<td align="center" onclick="hrefAndSelect(this)"><a
						href="../admin/proException.do?method=findProductException&id=${item.id}">${item.productName}</a></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.amount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.applyer}</td>
					<%
					    String[] sss1 = new String[] {"����", "�ύ", "����", "ͨ��"};
					    request.setAttribute("sss1", sss1);
					%>
					<td align="center" onclick="hrefAndSelect(this)">${my:getValue(item.status,
					sss1)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.logDate}</td>
					<td align="center" onclick="hrefAndSelect(this)"
						title="${item.description}">${my:truncateString(item.description,
					0, 10)}</td>
					<td align="center" onclick="hrefAndSelect(this)"><a
						title="������ظ���"
						href="../admin/proException.do?method=downProductException&id=${item.id}">${item.fileName}</a></td>
				</tr>
			</c:forEach>
		</table>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="98%" rightWidth="2%">
		<div align="right"><c:if test='${user.role != "MANAGER"}'>
			<input type="button" class="button_class"
				value="&nbsp;&nbsp;���ӻ����쳣&nbsp;&nbsp;" onclick="addproException()">&nbsp;&nbsp;<input
				type="button" class="button_class"
				value="&nbsp;&nbsp;�޸Ļ����쳣&nbsp;&nbsp;"
				onclick="updateproException()">&nbsp;&nbsp;<input
				type="button" class="button_class"
				value="&nbsp;&nbsp;ɾ�������쳣&nbsp;&nbsp;" onclick="delproException()">&nbsp;&nbsp;
			</c:if> <c:if test='${user.role == "MANAGER"}'>
			<input type="button" class="button_class"
				value="&nbsp;&nbsp;ͨ ��&nbsp;&nbsp;" onclick="pass()">&nbsp;&nbsp;<input
				type="button" class="button_class"
				value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onclick="reject()">&nbsp;&nbsp;
			</c:if></div>
	</p:button>

	<p:message />

</p:body></form>
</body>
</html>

