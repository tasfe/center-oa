<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="��Ϊ��Ԥ�ȼ��仯" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('ȷ����Ϊ��Ԥ�ȼ��仯?');
}

function selectCus()
{
    window.common.modal('../admin/pop.do?method=rptQueryAllCustomer&load=1');
}

function getCustomer(obj)
{
    $O('cid').value = obj.value;
    $O('cname').value = obj.pname;
    $O('oldcval').value = obj.pval;
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../credit/customer.do"><input
	type="hidden" name="method" value="interposeCredit">
<input type="hidden" name="cid" value=""> 
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">���Ʋ�Ʒ����</span> &gt;&gt; ��Ϊ��Ԥ�ȼ�</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>��Ϊ��Ԥ�ȼ���</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1">

			<p:cell title="ѡ��ͻ�">
			    <input type="text" class="input_class" name="cname" id="cname" style="width: 240px">
			    <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectCus()">&nbsp;&nbsp; 
			</p:cell>
			
			<p:cell title="�ͻ�ԭʼ����">
                <input type="text" class="input_class" name="oldcval" id="oldcval" readonly="readonly">
            </p:cell>
            
            <p:cell title="���º�ͻ�����">
                <input type="text" class="input_class" name="newcval" id="newcval" oncheck="notNone;isFloat;range(0, 99)"> <font color="red">*</font>
            </p:cell>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
		 value="&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>
	
	<p:message2></p:message2>
</p:body></form>
</body>
</html>

