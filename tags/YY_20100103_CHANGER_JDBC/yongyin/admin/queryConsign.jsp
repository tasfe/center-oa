<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�������б�" />
<script language="JavaScript" src="../js/common.js"></script>
<script src="../js/prototype.js"></script>
<script language="javascript">
function process()
{
	if (getRadioValue('consigns') == '')
	{
		alert('��ѡ�񷢻���');
		return;
	}
	
	document.location.href = '../admin/transport.do?method=findConsign&fullId=' + getRadioValue('consigns');
}

function load()
{
	loadForm();
}

function pagePrint()
{
	if (getRadioValue('consigns') == '')
	{
		alert('��ѡ�񷢻���');
		return;
	}
	
	window.open('../admin/transport.do?method=findConsign&forward=1&fullId=' + getRadioValue("consigns"));
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="bankList" action="../admin/transport.do"><input
	type="hidden" name="method" value="queryConsign"> <p:navigation
	height="22">
	<td width="550" class="navigation">���������� &gt;&gt; �������б�</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="90%">

	<p:subBody width="95%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr class="content1">
				<td width="15%" align="center">(����)��ʼʱ��</td>
				<td align="left">
				<p:plugin name="beginDate" size="20" value="${beginDate}"/>
				</td>
				<td width="15%" align="center">(����)����ʱ��</td>
				<td align="left">
				<p:plugin name="endDate" size="20" value="${endDate}"/>
				</td>
			</tr>
			
			<tr class="content2">
				<td width="15%" align="center">(����)��ʼʱ��</td>
				<td align="left">
				<p:plugin name="abeginDate" size="20" value="${abeginDate}"/>
				</td>
				<td width="15%" align="center">(����)����ʱ��</td>
				<td align="left">
				<p:plugin name="aendDate" size="20" value="${aendDate}"/>
				</td>
			</tr>

			<tr class="content1">
				<td width="15%" align="center">����״̬:</td>
				<td align="left"><select name="currentStatus"
					class="select_class" values="${currentStatus}">
					<option value="">--</option>
					<option value="1">��ʼ</option>
					<option value="2">ͨ��</option>
				</select></td>
				<td width="15%" align="center">�ظ�����:</td>
				<td align="left"><select name="reprotType" class="select_class"
					values="${reprotType}">
					<option value="">--</option>
					<option value="0">�޻ظ�</option>
					<option value="1">�����ջ�</option>
					<option value="2">�쳣�ջ�</option>
				</select></td>
			</tr>
			
			<tr class="content2">
				<td width="15%" align="center">���ţ�</td>
				<td align="left">
				<input name="fullId" size="20" value="${fullId}"  />
				</td>
				<td width="15%" align="center"></td>
				<td align="left">
				</td>
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
		<td class="caption"><strong>�������б�</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="95%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center" class="td_class">ѡ��</td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>����ʱ��</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>����״̬</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>�ظ�����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"><strong>����ʱ��</strong></td>

			</tr>
			
			<c:set var="adm" value='${user.role == "ADMIN"}'/>
			<c:forEach items="${consignList}" var="item"
				varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type="radio" name="consigns"
						statuss="${item.currentStatus}" value="${item.fullId}"
						${vs.index== 0 ? "checked" : ""}/></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.outTime}</td>
					<td align="center" onclick="hrefAndSelect(this)"><a
						<c:if test="${!adm}">
						href="../admin/transport.do?method=findConsign&fullId=${item.fullId}"
						</c:if>
						>${item.fullId}</a></td>
					<%
					    String[] sss = new String[] {"", "��ʼ", "ͨ��"};
					    request.setAttribute("sss", sss);
					%>
					<td align="center" onclick="hrefAndSelect(this)">${my:getValue(item.currentStatus,
					sss)}</td>
					<%
					    String[] sss1 = new String[] {"�޻ظ�", "�����ջ�", "�쳣�ջ�"};
					    request.setAttribute("sss1", sss1);
					%>
					<td align="center" onclick="hrefAndSelect(this)">${my:getValue(item.reprotType,
					sss1)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.arriveDate}</td>
				</tr>
			</c:forEach>
		</table>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="98%" rightWidth="2%">
		
		<div align="right">
			<input type="button" 
			class="button_class" onclick="pagePrint()"
			value="&nbsp;&nbsp;���ƴ�ӡ&nbsp;&nbsp;">&nbsp;&nbsp;
			<c:if test="${!adm}">
			<input type="button" class="button_class"
			value="&nbsp;&nbsp;��������&nbsp;&nbsp;" onclick="process()">&nbsp;&nbsp;
			</c:if>
		</div>
	</p:button>

	<p:message />

</p:body></form>
</body>
</html>

