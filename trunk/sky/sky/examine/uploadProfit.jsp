<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="�ϴ��ͻ�����" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">
function addBean()
{
	submit('ȷ���ϴ��ͻ���������?', null, checkValue);
}

function checkValue()
{   
    var fileName = $O('myFile').value;
        
    if ("" == fileName)
    {
        alert("������Ҫ������ļ���");
        return false;
    }
    
    if (fileName.indexOf('xls') == -1)
    {
        alert("ֻ֧��XLS�ļ���ʽ!");
        return false;
    }
    
    return true;
}

</script>

</head>
<body class="body_class"">
<form name="formEntry"
	action="../examine/city.do?method=uploadProfit"
	enctype="multipart/form-data" method="post"><p:navigation
	height="22">
	<td width="550" class="navigation"><span>����ͻ�����</span></td>
	<td width="85"></td>
</p:navigation> <br>
<p:body width="100%">

	<p:title>
		<td class="caption"><strong>ѡ���ļ���</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="80%">

		<p:table cells="1">
			<p:cell title="����ģ��">
				<a target="_blank"
					href="../admin/down.do?method=downProfitTemplateFile">���ؿͻ�������ģ��</a>
			</p:cell>
		</p:table>

		<p:table cells="1">
			<p:cell title="�����ļ�">
				<input type="file" name="myFile" class="button_class" style="width: 400px"/>
			</p:cell>
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>

