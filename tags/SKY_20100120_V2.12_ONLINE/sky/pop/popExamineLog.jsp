<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="������־�б�" />
<base target="_self">
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">

function load()
{
	loadForm();
}

function sures()
{
    window.close();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry"  method="post">
<p:navigation
	height="22">
	<td width="550" class="navigation">������־</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
	<p:title>
		<td class="caption"><strong>������־��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center"><strong>�ⵥID</strong></td>
				<td align="center"><strong>�ͻ�����</strong></td>
				<td align="center"><strong>ʱ��</strong></td>
			</tr>

			<c:forEach items="${list}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center">${item.outId}</td>
					<td align="center">${item.customerName}</td>
					<td align="center">${item.logTime}</td>
				</tr>
			</c:forEach>
		</table>

	</p:subBody>

	<p:line flag="1" />
	
	<p:button leftWidth="100%" rightWidth="0%">
        <div align="right"><input type="button" class="button_class"
            id="adds" style="cursor: pointer" value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"
            onclick="sures()"></div>
    </p:button>

	<p:message />

</p:body></form>
</body>
</html>

