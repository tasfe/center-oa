<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="����ģ�����" link="true" guid="true" cal="false"/>
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
         title: '����ģ���б�',
         url: '../make/make.do?method=queryMakeTemplate',
         colModel : [
             {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id}>', width : 40, align: 'center'},
             {display: '����', name : 'name', width : '20%'},
             {display: '����', name : 'description', sortable : false, width : 'auto'}
             ],
         extAtt: {
             name : {begin : '<a href=../make/make.do?method=findMakeTemplate&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'update', bclass: 'update', onpress : updateBean, auth: '0802'}
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


function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
        $l('../make/make.do?method=findMakeTemplate&id=' + getRadioValue('checkb'));
    else
        $error('���ܲ���');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../make/make.do" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>