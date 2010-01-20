<%@ page contentType="text/html;charset=GBK" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="���̶���" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>

<script language="javascript">
function load()
{
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry">
<p:navigation height="22">
	<td width="550" class="navigation">���̶�����ϸ</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>���̶�����Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:table cells="2">
			<p:cell title="��������">
			${bean.name}
			</p:cell>

			<p:cell title="����ʱ��">
			${bean.logTime}
			</p:cell>

			<p:cell title="ģʽ">
			${my:get('flowMode', bean.mode)}
			</p:cell>
			
			<p:cell title="����">
            ${my:get('parentType', bean.parentType)}
            </p:cell>

			<p:cells title="״̬" celspan="2">
			${my:get('flowStatus', bean.status)}
			</p:cells>
			
			<p:cells title="ģ��" celspan="2">
            <c:forEach items="${templateList}" var="item">
            <a title="�鿴����ģ��" href="../flow/template.do?method=findTemplateFile&id=${item.id}">${item.name}<a>&nbsp;&nbsp;
            </c:forEach>
            </p:cells>

			<p:cells celspan="2" title="��ע">
			${bean.description}
			</p:cells>
		</p:table>
	</p:subBody>

	<p:tr />

	<p:subBody width="100%">
		<table width="100%" border="0" cellspacing='1' id="tables">
			<tr align="center" class="content0">
				<td width="5%" align="center">˳��</td>
				<td width="10%" align="center">��������</td>
				<td width="15%" align="center">�ύģʽ</td>
				<td width="15%" align="center">��һ��������</td>
				<td width="20%" align="center">ģ������</td>
				<td width="25%" align="center">�߼�����</td>
			</tr>

			<c:forEach items="${bean.tokenVOs}" var="item" varStatus="vs">
				<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
					<td align="center">${item.orders + 1}</td>

                    <c:if test="${item.type == 0}">
					<td align="center">${item.name}</td>
					</c:if>
					
					<c:if test="${item.type == 1}">
                    <td align="center"><a href="../flow/flow.do?method=findFlowDefine&id=${item.subFlowId}" title="�����ѯ������">${item.name}</a></td>
                    </c:if>
                    
                    
					
					<td align="center">${item.handleVOs[0].processType}${item.pluginType == 998 ? '�����̲��' : ''}</td>
					<td align="center">${item.handleVOs[0].processName}</td>
					<td align="center">
					<c:forEach items="${item.tempalteVOs}" var="iitem">
					${iitem.templateName}��<input type="checkbox" ${iitem.viewTemplate == 0 ? "" : "checked=checked"}>�鿴&nbsp;&nbsp;
					<input type="checkbox" ${iitem.editTemplate == 0 ? "" : "checked=checked"}>�༭
					&nbsp;&nbsp;<br>
					</c:forEach>
					</td>
					<td align="center">
					<c:if test="${item.type == 0}">
					<input type="checkbox" ${item.operation.reject == 0 ? "" : "checked=checked"}>���ص���һ����
					<c:if test="${subFlow}">
					&nbsp;&nbsp;<input type="checkbox" ${item.operation.rejectParent == 0 ? "" : "checked=checked"}>���ص������̵���һ����
					</c:if>
					&nbsp;&nbsp;<input type="checkbox" ${item.operation.rejectAll == 0 ? "" : "checked=checked"}>���ص���ʼ
					&nbsp;&nbsp;<input type="checkbox" ${item.operation.exends == 0 ? "" : "checked=checked"}>�쳣����
					<br>
					��ֵ(���):${item.operation.liminal}
					</c:if>
                    </td>
				</tr>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
	</p:button>

	<p:message/>
</p:body></form>
</body>
</html>

