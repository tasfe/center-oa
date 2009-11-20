<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="��֯����" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/tree.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/pop.js"></script>
<script language="JavaScript" src="../js/oa/org.js"></script>
<script language="javascript">

var shipList = JSON.parse('${shipList}');

var shipMap = JSON.parse('${mapJSON}');

function addBean()
{
	$l('../admin/org.do?method=preForAddOrg');
}

var currentId = null;

treeview.prototype.onnodeclick = function(me)
{
    currentId = me.id;
    
    return false;
}

function checkBean()
{
    if (currentId == null)
    {
        alert('��ѡ���λ');
        return '';
    }
    
    if (currentId == '0')
    {
        alert('���»᲻�ܲ���');
        return '';
    }
    
    return currentId;
}

function updateBean()
{
    var id = checkBean();
    
    if (id != '')
    {
        $l('../admin/org.do?method=findOrg&update=1&id=' + id);
    }
}

function delBean()
{
    var id = checkBean();
    
    if (id != '')
    {
        if (window.confirm('ȷ��ɾ����λ?'))
        {
            $l('../admin/org.do?method=delOrg&id=' + id);
        }
    }
}

var tv = new treeview("treeview","../js/tree",false, false);

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply"><p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;">��֯����</span></td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>��֯(��λ)�ṹ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="80%">
		<p:table cells="1">
			<p:cells celspan="0">
				<span style="cursor: pointer;" onclick="allSelect(true)">ȫ��չ��</span> | <span
					style="cursor: pointer;" onclick="allSelect(false)">ȫ������</span>
				<br>
				<br>
				<div id=tree align="left"></div>
			</p:cells>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			id="ok_b" style="cursor: pointer"
			value="&nbsp;&nbsp;���Ӹ�λ&nbsp;&nbsp;" onclick="addBean()">&nbsp;&nbsp;<input
			type="button" class="button_class" id="update_b"
			style="cursor: pointer" value="&nbsp;&nbsp;�޸ĸ�λ&nbsp;&nbsp;"
			onclick="updateBean()">&nbsp;&nbsp;<input type="button"
			class="button_class" id="del_b" style="cursor: pointer"
			value="&nbsp;&nbsp;ɾ����λ&nbsp;&nbsp;" onclick="delBean()"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

