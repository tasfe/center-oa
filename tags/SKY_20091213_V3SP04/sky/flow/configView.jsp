<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="�������̲���" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">

function addBean()
{
    var msg = 'ȷ���������̲���?';
    
    if ($$('type') == "")
    {
        msg = 'ȷ��ж�����̲���?';
    }
    else
    {
        if ($O('processerName').value == '')
        {
            alert('��ѡ�������');
            return false;
        }
    }
    
	submit(msg, null, lverify);
}

function lverify()
{
	return true;
}
function load()
{
	loadForm();
}

var urlMap = 
{
"0" : "../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1",
"1" : "../admin/pop.do?method=rptQueryGroup&load=1&selectMode=1",
"2" : "",
"999" : ""
};

function selectProcesser()
{
	if ($$('type') == "")
	{
		alert("��ѡ��������");
		return;
	}

	window.common.modal(urlMap[$$('type')]);
}

function getGroup(oo)
{
	getStaffers(oo);
}

function getStaffers(oo)
{
	var item = oo[0];
    $O("processerName").value = item.pname;
    $O("processer").value = item.value;
}

function change(index, obj)
{
	$O('processerName').value = '';
	$O('processer').value = '';
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../flow/flow.do" method="post"><input
	type="hidden" name="method" value="configView"> <input
	type="hidden" name="flowId" value="${flowId}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">���̹���</span> &gt;&gt; �������̻���</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>���̲��ģ�</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1">
			<p:cells celspan="2" title="����˵��">
			���Ĳ�����ͷ�����<br>
			1��ְԱ����������̽�����,�ɾ����ĳһְԱ���Բ���<br>
			2��Ⱥ�����������̽�����,��Ⱥ���ڵ�����һְԱ���Բ���<br>
			</p:cells>

			<p:cells id="selects" celspan="2" title="���̻���">
				<table id="mselect">
					<tr>
						<td>��������: <select class="select_class" name="type"
							values="${view.type}" onchange="change(0, this)">
							<option value="">--</option>
							<option value=0>ְԱ���</option>
							<option value=1>Ⱥ����</option>
						</select>&nbsp; ������:<input type="text" name="processerName"
							value="${view.processerName}" size="15" readonly="readonly"
							style="cursor: pointer;" onclick="selectProcesser(0)"
							title="ѡ�������">&nbsp; <input type="button"
							value="&nbsp;...&nbsp;" name="qout" title="ѡ�������"
							class="button_class" onclick="selectProcesser(0)"> <input
							type="hidden" name="processer" value="${view.processer}">&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</p:cells>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			name="adds" style="cursor: pointer"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onclick="addBean()">&nbsp;&nbsp;
		<input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

