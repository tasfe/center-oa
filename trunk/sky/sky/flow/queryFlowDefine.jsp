<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="���̶������" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
      
	 guidMap = {
		 title: '���̶����б�',
		 url: '../flow/flow.do?method=queryFlowDefine',
		 colModel : [
		     {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} sstatus={status}>', width : 40, align: 'center'},
		     {display: '����', name : 'name', width : '30%'},
		     {display: '����', name : 'parentType', width : '80', cc : 'parentType'},
		     {display: 'ģʽ', name : 'mode', width : '70', cc : 'flowMode'},
		     {display: '����', name : 'type', width : '40', cc : 112},
		     {display: '״̬', name : 'status', width : '40', cc : 'flowStatus'},
		     {display: '������', name : 'stafferName', width : '15%'},
		     {display: 'ʱ��', name : 'logTime', width : 'auto', sortable : true}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../flow/flow.do?method=findFlowDefine&id={id} title=�鿴���̶���>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', bclass: 'add', onpress : addBean, auth: '0702'},
		     {id: 'update', bclass: 'update',  onpress : updateBean, auth: '0702'},
		     {id: 'update1', bclass: 'update', caption : '���û���',  onpress : configBean, auth: '0702'},
		     {id: 'update2', bclass: 'update', caption : '���ò���',  onpress : configBean1, auth: '0702'},
		     {id: 'del', bclass: 'del',  onpress : delBean, auth: '0702'},
		     {id: 'del1', bclass: 'del', caption: '����',  onpress : dropBean, auth: '0702'},
		     {id: 'del2', bclass: 'del', caption: 'ǿ�Ʒ���',  onpress : forceDropBean, auth: '0703'},
		     {id: 'search', bclass: 'search',  onpress : doSearch}
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
    
    highlights($("#mainTable").get(0), ['������', '����'], 'blue');
    
    highlights($("#mainTable").get(0), ['��ʼ', '����'], 'red');
}

function addBean(opr, grid)
{
    $l('../flow/addFlowDefine.jsp');
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').sstatus != 1 
        && window.confirm('ȷ��ɾ�����̶���?'))
    $ajax('../flow/flow.do?method=deleteFlowDefine&id=' + getRadioValue('checkb'), callBackFun);
}

function dropBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').sstatus == 1 
        && window.confirm('ȷ���������̶���?'))
    $ajax('../flow/flow.do?method=dropFlowDefine&force=0&id=' + getRadioValue('checkb'), callBackFun);
}

function forceDropBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').sstatus == 1 
        && window.confirm('ǿ�Ʒ������ܵ���ĳЩδ�������������̲������,ȷ��ǿ�Ʒ������̶���?')
        && window.confirm('����ȷ��ǿ�Ʒ���,ȷ��ǿ�Ʒ������̶���?'))
    $ajax('../flow/flow.do?method=dropFlowDefine&force=1&id=' + getRadioValue('checkb'), callBackFun);
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').sstatus == 0)
    {
        $l('../flow/flow.do?method=findFlowDefine&update=1&id=' + getRadioValue('checkb'));
    }
    else
    {
        $error();
    }
}

function configBean()
{
    if (getRadio('checkb') && getRadioValue('checkb') && getRadio('checkb').sstatus == 0)
    {
        $.blockUI({ message: $('#dataDiv'),css:{width: '40%'}});
    }
    else
    {
        $error();
    }
}

function configBean1()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $l('../flow/flow.do?method=preForConfigView&id=' + getRadioValue('checkb'));
    }
    else
    {
        $error();
    }
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryFlowDefine');
}

function $process()
{
    if (getRadio('checkb') && getRadioValue('checkb') && eCheck([$O('totalTokens')]))
    {
        var sid = $$('totalTokens');
    
        $l('../flow/flow.do?method=preForConfigToken&id=' + $$('checkb') + '&totalTokens=' + sid);
    }
    else
    {
        $error();
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
<input type="text" id="totalTokens" value="3" oncheck="isNumber;range(2, 99)" style="width: 85%"/>
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