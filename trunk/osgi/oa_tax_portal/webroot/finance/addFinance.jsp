<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加凭证" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../tax_js/autosuggest_debug.js"></script>
<script language="JavaScript" src="../tax_js/addTax.js"></script>
<script language="JavaScript" src="../tax_js/addFinance.js"></script>
<script language="javascript">

function addBean()
{
    submit('确定增加凭证?', null, checks);
}

var taxList = JSON.parse('${taxListStr}');

function initData()
{
	var dataList = [];
	
	for (var i = 0; i < taxList.length; i++)
	{
		dataList[i] = taxList[i].code + taxList[i].name;
	}
	
	return dataList;
}

function checks()
{
	var deList = document.getElementsByName('taxId');
	
	var inMoney = 0.0;
	
	var outMoney = 0.0;
	
	for (var i = 0; i < deList.length; i++)
	{
		if (deList[i].value == '')
		{
			continue;
		}
		
		var ins = getTrInnerObj(deList[i], 'inmoney');
		
		var ous = getTrInnerObj(deList[i], 'outmoney');
		
		
		var eachIn = 0.0;
		
		var eachOut = 0.0;
		
		if (ins.value != '')
		{
		    eachIn = parseFloat(ins.value);
			inMoney += eachIn;
		}
		
		if (ous.value != '')
		{
			eachOut = parseFloat(ous.value);
			
			outMoney += eachOut;
		}
		
		if (eachIn * eachOut != 0)
		{
		    alert('借贷双方不能都有金额');
		    
		    return false;
		}
		
		if (inMoney == outMoney && inMoney != 0.0)
		{
			inMoney = 0.0;
			
			outMoney = 0.0;
		}
		
		if (!checkSelect(deList[i]))
		{
		    return false;  
		}
	}
	
	if (formatNum(inMoney, 2) != formatNum(outMoney, 2))
	{
		alert('借贷必相等,借方:' + inMoney + ',贷方:' + outMoney);
				
		return false;
	}
	
    return true;
}

function load()
{
	loadForm();
	
	addTr();
}

function callClick(obj, el)
{
	taxChange(obj);
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../finance/finance.do" method="post">
<input type="hidden" name="method" value="addFinance"> 
<input type="hidden" name="refId" value="${refId}"> 

<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javaScript:window.history.go(-1);">凭证管理</span> &gt;&gt; 增加凭证</td>
	<td width="85"></td>
</p:navigation>

<p:body width="100%">

	<p:title>
		<td class="caption">
		<strong>凭证信息</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1">
			<p:tr align="left">
			凭证日期：
			<p:plugin name="financeDate" size="20" type="0" oncheck="cnow(12)"/>
	         凭证类型：
			<select name="type" class="select_class" style="width: 15%;" oncheck="notNone">
		         <option value="">--</option>
		         <p:option type="financeType"/>
	         </select>
			凭证归属：
			<select name="dutyId" class="select_class" style="width: 15%;" oncheck="notNone">
		         <option value="">--</option>
		         <p:option type="dutyList"></p:option>
	         </select>
			</p:tr>
			
			<p:tr align="left">
			描述：<textarea  name="description" rows="3" cols="80" oncheck="maxLength(200)"></textarea>
			</p:tr>
		</p:table>
	</p:subBody>
	
	<p:tr></p:tr>
	
	<tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="15%" align="center">摘要</td>
                        <td width="40%" align="center">科目</td>
                        <td width="25%" align="center">辅助核算</td>
                        <td width="8%" align="center">借方金额</td>
                        <td width="8%" align="center">贷方金额</td>
                        <td width="5%" align="left"><input type="button" accesskey="A"
                            value="增加" class="button_class" onclick="addTr()"></td>
                    </tr>
                </table>
                </td>
            </tr>
        </table>
        </td>
    </tr>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		  <input type="button" class="button_class" id="sub_b"
            value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean()">
        </div>
	</p:button>
	
	<p:message2/>
</p:body>
</form>
<table>
    <tr class="content1" id="trCopy" style="display: none;">
         <td>
         <textarea  name="idescription" rows="3"></textarea>
         </td>
         <td>
         <input type="text" style="width: 95%;"
                name="taxId" value="">
         <input type="hidden" name="taxId2" value="">
         </td>
         
         <td>
         <br>
         <input type="text" style="width: 85%;display: none;cursor: pointer;color: gray;" onclick="selectPrin(this)" title="选择部门" head="部门" 
                    name="departmentId" value="选择部门" readonly="readonly">
         <input type="hidden" name="departmentId2" value=""> 
         <br>
         <input type="text" style="width: 85%;display: none;cursor: pointer;color: gray;" onclick="selectStaffer(this)" title="选择职员" head="职员" 
                    name="stafferId" value="选择职员" readonly="readonly">
         <input type="hidden" name="stafferId2" value=""> 
         <br>
         <input type="text" style="width: 85%;display: none;cursor: pointer;color: gray;" onclick="selectUnit(this)" title="选择单位" head="单位"
                    name="unitId" value="选择单位" readonly="readonly">
         <input type="hidden" name="unitId2" value=""> 
         <br>
         <input type="text" style="width: 85%;display: none;cursor: pointer;color: gray;" onclick="selectProduct(this)" title="选择产品" head="产品"
                    name="productId" value="选择产品" readonly="readonly">
         <input type="hidden" name="productId2" value=""> 
         <br>
         <select name="depotId" class="select_class" style="width: 85%;display: none;" title="选择仓库" head="仓库">
         <option value="">选择仓库</option>
         <c:forEach var="item" items="${depotList}">
             <option value="${item.id}">${item.name}</option>
         </c:forEach>
         </select>
         <br>
         <select name=duty2Id class="select_class" style="width: 85%;display: none;" title="选择纳税实体" head="纳税实体">
         <option value="">选择纳税实体</option>
         <p:option type="dutyList"></p:option>
         </select>
         <br>
         <br>
         </td>
         
         <td>
         <input type="text" style="width: 100%;"
                    name="inmoney" value="0.0" oncheck="">
         </td>
                    
         <td align="center">
         <input type="text" style="width: 100%"
                    name="outmoney" value="0.0" oncheck=""></td>
                    
        <td width="5%" align="center"><input type=button
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
</table>
</body>
</html>

