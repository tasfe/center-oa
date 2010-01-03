<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="�ϳɲ�Ʒ" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/service/composeProduct.js"></script>
<script language="javascript">

function addBean()
{
    submit('ȷ���ϳɲ�Ʒ?', null, checks);
}

function checks()
{
    return true;
}

var current;
function selectProduct(obj)
{
    current = obj;
    
    var pobj = getDepartmentId(current);
    
    if (pobj.value == '')
    {
        alert('��ѡ�����');
        return;
    }
    
    window.common.modal('../admin/das.do?method=queryProduct&firstLoad=1&rpt=1&depotpartId=' + pobj.value);
}

function getProduct(oo)
{
    current.value = oo.productName;
    
    var hobj = getNextInput(current.nextSibling);
    
    hobj.value = oo.productId;
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
</script>

</head>
<body class="body_class" onload="addTr()">
<form name="formEntry" action="../admin/product.do" method="post"><input
	type="hidden" name="method" value="composeProduct"> 

<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javaScript:window.history.go(-1);">��Ʒ����</span> &gt;&gt; �ϳɲ�Ʒ</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>�ϳɲ�Ʒ</strong>������Ʒ�ֳ���Ĳ�Ʒ��ͬ��������С�</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1">
			<p:tr align="left">
			Ŀ�Ĳ�����
			<select name="dirDepotpart" class="select_class" style="width: 25%;" onchange="depotpartChange(this)" oncheck="notNone">
		         <option value="">--</option>
		         <c:forEach var="item" items="${depotpartList}">
		             <option value="${item.id}">${item.name}</option>
		         </c:forEach>
	         </select>
			Ŀ�Ĳ�Ʒ��<input type="text" style="width: 20%;cursor: pointer;" readonly="readonly" value="" oncheck="notNone" name="dirTargerName" 
			onclick="selectProduct(this)">
         <input type="hidden" name="dirProductId" value="">
         �ϳ�������<input type="text" style="width: 10%"
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
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="30%" align="center">Դ����</td>
                        <td width="30%" align="center">Դ��Ʒ</td>
                        <td width="30%" align="center">ʹ������</td>
                        <td width="5%" align="left"><input type="button" accesskey="A"
                            value="����" class="button_class" onclick="addTr()"></td>
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
            value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" onclick="addBean()">
        </div>
	</p:button>
	
	<p:message/>
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
         style="width: 100%;cursor: pointer;" readonly="readonly" value="" oncheck="notNone" name="targerName" onclick="selectProduct(this)">
         <input type="hidden" name="srcProductId" value="">
         </td>
         <td width="30%" align="center"><input type="text" style="width: 100%"
                    name="srcAmount" value="" oncheck="notNone;isNumber"></td>
        <td width="5%" align="center"><input type=button
            value="&nbsp;ɾ ��&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
</table>
</body>
</html>

