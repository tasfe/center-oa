<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ page import="com.china.centet.yongyin.constant.DefinedCommon" %>
<html>
<head>
<title>-=SKY����ƽ̨[V3.2005]=-</title>
<script src="../js/public.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link href="../css/ccc.css" rel="stylesheet" type="text/css" />

<META content="MSHTML 6.00.2900.2668" name=GENERATOR>
<script language="JavaScript" src="../js/common.js"></script>
<SCRIPT language=JavaScript type=text/JavaScript>
	function reset(){
	document.loginform.userName.value="";
	document.loginform.password.value=""
	}

function CheckuserName(str)
{
    var re = /^\w{1,16}$/;
    return re.test(str)||str=="";
}

function VerifyInput()
{
    if(document.loginform.userName.value.length <2
    || document.loginform.userName.value.length>16)
    {
 		alert("�û�����ʽ���ԣ�����Ϊ2��16λ���ֻ���ĸ������������!");
 		document.loginform.userName.focus();
 	 }
	else
	{
		if(document.loginform.password.value.length < 1)
		{
			alert("����������!");
 			document.loginform.password.focus();
 			return;
		}

		if(document.loginform.rand.value.length != 4)
		{
			alert("��������λ��֤��!");
 			document.loginform.rand.focus();
 			return;
		}

		if (document.loginform.spassword.value.length < 1)
		{
			alert("�������������!");
			document.loginform.spassword.focus();
			return;
		}
		
		
		for(var i = 0; i < 1000; i++)
		{
			document.getElementById(i + "");
		}

	    if (false)
	    {
		    var url = '../admin/checkuser.do?method=login&userName='
		    				 + $$('userName') + '&password=' + $$('password') + '&rand=' + $$('rand')
		    				 + '&spassword=' + $$('spassword');
		    var par = 'height=100, width=400, top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes,status=yes';
		    
		    window.open(url, "mainOpen", par);
		    
		    window.close();
	    }
	    else
	    {
	    	loginform.submit();
	    }
	    
		return false;
	}
}
function KeyPress()
{
	var event = common.getEvent();
    if(event.keyCode == 13)
    {
        VerifyInput();
    }
}

function again(obj)
{
	var ltime = new Date().getTime();
	
	obj.src = 'image.jsp?randomNum=' + ltime;
}
</SCRIPT>

<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
</HEAD>

<BODY bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0"
	marginheight="0"
	onload=loginform.userName.focus();loginform.userName.select()>
<FORM name=loginform onsubmit=VerifyInput() action=./checkuser.do
	method=post><input type="hidden" name="method" value="login" />


<table width="100%" height="100%" border="0" align="center"
	cellpadding="0" cellspacing="0">
	<table>
		<tr height="186">
			<td></td>
			<td></td>
		</tr>
	</table>
	<!--DWLayoutTable-->
	<tr>
		<td width="100%" height="100%" align="center" valign="middle">
		<table border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center" valign="middle"><img
					src="../images/login/login_L.gif" width="38" height="223" /></td>
				<td width="147" align="center" valign="middle"><img
					src="../images/login/login_logo.gif" width="159" height="223" /></td>
				<td align="center" valign="middle"><img
					src="../images/login/login_line.gif" width="79" height="223" /></td>
				<td align="center" valign="middle" class="login_bg_3" width="250">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="94" colspan="2" align="left" valign="middle"
							style="height: 52px">&nbsp;</td>
					</tr>
					<tr>
						<td align="right">
						<div align="left" class="STYLE1">��&nbsp;&nbsp;��&nbsp;</div>
						</td>
						<td align="left"><input name="userName" onkeypress=KeyPress()
							title=�ɺ����ֻ���Ӣ���ַ���3-16λ type="text" class="input"></td>
					</tr>
					<tr>
						<td align="right">
						<div align=left class="STYLE1">��&nbsp;&nbsp;��&nbsp;</div>
						</td>
						<td align="left"><input name="password" type="password"
							value="" onkeypress=KeyPress() class="input"></td>
					</tr>

					<tr>
						<td align="right" width="60">
						<div align="left">��֤��</div>
						</td>
						<td align="left"><input name="rand" type="text" value=""
							title="��֤�벻���ִ�Сд" style="ime-mode: disabled" maxlength="4"
							onkeypress=KeyPress() class="input11">&nbsp;<span
							id="rang_span"><img name="randImage" onclick="again(this)"
							style="cursor: pointer;" title="��������л���֤��" id="randImage"
							src="image.jsp" width="60" height="20" border="1" align="middle"></span></td>
					</tr>

					<tr>
						<td align="right" width="60">
						<div align="left">��������</div>
						</td>
						<td align="left"><input name="spassword" type="text" value=""
							onkeypress=KeyPress() class="input"></td>
					</tr>


					<tr>
						<td width="35" align="left"></td>
						<td align="left"><label><input name="logins"
							type="button" class="button" value="��¼" onclick="VerifyInput()"></label></td>
					</tr>
					<tr>
						<td align="left" colspan='2'><font color=red>${errorInfo}</font></td>
						<c:remove var="errorInfo" scope="session" />
					</tr>
				</table>
				</td>
				<td align="center" valign="middle"><img
					src="../images/login/login_r_2.gif" width="110" height="223" /></td>
				<td align="center" valign="middle"><img
					src="../images/login/login_r.gif" width="13" height="223" /></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<table align="center">
	<tr height="30%" align="center">
		<td align="center">������:<%out.print(DefinedCommon.webBeginTime);%>��<a href="http://58.213.146.91:7792"
			target="_blank"><u><font color=blue></font></u></a></td>
	</tr>
</table>
</FORM>
</body>
</html>
