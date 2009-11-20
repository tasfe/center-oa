<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="���Ʋ�Ʒ" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/jquery/jquery.js"></script>
<script language="JavaScript" src="../js/plugin/dialog/jquery.dialog.js"></script>
<link rel="stylesheet" type="text/css" href="../js/plugin/dialog/css/dialog.css">
<script language="JavaScript" src="../js/oa/make/02.js"></script>
<script language="javascript">

var makePosition = ${make.position};

var role = '${position.role}';

var makeid = '${make.id}';

var baseURL = '${eurl}';
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../make/make.do" method="post"><input
	type="hidden" name="method" value="passMake">
<input type="hidden" name="cid" value=""> 
<input type="hidden" name="rejectTokenId" value=""> 
<input type="hidden" name="id" value="${make.id}"> 


<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">���Ʋ�Ʒ����</span> &gt;&gt; �������Ʋ�Ʒ(��${make.status}��)</td>
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
            
            <p:cell title="ҵ��Ա">
             ${make.createrName}
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
			    <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectCus()">&nbsp;&nbsp; 
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
            <c:forEach items="${makeFileLWrapList}" var="item" varStatus="vs">
            <c:if test="${item.edit == true}">
               ${item.name}��<input type="button" value="&nbsp;���߱༭&nbsp;" name="log_g${vs.index}" id="log_g${vs.index}"
                   class="button_class" onclick="editTemplate('${item.path}')">&nbsp;&nbsp; 
            </c:if>
            
            <c:if test="${item.edit ==  false}">
                  ${item.name}��<input type="button" value="&nbsp;���߲鿴&nbsp;" name="log_g${vs.index}" id="log_g${vs.index}"
                   class="button_class" onclick="viewTemplate('${item.path}')">&nbsp;&nbsp; 
               </c:if>

               </c:forEach>
            </p:cell> 
            
            <p:cell title="���">
              <textarea name="reason" rows=2 cols=55 oncheck="notNone;" head="���"></textarea>  
              <font color="red">*</font>
            </p:cell> 
            
            <c:if test="${listNext ==  false}">
	            <p:cell title="�ύ��">
	              <input name="handerName" id="handerName" readonly="readonly">&nbsp;&nbsp; 
	              <input type="hidden" name="handerId" value=""> 
	              <input type="button" value="&nbsp;...&nbsp;" name="qout1" id="qout1"
	                    class="button_class" onclick="selectNext()">&nbsp;&nbsp; 
	              <font color="red">*</font>
	              <input type="button" value="&nbsp;������־&nbsp;" name="log_b" id="log_b"
	                    class="button_class" onclick="queryLog()">
	            </p:cell> 
            </c:if>
            
            <c:if test="${listNext ==  true}">
                <p:cell title="�ύ��">
                  <select class="select_class" name="handerId">
                  <option value="">--</option>
                  <c:forEach items="${stafferList}" var="item">
                  <option value="${item.id}">${item.name}</option>
                  </c:forEach>
                  </select>
                        &nbsp;&nbsp; 
                  <font color="red">*</font>
                  <input type="button" value="&nbsp;������־&nbsp;" name="log_b" id="log_b"
                        class="button_class" onclick="queryLog()">
                </p:cell> 
            </c:if>

		</p:table>
		
		
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="99%" rightWidth="1%">
		<div align="right"><input type="button" class="button_class" id="ok_b" name="ok_b"
		 value="&nbsp;&nbsp;ͨ ��&nbsp;&nbsp;" accesskey="O"
			onclick="addBean()">&nbsp;&nbsp;
		<c:if test="${position.ends != 1}">
		<input type="button" class="button_class" id="reject_b" name="reject_b"
         value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;" accesskey="R"
            onclick="rejectMake()">&nbsp;&nbsp;
        </c:if>
        <c:if test="${position.ends == 1}">
        <input type="button" class="button_class" id="reject_b" name="reject_b"
         value="&nbsp;&nbsp;���ڲ���&nbsp;&nbsp;" accesskey="E"
            onclick="rejectTokenMake()">
        </c:if>
		</div>
	</p:button>
</p:body>
<div id="dlg1" title="ѡ�񲵻ص�����ʷ����" style="width:320px;">
    <div style="padding:20px;height:200px;" id="dialog_inner" title="">
       
    </div>
</div>
</form>

</body>
</html>

