<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="Ԥ����ϸ" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/oa/budget.js"></script>
<script language="javascript">
function load()
{
    setAllReadOnly();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../budget/budget.do"><input
	type="hidden" name="method" value="updateBudget"> <input
	type="hidden" name="opr" value="0"> <input type="hidden"
	name="id" value="${bean.id}"> <p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javaScript:window.history.go(-1);">Ԥ�����</span> &gt;&gt; Ԥ����ϸ</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>Ԥ�������Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.budget.vo.BudgetVO" opr="1" />

		<p:table cells="2">
			<p:pro field="name" cell="2" innerString="size=60"/>
            
            <p:pro field="type">
            <p:option type="budgetType"></p:option>
            </p:pro>
            <p:pro field="level">
            <p:option type="budgetLevel"></p:option>
            </p:pro>
            
            <p:pro field="year" cell="2">
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
            
            <p:cells celspan="2" title="���˵��">
            <font color="blue">${applyBean.description}</font>
            </p:cells>
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
                        <td width="20%" align="center">(��ǰ)Ԥ����</td>
                        <td width="15%" align="center">Ԥ����</td>
                        <td width="15%" align="center">ʹ�ý��</td>
                        <td width="15%" align="center">Ԥ�ý��</td>
                        <td width="50%" align="center">����</td>
                    </tr>

                    <c:forEach items="${bean.itemVOs}" var="item" varStatus="vs">
                        <tr class="content2" id="trCopy${vs.index}">
                            <td width="20%"><input type="text" name="item_name"
                                id="f_item_name" maxlength="20" value="${item.feeItemName}" oncheck="notNone" head="Ԥ��������"
                                style="width: 100%;"></td>

                            <td width="20%" align="left"><input type="text"
                                id="f_item_budget" style="width: 100%;" oncheck="isFloat"
                                head="Ԥ����" value="${item.sbudget}" maxlength="12"
                                name="item_budget"></td>
                                
                            <td width="15%" align="left"><input type="text"
                                id="f_item_budget" style="width: 100%;" oncheck="isFloat"
                                head="Ԥ����" value="${item.srealMonery}" maxlength="12"
                                name="item_budget"></td>
                                
                            <td width="15%" align="left"><input type="text"
                                id="f_item_budget" style="width: 100%;" oncheck="isFloat"
                                head="Ԥ�ý��" value="${item.suseMonery}" maxlength="12"
                                name="item_budget"></td>

                            <td width="40%" align="left"><textarea
                                name="item_description" rows="2" style="width: 100%;"
                                id="f_item_description" oncheck="maxLength(200)">${item.description}</textarea></td>
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
						<td width="20%" align="center">(�����)Ԥ����</td>
						<td width="15%" align="center">Ԥ����</td>
						<td width="50%" align="center">����</td>
					</tr>

					<c:forEach items="${items}" var="item" varStatus="vs">
						<tr class="content2" id="trCopy${vs.index}">
							<td width="20%"><input type="text" name="item_name"
								id="f_item_name" maxlength="20" value="${item.feeItemName}" oncheck="notNone" head="Ԥ��������"
								style="width: 100%;"></td>

							<td width="20%" align="left"><input type="text"
								id="f_item_budget" style="width: 100%;" oncheck="isFloat"
								head="Ԥ����" value="${item.sbudget}" maxlength="12"
								name="item_budget"></td>

							<td width="40%" align="left"><textarea
								name="item_description" rows="2" style="width: 100%;"
								id="f_item_description" oncheck="maxLength(200)">${item.description}</textarea></td>
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
			id="ok_b" style="cursor: pointer" value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"
			onclick="javaScript:window.history.go(-1);"></div>
	</p:button>
</p:body></form>
</body>
</html>

