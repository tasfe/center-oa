<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="���Ʋ�Ʒ" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/oa/make/02.js"></script>
<script language="javascript">

var makePosition = ${make.position};

var role = '${position.role}';

var makeid = '${make.id}';

var baseURL = '${eurl}';

function load1()
{
    setAllReadOnly3();
}

</script>

</head>
<body class="body_class" onload="load1()">
<form name="formEntry">
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">���Ʋ�Ʒ����</span> &gt;&gt; ���Ʋ�Ʒ��ϸ(��${make.status}��)</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>���Ʋ�Ʒ��(��${make.status}��--${position.name})(${position.ends == 1 ? "<font color=red>������</font>" : "�м价"})</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:class value="com.china.center.oa.customize.make.bean.Make01Bean" opr="1"/>

		<p:table cells="1">
		
		    <p:cell title="���">
             ${make.id}
            </p:cell>  
		    
		    <p:cell title="����">
		     ${make.title}
		    </p:cell>  
		    
		    <p:cell title="����">
              <select class="select_class" name="type" values="${make.type}">
                  <p:option type="makeType"></p:option> 
              </select>
            </p:cell> 

			<p:pro field="cname" innerString="size=40">
			</p:pro>

			<p:pro field="description" cell="0" innerString="rows=3 cols=55" />
            
            <p:pro field="endTime"/>
            <p:pro field="flowTypeName"/>
            
            <p:pro field="endTime2"/>
            <p:pro field="flowTypeName2"/>
            
            <p:pro field="amount"/>
            
            <p:pro field="charact"/>
            
            <p:pro field="cdes" innerString="rows=5 cols=55 oncheck='minLength(100)'" outString="���ݲ�������50��" />
            
            <p:pro field="sampleType">
                <p:option type="sampleType"></p:option>
            </p:pro>
            
            <p:pro field="billType">
                <p:option type="mbillType"></p:option>
            </p:pro>
            
            <p:pro field="customerType">
                <p:option type="mcustomerType"></p:option>
            </p:pro>
            
            <p:pro field="certificate" innerString="size=60"/>
            
            <p:pro field="request">
                <p:option type="requestType"></p:option> 
            </p:pro>
            
            <p:pro field="appraisalType">
                <p:option type="appraisalType"></p:option>
            </p:pro>
            
            <p:pro field="gujiaType" innerString="style='width:240px'">
                <p:option type="gujiaType"></p:option>
            </p:pro>
            
            <p:pro field="appType">
                <option value="">--</option>
                <p:option type="mappType"></p:option>
            </p:pro>
            
            
            <p:cell title="��������">
            <c:forEach items="${makeFileLWrapList_All}" var="item" varStatus="vs">
            ����${item.tokenId}���ڡ�${item.name}��<input type="button" value="&nbsp;���߲鿴&nbsp;" name="log_g${vs.index}" id="log_g${vs.index}"
                   class="button_class" onclick="viewTemplate('${item.path}')"><br> 
               </c:forEach>
            </p:cell> 
            
            <p:cell title="������־">
              <input type="button" value="&nbsp;������־&nbsp;" name="log_b" id="log_b"
                    class="button_class" onclick="queryLog()">
            </p:cell> 

		</p:table>
		
		
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="99%" rightWidth="1%">
		<div align="right"><input type="button" class="button_class" id="return_b" name="return_b"
		 value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" accesskey="O"
			onclick="javascript:history.go(-1)">&nbsp;&nbsp;
		</div>
	</p:button>
</p:body>
</form>
</body>
</html>

