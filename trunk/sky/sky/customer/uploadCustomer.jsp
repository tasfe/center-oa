<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="�ϴ��ͻ�" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">
function addBean()
{
	submit('ȷ���ϴ��ͻ�����?', null, checkValue);
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

function exportCur()
{
    document.location.target = '_blank';
    document.location.href = '../customer/customer.do?method=exportNotPay';
}

</script>

</head>
<body class="body_class"">
<form name="addApply"
	action="../customer/customer.do?method=uploadCustomer"
	enctype="multipart/form-data" method="post"><p:navigation
	height="22">
	<td width="550" class="navigation"><span>����ͻ�</span></td>
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
					href="../admin/down.do?method=downTemplateFile">���ؿͻ����ϵ���ģ��(2008-12-26����ģ��������CODE��������ȷ��ģ���Ƿ�һ��)</a>
			</p:cell>
		</p:table>

		<p:table cells="1">
			<p:cell title="�����ļ�">
				<input type="file" name="myFile" class="button_class" />
			</p:cell>
			<p:cell title="����Ӧ�տͻ�">
                <input type="button" class="button_class"  value="����Ӧ�տͻ�" onclick="exportCur()"/>
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

