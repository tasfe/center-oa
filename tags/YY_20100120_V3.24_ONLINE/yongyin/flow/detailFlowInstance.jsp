<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="����ʵ��" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/key.js"></script>

<script language="javascript">
function load()
{
	$v('new_TR');
}

function approve()
{
	if (formCheck() && window.confirm('ȷ��ͨ��������ʵ��?'))
	{
		$('oprMode').value = '0';
		formEntry.submit();
	}
}

function reject()
{
	if (formCheck() && window.confirm('ȷ�����ش�����ʵ��?'))
	{
		$('oprMode').value = '1';
		formEntry.submit();
	}
}

function del()
{
	$('aatt').innerHTML = '';
	
	$('delAccachment').value = '1';
	
	$v('new_TR', true);
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry"
	action='../flow/flow.do?method=processFlowInstance' method="post"
	enctype="multipart/form-data"><input type="hidden" name="id"
	value="${bean.id}"> <input type="hidden" name="delAccachment"
	value="0"> <input type="hidden" name="oprMode" value="0">
<p:navigation height="22">
	<td width="550" class="navigation">����ʵ����ϸ</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>����ʵ����Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:table cells="2">
			<p:cells title="����" celspan="2">
			${bean.id}
			</p:cells>

			<p:cells title="����" celspan="2">
			${bean.title}
			</p:cells>

			<p:cell title="���̶���">
			${bean.flowName}
			</p:cell>

			<p:cell title="�ύʱ��">
			${bean.logTime}
			</p:cell>

			<p:cell title="��ǰ����">
			${bean.currentTokenName}
			</p:cell>

			<p:cell title="��ֹʱ��">
			${bean.endTime}
			</p:cell>

			<p:cells title="�ύ��" celspan="2">
			${bean.userName}
			</p:cells>

			<p:cells title="��ǰ������" celspan="2">
				<c:forEach items="${belong}" var="item">
			[${item.loginName}/${item.userName}]&nbsp;
			</c:forEach>
			</p:cells>

			<p:cells celspan="2" title="����">
				<span id="aatt"> <a title="������ظ���" target="_blank"
					href="../flow/flow.do?method=downFlowInstanceAttchment&id=${bean.id}">${bean.fileName}</a>

				<c:if test="${approve == '1'}">
					<a title="ɾ������" href="javascript:del()"> <img
						src="../images/del.gif" border="0" height="15" width="15"></a>
				</c:if> </span>

			</p:cells>

			<p:cells celspan="2" title="�¸���" id="new">
				<input type="file" name="attchment" size="40" class="button_class"
					contenteditable="false">&nbsp;
			<font color="blue">����ʹ��ѹ���ļ�,������������</font>
			</p:cells>

			<p:cells celspan="2" title="����">
			${bean.description}
			</p:cells>
		</p:table>
	</p:subBody>

	<p:tr />

	<p:subBody width="100%">
		<table width="100%" border="0" cellspacing='1' id="tables">
			<tr align="center" class="content0">
				<td width="15%" align="center">ʱ��</td>
				<td width="15%" align="center">��������</td>
				<td width="15%" align="center">�󻷽�</td>
				<td width="10%" align="center">������ʽ</td>
				<td width="10%" align="center">������</td>
				<td width="35%" align="center">��ע</td>
			</tr>

			<c:forEach items="${bean.logsVO}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<td align="center">${item.logTime}</td>
					<td align="center">${item.tokenName}</td>
					<td align="center">${item.nextTokenName}</td>
					<td align="center">${item.oprMode == 0 ? "ͨ��" : "<font
					color=red>����</font>"}</td>
					<td align="center">${item.userName}</td>
					<td align="center">${item.opinion}</td>
				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<c:if test="${approve == '1'}">
		<p:subBody width="100%">
			<p:table cells="1">
				<p:cell title="�������">
					<textarea oncheck="notNone;" name="opinion" cols=80 rows=3></textarea>
				</p:cell>
			</p:table>
		</p:subBody>

		<p:tr />

	</c:if>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><c:if test="${approve == '1'}">
			<input type="button" class="button_class" name="add_b"
				style="cursor: pointer" value="&nbsp;&nbsp;ͨ ��&nbsp;&nbsp;"
				onclick="approve()">&nbsp;&nbsp;
		<input type="button" class="button_class" name="reject_b"
				style="cursor: pointer" value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"
				onclick="reject()">&nbsp;&nbsp;
		</c:if> <input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

