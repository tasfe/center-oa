<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<p:link title="���Ʋ�Ʒ" cal="true"/>
<link rel="stylesheet" href="../js/plugin/accordion/accordion.css" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/accordion/jquery.accordion.js"></script>
<script language="JavaScript" src="../js/plugin/dialog/jquery.dialog.js"></script>
<link rel="stylesheet" type="text/css" href="../js/plugin/dialog/css/dialog.css"/>
<script language="JavaScript" src="../js/oa/make/02.js"></script>
<script language="javascript">

jQuery().ready(function(){
    
    jQuery('#flowDiv').accordion({
        autoheight: false
    });
    
    var accordions = jQuery('#flowDiv');
    
    accordions.accordion("activate", 3);
    
});

var makePosition = ${make.position};

var role = '${position.role}';

var makeid = '${make.id}';

var baseURL = '${eurl}';

var gtid;
function callHelp(id)
{
    gtid = id;
    
    window.open('../help/flow/makeflow.html', "myOpen");
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../make/make.do" method="post"><input
	type="hidden" name="method" value="passMake">
<input type="hidden" name="cid" value=""> 
<input type="hidden" name="rejectTokenId" value=""> 
<input type="hidden" name="id" value="${make.id}"> 

<div class="basic" id="flowDiv">

<div id="title_div">
<a><font color=blue>����${make.status}��${token.name}��--${position.name}(${position.ends == 1 ? "<font color=red>������</font>" : "�м价"})
(${token.id == 15 ? "<font color=red>����ʵ����ͨ�����۵�����,������������ǽ����˶�������</font>" : ""})</font></a>
</div>

        <p:class value="com.china.center.oa.customize.make.bean.Make01Bean" opr="2"/>

        
        <div id="base_div">
        <a><font>���Ʋ�Ʒ��Ϣ</font></a>
        <table width='100% border=' 0' cellpadding='0' cellspacing='0'
        class='table1'>
        <tr>
        <td>
        <p:table cells="1">
            
            <p:cell title="��ʶ">
             ${make.id}
            </p:cell>
            
            <p:cell title="����">
             ${make.title}
            </p:cell>  
            
            <p:cell title="����">
              <select class="select_class" name="type" values="${make.type}" autodisplay="1">
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
            
            <p:pro field="cdes" innerString="rows=5 cols=55 oncheck='minLength(100)'" outString="(���ݲ�������50��)" />
            
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

        </p:table>
        </td>
        </tr>
        </table>
        
        </div>
        
        <div id="attchment_div">
            <a><font color=red>�����鿴</font></a>
            <table width='100%' border='0' cellpadding='0' cellspacing='0'
            class='table1'>
            <tr>
            <td>
            <p:table cells="1">
                <p:cell title="�����鿴">
            <c:forEach items="${makeFileLWrapList_All}" var="item" varStatus="vs">
            ����${item.tokenId}���ڡ�${item.name}��<input type="button" value="&nbsp;���߲鿴&nbsp;" name="log_g${vs.index}" id="log_g${vs.index}"
                   class="button_class" onclick="viewTemplate('${item.path}')"><br> 
               </c:forEach>
            </p:cell> 
    
            </p:table>
            </td>
            </tr>
            </table>
        </div>
        
        <div id="apply_div">
            <a><font color=red>�ҵĴ���</font></a>
            <table width='100%' border='0' cellpadding='0' cellspacing='0'
            class='table1'>
            <tr>
            <td>
            <p:table cells="1">
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
                  <textarea name="reason" rows=3 cols=55 oncheck="notNone;" head="���"></textarea>  
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
                 
                 <p:tr>
                <input type="button" class="button_class" id="ok_b" name="ok_b"
         value="&nbsp;&nbsp;ͨ ��(O)&nbsp;&nbsp;" accesskey="O"
            onclick="addBean()">&nbsp;&nbsp;
        <c:if test="${position.ends != 1}">
        <input type="button" class="button_class" id="reject_b" name="reject_b"
         value="&nbsp;&nbsp;�� ��(R)&nbsp;&nbsp;" accesskey="R"
            onclick="rejectMake()">&nbsp;&nbsp;
        </c:if>
        <c:if test="${position.ends == 1}">
        <input type="button" class="button_class" id="reject_b" name="reject_b"
         value="&nbsp;&nbsp;���ڲ���(E)&nbsp;&nbsp;" accesskey="E"
            onclick="rejectTokenMake()">&nbsp;&nbsp;
        <input type="button" class="button_class" id="reject_b" name="reject_b"
         value="&nbsp;&nbsp;�쳣����(T)&nbsp;&nbsp;" accesskey="T"
            onclick="exceptionMake()">&nbsp;&nbsp;
        </c:if>
        <input type="button" class="button_class" id="help_b" name="help_b"
                 value="&nbsp;&nbsp;����(H)&nbsp;&nbsp;" accesskey="H"
                    onclick="callHelp('t${position.id}')"/>
                 </p:tr>
    
            </p:table>
            </td>
            </tr>
            </table>
        </div>
        
</div>

<div id="dlg1" title="ѡ�񲵻ص�����ʷ����" style="width:320px;">
    <div style="padding:20px;height:200px;" id="dialog_inner" title="">
   </div>
</div>

<div id="dlg2" title="ѡ���쳣����ԭ��" style="width:320px;">
    <div style="padding:20px;height:200px;" id="dialog_inner2" title="">
    
    <input type=radio name=exceptionReason value="1"/> ����δ��<br/>
    <input type=radio name=exceptionReason value="2"/> ���δ��<br/>
    <input type=radio name=exceptionReason value="3"/> �ͻ�����仯<br/>
    <input type=radio name=exceptionReason value="4"/> ��������<br/>
    <input type=radio name=exceptionReason value="5"/> ������ʱ�䲻��<br/>
    <input type=radio name=exceptionReason value="6"/> �������������������<br/>
    <input type=radio name=exceptionReason value="99"/> ����<br/>
   </div>
</div>
</form>

</body>
</html>

