<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="����Ԥ����" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">

var g_title = 'Ԥ����';

function addBean()
{
	submit(getTip());
}

function getTip()
{
    if(isNone(document.getElementById('id').value))
    {
        return 'ȷ������' + g_title + '?';
    }
    else
    {
        return 'ȷ���޸�' + g_title + '?';
    }
}

function load()
{
    if(isNone(document.getElementById('id').value))
    {
        document.getElementById('navigation').innerHTML = '����' + g_title;
    }
    else
    {
        document.getElementById('navigation').innerHTML = '�޸�' + g_title;
    }
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../budget/budget.do"><input
	type="hidden" name="method" value="addOrUpdateFeeItem"><input id="id"
    type="hidden" name="id" value="${bean.id}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">Ԥ�������</span> &gt;&gt; <font id="navigation">����Ԥ����</font></td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>Ԥ���������Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.budget.bean.FeeItemBean" opr="1"/>

		<p:table cells="1">

			<p:pro field="name" />
			<input type=text style="display: none;" size="1">

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message/>
</p:body></form>
</body>
</html>
