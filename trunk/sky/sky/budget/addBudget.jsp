<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="����Ԥ��" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/oa/budget.js"></script>
<script language="javascript">

function addBean(opr)
{
    $O('opr').value = opr;
    
    var title = $O('opr').value == 0 ? "����" : "�ύ";
    
	submit('ȷ��'+title+'Ԥ��?', null, check);
}

function clears()
{
    document.getElementById('f_item_budget').value = '';
    document.getElementById('f_item_description').value = '';
}

function load()
{
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../budget/budget.do" method="post"><input
	type="hidden" name="method" value="addBudget"> <input
    type="hidden" name="parentId" value="${parentId}"> 
	<input
    type="hidden" name="opr" value="0"> <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javaScript:window.history.go(-1);">Ԥ�����</span> &gt;&gt; ����Ԥ��</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>Ԥ�������Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.budget.bean.BudgetBean" />

		<p:table cells="2">
		    <p:cells title="����Ԥ��" celspan="2">${pbean.name}</p:cells>
			<p:pro field="name" cell="2" innerString="size=60"/>
			
			<p:pro field="type" value="${type}" innerString="readonly=true">
			<p:option type="budgetType"></p:option>
			</p:pro>
			
			<p:pro field="level" value="${nextLevel}" innerString="readonly=true">
            <p:option type="budgetLevel"></p:option>
            </p:pro>
            
            <p:pro field="budgetDepartment" />
            
            <c:if test="${type == 0}">
            <p:pro field="year" cell="1">
                <option value="">--</option>
                <c:forEach begin="2001" end="2100" var="item">
                    <option value="${item}">${item}</option>
                </c:forEach>
            </p:pro>
            </c:if>
            
            <c:if test="${type != 0}">
            <p:pro field="year" cell="1" value="${pbean.year}" innerString="readonly=true">
                <option value="">--</option>
                <c:forEach begin="2001" end="2100" var="item">
                    <option value="${item}">${item}</option>
                </c:forEach>
            </p:pro>
            </c:if>
            
			
			<p:pro field="beginDate" />
			<p:pro field="endDate" />
			
			<p:pro field="sail" />
			<p:pro field="orgProfit" />
			
			<p:pro field="realProfit" />
			<p:pro field="outSave" />
			
			<p:pro field="outMoney" />
			<p:pro field="inMoney" />
			
			<p:pro field="description" cell="2" innerString="rows=4 cols=60"/>
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
                        <td width="20%" align="center">Ԥ����</td>
                        <td width="20%" align="center">Ԥ����</td>
                        <td width="50%" align="center">����</td>
                        <td width="10%" align="left"><input type="button" accesskey="A"
                            value="����" class="button_class" onclick="addTr()"></td>
                    </tr>
                    
                    
                    <c:forEach items="${pbean.items}" var="item" varStatus="vs">
                        <tr class="content2">
                        <td width="20%">
                            <select name="item_name" id="f_item_name${vs.index}" quick=true values="${item.feeItemId}" style="width: 100%;">
                            <c:forEach items="${feeItems}" var="itemSub">
                            <option value="${itemSub.id}">${itemSub.name}</option>
                            </c:forEach>
                            </select>
                        </td>

                        <td width="20%" align="left"><input type="text" id="f_item_budget" style="width: 100%;" oncheck="isFloat3" head="Ԥ����"
                            maxlength="12" 
                            name="item_budget"></td>
                            
                        <td width="50%" align="left"><textarea name="item_description" rows="2"  style="width: 100%;" id="f_item_description" oncheck="maxLength(200)"></textarea></td>

                        <td width="10%" align="left"><input type=button value="ɾ��"  class="button_class" onclick="removeTr(this)"></td>
                    </tr>
                    </c:forEach>
                    
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
            id="view_1" style="cursor: pointer" value="&nbsp;&nbsp;�鿴���Ԥ��&nbsp;&nbsp;"
            onclick="viewother()">&nbsp;&nbsp;<input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"
			onclick="addBean(0)">&nbsp;&nbsp;<input type="button" class="button_class" id="sub_b"
            style="cursor: pointer" value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"
            onclick="addBean(1)"></div>
	</p:button>
</p:body>
</form>
<table style="display: none;">
<tr class="content2" id="trCopy0">
    <td width="20%">
        <select name="item_name" id="f_item_name" quick=true style="width: 100%;" oncheck="notNone" head="Ԥ����">
        <option value="">--</option>
        <c:forEach items="${feeItems}" var="itemSub">
        <option value="${itemSub.id}">${itemSub.name}</option>
        </c:forEach>
        </select>
    </td>

    <td width="20%" align="left"><input type="text" id="f_item_budget" style="width: 100%;" oncheck="isFloat3" head="Ԥ����"
        maxlength="12" 
        name="item_budget"></td>
        
    <td width="50%" align="left"><textarea name="item_description" rows="2"  style="width: 100%;" id="f_item_description" oncheck="maxLength(200)"></textarea></td>

    <td width="10%" align="left"><input type=button value="ɾ��" class=button_class onclick="removeTr(this)"></td>
</tr>
</table>

</body>
</html>

