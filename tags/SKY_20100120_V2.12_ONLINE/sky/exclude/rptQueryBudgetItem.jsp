<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="Ԥ����" />
<base target="_self">
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function querys()
{
}

function sures()
{
    var opener = window.common.opener();
    
    var oo = getRadio("beans");
    
    if (oo && oo.length == 0)
    {
        alert('��ѡ��Ԥ����');
        return;
    }
    
    if (oo)
    opener.getBudgetItem(oo);
    
    closes();
}

function closes()
{
	opener = null;
	window.close();
}

function load()
{
	loadForm();
}


function closesd()
{
    var opener = window.common.opener();
    
    opener = null;
    window.close();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../admin/pop.do" method="post"><input
	type="hidden" name="method" value="rptQueryStaffer"><input
	type="hidden" value="1" name="load"><input
    type="hidden" value="${selectMode}" name="selectMode"> <p:navigation
	height="22">
	<td width="550" class="navigation">Ԥ�������</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
	<p:title>
		<td class="caption"><strong>Ԥ���</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
			    <td align="center" width="5%">ѡ��</td>
				<td align="center"><strong>Ԥ����</strong></td>
				<td align="center"><strong>Ԥ���ܶ�</strong></td>
				<td align="center"><strong>�Ѿ�ʹ��</strong></td>
				<td align="center"><strong>ʣ����</strong></td>
			</tr>

			<c:forEach items="${beanList}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
				    <td align="center"><input type="radio" name="beans" pmoney="${item.sremainMonery}"
                        pname="${item.feeItemName}" value="${item.id}" ${vs.index== 0 ? "checked" : ""}/></td>
					<td align="center" onclick="hrefAndSelect(this)">${item.feeItemName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.sbudget}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.srealMonery}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.sremainMonery}</td>
				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		<input type="button" class="button_class"
            id="adds" style="cursor: pointer" value="&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;"
            onclick="sures()">&nbsp;&nbsp;
		<input type="button" class="button_class"
            value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onClick="closesd()" id="clo">&nbsp;&nbsp;
		</div>
	</p:button>

	<p:message />

</p:body></form>
</body>
</html>

