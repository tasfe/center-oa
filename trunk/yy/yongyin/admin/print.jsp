<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="my" uri="/tags/elFunction"%>
<html>
<head>
<title>�ⵥ</title>
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
				<font size="6">
				��&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;��
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
						<td style="height: 27px" align="center"><font size=5>
						�������յ�</font></td>
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
						<td><table class="border1"><tr><td>�ջ����ͣ�${my:get('outType', out.outType)}</td></tr></table></td>
						<td><table class="border1"><tr><td>�������ţ�${out.tranNo}</td></tr></table></td>
						<td><table class="border1"><tr><td>��ⵥ�ţ�${out.fullId}</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td><table class="border1"><tr><td>�ջ����ڣ�${out.outTime}</td></tr></table></td>
						<td><table class="border1"><tr><td>�������ڣ�&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr></table></td>
						<td><table class="border1"><tr><td>��Ӧ�̣�${out.customerName}</td></tr></table></td>
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
						<td width="10%"><table class="border1"><tr><td align="center">�ֱ�</td></tr></table></td>
						<td width="15%"><table class="border1"><tr><td align="center">��������</td></tr></table></td>
						<td width="8%"><table class="border1"><tr><td align="center">��λ</td></tr></table></td>
						<td width="15%"><table class="border1"><tr><td align="center">��������</td></tr></table></td>
						<td width="15%"><table class="border1"><tr><td align="center">��������</td></tr></table></td>
					</tr>
					
					<c:forEach items="${baseList}" var="item" varStatus="vs">
					<tr class="content2">
						<td><table class="border1"><tr><td align="center">${vs.index + 1}</td></tr></table></td>
						<td><table class="border1"><tr><td>${item.productName}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${out.depotpartName}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.amount}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center">${item.unit}</td></tr></table></td>
						<td><table class="border1"><tr><td align="center"></td></tr></table></td>
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
			<table width="100%" cellspacing='0' cellpadding="0" >
			<tr>
			<td>
			�ջ��ˣ�&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  </td> 
			<td >�����ˣ�&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td align="center" width="40%">�ֿ����ܣ�&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
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

