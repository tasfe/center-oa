<%@ page contentType="text/html;charset=GBK" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="׷��Ԥ��" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/oa/budget.js"></script>
<script language="javascript">

function addBean(opr)
{
    submit('ȷ��׷��Ԥ��?', null, check);
}

function clears()
{
    document.getElementById('f_item_budget').value = '';
    document.getElementById('f_item_description').value = '';
}

function load()
{
    setAllReadOnly($O('infoTable'));
    
    $r($O('applyReason'), false)
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../budget/budget.do" method="post"><input
    type="hidden" name="method" value="addBudgetApply"><input type="hidden"
    name="budgetId" value="${bean.id}"> <input type="hidden"
    name="parentId" value="${bean.parentId}"><input type="hidden"
    name="oprType" value="${oprType}"><p:navigation height="22">
    <td width="550" class="navigation"><span style="cursor: pointer;"
        onclick="javaScript:window.history.go(-1);">Ԥ�����</span> &gt;&gt; ׷��Ԥ��</td>
    <td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

    <p:title>
        <td class="caption"><strong>Ԥ�������Ϣ��</strong></td>
    </p:title>

    <p:line flag="0" />

    <p:subBody width="98%">
        <p:class value="com.china.center.oa.budget.vo.BudgetVO" opr="1" />

        <p:table cells="2" id="infoTable">
            <p:cells title="����Ԥ��" celspan="2">${pbean.name}</p:cells>
            
            <p:pro field="name" cell="2" innerString="size=60"/>
            
            <p:pro field="type" innerString="readonly=true">
            <p:option type="budgetType"></p:option>
            </p:pro>
            <p:pro field="level" innerString="readonly=true">
            <p:option type="budgetLevel"></p:option>
            </p:pro>
            
            
            <p:pro field="budgetDepartment" />
            <p:pro field="year" cell="1" innerString="readonly=true">
                <option value="">--</option>
                <c:forEach begin="2000" end="2100" var="item">
                    <option value="${item}">${item}</option>
                </c:forEach>
            </p:pro>
            
            <p:pro field="beginDate" />
            <p:pro field="endDate" />
            
            <p:pro field="sail" />
            <p:pro field="orgProfit" />
            
            <p:pro field="realProfit" />
            <p:pro field="outSave" />
            
            <p:pro field="outMoney" />
            <p:pro field="inMoney" />
            
            <p:pro field="description" cell="2" innerString="rows=4 cols=60"/>
            
            <p:cells celspan="2" title="׷��˵��">
            <textarea rows=4 cols=60 name="applyReason" oncheck="notNone" head="׷��˵��"></textarea><font color="red">*</font></p:cells>
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
                        <td width="10%" align="left"><input type="button" value="����" accesskey="A"
                            class="button_class" onclick="addTr()"></td>
                    </tr>

                    <c:forEach items="${bean.itemVOs}" var="item" varStatus="vs">
                        <tr class="content2" id="trCopy${vs.index}">
                            <td width="20%">
                            <select name="item_name" id="f_item_name" style="width: 100%;" values="${item.feeItemId}">
                            <c:forEach items="${feeItems}" var="itemSub">
                            <option value="${itemSub.id}">${itemSub.name}</option>
                            </c:forEach>
                            </select>
                            </td>

                            <td width="20%" align="left"><input type="text"
                                id="f_item_budget" style="width: 100%;" oncheck="isFloat3"
                                head="Ԥ����" value="${item.sbudget}" maxlength="12"
                                name="item_budget"></td>

                            <td width="50%" align="left"><textarea
                                name="item_description" rows="2" style="width: 100%;"
                                id="f_item_description" oncheck="maxLength(200)">${item.description}</textarea></td>

                            <td width="10%" align="left">
                            <input type=button value="ɾ��" class=button_class onclick="removeTr(this)">
                            </td>
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
            onclick="viewother()">&nbsp;&nbsp;<input type="button"
            class="button_class" id="sub_b" style="cursor: pointer"
            value="&nbsp;&nbsp;׷���ύ&nbsp;&nbsp;" onclick="addBean(1)"></div>
    </p:button>
</p:body></form>
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

