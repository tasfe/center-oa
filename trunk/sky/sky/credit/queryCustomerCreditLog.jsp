<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="�ͻ�������־" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
	 guidMap = {
		 title: '�ͻ�������־',
		 url: '../credit/customer.do?method=queryCustomerCreditLog&cid=${param.targerId}',
		 colModel : [
		     {display: '����', name : 'id', width : '10%', sortable : true, align: 'left'},
		     {display: 'ʱ��', name : 'logTime', width : '15%', sortable : true, align: 'left'},
		     {display: '��־', name : 'log', width : 'auto'},
		     {display: '���ֵ', name : 'val', width : '10%', toFixed: 2}
		     ],
		 extAtt: {
		 },
		 buttons : [
		     {id: 'search', bclass: 'search', onpress : doSearch, auth: 'true'},
		     {id: 'back', bclass: 'back', caption:'������һҳ', onpress : doBack}
		     ],
		 usepager: true,
		 useRp: true,
		 queryMode: 1,
		 auth: window.top.topFrame.gAuth,
		 cache: 0,
		 height: DEFAULT_HEIGHT,
		 queryCondition: null,
		 showTableToggleBtn: true,
		 def: allDef,
		 callBack: $callBack
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
}

function $callBack()
{
    loadForm();
}
 
function doBack()
{
    window.history.go(-1);
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryCustomerCreditLog');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../examine/city.do" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>