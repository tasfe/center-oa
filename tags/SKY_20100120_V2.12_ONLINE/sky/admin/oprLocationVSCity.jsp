<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="���¹�ϽȨ����" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">
function addBean()
{
	submit('ȷ�����¹�ϽȨ����?');
}

function clickPro(obj)
{
    var citys = document.getElementsByName('city');
    
    for (var i = 0; i < citys.length; i++)
    {
        if (citys[i].parentid && citys[i].parentid == obj.value)
        {
            citys[i].checked = obj.checked;
        }
    }
}

function load()
{
    loadForm();
    
    var pros = document.getElementsByName('pro');
    
    var citys = document.getElementsByName('city');
    
    for (var i = 0; i < pros.length; i++)
    {
        pros[i].checked = true;
    }
    
    for (var i = 0; i < citys.length; i++)
    {
        if (!citys[i].checked)
        {
            $O(citys[i].parentid).checked = false;
        }
    }
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../admin/location.do" method="post"><input
	type="hidden" name="method" value="addLocationVSCity">
<input type="hidden" name="locationId" value="${bean.id}"/>
 <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">�ֹ�˾����</span> &gt;&gt; ���¹�ϽȨ����</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>��ϽȨ������Ϣ����${bean.name}��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:table cells="1">
			<p:cells id="selects" celspan="0">
			<table id="mselect" class='table0' border="1">
			<c:forEach items="${provinceList}" var="item">
			<tr>
			<td><input type="checkbox" value="${item.id}" name="pro" id="${item.id}" onclick="clickPro(this)">${item.name}</td>
			<td>
			<table>
			<tr><td>
			<c:forEach items="${areaMap[item.id]}" var="subItem">
			<input name="city" type="checkbox" value="${subItem.id}" 
			${cityMap[subItem.id] == 1 ? "checked=true" : ""} parentid="${item.id}">
			${subItem.name}
			</c:forEach>
			</td></tr>
			</table>
			</td>
			</tr>
			</c:forEach>
			</table>
			</p:cells>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message/>
</p:body></form>
</body>
</html>

