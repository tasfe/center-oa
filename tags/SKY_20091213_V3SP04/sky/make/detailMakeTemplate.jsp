<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="����ģ��" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">

function load1()
{
}

function editTemplate(docURL) 
{
    if (window.ActiveXObject)
    {
        var openDocObj = new ActiveXObject("SharePoint.OpenDocuments.2");
    } 
    else
    {
        alert('��ʹ��IE���в���');
        return;
    }
    
    openDocObj.editDocument(docURL); 
    
}

</script>

</head>
<body class="body_class" onload="load1()">
<form name="formEntry">
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">����ģ�����</span> &gt;&gt; ����ģ����ϸ</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>����ģ�壺</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">

		<p:table cells="1">
		
		    <p:cell title="����">
             ${template.name}
            </p:cell>  
		    
		    <p:cell title="����">
		     ${template.description}
		    </p:cell>  
		    
		    <p:cell title="����">
              <input type="button" value="&nbsp;���߱༭&nbsp;" name="log_g${vs.index}" id="log_g${vs.index}"
                   class="button_class" onclick="editTemplate('${eurl}')">
            </p:cell> 

		</p:table>
		
		
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="99%" rightWidth="1%">
		<div align="right"><input type="button" class="button_class" id="return_b" name="return_b"
		 value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" accesskey="O"
			onclick="javascript:history.go(-1)">&nbsp;&nbsp;
		</div>
	</p:button>
</p:body>
</form>
</body>
</html>

