<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="��������ģ��" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">
function addBean()
{
	submit('ȷ����������ģ��?', null, checkFile);
}

function checkFile()
{
    var path = $$('att');
    
    if (path.toLowerCase().indexOf('.doc') == -1 && path.toLowerCase().indexOf('.xls') == -1)
    {
        alert('����֧��Word��Excel');
        return false;
    }
    
    return true;
}

</script>

</head>
<body class="body_class">
<form name="addApply" action="../flow/template.do?method=addTemplateFile" 
enctype="multipart/form-data" method="post">
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">����ģ�����</span> &gt;&gt; ��������ģ��</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>����ģ�������Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.flow.bean.TemplateFileBean" />

		<p:table cells="1">

			<p:pro field="name" innerString="size=50"/>

			<p:cell title="����ģ��">
			<input type="file" class="button_class" name="att" size="60" oncheck="notNone" head="����ģ��"> 
			<font color="red">*</font><font color="blue"><b>����֧��doc,xls��</b></font>
			<input type="text" style="visibility: hidden;" size="1"></p:cell>

			<p:pro field="description" cell="0" innerString="rows=4 cols=55" />

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message/>
</p:body></form>
</body>
</html>

