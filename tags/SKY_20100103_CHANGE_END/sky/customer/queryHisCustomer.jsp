<%@page contentType="text/html; charset=GBK" errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="��ʷ�ͻ�" link="true" guid="true" cal="false"/>
<script src="../js/json.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
	 guidMap = {
		 title: '�ͻ��޸���ʷ',
		 url: '../customer/customer.do?method=queryHisCustomer&id=${param.id}',
		 colModel : [
		     {display: '�ͻ�', name : 'name', width : '20%', sortable : false, align: 'left'},
		     {display: '����', name : 'code', width : '10%', sortable : false, align: 'left'},
		     {display: '����', name : 'selltype', width : '10%', sortable : false, align: 'left', cc: 101},
		     {display: '����', name : 'qqtype', width : '10%', sortable : false, align: 'left', cc: 104},
		     {display: '����', name : 'rtype', width : '10%', sortable : false, align: 'left', cc: 105},
		      {display: '�޸���', name : 'updaterName', width : '10%', sortable : false, align: 'left', cc: 106},
		     {display: 'ʱ��', name : 'loginTime', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../customer/customer.do?method=findHisCustomer&id={id} title=�鿴��ϸ>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'back', caption: '������һҳ', bclass: 'back', onpress : gback}
		     ],
		 usepager: true,
		 useRp: true,
		 queryMode: 1,
		 cache: 0,
		 showTableToggleBtn: true,
		 height: DEFAULT_HEIGHT,
		 def: allDef
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }
 
function gback()
{
    window.history.go(-1);
}
</script>
</head>
<body class="body_class" onload="load()">
<form>
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>