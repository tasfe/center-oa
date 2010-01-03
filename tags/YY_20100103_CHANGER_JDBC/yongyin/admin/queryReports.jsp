<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�̵��б�" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">
function process()
{
	if (getRadioValue('consigns') == '')
	{
		alert('��ѡ��');
		return;
	}
	
	document.location.href = '../admin/reports.do?method=listStorageLog&productId=' + getRadioValue('consigns');
}

function exports()
{
	document.location.href = '../admin/reports.do?method=export';
}

function exportsAll()
{
	document.location.href = '../admin/reports.do?method=exportAll';
}


function load()
{
	loadForm();
}

function cc()
{
	if (compareDays($$('beginDate'), $$('endDate')) > 40)
	{
		alert('��Ȳ��ܴ���40��');
		return false;
	}
	
	return true;
}
function stat()
{
	submit(null, null, cc);
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="bankList" action="../admin/reports.do"><input
	type="hidden" name="method" value="statReports"> <p:navigation
	height="22">
	<td width="550" class="navigation">�̵���� &gt;&gt; �̵��б�</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="90%">

	<p:subBody width="95%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr class="content1">
				<td width="15%" align="center">��ʼʱ��</td>
				<td align="left">
				<p:plugin name="beginDate" size="20" value="${beginDate}"  oncheck="notNone"/>
				</td>
				<td width="15%" align="center">����ʱ��</td>
				<td align="left">
				<p:plugin name="endDate" size="20" value="${endDate}"  oncheck="notNone"/>
				</td>
			</tr>

			<tr class="content2">
				<td width="15%" align="center">����:</td>
				<td align="left"><select name="depotpartId"
					class="select_class" values="${depotpartId}">
					<option value="">--</option>
					<c:forEach items="${depotpartList}" var="item">
					<option value="${item.id}">${item.name}</option>
					</c:forEach>
				</select></td>
				<td width="15%" align="center">�̵����</td>
				<td align="left"><select name="type" values="${type}" class="select_class">
							<option value="">--</option>
							<option value="0">ÿ���̵�</option>
							<option value="1">����</option>
						</select></td>
			</tr>

			<tr class="content1">
				<td colspan="4" align="right"><input type="button" onclick="stat()"
					class="button_class" value="&nbsp;&nbsp;�� ѯ&nbsp;&nbsp;">&nbsp;&nbsp;<input
					type="reset" class="button_class"
					value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></td>
			</tr>
		</table>

	</p:subBody>

	<p:title>
		<td class="caption"><strong>�̵��б�</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="95%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center" class="td_class">ѡ��</td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>��λ</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>��Ʒ</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>ԭʼ����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>�춯����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"><strong>��ǰ����</strong></td>
			</tr>

			<c:forEach items="${statList}" var="item"
				varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type="radio" name="consigns"
						value="${item.productId};${item.depotpartId}"
						${vs.index== 0 ? "checked" : ""}/></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.depotpartName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.storageName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.productName}</td>
					
					<td align="center" onclick="hrefAndSelect(this)">${item.preAmount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.changeAmount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.currentAmount}</td>
				</tr>
			</c:forEach>
		</table>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="98%" rightWidth="2%">
		<div align="right"><input type="button" class="button_class"
			value="&nbsp;&nbsp;�����춯&nbsp;&nbsp;" onclick="exports()">&nbsp;&nbsp;
			<input type="button" class="button_class"
			value="&nbsp;&nbsp;�����̵�&nbsp;&nbsp;" onclick="exportsAll()">&nbsp;&nbsp;
			<input type="button" class="button_class"
			value="&nbsp;&nbsp;�鿴��ϸ&nbsp;&nbsp;" onclick="process()">&nbsp;&nbsp;
		</div>
	</p:button>

	<p:message />

</p:body></form>
</body>
</html>

