<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="���ò�Ʒ��������" cal="false" />
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">

function addBean(opr)
{
}

function load()
{
	init();
}

function init()
{
	
}

function refush()
{
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../examine/product.do" method="post"><input
	type="hidden" name="method" value="configProductExamineItem"> <input type="hidden"
	name="pid" value="${examine.id}"> 
<c:forEach items="${newList}" var="item" varStatus="vs">
<input type="hidden" name="stafferIds" value="${item.stafferId}"> 
</c:forEach>
<p:navigation height="22">
	<td width="550" class="navigation">���ò�Ʒ����</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>��${examine.productName}����Ʒ����������Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0" id="tables">
			<tr align="center" class="content0">
				<td width="20%" align="left">��ʼʱ��</td>
				<td width="20%" align="left">����ʱ��</td>
				<td width="20%" align="left">ְԱ</td>
				<td width="20%" align="left">����</td>
				<td width="20%" align="left">������(ʵ��/Ԥ��)</td>
			</tr>

			<c:forEach items="${newList}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
				    <td width="20%">${item.beginTime}</td>
					<td width="20%">${item.endTime}</td>
					<td width="20%">${item.stafferName}</td>
					<td width="20%">${item.cityName}</td>
					<td>${item.realValue}/${item.planValue}</td>
				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

</p:body></form>
<p:message></p:message>
</body>
</html>

