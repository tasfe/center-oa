<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�ȵ��Ʒͳ��" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function querys()
{
	formEntry.submit();
}

function press()
{
	if (window.common.getEvent().keyCode == 13)
	{
		querys();
	}
}

function load()
{
	loadForm();
}

function detailStock(id)
{
	document.location.href = '../admin/stat.do?method=queryStockItemForStacProvider&load=1&alogTime='
							+ $$('alogTime') + '&blogTime=' + $$('blogTime') + '&providerId=' + id;
}

function exports()
{
	document.location.href = '../admin/stat.do?method=exportAll&alogTime='
							+ $$('alogTime') + '&blogTime=' + $$('blogTime');
}

</script>
</head>

<body class="body_class" onload="load()">
<form name="formEntry" action="../admin/stat.do"><input
	type="hidden" name="method" value="stacProvider"> <input
	type="hidden" value="1" name="firstLoad"> <p:navigation
	height="22">
	<td width="550" class="navigation">ͳ�ƹ��� &gt;&gt; ��Ӧ��ͳ��</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center">��ʼʱ��</td>
				<td align="center" width="35%"><p:plugin name="alogTime"
					type="0" value="${alogTime}" /></td>
				<td align="center">����ʱ��</td>
				<td align="center" width="35%"><p:plugin name="blogTime"
					type="0" value="${blogTime}" /></td>
			</tr>

			<tr align=center class="content1">
				<td align="right" colspan="4"><input type="button"
					class="button_class" value="&nbsp;&nbsp;�� ѯ&nbsp;&nbsp;"
					onclick="querys()">&nbsp;&nbsp;
			</tr>
		</table>

	</p:subBody>

	<p:title>
		<td class="caption"><strong>��Ӧ��ͳ�ƣ�</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="30%"><strong>��Ӧ��</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"
					width="20%"><strong>���</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"
					width="30%"><strong>��������</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"
					width="10%"><strong>����</strong></td>
			</tr>

			<c:forEach items="${list}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center" onclick="hrefAndSelect(this)">${item.providerName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.total)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.amount}</td>
					<td align="center" onclick="hrefAndSelect(this)"><a
						title="������ϸ" href="javascript:detailStock('${item.providerId}')"> <img
						src="../images/edit.gif" border="0" height="15" width="15"></a>
					</td>
				</tr>
			</c:forEach>
		</table>

		<p:formTurning form="formEntry" method="stacProvider"></p:formTurning>
	</p:subBody>

	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			name="adds" style="cursor: pointer"
			value="&nbsp;&nbsp;�� �� �� ��&nbsp;&nbsp;" onclick="exports()">&nbsp;&nbsp;
		</div>
	</p:button>

	<p:message />

</p:body></form>
</body>
</html>

