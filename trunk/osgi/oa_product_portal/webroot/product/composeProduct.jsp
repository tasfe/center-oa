<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="合成产品" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../product_js/composeProduct.js"></script>
<script language="javascript">

function addBean()
{
    submit('确定合成产品?', null, checks);
}

function checks()
{
	var srs = document.getElementsByName('srcRelation');
	
	var ret = $duplicate(srs);
	
	if (ret)
	{
		alert('相同产品不能在同一个储位');
	}
	
    return !ret;
}

var current;

function selectProduct(obj)
{
    current = obj;
    
    var pobj = getDepartmentId(current);
    
    if (pobj.value == '')
    {
        alert('请选择仓区');
        return;
    }
    
    //查询合成产品列表
    window.common.modal(RPT_PRODUCT + '&ctype=1');
}

function getEle(eles, name)
{
	for (var i = 0; i < eles.length; i++)
	{
		if (eles[i].name == name)
		{
			return eles[i];
		}
	}
	
	return null;
}

function getProductRelation(oos)
{
	var oo = oos[0];
	
	current.value = oo.pname;
	
	var tr = getTrObject(current);
	
	var eles = tr.getElementsByTagName('input');
    
    var hobj = getEle(eles, "srcProductId");
    
    hobj.value = oo.ppid;
    
    var pobj = getEle(eles, "srcPrice");
    
    pobj.value = oo.pprice;
    
    var aobj = getEle(eles, "srcAmount");
    
    aobj.value = oo.pamount;
    
    var reobj = getEle(eles, "srcRelation");
    
    reobj.value = oo.value;
    
    var srcDe = getEle(tr.getElementsByTagName('select'), "srcDepotpart");
    
    setSelect(srcDe, oo.pdepotpart);
}

function selectDepotpartProduct(obj)
{
    current = obj;
    
    var pobj = getDepartmentId(current);
    
    if (pobj.value == '')
    {
        alert('请选择仓区');
        return;
    }
    
   window.common.modal('../depot/storage.do?method=rptQueryProductInDepot&load=1&stafferId=0&selectMode=1&locationId='+ $$('srcDepot'));
}

function getProduct(oos)
{
	var oo = oos[0];
	
    current.value = oo.pname;
    
    var hobj = getNextInput(current.nextSibling);
    
    hobj.value = oo.value;
}

function getNextInput(el)
{
    if (el.tagName && el.tagName.toLowerCase() == 'input')
    {
        return el;
    }
    else
    {
        return getNextInput(el.nextSibling);
    }
}

var dList = JSON.parse('${depotpartListStr}');

function getDepartmentId(obj)
{
    var tr = getTrObject(obj);
    
    if (tr != null)
    {
        return tr.getElementsByTagName('select')[0];
    }
}

function depotpartChange(obj)
{
    var tr = getTrObject(obj);
    
    var inputs = tr.getElementsByTagName('input');
    
    for (var i = 0 ; i < inputs.length; i++)
    {
        var oo = inputs[i];
        
        if (oo.type.toLowerCase() != 'button')
        {
            oo.value = '';
        }
    }
}

function depotChange()
{
	var elements = new Array();
	
	var sels = document.getElementsByName('srcDepotpart');
	
	for (var i = 0; i < sels.length; i++)
	{
		elements.push(sels[i]);
	}
	
	elements.push($O('dirDepotpart'));
	
	var newsrcDepot = $$('srcDepot');
	
	for (var i = 0; i < elements.length; i++)
	{
		removeAllItem(elements[i]);
		
		//add new option
		for (var j = 0; j < dList.length; j++)
		{
			if (dList[j].locationId == newsrcDepot)
			{
				setOption(elements[i], dList[j].id, dList[j].name);
			}
		}
		
		depotpartChange(elements[i]);
	}
}

function load()
{
	addTr();
	
	depotChange();
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../product/product.do" method="post"><input
	type="hidden" name="method" value="composeProduct"> 

<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javaScript:window.history.go(-1);">产品管理</span> &gt;&gt; 合成产品</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>选择仓库</strong>
		<select name="srcDepot" class="select_class" style="width: 25%;" onchange="depotChange()" oncheck="notNone">
		         <c:forEach var="item" items="${depotList}">
		             <option value="${item.id}">${item.name}</option>
		         </c:forEach>
	     </select> (产品合成只能在仓库内进行) <strong>如果需要增加合成费用项,请到 公共资源-->配置管理</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1">
			<p:tr align="left">
			目的仓区：
			<select name="dirDepotpart" class="select_class" style="width: 25%;" onchange="depotpartChange(this)" oncheck="notNone">
		         <option value="">--</option>
		         <c:forEach var="item" items="${depotpartList}">
		             <option value="${item.id}">${item.name}</option>
		         </c:forEach>
	         </select>
			合成产品：<input type="text" style="width: 20%;cursor: pointer;" readonly="readonly" value="" oncheck="notNone" name="dirTargerName" 
			onclick="selectProduct(this)">
         <input type="hidden" name="dirProductId" value="">
         合成数量：<input type="text" style="width: 10%"
                    name="dirAmount" value="" oncheck="notNone;isNumber">
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
                <table width="100%" border="0" cellspacing='1' id="tables2">
                    <tr align="center" class="content0">
                        <td width="30%" align="center">合成费用项</td>
                        <td width="70%" align="center">费用</td>
                    </tr>
                    
                    <c:forEach items="${feeList}" var="item">
                    <tr align="center" class="content1">
                        <td width="30%" align="center">${item.value}</td>
                        <td width="30%" align="center">
                        <input type="hidden" 
                    name="feeItemId" value="${item.key}">
                        <input type="text" style="width: 100%"
                    name="feeItem" value="0.0" oncheck="notNone;isFloat">
                        </td>
                    </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
    
    <p:tr></p:tr>
	
	<tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="30%" align="center">源仓区</td>
                        <td width="30%" align="center">源产品</td>
                        <td width="15%" align="center">使用数量</td>
                        <td width="15%" align="center">可用数量</td>
                        <td width="15%" align="center">价格</td>
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
         <td width="95%" align="center">
         <select name="srcDepotpart" class="select_class" style="width: 100%;" onchange="depotpartChange(this)" oncheck="notNone">
         <option value="">--</option>
         <c:forEach var="item" items="${depotpartList}">
             <option value="${item.id}">${item.name}</option>
         </c:forEach>
         </select>
         </td>
         <td width="30%" align="center"><input type="text" 
         style="width: 100%;cursor: pointer;" readonly="readonly" value="" oncheck="notNone" name="targerName" onclick="selectDepotpartProduct(this)">
         <input type="hidden" name="srcProductId" value="">
         </td>
         <td width="15%" align="center"><input type="text" style="width: 100%"
                    name="useAmount" value="" oncheck="notNone;isNumber"></td>
         <td width="15%" align="center"><input type="text" style="width: 100%" readonly="readonly"
                    name="srcAmount" value="" oncheck="notNone;isNumber"></td>
         <td width="15%" align="center"><input type="text" style="width: 100%" readonly="readonly"
                    name="srcPrice" value="" oncheck="notNone;isFloat">
         <input type="hidden" 
                    name="srcRelation" value="">
         </td>
        <td width="5%" align="center"><input type=button
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
</table>
</body>
</html>

