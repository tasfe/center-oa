<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="�ͻ�������ϸ" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
	 guidMap = {
		 title: '�ͻ�������ϸ',
		 url: '../credit/customer.do?method=queryCustomerCredit&cid=${param.targerId}',
		 colModel : [
		     {display: 'ʱ��', name : 'logTime', width : '12%', sortable : true, align: 'left'},
		     {display: 'ָ��', name : 'pitemName', width : '15%'},
		     {display: 'ָ����', name : 'itemName', width : '20%'},
		     {display: '����', name : 'valueName', width : '10%'},
		     {display: '��־', name : 'log', width : 'auto'},
		     {display: 'ָ������', name : 'ptype', width : '10%', cc: 'creditType'},
		     {display: 'ָ��ֵ', name : 'val', width : '10%', toFixed: 2, sortable : true}
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
    
    highlights($("#mainTable").get(0), ['��ָ̬��'], 'red');
}
 
function doBack()
{
    window.history.go(-1);
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryCustomerCredit');
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