<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="���ӹ�Ӧ��" cal="false" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">

function addBean()
{
	submit('ȷ���������ӹ�Ӧ��?');
}


</script>

</head>
<body class="body_class">
<form name="formEntry" action="../customer/provider.do" method="post"><input
	type="hidden" name="method" value="addProvider"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">��Ӧ�̹���</span> &gt;&gt; ���ӹ�Ӧ��</td>
	<td width="85"></td>
</p:navigation> <br>
<p:body width="100%">

	<p:title>
		<td class="caption"><strong>��Ӧ�̻�����Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.customer.bean.ProviderBean" />

		<p:table cells="2">
			<p:pro field="name" cell="2" innerString="size=60"/>
			
			<p:pro field="code" cell="2" innerString="size=60"/>
			
			<p:pro field="type" innerString="quick=true">
                <p:option type="109"></p:option>
            </p:pro>
			<p:pro field="connector" />


			<p:pro field="phone" />
			<p:pro field="tel" />
			
            <p:pro field="fax" />
			<p:pro field="qq" />
            


			<p:pro field="bank" />
			<p:pro field="accounts" />
			
			<p:pro field="address" cell="2" innerString="size=60" />
            <p:pro field="email" cell="2" innerString="size=60" />

			<p:pro field="description" cell="0" innerString="rows=4 cols=60" />

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

