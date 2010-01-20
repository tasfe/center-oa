<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="ָ�����" link="true" guid="true" cal="true" dialog="true" />
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
         title: 'ָ�����б�',
         url: '../credit/credit.do?method=queryCreditItemThr',
         colModel : [
             {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lpid={pid}>', width : 40, align: 'center'},
             {display: '��������', name : 'name', width : '30%'},
             {display: '�÷�', name : 'per', width : '10%', sortable : true, toFixed: 2},
             {display: '����', name : 'face', width : '10%', cc: 'creditItemFace'},
             {display: '����', name : 'indexPos', width : '10%'},
             {display: '����', name : 'pName', sortable : true, cname: 'pid', width : 'auto'}
             ],
         extAtt: {
             name : {begin : '<a href=../credit/credit.do?method=findCreditItemThr&id={id}>', end : '</a>'}
         },
         buttons : [
            {id: 'add', bclass: 'add', onpress : addBean, auth: 'true'},
            {id: 'update', bclass: 'update', onpress : updateBean, auth: 'true'},
            {id: 'del', bclass: 'del', onpress : delBean, auth: 'true'},
            {id: 'search', bclass: 'search', onpress : doSearch, auth: 'true'}
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
    highlights($("#mainTable").get(0), ['����ָ��', '�ٷ���', '��ָ̬��'], 'blue');
    highlights($("#mainTable").get(0), ['����ָ��', 'ʵ��ֵ', '��ָ̬��'], 'red');
}

function addBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        $l('../credit/credit.do?method=preForAddCreditItemThr&pid=' + getRadio('checkb').lpid);
    }
    else
    $error('���ܲ���');
}


function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {    
        $l('../credit/credit.do?method=findCreditItemThr&id=' + $$('checkb'));
    }
    else
    $error('���ܲ���');
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if(window.confirm('ȷ��ɾ��?'))    
        $ajax('../credit/credit.do?method=deleteCreditItemThr&id=' + $$('checkb'), callBackFun);
    }
    else
    $error('���ܲ���');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryCreditItemThr');
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