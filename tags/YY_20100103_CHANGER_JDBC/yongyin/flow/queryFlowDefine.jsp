<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="���̶����б�" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function querys()
{
	$('method').value = 'queryFlowDefine';
	formEntry.submit();
}

function addBean()
{
	var sss = window.prompt('�����붨�����̵Ļ�������Ĭ������5�����ڣ�', '5');
	
	if (isNull(sss))
	{
		alert('�����뻷����');
		return false;
	}
	
	if (!isNumbers(sss))
	{
		alert('����������������');
		return false;
	}
	
	if (parseInt(sss) > 97)
	{
		alert('���������ܳ���95');
		return false;
	}
	
	if (parseInt(sss) < 5)
	{
		sss = "5";
	}
	
	
	document.location.href = '../flow/addFlowDefine.jsp?tokens=' + sss;
}

function press()
{
	if (window.common.getEvent().keyCode == 13)
	{
		querys();
	}
}

function resets()
{
	formEntry.reset();

	$('name').value = '';
}

function load()
{
	loadForm();

	$f('name');
}

function del(id)
{
	if (window.confirm('ȷ��ɾ�����̶���?'))
	{
		$('method').value = 'delFlowDefine';
		$('id').value = id;
		formEntry.submit();
	}
}

function drop(id)
{
	if (window.confirm('ȷ����������ʹ��?'))
	{
		$('method').value = 'dropFlowDefine';
		$('id').value = id;
		formEntry.submit();
	}
}

function view(id)
{
	$('method').value = 'preForProcessView';
	$('id').value = id;
	formEntry.submit();
}

function apply(id)
{
	$('method').value = 'preForApllyFlowInstance';
	$('id').value = id;
	formEntry.submit();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../flow/flow.do"><input
	type="hidden" name="method" value="queryFlowDefine"> <input
	type="hidden" value="1" name="firstLoad"> <input type="hidden"
	value="" name="id"> <p:navigation height="22">
	<td width="550" class="navigation">���̶������ &gt;&gt; ���̶����б�</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content1">
				<td align="center">��������</td>
				<td align="center" width="35%"><input type="text"
					onkeydown="press()" name="name" value="${name}"></td>
				<td align="right" colspan="2"><input type="button"
					class="button_class" value="&nbsp;&nbsp;�� ѯ&nbsp;&nbsp;"
					onclick="querys()">&nbsp;&nbsp; <input type="button"
					class="button_class" value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"
					onclick="resets()"></td>
			</tr>

		</table>

	</p:subBody>


	<p:title>
		<td class="caption"><strong>���̶����б�</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="30%"><strong>��������</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="20%"><strong>������</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="10%"><strong>״̬</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="20%"><strong>����ʱ��</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="20%"><strong>����</strong></td>
			</tr>

			<c:forEach items="${list}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center" onclick="hrefAndSelect(this)"><a
						href="../flow/flow.do?method=detailFlowDefine&id=${item.id}">
					${item.name} </a></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.userName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('flowDefineStatus', item.status)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.logTime}</td>
					<td align="center" onclick="hrefAndSelect(this)">
					<c:if test="${item.status != 2}">
					<a title="��������" href="javascript:apply('${item.id}')"> <img
								src="../images/change.gif" border="0" height="15" width="15"></a>
					</c:if>

					<c:if test="${user.role == 'WORKFLOW' && item.status == 0}">
					<a title="���ò���" href="javascript:view('${item.id}')"> <img
								src="../images/edit.gif" border="0" height="15" width="15"></a>
					<a title="�ϳ����̶���" href="javascript:drop('${item.id}')"> <img
								src="../images/begin.gif" border="0" height="15" width="15"></a>
					<a title="ɾ�����̶���" href="javascript:del('${item.id}')"> <img
								src="../images/del.gif" border="0" height="15" width="15"></a>
					</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>

		<p:formTurning form="formEntry" method="queryFlowDefine"></p:formTurning>
	</p:subBody>

	<p:line flag="1" />

	<c:if test="${user.role == 'WORKFLOW'}">
		<p:button leftWidth="100%" rightWidth="0%">
			<div align="right"><input type="button" class="button_class"
				name="adds" style="cursor: pointer"
				value="&nbsp;&nbsp;�������̶���&nbsp;&nbsp;" onclick="addBean()"></div>
		</p:button>
	</c:if>

	<p:message />

</p:body></form>
</body>
</html>

