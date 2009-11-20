<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="Ԥ������" link="true" guid="true" cal="false" />
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/oa/queryBudgetApply.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

function load()
{
	 guidMap = {
		 title: 'Ԥ�������б�',
		 url: '../budget/budget.do?method=queryBudgetApply',
		 colModel : [
		     {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lname={budgetName} status1={status}>', width : 40, sortable : false, align: 'center'},
		     {display: 'Ԥ��', name : 'budgetName', width : '25%', sortable : false, align: 'left'},
		     {display: '�ύ��', name : 'stafferName', width : '5%', sortable : false, align: 'left'},
		     {display: '��������', name : 'type', width : '8%', sortable : true, align: 'left', cc: 'budgetApplyType'},
		     {display: '״̬', name : 'status', width : '15%', sortable : false, align: 'left',cc: 'budgetApplyStatus'},
		     {display: 'ʱ��', name : 'logTime', width : 'auto', sortable : true}
		     ],
		 extAtt: {
		     budgetName : {begin : '<a href=../budget/budget.do?method=findBudgetApply&id={id}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'update', bclass: 'pass', caption: '�����ܼ��׼', onpress : cfo_pass, auth: '050602'},
		     {id: 'update1', bclass: 'reject', caption: '�����ܼವ��', onpress : cfo_reject, auth: '050602'},
		     {id: 'update', bclass: 'pass', caption: '�ܾ����׼', onpress : coo_pass, auth: '050603'},
             {id: 'update1', bclass: 'reject', caption: '�ܾ�����', onpress : coo_reject, auth: '050603'},
		     {id: 'update', bclass: 'pass', caption: '���³���׼', onpress : ceo_pass, auth: '050604'},
             {id: 'update1', bclass: 'reject', caption: '���³�����', onpress : ceo_reject, auth: '050604'}
		     ],
		 usepager: true,
		 useRp: true,
		 queryMode: 1,
		 cache: 0,
		 auth: window.top.topFrame.gAuth,
		 showTableToggleBtn: true,
		 height: DEFAULT_HEIGHT,
		 def: allDef,
		 callBack: loadForm //for firefox load ext att
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
}

</script>
</head>
<body onload="load()" class="body_class">
<p:cache />

<p:pop title="�����벵��ԭ��" id="rejectReson"/>

<p:message />
<table id="mainTable" style="display: none"></table>
</body>