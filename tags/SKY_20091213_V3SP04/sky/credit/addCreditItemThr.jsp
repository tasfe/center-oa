<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="����ָ����" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
    $O('goon').value = '0';
    
	submit('ȷ������ָ����?');
}

function addBean1()
{
    $O('goon').value = '1';
    
    submit('ȷ������ָ����?');
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../credit/credit.do?"><input
	type="hidden" name="method" value="addCreditItemThr">
<input type="hidden" name="pid" value="${parent.id}"> 
<input type="hidden" name="goon" value="0"> 
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">ָ�������</span> &gt;&gt; ����ָ����</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>ָ���������Ϣ��(�����ڰٷֱ����Ǳ�ʶ�����Ⱥ�,��ʵ��ֵ�µ��ξ��Ǿ������ָ��)</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.credit.bean.CreditItemThrBean"/>

		<p:table cells="1">
		    
		    <p:cell title="���������">
		      ${parent.name}(${my:get('creditItemType', parent.type)})
		    </p:cell>  
			
			<p:pro field="name"/>
			
			<p:pro field="per"/>
			
			<p:pro field="indexPos" outString="${parent.type == 0 ? '(����ֵ)' : parent.unit}"/>
			
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
                        <td width="40%" align="center">���ۼ���</td>
                        <td width="30%" align="center">�÷�</td>
                        <td width="30%" align="center">����</td>
                    </tr>

                    <c:forEach items="${thrList}" var="item" varStatus="vs">
                        <tr class="content2" id="trCopy${vs.index}">
                            <td width="40%" align="center">${item.name}</td>
                            <td width="30%" align="center">${item.per}</td>
                            <td width="30%" align="center">${item.indexPos}</td>

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
		<div align="right"><input type="button" class="button_class" id="ok_b"
		 value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"
			onclick="addBean()">&nbsp;&nbsp;
			<input type="button" class="button_class" id="ok_b_1"
         value="&nbsp;&nbsp;����������&nbsp;&nbsp;"
            onclick="addBean1()">
	   </div>
	</p:button>
    <p:message2></p:message2>
</p:body></form>
</body>
</html>

