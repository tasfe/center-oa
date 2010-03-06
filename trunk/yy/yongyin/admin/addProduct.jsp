<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@include file="./common.jsp"%>
<html>
<head>

<p:link title="���Ӳ�Ʒ" cal="false"/>

<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/prototype.js"></script>
<script language="JavaScript" src="../js/buffalo.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">

var END_POINT="${pageContext.request.contextPath}/bfapp";

var buffalo = new Buffalo(END_POINT);
buffalo.onError = new Function();

function callbacks()
{
	if (trim($("name").value) == '')
	{
		alert("���Ʋ���Ϊ�գ�������");
        return false;
	}

	if (trim($("code").value) == '')
	{
		alert("���벻��Ϊ�գ�������");
        return false;
	}

	if (formCheck())
	buffalo.remoteCall("productDAO.getNum",[$("name").value, $("code").value], function(reply) {
		        var result = reply.getResult();
		        if (result > 0)
		        {
		        	alert("�˲�Ʒ�Ѿ�����,��������д");
		        	return false;
		        }

		        if (window.confirm("ȷ�����Ӵ˲�Ʒ"))
		        formBean.submit();
		});
}

function load()
{
	loadForm();
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formBean" action="./product.do?method=addProduct" enctype="multipart/form-data" method="post">
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
				<td width="550" class="navigation">�ճ�ά�� &gt;&gt; <span
					style="cursor: hand" onclick="javascript:history.go(-1)">�����Ʒ</span>
				&gt;&gt; ���Ӳ�Ʒ</td>
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
								<td class="caption"><strong>��Ʒ��Ϣ:</strong></td>
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
						<td width="30%">��Ʒ����:</td>
						<td><input type="text" name="name" maxlength="50"><font
							color="#FF0000">*</font></td>
					</tr>

					<tr class="content2">
						<td width="30%">��Ʒ����:</td>
						<td><input type="text" name="code" value="" maxlength="16"><font
							color="#FF0000">*</font></td>
					</tr>
					
					<tr class="content1">
						<td width="30%">�����ڣ��죩:</td>
						<td><input type="text" name="makeDays" oncheck="notNone;isInt" value="" maxlength="16"><font
							color="#FF0000">*</font></td>
					</tr>
					
					<tr class="content2">
						<td width="30%">�����ڣ��죩:</td>
						<td><input type="text" name="flowDays" oncheck="notNone;isInt" value="" maxlength="16"><font
							color="#FF0000">*</font></td>
					</tr>
					
					<tr class="content1">
						<td width="30%">��С��������������:</td>
						<td><input type="text" name="minAmount" oncheck="notNone;isInt" value="" maxlength="16"><font
							color="#FF0000">*</font></td>
					</tr>

					<tr class="content2">
						<td width="30%">��Ʒ����:</td>
						<td><select name="genre" oncheck="notNone;" quick="true" style="width:200px">
							<option value="">--</option>
							<c:forEach items="${list}" var="item">
							<option value="${item.id}">${item.name}</option>
							</c:forEach>
						</select><font color="#FF0000">*</font></td>
					</tr>

					<tr class="content2">
						<td width="30%">��Ʒ����:</td>
						<td><select name="temp" oncheck="notNone;" style="width:200px">
							<option value="">--</option>
							<option value="0">����</option>
							<option value="1">������</option>
						</select><font color="#FF0000">*</font></td>
					</tr>
					
					<tr class="content2">
						<td width="30%">�̵����:</td>
						<td><select name="type" oncheck="notNone;" style="width:200px">
							<option value="">--</option>
							<option value="0">ÿ���̵�</option>
							<option value="1">����</option>
						</select><font color="#FF0000">*</font></td>
					</tr>

					<tr class="content2">
						<td width="30%">��ƷͼƬ:</td>
						<td><input type="file" name="picPath" class="button_class" size="40" contenteditable="false"></td>
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
			onclick="callbacks()">&nbsp;&nbsp; <input type="button"
			class="button_class" onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
		</td>
		<td width="18%"></td>
	</tr>

	<tr height="10">
		<td height="10" colspan='2'></td>
	</tr>


	<tr>
		<td colspan='2' align="center"><FONT color="blue">${MESSAGE_INFO}</FONT><FONT
			color="red">${errorInfo}</FONT></td>
	</tr>


</table>


</form>
</body>
</html>

