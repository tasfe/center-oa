<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="my" uri="/tags/elFunction"%>
<html>
<head>
<title>��ӡ������</title>
<link href="../css/self.css" type=text/css rel=stylesheet>
<script language="javascript">
function pagePrint()
{
	document.getElementById('ptr').style.display = 'none';
	window.print();
	document.getElementById('ptr').style.display = 'block';
}
</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="na">
	<tr>
		<td height="6" >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center">
				<font size="6"><b>
				��&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;��
				</b>
				</font></td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<table width="90%" border="0" cellpadding="0" cellspacing="0"
	align="center">
	<tr>
		<td colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>

			</tr>
			<tr>
				<td align="center">
				<table width="100%" border="0" cellspacing="2">
					<tr>
						<td style="height: 27px" align="center"><font size=5><b>
						������</b></font></td>
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
		<td colspan='2' align='center'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
			<td>
			<table width="100%" cellspacing='0' cellpadding="0" >
			<tr><td>
			�Ʊ����ڣ�${year} / ${month} / ${day} </td> 
			<td align="right">ҳ�Σ�&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
			</table>
			</td>
			</tr>
			
			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0"  class="border">
					<tr class="content2">
						<td><table class="border1"><tr><td>�ջ����ͣ�${out.outType == 0 ? "���۳���" : "��������"}</td></tr></table></td>
						<td><table class="border1"><tr><td>���۵��ţ�${out.fullId}</td></tr></table></td>
						<td><table class="border1"><tr><td>�ֱ�${out.depotpartName}</td></tr></table></td>
						<td><table class="border1"><tr><td>���䷽ʽ��${tss1.name} &gt;&gt; ${tss.name}</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="3"><table class="border1"><tr><td>�ͻ����ƣ�${out.customerName}</td></tr></table></td>
						<td><table class="border1"><tr><td>��ϵ�ˣ�${out.connector}</td></tr></table></td>
					</tr>
					
					<tr class="content2">
						<td colspan="3"><table class="border1"><tr><td>�ͻ���ַ��${out.customerAddress}</td></tr></table></td>
						<td><table class="border1"><tr><td>��ϵ��ʽ��${out.connector}</td></tr></table></td>
					</tr>
				</table>
				</td>
			</tr>
			
			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0"  class="border2">
					<tr class="content2">
						<td width="8%"><table class="border1"><tr><td align="center">���</td></tr></table></td>
						<td width="30%"><table class="border1"><tr><td align="center">Ʒ��</td></tr></table></td>
						<td width="15%"><table class="border1"><tr><td align="center">��������</td></tr></table></td>
						<td width="8%"><table class="border1"><tr><td align="center">��λ</td></tr></table></td>
						<td width="20%"><table class="border1"><tr><td align="center">��ע</td></tr></table></td>
					</tr>
					
					<c:forEach items="${baseList}" var="item" varStatus="vs">
					<tr class="content2">
						<td><table class="border1"><tr><td align="center">${vs.index + 1}</td></tr></table></td>
						<td><table class="border1"><tr><td>${item.productName}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.amount}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.unit}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
					</tr>
					</c:forEach>
					
					<c:forEach varStatus="vs" begin="1" end="${(4 - my:length(baseList)) > 0 ? (4 - my:length(baseList)) : 0}">
					<tr class="content2">
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
					</tr>
					</c:forEach>
				</table>
				</td>
			</tr>
			
			<tr>
				<td height="15"></td>
			</tr>


			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0">
					<tr>
						<td>
						�����ˣ�&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>�����ˣ�&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>�ͻ���&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>

					
					<tr>
						<td height="15"></td>
					</tr>

					<tr>
						<td colspan="3" align="center"><b>��һ��
						�ֿ�����������&nbsp;&nbsp;&nbsp;�ڶ��� ����������������
						&nbsp;&nbsp;&nbsp;������ �ͻ�����������<b></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>
	
	<tr id="ptr">
		<td width="92%">
		<div align="right"><input type="button" name="pr"
			class="button_class" onclick="pagePrint()"
			value="&nbsp;&nbsp;�� ӡ&nbsp;&nbsp;"></div>
		</td>
	</tr>
</table>
</body>
</html>

