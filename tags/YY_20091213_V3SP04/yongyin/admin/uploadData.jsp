<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�ϴ������ļ�" />
<script src="../js/CommonScriptMethod.js"></script>
<script src="../js/prototype.js"></script>
<script src="../js/common.js"></script>
<style type="text/css">
		<!--
		body,td,th {
			font-size: 12px;
		}
		
		.style1 {color: #000000}
		.width {
		}
		-->
		</style>

<Script Language="JavaScript">
function checkValue()
{   
	if ($("ttpe").value == '')
	{
		alert("������Ҫ������ļ�����");
        return false;
	}
	
    if(window.confirm("ȷ���ϴ�?"))
    {
        var fileName = document.form1.myFile.value;
        
        //alert(fileName);
        if ("" == fileName)
        {
            alert("������Ҫ������ļ���");
            return false;
        }
        
        if ($("ttpe").value == "1")
        {
        	if (fileName.indexOf('��Ʒ.xls') == -1)
        	{
        		alert("�ļ����Ʊ���:��Ʒ.xls");
            	return false;
        	}
        }
        
        if ($("ttpe").value == "2")
        {
        	if (fileName.indexOf('�ͻ�.xls') == -1)
        	{
        		alert("�ļ����Ʊ���:��Ʒ.xls");
            	return false;
        	}
        }
        
        if ($("ttpe").value == "3")
        {
        	if (fileName.indexOf('����.xls') == -1)
        	{
        		alert("�ļ����Ʊ���:��Ʒ.xls");
            	return false;
        	}
        }
        
        if ($("ttpe").value == "4")
        {
        	if (fileName.indexOf('ְԱ.xls') == -1)
        	{
        		alert("�ļ����Ʊ���:��Ʒ.xls");
            	return false;
        	}
        }
        
        if ($("ttpe").value == "5")
        {
        	if (fileName.indexOf('����.xls') == -1)
        	{
        		alert("�ļ����Ʊ���:��Ʒ.xls");
            	return false;
        	}
        }
        
        if ($("ttpe").value == "7")
        {
        	if (fileName.indexOf('��Ʒ���.xls') == -1)
        	{
        		alert("�ļ����Ʊ���:��Ʒ���.xls");
            	return false;
        	}
        }

        return true;
    }
    return false;
}

function uploads()
{
	if (checkValue())
	{
		form1.action = '../admin/upload.do';
		form1.enctype="multipart/form-data";
		form1.method="post"
		form1.submit();
	}
}
</Script>

</head>
<body id="bodys" class="body_class">

<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td height="22" valign="bottom">
		<table width="100%" height="22" border="0" cellpadding="0"
			cellspacing="0">
			<tr valign="middle">
				<td width="8"></td>
				<td width="30">
				<div align="center"><img src="../images/dot_a.gif" width="9"
					height="9"></div>
				</td>
				<td width="550" class="navigation">�ճ����ݵ��� &gt;&gt; ���ݵ���</td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td height="6" valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<!--DWLayoutTable-->
			<tr>
				<td width="8" height="6"
					background="../images/index_sp_welcome_center_10.gif"><img
					src="../images/index_sp_welcome_center_07.gif" width="8" height="6"></td>
				<td width="190"
					background="../images/index_sp_welcome_center_08.gif"></td>
				<td width="486"
					background="../images/index_sp_welcome_center_10.gif"></td>
				<td align="right"
					background="../images/index_sp_welcome_center_10.gif">
				<div align="right"><img
					src="../images/index_sp_welcome_center_12.gif" width="23"
					height="6"></div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>


<form action="./upload" enctype="multipart/form-data" method="post"
	name="form1">

<table width="70%" border="0" cellpadding="0" cellspacing="0"
	align="center" id="tables">
	<tr>
		<td valign="top" colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<!--DWLayoutTable-->
			<tr>
				<td width="784" height="2"></td>
			</tr>
			<tr>
				<td align="center" valign="top">
				<div align="left">
				<table width="90%" border="0" cellspacing="2">
					<tr>
						<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="10">
							<tr>
								<td width="15">&nbsp;</td>
								<td width="6"><img src="../images/dot_r.gif" width="6"
									height="6"></td>
								<td class="caption"><strong>ѡ���ļ�:</strong></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>


	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>


	<tr>
		<td colspan='2' align='center'>
		<table border="0" width=70% class="border" align=center>

			<tr class="content1">
				<td class="content2">������������</td>
				<td class="content2"><select name="ttpe">
					<option value="">----</option>
					<option value="1">��Ʒ����</option>
					<option value="2">�ͻ�����</option>
					<option value="3">���ŵ���</option>
					<option value="4">ְԱ����</option>
					<option value="5">���е���</option>
					<option value="6">�ⵥ���ո����</option>
					<option value="7">��Ʒ��浼��</option>
				</select></td>
			</tr>

			<tr class="content2">
				<td class="content2">ѡ��Ҫ�ϴ����ļ�</td>
				<td class="content2"><input type="file" name="myFile"
					class="button_class" /></td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>


	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td width="85%" id="submits">
		<div align="right"><input type="button"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onclick="uploads()"
			class="button_class" /></div>
		</td>
		<td width="15%"></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td colspan='2' align="center"><FONT color="blue">${MESSAGE_INFO}</FONT><FONT
			color="red">${errorInfo}</FONT></td>
	</tr>

</table>
</form>
<br>
<br>

</body>
</html>
