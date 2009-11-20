<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="./error.jsp"%>
<%@include file="./common.jsp"%>
<html>
<head>
<p:link title="�޸Ļ�Ա" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>

<script language="javascript">

var oldCard = '${bean.cardNo}';
function addApplys()
{
	if (oldCard != $$('cardNo'))
	{
		if (!window.confirm('ȷ���޸Ļ�Ա�Ŀ���?ֻ�л�����ʱ����б����޸Ļ�Ա�Ŀ���.'))
		{
			return;
		}
	}
	submit('ȷ���޸Ļ�Ա[' + $$('name') + ']');
}

function load()
{
	loadForm();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="addApply" action="../member/member.do">
	<input type="hidden" name="method" value="updateMember">
	<input type="hidden" name="id" value="${bean.id}">
<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: hand"
		onclick="javascript:history.go(-1)">��Ա����</span> &gt;&gt; �޸Ļ�Ա</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>��Ա��Ϣ�����޸Ļ�Ա�Ŀ��žͿ���ʵ�ֻ����Ĺ��ܡ�</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.centet.yongyin.bean.MemberBean" />

		<p:table cells="2">
			<p:pro field="name" value="${bean.name}" />
			<p:pro field="cardNo" value="${bean.cardNo}" />
			<p:pro field="handphone" value="${bean.handphone}"/>
			<p:pro field="password" value="${bean.password}"/>
			<p:pro field="email" value="${bean.email}"/>
			<p:pro field="connect" value="${bean.connect}"/>

			<c:if test="${user.role == 'SHOP'}">
			<p:pro field="grade" value="${bean.grade}">
				<option value="0">�տ�</option>
				<option value="1">����</option>
				<option value="2">��</option>
				<option value="3">����</option>
			</p:pro>
			</c:if>

			<c:if test="${user.role != 'SHOP'}">
			<p:pro field="grade" innerString="readonly=true" value="${bean.grade}">
				<option value="0">�տ�</option>
				<option value="1">����</option>
				<option value="2">��</option>
				<option value="3">����</option>
			</p:pro>
			</c:if>

			<p:pro field="type"  value="${bean.type}">
				<option value="0">��ͨ��Ա</option>
				<option value="1">���û�Ա</option>
			</p:pro>
			<p:pro field="company"  value="${bean.company}"/>

			<p:pro field="position" value="${bean.position}"/>

			<p:pro field="area" cell="1" value="${bean.area}"/>

			<p:pro field="sex"  value="${bean.sex}">
				<option value="0">��</option>
				<option value="1">Ů</option>
			</p:pro>

			<p:pro field="address"   value="${bean.address}" innerString="size='80'" cell="2"/>

			<p:pro field="description" value="${bean.description}" innerString="cols=80 rows=3" cell="2"/>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" name="adds"
			style="cursor: pointer" value="&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;"
			onclick="addApplys()">&nbsp;&nbsp; <input type="button"
			class="button_class" onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"></div>
	</p:button>

	<p:message/>
</p:body></form>
</body>
</html>

