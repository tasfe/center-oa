<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="������־" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">
function load()
{
    setAllReadOnly();
}
</script>

</head>
<body class="body_class" onload="load()">
<form>
 <p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javaScript:window.history.go(-1);">������־����</span> &gt;&gt; ������־��Ϣ</td>
	<td width="85"></td>
</p:navigation> <br>

<div style="width: 2000px">
<p:body width="100%">

	<p:title>
		<td class="caption"><strong>������־������Ϣ��</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1">
			<p:cell title="��������"><p:plugin name="workDate" value="${bean.workDate}"/></p:cell>
			
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
                        <td width="10%" align="center">��������</td>
                        <td width="10%" align="center">��������</td>
                        <td width="10%" align="center">��ʼʱ��</td>
                        <td width="10%" align="center">����ʱ��</td>
                        <td width="10%" align="center">��ɽ��</td>
                        <td width="20%" align="center">��������</td>
                        <td width="10%" align="center">����ʱ��</td>
                        <td width="30%" align="center">��ע��Ҫ</td>
                    </tr>
                    
                    <c:forEach items="${bean.visits}" var="item">
                    <tr class="content1">
				         <td width="10%" align="center">
				         <select name="workType" class="select_class" style="width: 100%;" values="${item.workType}">
				         <p:option type="110"/>
				         </select>
				         </td>
				         <td width="10%" align="center"><input type="text" style="width: 100%;cursor: pointer;" 
				         readonly="readonly" value="${item.targerName}" oncheck="notNone" name="targerName" >
				         </td>
				         <td width="10%" align="center"><input type="text" readonly="readonly"  style="cursor: pointer; width: 100%"
				                    name="beginTime" value="${item.beginTime}" oncheck="notNone"></td>
				         <td width="10%" align="center"><input type="text" readonly="readonly"  style="cursor: pointer; width: 100%"
				                    name="endTime" value="${item.endTime}"></td>
				         <td width="10%" align="center">
				         <select name="result" class="select_class" style="width: 100%;" values="${item.result}">
				         <p:option type="111"/>
				         </select>
				         </td>
				         <td width="20%" align="center">
				         <textarea name="nextWork" rows="2"  style="width: 100%;">${item.nextWork}</textarea>
				         </td>
				         <td width="10%" align="center">
				         <input type="text" readonly="readonly" style="cursor: pointer; width: 100%"
				                    name="nextDate" value="${item.nextDate}"/>
				         </td>
				         <td width="30%" align="center">
				         <textarea name="description" rows="2"  style="width: 100%;"/>${item.description}</textarea>
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
		<div align="right"><input type="button" class="button_class" id="res_b"
            style="cursor: pointer" value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"
            onclick="window.history.go(-1)"></div>
	</p:button>
</p:body>
</div>
</form>
</body>
</html>

