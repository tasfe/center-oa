<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="���Ӵ�λ" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/string.js"></script>
<c:set var="dis" value='${param.add != "1" ? "�޸�" : "����"}' />
<script language="javascript">
function addApplys()
{
	submit('ȷ��${dis}��λ[' + $$('name') + ']');
}

function load()
{
	loadForm();
	$f('name');
}

function selectProduct()
{
	var locationIdI = '${currentLocationId}';
	window.common.modal('./product.do?method=rptInQueryProduct&firstLoad=1&locationInner='+ locationIdI);
}

function removes()
{
	fromSelect = $('productName');
	
	for (i = fromSelect.options.length-1; i >= 0; i--) 
	{
		var opp = fromSelect.options[i];
		if (opp.selected == true)
		{
			if (opp.amount != null && opp.amount > 0)
			{
				alert('��Ʒ������Ϊ0������ɾ��!');
				return;
			}

			$('productId').value = $('productId').value.delSubString(opp.value + '#');
			
			fromSelect.remove(i);
		}
	}
	
	setSelectIndex(fromSelect, 0);
}

function getProduct(ox)
{
	if (ox == null || ox.length == 0)
	{
		return;
	}
	
	for (i = 0; i < ox.length; i++)
	{
		if ($('productId').value.indexOf( '#' + ox[i].value + '#') == -1)
		{
			$('productId').value = $('productId').value + (ox[i].value + '#');
			
			setOption($('productName'), ox[i].value, ox[i].productName);
		}
	}
	
	setSelectIndex($('productName'), 0);
}


</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../admin/das.do" method="post"><input
	type="hidden" name="id" value="${bean.id}"> <input
	type="hidden" name="productId" value="#${bean.productId}"> <input
	type="hidden" name="depotpartId"
	value="${my:show(param.depotpartId, bean.depotpartId)}"> <input
	type="hidden" name="method"
	value='${param.add != "1" ? "updateStorage" : "addStorage"}'> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: hand"
		onclick="javascript:history.go(-1)">��λ����</span> &gt;&gt; ${dis}��λ</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="80%">

	<p:title>
		<td class="caption"><strong>��λ��Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="75%">

		<p:class value="com.china.centet.yongyin.bean.StorageBean" />

		<p:table cells="1">

			<p:pro field="name" value="${bean.name}" />

			<p:cell title="ѡ���Ʒ">
				<input type="button" value="&nbsp;ѡ���Ʒ&nbsp;" name="qout"
					class="button_class" onclick="selectProduct()">&nbsp;&nbsp;
					<input type="button" value="&nbsp;ɾ����Ʒ&nbsp;" name="qout"
					class="button_class" onclick="removes()">
			</p:cell>

			<p:pro field="productId"
				innerString="multiple=true size=12 style='width: 520px'">
				<c:forEach items="${relations}" var="item">
					<option value="${item.productId}" amount="${item.amount}">${item.productName}</option>
				</c:forEach>
			</p:pro>

			<p:pro field="description" value="${bean.description}"
				innerString="cols=55 rows=3" />

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="87%" rightWidth="13%">
		<div align="right"><input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;"
			onclick="addApplys()">&nbsp;&nbsp; <input type="button"
			class="button_class" onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
	</p:button>

	<p:message />
</p:body></form>
</body>
</html>

