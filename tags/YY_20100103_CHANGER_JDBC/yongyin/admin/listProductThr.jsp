<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@include file="./common.jsp"%>

<html>
<head>
<p:link title="��Ʒ��ѯ" />
<script language="JavaScript" src="../js/prototype.js"></script>
<script language="JavaScript" src="../js/buffalo.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="javascript">
var END_POINT="${pageContext.request.contextPath}/bfapp";

var buffalo = new Buffalo(END_POINT);

function modfiys()
{
	var obj = getRadio('relation');
	if (obj == null)
	{
		alert('��ѡ��');
		return;
	}
	
	if (window.confirm("��ȷ����Ҫ���Ĳ�Ʒ����?"))
	{
		var sss = window.prompt('��������º�ġ�' + obj.productName + '��������', obj.am);
		
		if (!(sss == null || sss == ''))
		{
			if (isNumbers(sss))
			{
				var num = parseInt(sss);
				if (num < 0)
				{
					alert('���������0������');
					return;
				}
				
				if (window.confirm("��ȷ�ϸ��ĺ�Ĳ�Ʒ��" + obj.productName  + "�������ǣ�" + num ))
				//���ú�̨
				buffalo.remoteCall("storageManager.changeStorage",[obj.value, num, '${user.stafferName}'], function(reply) {	
			        var resultobject = reply.getResult();
			        
			        if(typeof(resultobject) == 'boolean')
					{
						var tr = getTrObject(getRadio('relation'));
						
						var tds = tr.getElementsByTagName('TD');
						
						tds[5].innerText = num;
						
						obj.am = num;
						
						alert('�޸Ĳ�Ʒ��' + obj.productName +'������:' + num + ' �ɹ�!');
						
						return;
					}
					else
					{
						alert(resultobject.detail.errorContent);
					}
			        return;
				});
			}
			else
			{
				alert('����������');
			}
		}
	}
}

function load()
{
	loadForm();
	$f('name');
}

function backs()
{
	document.location.href = '../admin/das.do?method=queryDepotpart';
}

function res()
{
	$('name').value = '';
	$('code').value = '';
	$('storageName').value = '';
}


</script>
</head>
<body class="body_class" onload="load()">
<form action="../admin/das.do" name="adminForm"><input
	type="hidden" value="queryProduct" name="method"> <input
	type="hidden" value="1" name="firstLoad"> <input type="hidden"
	value="${depotpartBean.id}" name="depotpartId">
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
				<td width="550" class="navigation">�������� &gt;&gt; �����б� &gt;&gt;
				�����Ʒ</td>
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
		<td align='center' colspan='2'>
		<table width="85%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr class="content1">
						<td width="15%" align="center">��Ʒ����</td>
						<td align="center"><input type="text" name="name"
							value="${name}"></td>
						<td width="15%" align="center">��Ʒ����</td>
						<td align="center"><input type="text" name="code"
							value="${code}"></td>
					</tr>

					<tr class="content2">
						<td width="15%" align="center">��λ</td>
						<td align="center"><input type="text" name="storageName"
							value="${storageName}"></td>
						<td colspan="2" align="right"><input type="submit"
							class="button_class" value="&nbsp;&nbsp;�� ѯ&nbsp;&nbsp;">&nbsp;&nbsp;<input
							type="button" onclick="res()" class="button_class"
							value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>

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
								<td width="35">&nbsp;</td>
								<td width="6"><img src="../images/dot_r.gif" width="6"
									height="6"></td>
								<td class="caption"><strong>�����Ʒ:</strong></td>
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
		<td align='center' colspan='2'>
		<table width="85%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr align="center" class="content0">
						<td align="center" width="8%" class="td_class"><B>ѡ��</B></td>
						<td align="center" onclick="tableSort(this, true)"
							class="td_class"><B>����</B></td>
						<td align="center" onclick="tableSort(this)" class="td_class"><B>��λ</B></td>
						<td align="center" onclick="tableSort(this)" class="td_class"><B>��Ʒ����</B></td>
						<td align="center" onclick="tableSort(this)" class="td_class"><B>��Ʒ����</B></td>
						<td align="center" onclick="tableSort(this, true)"
							class="td_class"><B>��Ʒ����</B></td>
					</tr>

					<c:forEach items="${productList}" var="item" varStatus="vs">
						<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
							<td align="center"><input type="radio" name="relation"
								index="0" productName="${item.productName}" am="${item.amount}"
								value="${item.id}" /></td>
							<td onclick="hrefAndSelect(this)" align="center">${depotpartBean.name}</td>
							<td onclick="hrefAndSelect(this)" align="center">${item.storageName}</td>
							<td onclick="hrefAndSelect(this)" align="center">${item.productName}</td>
							<td onclick="hrefAndSelect(this)" align="center">${item.productCode}</td>
							<td onclick="hrefAndSelect(this)" align="center">${item.amount}</td>
						</tr>
					</c:forEach>
				</table>

				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					align="center" class="table1">
					<tr>
						<td colspan="8" align="right"><c:if
							test="${!ProductList.firstPage}">
							<a
								href="../admin/das.do?method=queryProduct&page=previous&depotpartId=${depotpartBean.id}"><font
								color="blue"><img src="../images/preview.gif" border="0"></font></a>
						</c:if> <c:if test="${!ProductList.lastPage}">
							<a
								href="../admin/das.do?method=queryProduct&page=next&depotpartId=${depotpartBean.id}"><font
								color="blue"><img src="../images/next.gif" border="0"></font></a>
						</c:if></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>

	<tr height="10">
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td width="92%">
		<div align="right"><input type="button" class="button_class" style="visibility: hidden"
			value="&nbsp;�޸Ĳ�Ʒ����&nbsp;" onclick="modfiys()">&nbsp;&nbsp; <input
			type="button" class="button_class" onclick="backs()"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
		</td>
		<td width="8%"></td>
	</tr>

</table>

</form>
</body>
</html>
