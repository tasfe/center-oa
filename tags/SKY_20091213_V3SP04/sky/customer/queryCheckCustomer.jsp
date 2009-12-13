<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="�ͻ���Ϣ���" link="true" guid="true" cal="false"/>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

var updatFlag = window.top.topFrame.containAuth('021602') ? '1' : '0';
function load()
{
	 guidMap = {
		 title: '�ͻ���Ϣ�������',
		 url: '../customer/check.do?method=queryCustomerCheck',
		 colModel : [
		     {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status}>', width : 40, sortable : false, align: 'center'},
		     {display: '��ʶ', name : 'id', width : '12%', sortable : false, align: 'left'},
		     {display: '����ְԱ', name : 'checkerName', width : '7%'},
		     {display: '����ְԱ', name : 'applyerName', width : '7%'},
		     {display: '�����', name : 'approverName', width : '7%'},
		     {display: '״̬', name : 'status', width : '5%', cc: 'commonStatus'},
		     {display: '��ʼ/��ȷ/����', name : 'result', content: '{retInit}/{retOK}/{retError}', width : '14%', sortable : true},
		     {display: 'ʱ��', name : 'logTime', width : '15%', sortable : true},
		     {display: 'ʱ��', name : 'time_', content: '{beginTime}��{endTime}', width : 'auto', cc: 'commonStatus'}
		     ],
		 extAtt: {
		     id : {begin : '<a href=../customer/queryCheckItem.jsp?id={id}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', bclass: 'add', onpress : addBean, auth: '021601'},
		     {id: 'update', bclass: 'update', caption: '����', onpress : goonBean, auth: '021601'},
		     {id: 'del', bclass: 'delete', onpress : delBean, auth: '021601'},
		     {id: 'pass', bclass: 'pass', caption: 'ͨ��',  onpress : passBean, auth: '021602'},
		     {id: 'reject', bclass: 'reject', caption: '����', onpress : rejectBean, auth: '021602'},
		     {id: 'queryItem', bclass: 'search', caption: '�����Ϣ', onpress : queryItem, auth: '021601'},
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
		 callBack: loadForm //for firefox load ext att
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }
 
function doSearch()
{
    window.common.qmodal('../admin/query.do?method=popCommonQuery&key=queryCustomerCheck');
}

function delBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (getRadio('checkb').lstatus != 2)
        {
	        if (window.confirm('��������벵�ػ�ɾ����ǰ�Ѿ��˶Ե���Ϣ,ȷ��ɾ��--' + getRadioValue('checkb')))
	        {
	            $ajax('../customer/check.do?method=delCheck&id=' + getRadioValue('checkb'), callBackFun);
	        }
        }
        else
        {
            alert('���ܴ˲���');
        }
    }
}

function callBackFun(data)
{
    reloadTip(data.msg, data.ret == 0);

    if (data.ret == 0)
    commonQuery();
}

function passBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       if (getRadio('checkb').lstatus == 1)
       {
           if (window.confirm('ȷ��ͨ��--' + getRadioValue('checkb'))) 
           $ajax('../customer/check.do?method=passCheck&id=' + getRadioValue('checkb'), callBackFun);
       }
    }
}

function rejectBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       if (getRadio('checkb').lstatus == 1)
       {
           if (window.confirm('ȷ������--' + getRadioValue('checkb'))) 
           $ajax('../customer/check.do?method=rejectCheck&id=' + getRadioValue('checkb'), callBackFun);
       }
    }
}

function addBean(opr, grid)
{
   $l('../customer/check.do?method=preForAddCheck');
}

function queryItem(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       if (getRadio('checkb').lstatus == 2)
       {
           $l('../customer/queryCheckItem.jsp?id=' + getRadioValue('checkb'));
       }
       else
       {
           alert('���ܴ˲���,ֻ����ͨ���Ĳſ������');
       }
    }
}

function goonBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       if (getRadio('checkb').lstatus == 99)
       {
           $l('../customer/check.do?method=findCheck&id=' + getRadioValue('checkb') + '&update=1');
       }
       else
       {
           alert('���ܴ˲���,ֻ���ǽ����Ĳſ�������');
       }
    }
}
</script>
</head>
<body onload="load()" class="body_class">
<form>
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>