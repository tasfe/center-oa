<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="�����Ͽͻ�����" cal="false" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/pop.js"></script>
<script language="javascript">

function addBean(opr)
{
	submit('ȷ�����������ӿ��˵��Ͽͻ�����?', null, null);
}

function load()
{
	loadForm();

	init();
}

function init()
{
	
}

function refush()
{
	$l('../examine/examine.do?method=queryAllSubOldCustomerExamine&pid=${parentBean.id}');
}


</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../examine/examine.do" method="post"><input
	type="hidden" name="method" value="configAllOldCustomerExamine"> <input type="hidden"
	name="pid" value="${parentBean.id}"> 
	<c:forEach items="${subs}" var="item">
        <input type="hidden" name="subItemId" value="${item.id}"> 
    </c:forEach>
	<p:navigation height="22">
	<td width="550" class="navigation">�����Ͽͻ�����</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>��${parentBean.stafferName}�������ӿ��ˡ��Ͽͻ�������Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="tables">
			<tr align="center" class="content0">
				<td width="5%" align="center">����</td>
				<td width="15%" align="left">��ʼʱ��</td>
				<td width="15%" align=left>����ʱ��</td>
				<c:forEach items="${subs}" var="item">
				<td align="center">${item.stafferName}</td>
				</c:forEach>
			</tr>

			<c:forEach items="${defaultList}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content2" : "content1"}'>
				    <td width="5%" align="center">${item.step}</td>
					<td width="15%">${item.beginTime}</td>
					<td width="15%">${item.endTime}</td>
					<c:forEach items="${subs}" var="subItem">
					<td align="center"><input type="text" ${readonlyModal[subItem.id] ? 'readonly=readonly' : ''}
						style="width: 100%" value="${subMap[subItem.id][vs.index].planValue}" 
						oncheck="isNumber" name="realValue_${subItem.id}"></td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

    <c:if test="${readonly != 1}">
	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			name="adds" style="cursor: pointer"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onclick="addBean(1)">&nbsp;&nbsp;
			<input type="button" class="button_class"
			name="adds" style="cursor: pointer"
			value="&nbsp;&nbsp;ˢ ��&nbsp;&nbsp;" onclick="refush()">
		</div>
	</p:button>
	</c:if>
</p:body></form>
<p:message></p:message>
</body>
</html>

