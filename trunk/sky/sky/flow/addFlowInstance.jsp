<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="����ʵ��" cal="true" />
<link rel="stylesheet" href="../js/plugin/accordion/accordion.css" />
<script type="text/javascript" src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/accordion/jquery.accordion.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>

<script type="text/javascript">
jQuery().ready(function(){
	
	jQuery('#flowDiv').accordion({
		autoheight: true
	});
	
	var accordions = jQuery('#flowDiv');
	
	accordions.accordion("activate", ${currentIndex});
	
	<c:forEach items="${bean.tokenVOs}" var="item" varStatus="vs">
	<c:if test="${vs.index != currentIndex}">
	$('#sub_div_${vs.index}').block({ message: null }); 
	</c:if>
	</c:forEach>
});

function editTemplate(path) 
{
    if (window.ActiveXObject)
    {
        var openDocObj = new ActiveXObject("SharePoint.OpenDocuments.2");
    } 
    else
    {
        alert('��ʹ��IE���в���');
        return;
    }
    
    openDocObj.editDocument("${eurl}" + path); 
    
}

function viewTemplate(path) 
{
    if (window.ActiveXObject)
    {
        var openDocObj = new ActiveXObject("SharePoint.OpenDocuments.2");
    } 
    else
    {
        alert('��ʹ��IE���в���');
        return;
    }
    
    openDocObj.ViewDocument("${rurl}" + path); 
}

function save()
{
    $O('operation').value = 0;
    
    submit('ȷ����������ʵ��?');
}

function submits()
{
    $O('operation').value = 1;
    
    submit('ȷ���ύ����ʵ��?');
}

var urlMap = 
{
"2" : "../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1",
"1" : "../admin/pop.do?method=rptQueryStaffer2&selectMode=1",
"0" : "../admin/pop.do?method=rptQueryStaffer2&selectMode=1",
"999" : ""
};

function selectNext(index, pid)
{
    window.common.modal(urlMap[index] + '&mode=' + index + '&pid=' + pid);
}

function getStaffers(oo)
{
    if (oo.length > 0)
    {
        var item = oo[0];
        $O("processer").value = item.pname;
        $O("processId").value = item.value;
    }
}

function pass()
{
    
}

function reject()
{
    
}

function rejectAll()
{
    
}

function exends()
{
    
}

function unload()
{
    if ($$('operation') == '')
    window.common.getEvent().returnValue="ϵͳ�����Զ����������ʵ��,ȷ������?";
}

function load()
{
    if (!window.ActiveXObject)
    {
        alert('OFFICE�ؼ�δ����,��鿴����');
    }
}

</script>
</head>
<body class="body_class" onbeforeunload="unload()" onload="load()">
<form name="formEntry" action="../flow/instance.do?method=addFlowInstance" 
enctype="multipart/form-data"  method="post"><input type="hidden"
    name="id" value="${instanceId}"/> <input type="hidden"
    name="processId" value=""/> <input type="hidden"
    name="flowId" value="${flowId}"/> <input type="hidden"
    name="operation" value=""/> 
<div class="basic" id="flowDiv">
<c:forEach items="${bean.tokenVOs}" var="item" varStatus="vs">
<c:if test="${vs.first}">
<a>${item.name}:</a>
<div id="sub_div_${vs.index}"><p:class
	value="com.china.center.oa.flow.bean.FlowInstanceBean" opr="0"/>
<table width='100% border=' 0' cellpadding='0' cellspacing='0'
	class='table1'>
	<tr>
		<td>
		<p:table cells="1">
			<p:cells celspan="1" title="���̶���">
		     ${bean.name}
		     </p:cells>

			<p:pro field="title" innerString="size=60" />
			
			<p:pro field="liminal" innerString="size=60" value="0.0"/>

			<p:cell title="����">
				<input type="file" name="attchment" size="60" class="button_class"
					contenteditable="false"/>&nbsp; 
				<font color="blue">����ʹ��ѹ���ļ�,������������</font>
			</p:cell>

			<p:pro field="description" innerString="cols=80 rows=3" />
			
			<p:cells celspan="1" title="ģ��༭">
            <c:forEach items="${templates}" var="itemEach">
            <c:if test="${itemEach.readonly == 1}">
            ${itemEach.name}��&nbsp;&nbsp;<input class="button_class"
                    type="button" name="view_b" onclick=viewTemplate('${itemEach.path}') value="����Ԥ��"/>&nbsp;&nbsp;
            </c:if>
            <c:if test="${itemEach.readonly == 0}">
            ${itemEach.name}��&nbsp;&nbsp;<input class="button_class"
                    type="button" name="view_b" onclick=editTemplate('${itemEach.path}') value="���߱༭"/>&nbsp;&nbsp;
            </c:if>
            &nbsp;&nbsp;
            </c:forEach>
            </p:cells>
            
            <p:cells celspan="1" title="�ύ��">
            <input type="text" name="processer" readonly="readonly" style="cursor: pointer;" oncheck="notNone" head="�»�������"/>&nbsp;
            <font color=red>*</font>
            <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectNext(${item.pluginType}, '${item.handleVOs[0].processer}')">&nbsp;&nbsp;
            </p:cells>
            
            <p:cells celspan="0" align="right">
            <input class="button_class"
                    type="button" name="save_b" onclick=save() value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"/>&nbsp;&nbsp;
            <input class="button_class"
                    type="button" name="save_s" onclick=submits() value="&nbsp;&nbsp;�� ��&nbsp;&nbsp;"/>
            </p:cells>
            
		</p:table>
		</td>
	</tr>
