<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="ָ��������" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     guidMap = {
         title: 'ָ������б�',
         url: '../credit/credit.do?method=queryCreditItem',
         colModel : [
             {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lper={per} ltype={type}>', width : 40, align: 'center'},
             {display: 'ָ��', name : 'name', width : '30%'},
             {display: '����', name : 'per', width : '10%', toFixed: 2},
             {display: '��߷�', name : 'point', width : '10%'},
             {display: '����', name : 'type', width : '15%', cc: 'creditType'},
             {display: '�������֮��', name : 'perAmount', width : 'auto'}
             ],
         extAtt: {
             
         },
         buttons : [
            {id: 'update', bclass: 'update', caption: '�޸ı���', onpress : updateBean, auth: 'true'}
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
    
    highlights($("#mainTable").get(0), ['��ָ̬��'], 'blue');
    highlights($("#mainTable").get(0), ['��ָ̬��'], 'red');
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').ltype == 0)
    $.messager.prompt('�޸ı���', '���������(ֻ��������)', getRadio('checkb').lper,
	    function(value, isOk)
	    {
	        if (isOk)
	        if (isFloat(value))
	        $ajax('../credit/credit.do?method=updateCreditItemPer&id=' + $$('checkb') + '&newPer=' + value, callBackFun);
	        else
            $error('ֻ����������');           
	    });
	else
    $error('���ܲ���');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>