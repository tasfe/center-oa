<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="���Ӹ�λ" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/tree.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/oa/org.js"></script>
<script language="javascript">

var shipList = JSON.parse('${shipList}');

var shipMap = JSON.parse('${mapJSON}');

var levelMap = {};

function addBean()
{
    submit('ȷ�����Ӹ�λ����֯�ṹ?', null, checkBean);
}

function checkBean()
{
    var checks = document.getElementsByTagName('input');
    
    var count = 0;
    var cuLevel = $$('level');
    var id;
    for (var i = 0; i < checks.length; i++)
    {
        var ele = checks[i];
        if (ele.type == 'checkbox' && ele.checked)
        {
            count ++;
            
            if (cuLevel <= levelMap[ele.value])
            {
                alert('���ӵĸ�λ�����ܸ����ϼ���λ�ļ���');
                return false;
            }
            
            id = ele.value;
        }
    }
    
    if (count == 0)
    {
        alert('��ѡ���ϼ���λ');
        return false;
    }
    
    return true;
}


var tv = new treeview("treeview","../js/tree", true, false);

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../admin/org.do"><input
	type="hidden" name="method" value="addOrg"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">��֯����</span> &gt;&gt; ���Ӹ�λ</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>��λ������Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.publics.bean.PrincipalshipBean" />

		<p:table cells="1">
			<p:pro field="name" />

			<p:pro field="level">
				<option value="">--</option>
				<option value="1">1��</option>
				<option value="2">2��</option>
				<option value="3">3��</option>
				<option value="4">4��</option>
				<option value="5">5��</option>
				<option value="6">6��</option>
				<option value="7">7��</option>
				<option value="8">8��</option>
				<option value="9">9��</option>
				<option value="10">10��</option>
				<option value="11">11��</option>
			</p:pro>

			<p:cell title="�ϼ���λ">
			    <span style="cursor: pointer;" onclick="allSelect(true)">ȫ��չ��</span> | <span
                    style="cursor: pointer;" onclick="allSelect(false)">ȫ������</span>
                <br>
                <br>
				<div id=tree></div>
			</p:cell>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

