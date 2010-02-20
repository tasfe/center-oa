<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="���Ʋ�Ʒ����" link="true" guid="true" cal="true" dialog="true" />
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
         title: '���Ʋ�Ʒ�����б�',
         url: '../make/make.do?method=querySelfMake',
         colModel : [
             {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status} lposition={position}>', width : 40, align: 'center'},
             {display: '��ʶ', name : 'id', width : '15%'},
             {display: '����', name : 'title', width : '15%'},
             {display: '����', name : 'token', content: '��{status}��', width : '8%'},
             {display: '����', name : 'statusName', width : '8%'},
             {display: 'λ��', name : 'positionName', width : '8%'},
             {display: '״̬', name : 'endType', width : '8%', cc: 'exceptionReason'},
             {display: '������', name : 'createrName', width : '6%'},
             {display: '��ǰ������', name : 'handerName', width : '6%'},
             {display: '����', name : 'type', width : '8%', cc: 'makeType'},
             {display: '����ʱ��', name : 'logTime', sortable : true, width : 'auto'}
             ],
         extAtt: {
             title : {begin : '<a href=../make/make.do?method=findMake&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', onpress : addBean, auth: 'true'},
             {id: 'update', bclass: 'update',  onpress : updateBean, auth: 'true'},
             {id: 'del', bclass: 'del',  onpress : delBean, auth: 'true'},
             {id: 'log', bclass: 'search', caption: '������־', onpress : doLog, auth: 'true'},
             {id: 'search', bclass: 'search', onpress : doSearch}
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
    
    highlights($("#mainTable").get(0), ['����', '��9999��'], 'blue');
    highlights($("#mainTable").get(0), ['ҵ��Ա'], 'red');
}


function addBean(opr, grid)
{
    $l('../make/addMake01.jsp');
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') 
        && getRadio('checkb').lstatus == 1 && getRadio('checkb').lposition == 11)
    {    
        if(window.confirm('ȷ��ɾ������?'))    
        $ajax('../make/make.do?method=delMake&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('���ܲ���');
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') 
        && getRadio('checkb').lstatus == 1 && getRadio('checkb').lposition == 11)
    $l('../make/make.do?method=findMake&update=1&id=' + getRadioValue('checkb'));
    else
    $error('���ܲ���');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=querySelfMake');
}

function doLog()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
        window.common.modal('../admin/pop.do?method=rptQueryLog&fk=' + getRadioValue('checkb'));
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