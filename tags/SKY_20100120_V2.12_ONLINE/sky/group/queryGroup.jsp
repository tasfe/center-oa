<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="Ⱥ�����" link="true" guid="true" cal="false"/>
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
	 guidMap = {
		 title: 'Ⱥ���б�',
		 url: '../group/group.do?method=queryGroup',
		 colModel : [
		     {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, align: 'center'},
		     {display: '����', name : 'name', width : '30%'},
		     {display: '����', name : 'type', width : 'auto', cc: 'groupType'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../group/group.do?method=findGroup&id={id}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', bclass: 'add', onpress : addBean, auth: 'true'},
		     {id: 'update', bclass: 'update',  onpress : updateBean, auth: 'true'},
		     {id: 'del', bclass: 'del',  onpress : delBean, auth: 'true'}
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
		 callBack: loadForm
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }

function addBean(opr, grid)
{
    $l('../group/addGroup.jsp?type=0');
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && window.confirm('ȷ��ɾ��Ⱥ��?'))
    $ajax('../group/group.do?method=delGroup&id=' + getRadioValue('checkb'), callBackFun);
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $l('../group/group.do?method=findGroup&update=1&id=' + getRadioValue('checkb'));
    }
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../group/group.do" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>