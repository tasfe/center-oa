<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�����б�" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/fanye.js"></script>
<script language="javascript">
function addApplys()
{
	bankList.submit();
}

function modifyApplys()
{
	if (getRadioValue('banks') == '')
	{
		alert('��ѡ������');
		return;
	}
	
	document.location.href = '../admin/common.do?method=preForaddBank&id=' + getRadioValue('banks');
}

function modifyMoney()
{
	if (getRadioValue('banks') == '')
	{
		alert('��ѡ������');
		return;
	}
	
	document.location.href = '../admin/common.do?method=findMoney&id=' + getRadioValue('banks');
}

function load()
{
	initPage(15, $('result'), "tr", 1, 0, true);
	
	$f('con');
}

function press()
{
	if (window.common.getEvent().keyCode == 13)
	{
		queryInner($$('con'), 1);
	}
}

function resets()
{
	initPage(15, $('result'), "tr", 1, 1, false);
	
	$('con').value = '';
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="bankList" action="../admin/common.do"><input
	type="hidden" name="method" value="preForaddBank"> <p:navigation
	height="22">
	<td width="550" class="navigation">���й���</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="90%">

	<p:subBody width="85%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center" >��������</td>
				<td align="center" 
					width="35%"><input type="text" name="con" onkeypress="press()"><input type="text" name="con" style="visibility: hidden;" size=1></td>
				<td align="center" colspan="2"><input type="button" class="button_class"
				value="&nbsp;&nbsp;�� ѯ&nbsp;&nbsp;" onclick="queryInner($$('con'), 1)">&nbsp;&nbsp;
				<input type="button" class="button_class"
				value="&nbsp;&nbsp;ȫ ��&nbsp;&nbsp;" onclick="resets()">
				</td>
			</tr>
		</table>

	</p:subBody>
	

	<p:title>
		<td class="caption"><strong>�����б�</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="85%">
		<table width="100%" align="center" cellspacing='1' class="table0" id="result">
			<tr align=center class="content0">
				<td align="center" class="td_class">ѡ��</td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="35%"><strong>��������</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>�������(Ԫ)[${lastId}]</strong></td>
			</tr>

			<c:forEach items="${requestScope.listBank}" var="item" varStatus="vs">
				<tr class="${s.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type="radio" name="banks"
						value="${item.id}" ${vs.index== 0 ? "checked" : ""}/></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.name}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.locationName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.lmoney}</td>
				</tr>
			</c:forEach>
		</table>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="87%" rightWidth="13%">
		<div align="right"><input type="button" class="button_class" name="adds"
			style="cursor: pointer" value="&nbsp;&nbsp;��������&nbsp;&nbsp;"
			onclick="addApplys()">&nbsp;&nbsp;<input type="button"
			class="button_class" value="&nbsp;&nbsp;�޸�����&nbsp;&nbsp;"
			onclick="modifyApplys()">&nbsp;&nbsp;<input type="button"
			class="button_class" value="&nbsp;&nbsp;�޸����&nbsp;&nbsp;"
			onclick="modifyMoney()"></div>
	</p:button>

	<p:message />
	
</p:body></form>
</body>
</html>

