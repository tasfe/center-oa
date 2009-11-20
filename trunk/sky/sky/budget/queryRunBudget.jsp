<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="Ԥ�����" link="true" guid="true" cal="false" />
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/oa/queryBudget.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

function load()
{
	 guidMap = {
		 title: '����Ԥ���б�',
		 url: '../budget/budget.do?method=queryRunBudget',
		 colModel : [
		     {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lname={name} status1={status} etype={type}>', width : 40, sortable : false, align: 'center'},
		     {display: 'Ԥ��', name : 'name', width : '12%', sortable : false, align: 'left'},
		     {display: '�ύ��', name : 'stafferName', width : '5%', sortable : false, align: 'left'},
		     {display: '״̬', name : 'status', width : '5%', sortable : false, align: 'left',cc: 'budgetStatus'},
		     {display: 'Ԥ����', name : 'stotal', width : '10%', sortable : true, align: 'left', cname: 'total'},
		     {display: 'Ԥ��ʱ��', name : 'beginDate', content : '{beginDate}��{endDate}',width : '18%', sortable : true, align: 'left', cname: 'beginDate'},
		     {display: '����', name : 'type', width : '8%', sortable : true, align: 'left', cc: 'budgetType'},
		     {display: '����', name : 'level', width : '8%', sortable : true, align: 'left', cc: 'budgetLevel'},
		     {display: '��Ԥ��', name : 'parentName', width : '10%', sortable : true, align: 'left'},
		     {display: 'Ԥ�㲿��', name : 'budgetDepartment', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../budget/budget.do?method=findBudget&update=1&id={id}>', end : '</a>'},
		     parentName : {begin : '<a href=../budget/budget.do?method=findBudget&update=1&id={parentId}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'update', bclass: 'update', caption: '���Ԥ��', onpress : updateBean2, auth: '050601'},
		     {id: 'update', bclass: 'update', caption: '׷��Ԥ��', onpress : updateBean3, auth: '050601'},
		     {id: 'search', bclass: 'search', onpress : doSearch1}
		     ],
		 usepager: true,
		 useRp: true,
		 queryMode: 1,
		 cache: 0,
		 auth: window.top.topFrame.gAuth,
		 showTableToggleBtn: true,
		 height: DEFAULT_HEIGHT,
		 def: allDef,
		 callBack: $callBack //for firefox load ext att
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
}

function $callBack()
{
    loadForm();
    
    $.highlight($("#mainTable").get(0), 'ͨ��', 'blue');
}

</script>
</head>
<body onload="load()" class="body_class">
<p:cache />

<div id="logDiv" style="display:none">
<p align='left'><label><font color=""><b>������־:</b></font></label></p>
<p><label>&nbsp;</label></p>
<div id="logD" align='left'>
</div>
<p><label>&nbsp;</label></p>
<p>
<input type='button' id='div_b_cancle' value='&nbsp;&nbsp;�� ��&nbsp;&nbsp;' class='button_class' onclick='$cancle()'/>
</p>
<p><label>&nbsp;</label></p>
</div>

<p:message />
<table id="mainTable" style="display: none"></table>
</body>