<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="�ͻ���Ϣ�������" cal="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">

function addBean()
{
	submit('ȷ������ͻ���Ϣ�˶�?');
}

function load()
{
    $O('beginTime').value = '';
    $O('endTime').value = '';
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../customer/check.do" method="post"><input
	type="hidden" name="method" value="goonCheck"><input
    type="hidden" name="id" value="${bean.id}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">�ͻ�����</span> &gt;&gt; �ͻ���Ϣ��˼���</td>
	<td width="85"></td>
</p:navigation> <br>
<p:body width="100%">

	<p:title>
		<td class="caption"><strong>���������Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.customer.bean.CustomerCheckBean" opr="1"/>

		<p:table cells="1">
			<p:pro field="checkerId" cell="1" innerString="readonly=readonly">
		        <option value="">--</option>
		        <c:forEach items="${list}" var="item">
		        <option value="${item.id}">${item.name}</option>
		        </c:forEach>   
			</p:pro>
			
			<p:pro field="beginTime"/>
			
			<p:pro field="endTime"/>
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

