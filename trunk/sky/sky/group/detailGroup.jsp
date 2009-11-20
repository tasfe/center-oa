<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="Ⱥ��" />
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="javascript">

function load()
{
    setAllReadOnly();
}

</script>

</head>
<body class="body_class" onload="load()">
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">Ⱥ�����</span> &gt;&gt; Ⱥ����ϸ</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>Ⱥ�������Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.group.bean.GroupBean" opr="1"/>

		<p:table cells="1">
			<p:pro field="name" />
			
			<p:pro field="type">
			<p:option type="groupType"></p:option>
			</p:pro>
			
			<p:cell title="��Ա">
                <select multiple="multiple" size="20" style="width: 260px" name="vsStafferIds" disall="true">
                <c:forEach items="${items}" var="item">
                <option value="${item.stafferId}">${item.stafferName}</option>
                </c:forEach>
                </select>
            </p:cell>
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"
			onclick="javascript:history.go(-1)"></div>
	</p:button>

	<p:message/>
</p:body>
</body>
</html>

