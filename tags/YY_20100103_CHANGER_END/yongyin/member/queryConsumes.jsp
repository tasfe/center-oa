<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="��Ա�����б�" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/fanye.js"></script>
<script language="javascript">
function querys()
{
	formEntry.submit();
}

function addBean()
{
	document.location.href = '../member/addConsume.jsp';
}

function modifyApplys()
{
	if (getRadioValue('beans') == '')
	{
		alert('��ѡ���Ա����');
		return;
	}
	
	document.location.href = '../member/member.do?method=findMember&id=' + getRadioValue('beans') + '&type=1';
}

function press()
{
	if (window.common.getEvent().keyCode == 13)
	{
		querys();
	}
}

function detail(id)
{
	document.location.href = '../member/member.do?method=findConsume&id=' + id;
}

function resets()
{
	formEntry.reset();
	
	$('cardNo').value = '';
	$('name').value = '';
}

function load()
{
	loadForm();
	$f('cardNo');
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../member/member.do"><input
	type="hidden" name="method" value="queryConsumes"><input type="hidden" value="1"
	name="firstLoad"> <p:navigation
	height="22">
	<td width="550" class="navigation">��Ա���ѹ��� &gt;&gt; ��Ա�����б�</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content1">
				<td align="center" >��ʼʱ��</td>
				<td align="center" width="35%"><p:plugin name="alogTime" value="${alogTime}" /></td>
				<td align="center" >����ʱ��</td>
				<td align="center" width="35%"><p:plugin name="blogTime" value="${blogTime}" /></td>
			</tr>
			
			<tr align=center class="content1">
				<td align="center" >��Ա����</td>
				<td align="center" width="35%"><input type="text" name="cardNo" value="${cardNo}" onkeypress="press()"></td>
				<td align="center" >��Ʒ����</td>
				<td align="center" width="35%"><input type="text" name="productName" value="${productName}" onkeypress="press()"></td>
			</tr>
			
			<tr align=center class="content0">
				<td align="right" colspan="4"><input type="button" class="button_class"
				value="&nbsp;&nbsp;�� ѯ&nbsp;&nbsp;" onclick="querys()">&nbsp;&nbsp;
				<input type="button" class="button_class"
				value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onclick="resets()">
				</td>
			</tr>
		</table>

	</p:subBody>
	

	<p:title>
		<td class="caption"><strong>��Ա�����б�</strong></td>
		<td class="caption" align="right"><strong>���Ѷ�:${total}</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0" id="result">
			<tr align=center class="content0">
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>�ͻ�</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>��Ʒ</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>���ѽ��</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>�ۿ�</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>������</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>ʱ��</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>����</strong></td>
			</tr>

			<c:forEach items="${listConsume}" var="item" varStatus="vs">
				<tr class="${s.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center" onclick="hrefAndSelect(this)"><a title="�鿴��Ա��ϸ" href="../member/member.do?method=findMember&id=${item.memberId}">${item.cardNo}</a></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.memberName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.productName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.cost}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.rebate}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.point}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.locationName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.userName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.logTime}</td>
					<td align="center" onclick="hrefAndSelect(this)">
					<a title="������ϸ" href="javascript:detail('${item.id}')">
					<img src="../images/detail.png" border="0" height="15"></a>
					</td>
				</tr>
			</c:forEach>
		</table>
		
		<p:formTurning form="formEntry" method="queryConsumes"></p:formTurning>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" name="adds"
			style="cursor: pointer" value="&nbsp;&nbsp;���ӻ�Ա����&nbsp;&nbsp;"
			onclick="addBean()">&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
	</p:button>

	<p:message />
	
</p:body></form>
</body>
</html>

