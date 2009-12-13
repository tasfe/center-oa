<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="Ԥ����־����" link="true" guid="true" cal="false" />
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

function load()
{
	 guidMap = {
		 title: 'Ԥ����־�б�',
		 url: '../budget/budget.do?method=queryBudgetLog',
		 colModel : [
		     {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, sortable : false, align: 'center'},
		     {display: 'Ԥ��', name : 'budgetName', width : '12%', sortable : false, align: 'left'},
		     {display: '�ύ��', name : 'stafferName', width : '5%', sortable : false, align: 'left'},
		     {display: 'Ԥ����', name : 'feeItemName', width : '8%', sortable : true},
		     {display: '����', name : 'locationName', width : '10%'},
		     {display: '���', name : 'billId', width : '10%'},
		     {display: '���', name : 'smonery', width : '5%'},
		     {display: 'ʹ��ǰ', name : 'sbeforemonery', width : '5%'},
		     {display: 'ʹ�ú�', name : 'saftermonery', width : '5%'},
		     {display: 'ʱ��', name : 'logTime', width : 'auto', sortable : true}
		     ],
		 extAtt: {
		     //budgetName : {begin : '<a href=../budget/budget.do?method=findBudgetApply&id={id}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'search', bclass: 'search', onpress : doSearch}
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


function doSearch()
{
    window.common.qmodal('../admin/query.do?method=popCommonQuery&key=queryBudgetLog');
}


</script>
</head>
<body onload="load()" class="body_class">
<p:cache />

<p:message />
<table id="mainTable" style="display: none"></table>
</body>