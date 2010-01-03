<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�ɹ����б�" />
<script language="JavaScript" src="../js/prototype.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/title_div.js"></script>
<script language="javascript">
function querys()
{
	$('method').value = 'queryStock';
	formEntry.submit();
}

function addBean()
{
	document.location.href = '../stock/stock.do?method=preForAddStock';
}

function press()
{
	if (window.common.getEvent().keyCode == 13)
	{
		querys();
	}
}

function resets()
{
	formEntry.reset();

	$('ids').value = '';
	setSelectIndex($('status'), 0);
	setSelectIndex($('pay'), 0);
	setSelectIndex($('locationId'), 0);
}

function load()
{
	loadForm();

	$f('ids');

	$('update').value = '';
}

function del(id)
{
	if (window.confirm('ȷ��ɾ���ɹ���?'))
	{
		$('method').value = 'delStock';
		$('id').value = id;
		formEntry.submit();
	}
}

function reject(id)
{
	if (window.confirm('ȷ�����ش˲ɹ���?'))
	{
		var sss = window.prompt('�����벵�����۵�ԭ��', '');

		$('reason').value = sss;

		if (!(sss == null || sss == ''))
		{
			$('method').value = 'updateStockStatus';
			$('id').value = id;
			$('reject').value = '1';
			$('pass').value = '';
			formEntry.submit();
		}
		else
		{
			alert('�����벵��ԭ��');
		}
	}
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

function passTo(id)
{
	if (window.confirm('ȷ��ͨ���˲ɹ���?'))
	{
		$('method').value = 'updateStockStatus';
		$('id').value = id;
		$('reject').value = '';
		$('pass').value = '1';
		formEntry.submit();
	}
}

function sub(id)
{
	if (window.confirm('ȷ���ύ�˲ɹ���?'))
	{
		$('method').value = 'updateStockStatus';
		$('id').value = id;
		$('reject').value = '';
		$('pass').value = '1';
		formEntry.submit();
	}
}

function update(id)
{
	$('method').value = 'findStock';
	$('id').value = id;
	$('stockAskChange').value = '';
	$('process').value = '';
	$('update').value = '1';
	formEntry.submit();
}

function ask(id)
{
	$('method').value = 'findStock';
	$('id').value = id;
	$('stockAskChange').value = '';
	$('process').value = '1';
	$('update').value = '';
	formEntry.submit();
}

function askChange(id)
{
	$('method').value = 'findStock';
	$('id').value = id;
	$('stockAskChange').value = '1';
	$('process').value = '';
	$('update').value = '';
	formEntry.submit();
}

function end(id)
{
	if (window.confirm('ȷ���ɹ�����Ʒ�ѵ��������ɹ������Զ�������ⵥ?'))
	{
		$('method').value = 'endStock';
		$('id').value = id;
		formEntry.submit();
	}
}

function pay(id, status)
{
	var tipMap = {"1": "ȷ����׼�ɹ����ܵĲɹ���������?", "2": "ȷ�����ܾ�������ɹ�����?", "3": "ȷ�����زɹ�����ɹ���������?"};

	if (window.confirm(tipMap[status]))
	{
		$('method').value = 'payStock';
		$('id').value = id;
		$('payStatus').value = status;
		formEntry.submit();
	}
}

function out(id)
{
	document.location.href = '../stock/stock.do?method=findStock&id=' + id + "&out=1";
}

function exports()
{
	document.location.href = '../stock/stock.do?method=exportStock';
}
</script>

</head>
<body class="body_class" onload="load()"
	onkeypress="tooltip.bingEsc(event)">
<form name="formEntry" action="../stock/stock.do"><input
	type="hidden" name="method" value="queryStock"> <input
	type="hidden" value="1" name="firstLoad"> <input type="hidden"
	value="" name="id"> <input type="hidden" value="" name="pass">
<input type="hidden" value="" name="payStatus"> <input
	type="hidden" value="" name="reject"> <input type="hidden"
	value="" name="reason"> <input type="hidden" value=""
	name="update"><input type="hidden" value=""
	name="stockAskChange"> <input type="hidden" value=""
	name="process"> <p:navigation height="22">
	<td width="550" class="navigation">�ɹ������� &gt;&gt; �ɹ����б�</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center">��ʼʱ��</td>
				<td align="center" width="35%"><p:plugin name="alogTime"
					value="${alogTime}" /></td>
				<td align="center">����ʱ��</td>
				<td align="center" width="35%"><p:plugin name="blogTime"
					value="${blogTime}" /></td>
			</tr>

			<tr align=center class="content1">
				<td align="center">����</td>
				<td align="center" width="35%"><input type="text"
					onkeydown="press()" name="ids" value="${ids}"></td>
				<td align="center">״̬</td>
				<td align="center" width="35%"><select name="status"
					class="select_class" values="${status}">
					<option value="">--</option>
					<option value="0">����</option>
					<option value="1">�ύ</option>
					<option value="2">����</option>
					<option value="3">������ͨ��</option>
					<option value="4">�˼�Աͨ��</option>
					<option value="5">�ɹ�����ͨ��</option>
					<option value="6">�ɹ�����ͨ��</option>
					<option value="7">�ɹ���</option>
					<option value="8">�ɹ�����</option>
				</select></td>
			</tr>

			<tr align=center class="content0">
				<td align="center">�ɹ�����</td>
				<td align="center" width="35%"><select name="locationId"
					class="select_class" values="${locationId}">
					<option value="">--</option>
					<c:forEach items="${locations}" var="item">
						<option value="${item.id}">${item.locationName}</option>
					</c:forEach>
				</select></td>
				<td align="center">����</td>
				<td align="center" width="35%"><select name="pay"
					class="select_class" values="${pay}">
					<option value="">--</option>
					<option value="0">δ����</option>
					<option value="1">�Ѹ���</option>
					<option value="2">��������</option>
					<option value="3">���벵��</option>
				</select></td>
			</tr>

			<tr align=center class="content0">
				<td align="center">�Ƿ�����</td>
				<td align="center" width="35%"><select name="over"
					class="select_class" values="${over}">
					<option value="">--</option>
					<option value="0">δ����</option>
					<option value="1">������</option>
				</select></td>
				<td align="right" colspan="2"><input type="button" id="b_query"
					class="button_class" value="&nbsp;&nbsp;�� ѯ&nbsp;&nbsp;"
					onclick="querys()">&nbsp;&nbsp; <input type="button"
					class="button_class" value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" id="b_reset"
					onclick="resets()"></td>
			</tr>
		</table>

	</p:subBody>


	<p:title>
		<td class="caption"><strong>�ɹ����б�</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="10%"><strong>����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="5%"><strong>�ɹ���</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="5%"><strong>״̬</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="5%"><strong>�ɹ�����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="10%"><strong>��������</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this, true)"
					width="10%"><strong>�ϼƽ��</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="5%"><strong>����</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="10%"><strong>����ʱ��</strong></td>
				<td align="center" class="td_class" onclick="tableSort(this)"
					width="5%"><strong>����</strong></td>
			</tr>

			<c:forEach items="${list}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center" onclick="hrefAndSelect(this)"
						onMouseOver="showDiv('${item.id}')" onmousemove="tooltip.move()"
						onmouseout="tooltip.hide()"><a onclick="hrefAndSelect(this)"
						href="../stock/stock.do?method=findStock&id=${item.id}">
					${item.id} </a></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.userName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('stockStatus',
					item.status)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.locationName}</td>
					<c:if test="${item.overTime == 0}">
						<td align="center" onclick="hrefAndSelect(this)"><font
							color=blue>${item.needTime}</font></td>
					</c:if>
					<c:if test="${item.overTime == 1}">
						<td align="center" onclick="hrefAndSelect(this)"><font
							color=red>${item.needTime}</font></td>
					</c:if>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.total)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:get('stockPay', item.pay)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.logTime}</td>
					<td align="center" onclick="hrefAndSelect(this)">
					<c:if test="${item.display == 0}">
						<c:if test="${user.role == 'COMMON'}">
							<a title="�ύ�ɹ���" href="javascript:sub('${item.id}')"> <img
								src="../images/realse.gif" border="0" height="15" width="15"></a>

							<a title="�޸Ĳɹ���" href="javascript:update('${item.id}')"> <img
								src="../images/edit.gif" border="0" height="15" width="15"></a>

							<a title="ɾ���ɹ���" href="javascript:del('${item.id}')"> <img
								src="../images/del.gif" border="0" height="15" width="15"></a>
						</c:if>

						<c:if test="${user.role != 'COMMON'  && user.role != 'PRICE'}">
							<a title="����ͨ���ɹ���" href="javascript:passTo('${item.id}')"> <img id="img_${vs.index}"
								src="../images/realse.gif" border="0" height="15" width="15"></a>

							<a title="���زɹ���" href="javascript:reject('${item.id}')"> <img
								src="../images/reject.gif" border="0" height="15" width="15"></a>
						</c:if>

						<c:if test="${user.role == 'PRICE'}">
							<a title="�ɹ���ѯ��" href="javascript:ask('${item.id}')"> <img id="ask_img_${vs.index}"
								src="../images/change.gif" border="0" height="15" width="15"></a>

							<a title="����ͨ���ɹ���" href="javascript:passTo('${item.id}')"> <img
								src="../images/realse.gif" border="0" height="15" width="15"></a>

							<a title="���زɹ���" href="javascript:reject('${item.id}')"> <img
								src="../images/reject.gif" border="0" height="15" width="15"></a>
						</c:if>

						<c:if test="${user.role == 'STOCK'}">
							<a title="ѯ�۱䶯" href="javascript:askChange('${item.id}')"> <img
								src="../images/change.gif" border="0" height="15" width="15"></a>
							<c:if test="${item.status == 7}">
								<a title="�ɹ�����" href="javascript:end('${item.id}')"> <img id="end_img_${vs.index}"
									src="../images/end.gif" border="0" height="15" width="15"></a>
							</c:if>
						</c:if>
					</c:if> 
					
					<c:if test="${user.role == 'STOCK'}">
						<c:if test="${item.status == 7}">
							<a title="�ɹ�����" href="javascript:end('${item.id}')"> <img id="end_img_${vs.index}"
								src="../images/end.gif" border="0" height="15" width="15"></a>
						</c:if>

						<c:if test="${item.status == 8}">
							<c:if test="${item.pay == 0 || item.pay == 3}">
								<a title="���븶��" href="javascript:pay('${item.id}', '2')"> <img id="pay_img_${vs.index}"
									src="../images/pay.gif" border="0" height="15" width="15"></a>
							</c:if>
						</c:if>
					</c:if> 
					
					<c:if test="${user.role == 'ADMIN' && item.status == 8}">
						<a title="�ɹ����ɵ���" href="javascript:out('${item.id}')"> <img
							src="../images/change.gif" border="0" height="15" width="15"></a>
					</c:if> <c:if
						test="${user.role == 'MANAGER' && item.pay == 2 && user.locationID == '0'}">
						<a title="��׼����" href="javascript:pay('${item.id}', '1')"> <img id="pay_approve_img_${vs.index}"
							src="../images/pay.gif" border="0" height="15" width="15"></a>
						<a title="���ظ���" href="javascript:pay('${item.id}', '3')"> <img id="pay_reject_img_${vs.index}"
							src="../images/reject.gif" border="0" height="15" width="15"></a>
					</c:if>
					
					</td>
				</tr>
			</c:forEach>
		</table>

		<p:formTurning form="formEntry" method="queryStock"></p:formTurning>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		<c:if test="${user.role == 'MANAGER'}">
		<input type="button" class="button_class"
			id="b_export" style="cursor: pointer"
			value="&nbsp;&nbsp;�����ɹ���&nbsp;&nbsp;" onclick="exports()">
		</c:if>
			 <c:if test="${user.role == 'COMMON'}">
			<input type="button" class="button_class" name="adds"
				style="cursor: pointer" value="&nbsp;&nbsp;���Ӳɹ���&nbsp;&nbsp;"
				onclick="addBean()">
		</c:if>
		</div>
	</p:button>

	<p:message />

</p:body></form>
</body>
</html>

