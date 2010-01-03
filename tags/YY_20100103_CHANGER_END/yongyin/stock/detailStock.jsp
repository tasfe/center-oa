<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�ɹ���" />
<script language="JavaScript" src="../js/prototype.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/title_div.js"></script>

<script language="javascript">
function load()
{
	//tooltip.init();
}

var jmap = {};
<c:forEach items="${map}" var="item">
	jmap['${item.key}'] = "${item.value}";
</c:forEach>

function showDiv(id)
{
	if (jmap[id] != null && jmap[id] != '')
	tooltip.showTable(jmap[id]);
}

function out(id)
{
	if (window.confirm('ȷ���Ѵ˲ɹ��Ĳ�Ʒ����?'))
	{
		var sss = window.prompt('���������䵥�ţ�', '');

		$('tranNo').value = sss;

		if (!(sss == null || sss == ''))
		{
			$('method').value = 'stockItemChangeToOut';
			$('id').value = id;
			formEntry.submit();
		}
		else
		{
			alert('���������䵥��');
		}
	}
}
</script>

</head>
<body class="body_class" onload="load()" onkeydown="tooltip.bingEsc(event)">
<form action="../stock/stock.do" name="formEntry">
<input type="hidden" name="method" value="">
<input type="hidden" name="tranNo" value="">
<input type="hidden" name="id" value="">
<p:navigation height="22">
	<td width="550" class="navigation">�ɹ�����ϸ</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>�ɹ�����Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:table cells="2">
			<p:cell title="����">
			${bean.id}
			</p:cell>

			<p:cell title="�ɹ���">
			${bean.userName}
			</p:cell>

			<p:cell title="����">
			${bean.locationName}
			</p:cell>

			<p:cell title="״̬">
			${my:get('stockStatus', bean.status)}
			</p:cell>

			<p:cell title="¼��ʱ��">
			${bean.logTime}
			</p:cell>

			<p:cell title="�赽��ʱ��">
			${bean.needTime}
			</p:cell>

			<p:cell title="����">
			${bean.flow}
			</p:cell>

			<p:cell title="�ܼƽ��">
			${my:formatNum(bean.total)}
			</p:cell>

			<p:cells celspan="2" title="�쳣״̬">
			${my:get('stockExceptStatus', bean.exceptStatus)}
			</p:cells>

			<p:cells celspan="2" title="��ע">
			${bean.description}
			</p:cells>
		</p:table>
	</p:subBody>

	<p:tr />

	<p:subBody width="100%">
		<table width="100%" border="0" cellspacing='1' id="tables">
			<tr align="center" class="content0">
				<td width="15%" align="center">�ɹ���Ʒ</td>
				<td width="10%" align="center">�ɹ�����</td>
				<td width="10%" align="center">��ǰ����</td>
				<td width="5%" align="center">�Ƿ�ѯ��</td>
				<td width="10%" align="center">�ο��۸�</td>
				<td width="10%" align="center">ʵ�ʼ۸�</td>
				
				<c:if test="${user.role != 'COMMON' && user.role != 'MANAGER'}">
				<td width="15%" align="center">��Ӧ��</td>
				</c:if>
				<td width="5%" align="center">�Ƿ����</td>
				<td width="10%" align="center">�ϼƽ��</td>
				<c:if test="${out == 1}">
				<td width="10%" align="center">����</td>
				</c:if>
			</tr>

			<c:forEach items="${bean.itemVO}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<td align="center" style="cursor: pointer;"
					onMouseOver="showDiv('${item.id}')" onmousemove="tooltip.move()" onmouseout="tooltip.hide()"
					><a href="../admin/product.do?method=findProduct&productId=${item.productId}&detail=1">${item.productName}</a></td>

					<td align="center">${item.amount}</td>
					
					<td align="center">${item.productNum}</td>

					<td align="center">${item.status == 0 ? "��" : "��"}</td>

					<td align="center">${my:formatNum(item.prePrice)}</td>

					<td align="center">${my:formatNum(item.price)}</td>

					<c:if test="${user.role != 'COMMON' && user.role != 'MANAGER'}">
					<td align="center">${item.providerName}</td>
					</c:if>

					<td align="center">${item.hasRef == 0 ? "<font color=red>��</font>" : "��"}</td>

					<td align="center">${my:formatNum(item.total)}</td>

					<c:if test="${out == 1}">
						<td align="center">
						<c:if test="${item.hasRef == 0}">
						<a title="�ɹ�����" href="javascript:out('${item.id}')"> <img
									src="../images/change.gif" border="0" height="15" width="15"></a>
						</td>
						</c:if>
					</c:if>
				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:tr />

	<p:subBody width="100%">
		<table width="100%" border="0" cellspacing='1' id="tables">
			<tr align="center" class="content0">
				<td width="10%" align="center">������</td>
				<td width="10%" align="center">��������</td>
				<td width="10%" align="center">ǰ״̬</td>
				<td width="10%" align="center">��״̬</td>
				<td width="45%" align="center">���</td>
				<td width="15%" align="center">ʱ��</td>
			</tr>

			<c:forEach items="${logs}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<td align="center">${item.actor}</td>

					<td align="center">${item.oprModeName}</td>

					<td align="center">${item.preStatusName}</td>

					<td align="center">${item.afterStatusName}</td>

					<td align="center">${item.description}</td>

					<td align="center">${item.logTime}</td>

				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
	</p:button>

	<tr>
		<td colspan='2' align="center"><FONT color="blue">${MESSAGE_INFO}</FONT><FONT
			color="red">${errorInfo}</FONT></td>
	</tr>
</p:body></form>
</body>
</html>

