<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="预算管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../budget_js/queryBudget.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

function load()
{
     preload();
     
	 guidMap = {
		 title: '预算列表',
		 url: '../budget/budget.do?method=queryBudget',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lname={name} status1={status} etype={type}>', width : 40, sortable : false, align: 'center'},
		     {display: '预算', name : 'name', width : '12%', sortable : false, align: 'left'},
		     {display: '提交人', name : 'stafferName', width : '5%', sortable : false, align: 'left'},
		     {display: '状态', name : 'status', width : '5%', sortable : false, align: 'left',cc: 'budgetStatus'},
		     {display: '预算/使用', name : 'stotal', width : '18%', content : '{stotal}/{srealMonery}', sortable : true, cname: 'total'},
		     {display: '预算时间', name : 'beginDate', content : '{beginDate}至{endDate}', width : '18%', sortable : true, align: 'left', cname: 'beginDate'},
		     {display: '级别', name : 'type', width : '8%', sortable : true, align: 'left', cc: 'budgetType'},
		     {display: '类型', name : 'level', width : '8%', sortable : true, align: 'left', cc: 'budgetLevel'},
		     {display: '父预算', name : 'parentName', width : '8%', sortable : true, align: 'left'},
		     {display: '预算部门', name : 'budgetDepartment', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../budget/budget.do?method=findBudget&update=1&id={id}>', end : '</a>'},
		     parentName : {begin : '<a href=../budget/budget.do?method=findBudget&update=1&id={parentId}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add1', caption: '增加公司预算', bclass: 'add', onpress : addRootBean, auth: '0504'},
		     {id: 'add', bclass: 'add', caption: '增加部门预算', onpress : addBean, auth: '0502'},
		     {id: 'update', bclass: 'update', onpress : updateBean, auth: '0502'},
		     {id: 'del', bclass: 'delete', onpress : delBean, auth: '0502'},
		     {id: 'pass', caption: '通过部门预算', bclass: 'pass', onpress : doPass, auth: '0503'},
		     {id: 'reject', caption: '驳回部门预算', bclass: 'reject', onpress : doReject, auth: '0503'},
		     {id: 'pass1', caption: '通过公司预算', bclass: 'pass', onpress : doPass1, auth: '0505'},
             {id: 'reject1', caption: '驳回公司预算', bclass: 'reject', onpress : doReject1, auth: '0505'},
		     {id: 'log', bclass: 'search', caption: '子预算',  onpress : subBean},
		     {id: 'log', bclass: 'search', caption: '相关日志',  onpress : logBean},
		     {id: 'search', bclass: 'search', onpress : doSearch}
		     ],
		<p:conf/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
}

function $callBack()
{
    loadForm();
    
    $.highlight($("#mainTable").get(0), '结束', 'red');
    
    $.highlight($("#mainTable").get(0), '通过', 'blue');
    
}

</script>
</head>
<body onload="load()" class="body_class">
<p:cache />
<p:pop title="请输入驳回原因" id="rejectReson"></p:pop>
<div id="logDiv" style="display:none">
<p align='left'><label><font color=""><b>审批日志:</b></font></label></p>
<p><label>&nbsp;</label></p>
<div id="logD" align='left'>
</div>
<p><label>&nbsp;</label></p>
<p>
<input type='button' id='div_b_cancle' value='&nbsp;&nbsp;关 闭&nbsp;&nbsp;' class='button_class' onclick='$cancle()'/>
</p>
<p><label>&nbsp;</label></p>
</div>

<p:message />
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>