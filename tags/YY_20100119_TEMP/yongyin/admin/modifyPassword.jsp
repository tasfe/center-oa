<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>

<p:link title="�޸�����" />
<script src="../js/common.js"></script>
<script language="javascript">
function press()
{
	if (event.keyCode == 13)
	{
		if (checkValue())
		{
			if (window.confirm('ȷ���޸�����?'))
			modifyPassword.submit();
		}
	}
}

function mod()
{
	if (checkValue())
	{
		if (window.confirm('ȷ���޸�����?'))
		modifyPassword.submit();
	}
}

function checkValue()
{
 	if("" == modifyPassword.oldPassword.value)
    {
        alert("ԭ���벻��Ϊ�գ�������");
        modifyPassword.oldPassword.focus();
        return false;
    }
    if("" == modifyPassword.newPassword.value)
    {
        alert("�����벻��Ϊ�գ�������");
        modifyPassword.newPassword.focus();
        return false;
    }
    if("" == modifyPassword.confirmPassword.value)
    {
        alert("ȷ�����벻��Ϊ�գ�������");
        modifyPassword.confirmPassword.focus();
        return false;
    }    
    if (trim(modifyPassword.confirmPassword.value) != trim(modifyPassword.newPassword.value))
    {
        alert("�����ȷ�����벻һ�£�����������");
        modifyPassword.confirmPassword.focus();
        return false;
    }
    
    if (trim(modifyPassword.confirmPassword.value).length < 10)
    {
    	alert("�����벻��С��10λ��������");
        modifyPassword.newPassword.focus();
        return false;
    }
    
    if (isNumbers($$('confirmPassword')) || isLetter($$('confirmPassword')) || isNumOrLetter($$('confirmPassword')))
    {
    	alert("���벻��С��10λ�������ֺ��ַ������,�������ַ�");
        modifyPassword.newPassword.focus();
        return false;
    }   
    
    return true;
}


function isNumOrLetter(str)
{
    var reg = /^[A-Za-z0-9]*$/;
    return reg.test(str);
}
</script>

</head>
<body class="body_class">
<form name="modifyPassword" action="./checkuser.do"><input
	type="hidden" name="method" value="modifyPassword">
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
				<td width="550" class="navigation">�޸����� &gt;&gt; �޸�����</td>
				<td width="85"></td>
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

<br>
<table width="85%" border="0" cellpadding="0" cellspacing="0"
	align="center">
	<tr>
		<td valign="top" colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<!--DWLayoutTable-->
			<tr>
				<td width="784" height="6"></td>
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
								<td class="caption"><strong>��������:</strong></td>
							</tr>
						</table>
						</td>
					</tr>


				</table>
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
		<table width="65%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr class="content2">
						<td width="30%">ԭ����:</td>
						<td><input type="password" name="oldPassword"
							onkeypress="press()"><font color="#FF0000">*</font></td>
					</tr>
					<tr class="content1">
						<td width="30%">������:</td>
						<td><input type="password" name="newPassword"
							onkeypress="press()"><font color="#FF0000">*</font></td>
					</tr>
					<tr class="content2">
						<td width="30%">ȷ������:</td>
						<td><input type="password" name="confirmPassword"
							onkeypress="press()"><font color="#FF0000">*</font></td>
					</tr>
				</table>
				</td>
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
		<td width="82%">
		<div align="right"><input name="add" type="button"
			class="button_class" value="&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;"
			onclick="mod()">&nbsp;&nbsp;
		<input name="cancel" type="reset" class="button_class"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
		</td>
		<td width="18%"></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td colspan='2' align="center"><FONT color="blue">${MESSAGE_INFO}</FONT>
		</td>
	</tr>


</table>


</form>
</body>
</html>

