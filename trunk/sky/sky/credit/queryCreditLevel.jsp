<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="���õȼ�����" link="true" guid="true" cal="false" dialog="false" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     guidMap = {
         title: '���õȼ��б�',
         url: '../credit/credit.do?method=queryCreditLevel',
         colModel : [
             {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lper={per}>', width : 40, align: 'center'},
             {display: '���õȼ�', name : 'name', width : '40%'},
             {display: '�ȼ�������', name : 'min', width : '20%', toFixed: 2},
             {display: '�ȼ�������', name : 'max', width : 'auto', toFixed: 2}
             ],
         extAtt: {
             
         },
         buttons : [
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
</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>