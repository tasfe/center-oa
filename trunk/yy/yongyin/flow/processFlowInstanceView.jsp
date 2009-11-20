<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�鿴���̲���" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">

var cindex = -1;
function addBean(opr)
{
	submit('ȷ���ύ�����̵Ĳ�����?', null, lverify);
}

function lverify()
{
	var checkArr = document.getElementsByName('check_init');

	var isSelect = 0;

	var imap = {};

	for (var i = 0; i < checkArr.length; i++)
	{
		var obj = checkArr[i];

		var index = obj.value;

		if (obj.checked)
		{
			isSelect++;
			if ($('tokenType_' + i).value == '' )
			{
				alert('�鿴���Ͳ���Ϊ��');
				return false;
			}

			if ($$('tokenType_' + i)  != "2")
			{
				if ($$('processerId_' + i) == '')
				{
					alert('��ѡ�������');
					return false;
				}
			}
		}
	}

	if (isSelect == 0)
	{
		alert('��ѡ�������');
		return false;
	}

	return true;
}
function load()
{
	loadForm();

	init();
}

function init()
{
	var checkArr = document.getElementsByName('check_init');

	for (var i = 0; i < checkArr.length; i++)
	{
		var obj = checkArr[i];

		var index = obj.value;

		if (obj.checked)
		{
			$d('tokenType_' + index, false);
			$d('processer_' + index, false);
		}
		else
		{
			$('tokenType_' + index).value = '';
			$('processer_' + index).value = '';
			$d('tokenType_' + index);
			$d('processer_' + index);
		}
	}
}


var urlMap = 
{
"0" : "../admin/common.do?method=rptUser&firstLoad=1",
"1" : "../admin/common.do?method=rptRole",
"3" : "../admin/common.do?method=rptStaffer&firstLoad=1"
};

function selectProcesser(index)
{
	cindex = index;

	if ($$('tokenType_' +cindex) == "")
	{
		alert("��ѡ��鿴����");
		return;
	}

	if ($$('tokenType_' +cindex) == "2")
	{
		return;
	}
	
	window.common.modal(urlMap[$$('tokenType_' +cindex)]);
}

function getUserOrRole(oo)
{
	if (cindex != -1)
	{
		$("processer_" + cindex).value = oo.processerName;
		$("processerId_" + cindex).value = oo.value;
	}
}

function getStaffer(oo)
{
	if (cindex != -1)
	{
		$("processer_" + cindex).value = oo.processerName;
		$("processerId_" + cindex).value = oo.value;
	}
}

function change(index, obj)
{
	$('processer_' + index).value = '';
	$('processerId_' + index).value = '';
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../flow/flow.do" method="post"><input
	type="hidden" name="method" value="processFlowViewer">
	<input type="hidden" name="oprMode" value="">
	<input
	type="hidden" name="flowId" value="${id}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: hand"
		onclick="javascript:history.go(-1)">���̹���</span> &gt;&gt; �鿴���̲���</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>���̲�����Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:table cells="1">
			<p:cells celspan="2" title="����˵��">
			�鿴���ͷ�����<br>
			1����Ա�鿴�������̽��������ĳһ��Ա���Բ鿴<br>
			2�������ɫ�鿴�������̽���������������ؽ�ɫ�鿴,���˳�������Ա֮��<br>
			3��ȫ�����������˿��Բ鿴<br>
			4��ְԱ���Ǿ��������н�������ĳһְԱ���Բ鿴��ֻҪ��¼����Ա���������ְԱ�����Բ鿴
			</p:cells>
			
			<p:cells id="names" celspan="2" title="��������">${bean.name}</p:cells>

			<p:cells id="selects" celspan="2" title="���̲���">
				<table id="mselect">
					<c:forEach begin="0" end="9" var="item" >
					<tr>
						<td>
						<input type="checkbox" name="check_init" value="${item}" onclick="init()" ${view[item].id > 0 ? "checked='checked'" : ""}>
						�鿴����:
							<select class="select_class" name="tokenType_${item}" onchange="change(${item}, this)" values="${view[item].type}">
								<option value="">--</option>
								<option value="3">ְԱ</option>
								<option value="0">��Ա</option>
								<option value="1">�����ɫ</option>
								<option value="2">ȫ��</option>
							</select>&nbsp;
							�鿴��:<input
							type="text" name="processer_${item}" value="${view[item].processerName}" size="15" readonly="readonly">&nbsp;
							<input type="button" value="&nbsp;...&nbsp;" name="qout"
							class="button_class" onclick="selectProcesser(${item})">
							<input type="hidden" name="processerId_${item}" value="${view[item].processer}">
							</td>
					</tr>
					</c:forEach>
					
				</table>
			</p:cells>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		<input type="button" class="button_class"
			name="adds" style="cursor: pointer"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onclick="addBean(1)">&nbsp;&nbsp;
		<input type="button" class="button_class"
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

