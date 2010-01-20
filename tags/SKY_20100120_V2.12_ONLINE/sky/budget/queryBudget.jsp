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
		 title: 'Ԥ���б�',
		 url: '../budget/budget.do?method=queryBudget',
		 colModel : [
		     {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lname={name} status1={status} etype={type}>', width : 40, sortable : false, align: 'center'},
		     {display: 'Ԥ��', name : 'name', width : '12%', sortable : false, align: 'left'},
		     {display: '�ύ��', name : 'stafferName', width : '5%', sortable : false, align: 'left'},
		     {display: '״̬', name : 'status', width : '5%', sortable : false, align: 'left',cc: 'budgetStatus'},
		     {display: 'Ԥ��/ʹ��', name : 'stotal', width : '18%', content : '{stotal}/{srealMonery}', sortable : true, cname: 'total'},
		     {display: 'Ԥ��ʱ��', name : 'beginDate', content : '{beginDate}��{endDate}', width : '18%', sortable : true, align: 'left', cname: 'beginDate'},
		     {display: '����', name : 'type', width : '8%', sortable : true, align: 'left', cc: 'budgetType'},
		     {display: '����', name : 'level', width : '8%', sortable : true, align: 'left', cc: 'budgetLevel'},
		     {display: '��Ԥ��', name : 'parentName', width : '8%', sortable : true, align: 'left'},
		     {display: 'Ԥ�㲿��', name : 'budgetDepartment', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../budget/budget.do?method=findBudget&update=1&id={id}>', end : '</a>'},
		     parentName : {begin : '<a href=../budget/budget.do?method=findBudget&update=1&id={parentId}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add1', caption: '���ӹ�˾Ԥ��', bclass: 'add', onpress : addRootBean, auth: '0504'},
		     {id: 'add', bclass: 'add', caption: '���Ӳ���Ԥ��', onpress : addBean, auth: '0502'},
		     {id: 'update', bclass: 'update', onpress : updateBean, auth: '0502'},
		     {id: 'del', bclass: 'delete', onpress : delBean, auth: '0502'},
		     {id: 'pass', caption: 'ͨ������Ԥ��', bclass: 'pass', onpress : doPass, auth: '0503'},
		     {id: 'reject', caption: '���ز���Ԥ��', bclass: 'reject', onpress : doReject, auth: '0503'},
		     {id: 'pass1', caption: 'ͨ����˾Ԥ��', bclass: 'pass', onpress : doPass1, auth: '0505'},
             {id: 'reject1', caption: '���ع�˾Ԥ��', bclass: 'reject', onpress : doReject1, auth: '0505'},
		     {id: 'log', bclass: 'search', caption: '��Ԥ��',  onpress : subBean},
		     {id: 'log', bclass: 'search', caption: '�����־',  onpress : logBean},
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
		 callBack: $callBack //for firefox load ext att
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
}

function $callBack()
{
    loadForm();
    
    $.highlight($("#mainTable").get(0), '����', 'red');
    
    $.highlight($("#mainTable").get(0), 'ͨ��', 'blue');
    
}

</script>
</head>
<body onload="load()" class="body_class">
<p:cache />
<p:pop title="�����벵��ԭ��" id="rejectReson"/>

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