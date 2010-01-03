<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�������̶���" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">

var cindex = -1;
function addBean(opr)
{
	submit('ȷ���������̶���?', null, lverify);
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
			if ($('tokenName_' + i).value == '' || $('tokenType_' + i).value == '' )
			{
				alert('�������ƻ��ߴ������Ͳ���Ϊ��');
				return false;
			}

			if ($$('tokenType_' + i)  != "2")
			{
				if ($$('processerId_' + i) == '')
				{
					alert('��ѡ�񻷽ڲ�����');
					return false;
				}
			}

			if (imap[$('tokenName_' + i).value] == $('tokenName_' + i).value)
			{
				alert('�������Ʋ����ظ�');
				return false;
			}

			imap[$('tokenName_' + i).value] = $('tokenName_' + i).value;
		}
	}

	if (isSelect < 1)
	{
		alert('������Ҫ��������');
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
			$d('tokenName_' + index, false);
			$d('tokenType_' + index, false);
			$d('processer_' + index, false);
		}
		else
		{
			$('tokenName_' + index).value = '';
			$('tokenType_' + index).value = '';
			$('processer_' + index).value = '';
			$d('tokenName_' + index);
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
		alert("��ѡ��������");
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
<form name="addApply" action="../flow/flow.do" method="post"><input
	type="hidden" name="method" value="addFlowDefine">
	<input type="hidden" name="oprMode" value="">
	<input
	type="hidden" name="id" value="${bean.id}"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: hand"
		onclick="javascript:history.go(-1)">���̹���</span> &gt;&gt; �������̶���</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>���̶�����Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.centet.yongyin.bean.FlowDefineBean" />

		<p:table cells="1">
			<p:cells celspan="2" title="����˵��">
			�������ͷ�����<br>
			1����Ա������������еľ��廷������ĳһ��¼�˳е�<br>
			2�������ɫ������������еľ��廷��������������ؽ�ɫ����,���˳�������Ա֮��<br>
			3��ȫ�����������˿��Դ���һ�����ڿ�ʼ�Ļ���<br>
			4��ְԱ���Ǿ��������еľ��廷������ĳһְԱ�е���ֻҪ��¼����Ա���������ְԱ�����Դ���
			</p:cells>

			<p:pro field="name" innerString="size=40"/>

			<p:pro field="description"  innerString="cols=80 rows=3" />
			
			<p:cells id="selects" celspan="2" title="���̻���">
				<table id="mselect">
					<tr>
						<td>
							<input type="checkbox" name="check_init" value="0" onclick="init()" checked="checked">��ʼ����
							����:<input
							type="text" name="tokenName_0" value="" size="20" >&nbsp;
							��������:
							<select class="select_class" name="tokenType_0" values="2" onchange="change(0, this)">
								<option value="">--</option>
								<option value="3">ְԱ</option>
								<option value="0">��Ա</option>
								<option value="1">�����ɫ</option>
								<option value="2">ȫ��</option>
							</select>&nbsp;
							������:<input
							type="text" name="processer_0" value="" size="15" readonly="readonly">&nbsp;
							<input type="button" value="&nbsp;...&nbsp;" name="qout"
							class="button_class" onclick="selectProcesser(0)">
							<input type="hidden" name="processerId_0" value="">
							</td>
					</tr>
					
					<c:forEach begin="1" end="${param.tokens - 1}" var="item" >
					<tr>
						<td><input type="checkbox" name="check_init" value="${item}" onclick="init()">����${item + 1}��
							����:<input
							type="text" name="tokenName_${item}" value="" size="20">&nbsp;
							��������:
							<select class="select_class" name="tokenType_${item}" onchange="change(${item}, this)">
								<option value="">--</option>
								<option value="3">ְԱ</option>
								<option value="0">��Ա</option>
								<option value="1">�����ɫ</option>
								<option value="2">ȫ��</option>
							</select>&nbsp;
							������:<input
							type="text" name="processer_${item}" value="" size="15" readonly="readonly">&nbsp;
							<input type="button" value="&nbsp;...&nbsp;" name="qout"
							class="button_class" onclick="selectProcesser(${item})">
							<input type="hidden" name="processerId_${item}" value="">
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

	<p:message/>
</p:body></form>
</body>
</html>

