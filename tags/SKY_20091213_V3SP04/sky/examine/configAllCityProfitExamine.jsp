<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="��������ɽ�������" cal="true" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/pop.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="javascript">

<c:forEach items="${subs}" var="item">
var listj_${item.id} = JSON.parse('${listJSONMap[item.id]}');
</c:forEach>

var llistj = JSON.parse('${llistJSON}');

function addBean(opr)
{
	submit('ȷ����ѡ������򵽿��˵�ְԱ?', null, null);
}

function load()
{
	loadForm();

	init();
}

function init()
{
    var arr = llistj;
    <c:forEach items="${subs}" var="item">
    var arr1 = listj_${item.id};
	for (var i = 0 ; i < arr.length; i++)
	{
	   var obj = $O('i' + arr[i].cityId + '${item.id}');
	   
	   if (containInList(arr[i].cityId, arr1))
	   {
	       obj.checked = true;
	   }
	}
	</c:forEach>
}

function containInList(id, listj)
{
    for(var i = 0; i < listj.length; i++)
    {
        if (listj[i] == id)
        {
            return true;
        }
    }
    
    return false;
}

function refush()
{
	$l('../examine/examine.do?method=queryAllSubCityProfitExamine&pid=${parentBean.id}');
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../examine/examine.do" method="post"><input
	type="hidden" name="method" value="configAllCityProfitExamine"> <input type="hidden"
	name="pid" value="${parentBean.id}"> 
	<c:forEach items="${subs}" var="item">
        <input type="hidden" name="subItemId" value="${item.id}"> 
    </c:forEach>
	<p:navigation height="22">
	<td width="550" class="navigation">��������ɽ�������</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>��${parentBean.stafferName}��������Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="tables">
			<c:forEach items="${subs}" var="item">
			<tr align="center" class="content0">
			    <td width="10%">${item.stafferName}</td> 
			    <td align="left">
				<c:forEach items="${llits}" var="subItem" varStatus="va">
				<input id="i${subItem.cityId}${item.id}"  type="checkbox" name="city_${item.id}"  value="${subItem.cityId}" ${readonlyModal[item.id] ? 'disabled=disabled' : ''}>
				<a target="_blank" href='../examine/queryCityProfit.jsp?cityId=${subItem.cityId}' title="�����ѯ����������">
				${subItem.cityName}</a>&nbsp;
				<c:if test="${(va.index + 1) % 8 == 0}"><br></c:if>
				</c:forEach>
				</td>
			</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

    <c:if test="${readonly != 1}">
	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			name="adds" style="cursor: pointer"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onclick="addBean(1)">&nbsp;&nbsp;
			<input type="button" class="button_class"
			name="adds" style="cursor: pointer"
			value="&nbsp;&nbsp;ˢ ��&nbsp;&nbsp;" onclick="refush()">
		</div>
	</p:button>
	</c:if>

</p:body></form>
<p:message></p:message>
</body>
</html>

