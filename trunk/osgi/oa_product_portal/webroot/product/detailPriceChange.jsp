<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="产品调价"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">

function load()
{
}
</script>

</head>

<body class="body_class">
<form name="formEntry">
<input type="hidden" name="id" value="${bean.id}">
<p:navigation
	height="22">
	<td width="550" class="navigation">调价明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>调价基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:table cells="2">
			<p:cell title="时间">${bean.logTime}</p:cell>
			<p:cell title="调价人">${bean.stafferName}</p:cell>
			<p:cell title="状态" end="true">${my:get('priceChangeStatus', bean.status)}</p:cell>
			<p:cell title="调价原因" end="true">${bean.description}</p:cell>
			<p:cell title="核对状态" end="true">
               ${my:get('pubCheckStatus', bean.checkStatus)}
            </p:cell>
            
            <p:cell title="核对信息" end="true">
               ${bean.checks}
            </p:cell>
		</p:table>
	</p:subBody>
	
	<p:tr/>
	
	<p:subBody width="100%">
		<p:table cells="2">
			<tr align="center" class="content0">
				<td width="10%" align="center">类型</td>
				<td width="25%" align="center">产品</td>
				<td align="center">数量</td>
				<td align="center">价格</td>
				<td align="center">位置</td>
				<td align="center">职员</td>
			</tr>
			
			<c:forEach items="${bean.srcVOList}" var="item">
				<tr align="center" class="content2">
					<td align="center"><font color="red"><b>调价前</b></font></td>
					<td align="center">${item.productName}(${item.productCode})</td>
					<td align="center">${item.amount}</td>
					<td align="center">${my:formatNum(item.price)}</td>
					<td align="center">${item.deportName} --> ${item.depotpartName}</td>
					<td align="center">${item.stafferName}</td>
				</tr>
				<c:forEach items="${map[item.refId]}" var="inner">
					<tr align="center" class="content1">
						<td align="center"><font color="blue"><b>调价后</b></font></td>
						<td align="center">${inner.productName}(${inner.productCode})</td>
						<td align="center">${inner.amount}</td>
						<td align="center">${my:formatNum(inner.price)}</td>
						<td align="center">${inner.deportName} --> ${inner.depotpartName}</td>
						<td align="center">${inner.stafferName}</td>
					</tr>
				</c:forEach>
			</c:forEach>
		</p:table>
	</p:subBody>
	
	

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		<input type="button" class="button_class" id="return_b"
			style="cursor: pointer" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"
			onclick="javascript:history.go(-1)">
		</div>
	</p:button>

	<p:message2/>
</p:body></form>
</body>
</html>

