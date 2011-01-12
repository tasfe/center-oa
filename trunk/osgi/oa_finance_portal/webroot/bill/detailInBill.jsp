<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="收款单详细" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">


</script>

</head>
<body class="body_class">
<form name="formEntry" action="../finance/invoiceins.do" method="post">
<input type="hidden" name="method" value=""> <p:navigation
	height="22">
	<td width="550" class="navigation"><span>收款单详细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>收款单信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">

		<p:class value="com.china.center.oa.finance.bean.InBillBean" opr="2"/>

		<p:table cells="2">
		
		    <p:cell title="标识">
               ${bean.id}
            </p:cell>

			<p:pro field="type" innerString="style='WIDTH: 340px;'">
			   <p:option type="inbillType" />
			</p:pro>

			<p:pro field="status" innerString="style='WIDTH: 340px;'">
               <p:option type="inbillStatus" />
            </p:pro>
            
            <p:cell title="帐号">
               ${bean.bankName}
            </p:cell>
			
			<p:cell title="客户">
               ${bean.customerName}
            </p:cell>
			
			<p:cell title="关联单据">
			    ${bean.outId}
            </p:cell>
            
            <p:cell title="关联回款">
                ${bean.paymentId}
            </p:cell>
            
            <p:cell title="开单人">
               ${bean.stafferName}
            </p:cell>
            
            <p:cell title="职员">
               ${bean.ownerName}
            </p:cell>
            
            <p:cell title="总金额">
               ${my:formatNum(bean.moneys)}
            </p:cell>
            
            <p:cell title="时间" end="true">
               ${bean.logTime}
            </p:cell>

			<p:pro field="description" cell="0" innerString="rows=3 cols=55" />

		</p:table>

	</p:subBody>

	
	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input
            type="button" name="ba" class="button_class"
            onclick="javascript:history.go(-1)"
            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<p:message2 />
</p:body></form>
</body>
</html>

