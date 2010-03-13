<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="����ʵ������" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var titleMap = {"0" : "�ҵ�ʵ��", "1" : "�ҵ�����", "2" : "�ҵĲ���", "3" : "��ʷ����"};

var sMap = {"0" : "������", "1" : "������", "2" : "������", "3" : "����/������"};

var buttonsMap = 
{
    "0" : 
    [
        {id: 'update', bclass: 'update',  caption : '�༭', onpress : updateBean},
        {id: 'submits', bclass: 'pass', caption : '�ύ',  onpress : configBean},
        {id: 'del', bclass: 'del',  onpress : delBean},
        {id: 'search', bclass: 'search',  onpress : doSearch}
    ],
    "1" : 
    [
        {id: 'update', bclass: 'update',  caption : '��������', onpress : updateBean2},
        {id: 'search', bclass: 'search',  onpress : doSearch}
    ],
    "2" : 
    [
        {id: 'search', bclass: 'search',  onpress : doSearch}
    ],    
    "3" : 
    [
        {id: 'search', bclass: 'search',  onpress : doSearch}
    ]
};
var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
      
	 guidMap = {
		 title: titleMap['${param.operation}'],
		 url: '../flow/instance.do?method=queryFlowInstance&operationMode=${param.operation}',
		 colModel : [
		     {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={instanceId} sstatus={status}>', width : 40, align: 'center'},
		     {display: '����', name : 'title', width : '30%'},
		     {display: sMap['${param.operation}'], name : 'stafferName', sortable : false, width : '10%'},
		     {display: '��ǰ����', name : 'tokenName', width : '15%'},
		     {display: '���̶���', name : 'flowName', sortable : true, cname: 'FlowDefineBean.name', width : '15%'},
		     {display: '����ʱ��', name : 'logTime', sortable : true, width : 'auto'}
		     ],
		 extAtt: {
		     title : {begin : '<a href=../flow/instance.do?method=findFlowInstance&id={instanceId} title=�鿴����ʵ��>', end : '</a>'}
		 },
		 buttons : buttonsMap['${param.operation}'],
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
    
    $.highlight($("#mainTable").get(0), '��������', 'blue');
}
 

function addBean(opr, grid)
{
    $l('../flow/addFlowDefine.jsp');
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').sstatus == 0 && window.confirm('ȷ��ɾ������ʵ��?'))
    $ajax('../flow/instance.do?method=deleteFlowInstance&id=' + getRadioValue('checkb'), callBackFun);
}

function dropBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').sstatus == 1 && window.confirm('ȷ����������ʵ��?'))
    $ajax('../flow/flow.do?method=dropFlowDefine&id=' + getRadioValue('checkb'), callBackFun);
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').sstatus == 0)
    {
        $l('../flow/instance.do?method=findFlowInstance&update=1&id=' + getRadioValue('checkb'));
    }
}

function updateBean2(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').sstatus != 0)
    {
        $l('../flow/instance.do?method=findFlowInstance&update=1&id=' + getRadioValue('checkb'));
    }
}

function configBean()
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').sstatus == 0)
    {
        $.blockUI({ message: $('#dataDiv'),css:{width: '40%'}});
    }
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryFlowInstance${param.operation}');
}

function $process()
{
    if (getRadio('checkb') && getRadioValue('checkb') && eCheck([$O('totalTokens')]))
    {
        var sid = $$('totalTokens');
    
        $l('../flow/flow.do?method=preForConfigToken&id=' + $$('checkb') + '&totalTokens=' + sid);
    }
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../flow/template.do" method="post">
<p:cache></p:cache>
</form>

<div id="dataDiv" style="display:none">
<p align='left'><label><font color=""><b>�����뻷����</b></font></label></p>
<p><label>&nbsp;</label></p>
<input type="text" id="totalTokens" value="2" oncheck="isNumber;range(2, 99)" style="width: 85%"/>
<p><label>&nbsp;</label></p>
<p>
<input type='button' value='&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;' id='div_b_ok1' class='button_class' onclick='$process()'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' id='div_b_cancle' value='&nbsp;&nbsp;�� ��&nbsp;&nbsp;' class='button_class' onclick='$close()'/>
</p>
<p><label>&nbsp;</label></p>
</div>

<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>