</table>
</div>
</c:if>

<c:if test="${!vs.first}">
<a>${item.name}:</a>
<div id="sub_div_${vs.index}">
<table width='100% border=' 0' cellpadding='0' cellspacing='0'
    class='table1'>
    <tr>
        <td>
        <p:table cells="1">
            <p:cells celspan="1" title="���̶���">
             ${bean.name}
             </p:cells>
            
            <p:cells celspan="1" title="ģ��༭">
            <c:forEach items="${templates}" var="itemEach">
            <c:if test="${itemEach.readonly == 1}">
            ${itemEach.name}��&nbsp;&nbsp;<input class="button_class"
                    type="button" name="view_b" onclick=viewTemplate('${itemEach.path}') value="����Ԥ��"/>&nbsp;&nbsp;
            </c:if>
            <c:if test="${itemEach.readonly == 0}">
            ${itemEach.name}��&nbsp;&nbsp;<input class="button_class"
                    type="button" name="view_b" onclick=editTemplate('${itemEach.path}') value="���߱༭"/>&nbsp;&nbsp;
            </c:if>
            &nbsp;&nbsp;
            </c:forEach>
            </p:cells>
            
            <c:if test="${!vs.first}">
            <p:cells celspan="1" title="�ύ��">
            <input type="text" name="processer_${vs.index}" readonly="readonly" style="cursor: pointer;"/>&nbsp;
            <font color=red>*</font>
            <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectNext(${item.pluginType}, '${item.handleVOs[0].processer}')">&nbsp;&nbsp;
            </p:cells>
            </c:if>
            
            <p:cells celspan="0" align="right">
            <input class="button_class"
                    type="button" name="pass_b" onclick=pass() value="&nbsp;&nbsp;ͨ ��&nbsp;&nbsp;"/>&nbsp;&nbsp;
            <c:if test="${item.operation.reject == 1}">
            <input class="button_class"
                    type="button" name="reject_b" onclick=reject() value="&nbsp;&nbsp;���ص���һ��&nbsp;&nbsp;"/>&nbsp;&nbsp;
            </c:if>
            
            <c:if test="${item.operation.rejectAll == 1}">
            <input class="button_class"
                    type="button" name="rejectAll_b" onclick=rejectAll() value="&nbsp;&nbsp;���ص���ʼ&nbsp;&nbsp;"/>&nbsp;&nbsp;
            </c:if>
            
            <c:if test="${item.operation.exends == 1}">
            <input class="button_class"
                    type="button" name="exends_b" onclick=exends() value="&nbsp;&nbsp;�쳣����&nbsp;&nbsp;"/>&nbsp;&nbsp;
            </c:if>
            </p:cells>
            
        </p:table>
        </td>
    </tr>
</table>
</div>
</c:if>
</c:forEach>

<c:if test="${bean.mode == 1}">
<a>ģ��鿴:</a>
<div>
<p>&nbsp;</p>
<c:forEach items="${templates}" var="itemEach">
<c:if test="${itemEach.readonly == 1}">
${itemEach.name}��&nbsp;&nbsp;<input class="button_class"
        type="button" name="view_bxx" onclick=viewTemplate('${itemEach.path}') value="����Ԥ��">&nbsp;&nbsp;
</c:if>
&nbsp;&nbsp;
</c:forEach>
<p>&nbsp;</p>
</div>
</c:if>

<a>������־:</a>
<div>
<table width="100%" border="0" cellspacing='1' id="tables">
  <tr align="center" class="content0">
      <td width="15%" align="center">ʱ��</td>
      <td width="15%" align="center">��������</td>
      <td width="15%" align="center">�󻷽�</td>
      <td width="10%" align="center">������ʽ</td>
      <td width="10%" align="center">�ύ��</td>
      <td width="35%" align="center">��ע</td>
  </tr>

  <c:forEach items="${logsVO}" var="item" varStatus="vs">
      <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
          <td align="center">${item.logTime}</td>
          <td align="center">${item.tokenName}</td>
          <td align="center">${item.nextTokenName}</td>
          <td align="center">${my:get('instanceOper', tem.oprMode)}</td>
          <td align="center">${item.stafferName}</td>
          <td align="center">${item.opinion}</td>
      </tr>
  </c:forEach>
</table>
</div>
</form>
</body>
</html>