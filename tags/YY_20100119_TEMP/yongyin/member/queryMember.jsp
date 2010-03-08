<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="��Ա�б�" />
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
	document.location.href = '../member/addMember.jsp';
}

function modifyApplys(id)
{
	document.location.href = '../member/member.do?method=findMember&id=' + id + '&type=1';
}

function queryExchange(memberId)
{
	document.location.href = '../member/member.do?method=queryExchanges&firstLoad=1&memberId=' + memberId;
}

function press()
{
	if (window.common.getEvent().keyCode == 13)
	{
		querys();
	}
}

function dels(id, card)
{
	if (window.confirm('ȷ��ɾ����Ա--' + card))
	document.location.href = '../member/member.do?method=delMember&id=' + id;
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
	type="hidden" name="method" value="queryMembers"><input type="hidden" value="1"
	name="firstLoad"> <p:navigation
	height="22">
	<td width="550" class="navigation">��Ա���� &gt;&gt; ��Ա�б�</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center" >��Ա����</td>
				<td align="center" width="35%"><input type="text" name="cardNo" value="${cardNo}" onkeypress="press()"></td>
				<td align="center" >��Ա����</td>
				<td align="center" width="35%"><input type="text" name="name" value="${name}" onkeypress="press()"></td>
			</tr>

			<tr align=center class="content1">
				<td align="center" >��Ա�ȼ�</td>
				<td align="center" width="35%">
				<select name="grade" class="select_class" values="${grade}">
					<option value="">--</option>
					<option value="0">��ͨ��</option>
					<option value="1">����</option>
					<option value="2">��</option>
					<option value="3">����</option>
				</select>
				</td>
				<td align="center" >��Ա����</td>
				<td align="center" width="35%">
				<select name="type" class="select_class" values="${type}">
					<option value="">--</option>
					<option value="0">��ͨ��Ա</option>
					<option value="1">���û�Ա</option>
				</select>
				</td>
			</tr>

			<tr align=center class="content1">
				<td align="right" colspan="4"><input type="button" class="button_class"
				value="&nbsp;&nbsp;�� ѯ&nbsp;&nbsp;" onclick="querys()">&nbsp;&nbsp;
				<input type="button" class="button_class"
				value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onclick="resets()">
				</td>
			</tr>
		</table>

	</p:subBody>


	<p:title>
		<td class="caption"><strong>��Ա�б���</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0" id="result">
			<tr align=center class="content0">
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>�ȼ�</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>���û���</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>�ֻ�</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>����</strong></td>
			</tr>

			<c:forEach items="${listMember}" var="item" varStatus="vs">
				<tr class="${s.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center" onclick="hrefAndSelect(this)"><a href="../member/member.do?method=findMember&id=${item.id}">${item.cardNo}</a></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.name}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('memberGrade' ,item.grade)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('memberType' ,item.type)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.point}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.usepoint}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.handphone}</td>
					<td align="center" onclick="hrefAndSelect(this)"><a title="��Ա���Ѽ�¼"
					href="../member/member.do?method=queryConsumes&firstLoad=1&cardNo=${item.cardNo}">
					<img src="../images/list.gif" border="0" height="15" width="15"></a>
					<a title="�޸Ļ�Ա��Ϣ������" href="javascript:modifyApplys('${item.id}')">
					<img src="../images/edit.gif" border="0" height="15"></a>
					<a title="���ֶһ�" href="javascript:queryExchange('${item.id}')">
					<img src="../images/change.gif" border="0" height="15"></a>
					<c:if test="${user.role == 'SHOP'}">
					<a title="ɾ����Ա��Ϣ" href="javascript:dels('${item.id}', '${item.cardNo}')">
					<img src="../images/del.gif" border="0" height="15"></a>
					</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>

		<p:formTurning form="formEntry" method="queryMembers"></p:formTurning>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" name="adds"
			style="cursor: pointer" value="&nbsp;&nbsp;���ӻ�Ա&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message />

</p:body></form>
</body>
</html>
