<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�ɹ���" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/title_div.js"></script>

<script language="javascript">
function load()
{
	loadForm();

	tooltip.init();
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

function askChange(id, name)
{
	if (window.confirm('ȷ���޸Ĳ�Ʒ[' + name + "]�Ĺ�Ӧ��?"))
	{
		var pid = $$('newProvide_' + id);
		document.location.href = '../stock/stock.do?method=stockItemAskChange&stockId=${bean.id}&id=' + id + "&providerId=" + pid;
	}
}

function passTO()
{
	if (window.confirm('ȷ��ͨ���˲ɹ���?'))
	{
		$('pass').value = '1';
		$('reject').value = '';
		formEntry.submit();
	}
}


function rejectToAsk()
{
	if (window.confirm('ȷ�����ش˲ɹ�����ѯ��?'))
	{
		var sss = window.prompt('�����벵����', '');

		$('reason').value = sss;

		if (!(sss == null || sss == ''))
		{
			$('pass').value = '';
			$('reject').value = '2';
			formEntry.submit();
		}
		else
		{
			alert('�����벵��ԭ��');
		}
	}
}
</script>

</head>
<body class="body_class" onload="load()" onkeydown="tooltip.bingEsc(event)">
<form name="formEntry" action="../stock/stock.do" method="post">
<input type="hidden" name="method" value="updateStockStatus">
<input type="hidden" name="id" value="${bean.id}">
<input type="hidden" name="pass" value="1">
<input type="hidden" value="" name="reason"> 
<input type="hidden" name="reject" value="1">
<p:navigation height="22">
	<td width="550" class="navigation">�ɹ�����ϸ</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

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
				<td width="10%" align="center">�Ƿ�ѯ��</td>
				<td width="10%" align="center">�ο��۸�</td>
				<td width="10%" align="center">ʵ�ʼ۸�</td>
				<td width="25%" align="center">��Ӧ��</td>
				<td width="5%" align="center">�ϼƽ��</td>
				<td width="10%" align="center">����</td>
			</tr>

			<c:forEach items="${bean.itemVO}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<td align="center" style="cursor: pointer;"
					onMouseOver="showDiv('${item.id}')" onmousemove="tooltip.move()" onmouseout="tooltip.hide()"
					>${item.productName}</td>

					<td align="center">${item.amount}</td>
					
					<td align="center">${item.productNum}</td>

					<td align="center">${item.status == 0 ? "��" : "��"}</td>

					<td align="center">${my:formatNum(item.prePrice)}</td>

					<td align="center">${my:formatNum(item.price)}</td>

					<td align="left">
					<select style="width: 250px;" id="newProvide_${item.id}" values="${item.providerId}">
					<c:forEach items="${map1[item.id]}" var="item1">
					<option value="${item1.providerId}">${item1.providerName}</option>
					</c:forEach>
					</select>
					</td>

					<td align="center">${my:formatNum(item.total)}</td>

					<td align="center">
					<a title="�䶯ϵͳѡ��ѯ��" href="javascript:askChange('${item.id}', '${item.productName}')"> <img
								src="../images/change.gif" border="0" height="15" width="15"></a>
					</td>
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
			name="sub1" style="cursor: pointer"
			value="&nbsp;&nbsp;ͨ ��&nbsp;&nbsp;" onclick="passTO()">&nbsp;&nbsp;
			<c:if test="${bean.type == 0}">
			<input type="button" class="button_class"
			name="sub1" style="cursor: pointer"
			value="&nbsp;&nbsp;���ص�ѯ��&nbsp;&nbsp;" onclick="rejectToAsk()">
			&nbsp;&nbsp;
			</c:if>
			<input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

