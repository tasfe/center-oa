<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="流程模板" />
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="javascript">

function viewTemplate() 
{
    if (window.ActiveXObject)
    {
        var openDocObj = new ActiveXObject("SharePoint.OpenDocuments.2");
    } 
    else
    {
        alert('请使用IE进行操作');
        return;
    }
    
    openDocObj.ViewDocument("${url}"); 
}

function load()
{
    setAllReadOnly();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../flow/template.do" method="post"><input type="hidden"
	name="method" value="updateTemplateFile"> <input type="hidden"
    name="id" value="${bean.id}"><p:navigation height="22">
	<td width="550" class="navigation">流程模板查看</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>流程模板基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.flow.bean.TemplateFileBean"
			opr="1" />

		<p:table cells="1">

			<p:pro field="name" innerString="size=50" />

			<p:cell title="模板">
				<a href="javaScript:void(0);">${bean.fileName}</a>&nbsp;&nbsp;<input class="button_class"
					type="button" name="view_b" onclick="viewTemplate()" value="在线预览">
			</p:cell>

			<p:pro field="description" cell="0" innerString="rows=4 cols=55" />

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
			onclick="javascript:history.go(-1)"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

