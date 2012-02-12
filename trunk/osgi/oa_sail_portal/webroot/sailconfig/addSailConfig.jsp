<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加结算价格" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定增加结算价格?', null, null);
}

function selectProduct()
{
    window.common.modal('../product/product.do?method=rptQueryProduct&load=1&selectMode=1');
}

function getProduct(oos)
{
    var obj = oos[0];
    
    $O('productName').value = obj.pname;   
    $O('productId').value = obj.value;   
}

//选择职位
function selectPrin()
{
    window.common.modal('../admin/org.do?method=popOrg');
}

function setOrgFromPop(id, name, level)
{
    $O('industryId').value = id;
    
    $O('industryName').value = name;
}

function clears()
{
    $O('productId').value = '公共';
    $O('productName').value = '';
}


</script>

</head>
<body class="body_class">
<form name="formEntry" action="../sail/config.do" method="post">
<input type="hidden" name="method" value="addSailConfig">
<input type="hidden" name="industryId" value="">
<input type="hidden" name="productId" value="0">

<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">结算价格管理</span> &gt;&gt; 增加结算价格</td>
	<td width="85"></td>
	
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>结算价格基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.sail.bean.SailConfBean" />

		<p:table cells="1">
		    
		    <p:pro field="sailType">
                <p:option type="productSailType" empty="true"/>
            </p:pro>

			<p:pro field="productId" value="公共" innerString="size=60">
			     <input type="button" value="&nbsp;选择产品&nbsp;" name="qout1" id="qout1"
                    class="button_class" onclick="selectProduct()">&nbsp;
                 <input type="button" value="&nbsp;清 空&nbsp;" name="qout" id="qout"
                        class="button_class" onclick="clears()">&nbsp;&nbsp;
			</p:pro>
			
			<p:pro field="industryId" innerString="size=60">
                 <input type="button" value="&nbsp;选择事业部&nbsp;" name="qout2" id="qout2"
                    class="button_class" onclick="selectPrin()">&nbsp;
            </p:pro>
            
            <p:pro field="pratio" value="0" innerString="size=60 oncheck='isMathNumber'"/>
            
            <p:pro field="iratio" value="0" innerString="size=60 oncheck='isMathNumber'"/>
			
			<p:pro field="description" cell="0" innerString="rows=3 cols=55" />

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message2/>
</p:body></form>
</body>
</html>

