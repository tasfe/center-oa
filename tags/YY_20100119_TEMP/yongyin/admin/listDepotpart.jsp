<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�����б�" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="javascript">
function addDepotpart()
{
	document.location.href = '../admin/addDepotpart.jsp?add=1';
}

function modifyDepotpart()
{
	if (getRadioValue('depotparts') == '')
	{
		alert('��ѡ�����');
		return;
	}
	
	document.location.href = '../admin/das.do?method=findDepotpart&modfiy=1&id=' + getRadioValue('depotparts');
}

function delDepotpart()
{
	if (getRadioValue('depotparts') == '')
	{
		alert('��ѡ�����');
		return;
	}
	
	if (window.confirm('ȷ��ɾ������-' + getRadio('depotparts').depotpartName))
	{
		document.location.href = '../admin/das.do?method=delDepotpart&id=' + getRadioValue('depotparts');
	}
}

function managers()
{
	if (getRadioValue('depotparts') == '')
	{
		alert('��ѡ�����');
		return;
	}
	
	document.location.href = '../admin/das.do?method=queryStorage&depotpartId=' + getRadioValue('depotparts');
}

function pmanagers()
{
	if (getRadioValue('depotparts') == '')
	{
		alert('��ѡ�����');
		return;
	}
	
	document.location.href = '../admin/das.do?method=queryProduct&firstLoad=1&depotpartId=' + getRadioValue('depotparts');
}

function moveProuct()
{
	document.location.href = '../admin/das.do?method=queryDepotpartForMove';
}

</script>

</head>
<body class="body_class">
<form name="bankList" action="../admin/das.do"><input
	type="hidden" name="method" value="addDepotpart"> <p:navigation
	height="22">
	<td width="550" class="navigation">��������  &gt;&gt; �����б�</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="90%">

	<p:title>
		<td class="caption"><strong>�����б�</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="75%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center" class="td_class">ѡ��</td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="35%"><strong>��������</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>��������</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>��������</strong></td>
			</tr>

			<c:forEach items="${requestScope.depotpartList}" var="item"
				varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type="radio" name="depotparts"
						depotpartName="${item.name}" value="${item.id}" ${vs.index== 0 ? "checked" : ""}/></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.name}</td>
					<%
								String[] sss = new String[]{"��Ʒ��" , "��Ʒ��", "���ϲ�"};
								request.setAttribute("sss", sss);
							%>
					<td align="center" onclick="hrefAndSelect(this)">${my:getValue(item.type, sss)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.description}</td>
				</tr>
			</c:forEach>
		</table>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="89%" rightWidth="11%">
		<div align="right">
			<input type="button" class="button_class"
			value="&nbsp;&nbsp;��Ʒ����&nbsp;&nbsp;" onclick="pmanagers()">&nbsp;&nbsp;
			<input type="button" class="button_class"
			value="&nbsp;&nbsp;��λ����&nbsp;&nbsp;" onclick="managers()">&nbsp;&nbsp;<input
			type="button" class="button_class" style="cursor: pointer"
			value="&nbsp;&nbsp;���Ӳ���&nbsp;&nbsp;" onclick="addDepotpart()">&nbsp;&nbsp;<input
			type="button" class="button_class"
			value="&nbsp;&nbsp;�޸Ĳ���&nbsp;&nbsp;" onclick="modifyDepotpart()">&nbsp;&nbsp;<input
			type="button" class="button_class"
			value="&nbsp;&nbsp;ɾ������&nbsp;&nbsp;" onclick="delDepotpart()">&nbsp;&nbsp;<input
			type="button" class="button_class"
			value="&nbsp;&nbsp;��Ʒ�ƶ�&nbsp;&nbsp;" onclick="moveProuct()">&nbsp;&nbsp;</div>
	</p:button>

	<p:message />

</p:body></form>
</body>
</html>

