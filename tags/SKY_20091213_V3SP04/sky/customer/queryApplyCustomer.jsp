<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="�ͻ�����" link="true" guid="true" cal="false"/>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
var updateCode = window.top.topFrame.containAuth('0214') ? '1' : '0';
function load()
{
	 guidMap = {
		 title: '����ͻ��б�',
		 url: '../customer/customer.do?method=queryApplyCustomer',
		 colModel : [
		     {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lname={name}>', width : 40, sortable : false, align: 'center'},
		     {display: '�ͻ�', name : 'name', width : '20%', sortable : false, align: 'left'},
		     {display: '����', name : 'opr', width : '10%', sortable : false, align: 'left', cc: 'customerOpr'},
		     {display: '������', name : 'stafferName', width : '10%', sortable : false, align: 'left'},
		     {display: '����', name : 'selltype', width : '10%', sortable : false, align: 'left', cc: 101},
		     {display: '����', name : 'qqtype', width : '10%', sortable : false, align: 'left', cc: 104},
		     {display: '����', name : 'rtype', width : '10%', sortable : false, align: 'left', cc: 105},
		     {display: '״̬', name : 'status', width : '11%', sortable : false, align: 'left', cc: 'customerStatus'},
		     {display: 'ʱ��', name : 'loginTime', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../customer/customer.do?method=findApplyCustomer&id={id}&updateCode='+ updateCode + '>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', caption: '��������', bclass: 'add', auth: '0202', onpress : addBean},
		     {id: 'del',  caption: 'ɾ������', bclass: 'delete', auth: '0202', onpress : delBean},
		     {id: 'pass', caption: 'ͨ��',bclass: 'pass', auth: '0203', onpress : doPass},
		     {id: 'reject', caption: '����',bclass: 'reject', auth: '0203', onpress : doReject},
		     {id: 'assign', caption: '�������',bclass: 'update', auth: '0214', onpress : doAssignCode}
		     ],
		 usepager: true,
		 useRp: true,
		 auth: window.top.topFrame.gAuth,
		 queryMode: 0,
		 cache: 0,
		 queryCondition: null,
		 showTableToggleBtn: true,
		 height: DEFAULT_HEIGHT,
		 def: allDef,
		 callBack: loadForm
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }
 
function doPass()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('ȷ��ͨ��--' + getRadio('checkb').lname))
        {
            $ajax('../customer/customer.do?method=processApply&operation=0&id=' + getRadioValue('checkb'), callBackFun);
        }
    }
}

function callBackFun(data)
{
    reloadTip(data.msg, data.ret == 0);
    
    if (data.ret == 0)
    commonQuery();
}

function doReject()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('ȷ������--' + getRadio('checkb').lname))
        {
            $ajax('../customer/customer.do?method=processApply&operation=1&id=' + getRadioValue('checkb'), callBackFun);
        }
    }
}

function doAssignCode()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $l('../customer/customer.do?method=findApplyCustomer&updateCode=1&id=' + getRadioValue('checkb'));
    }
}

function delBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('ȷ��ɾ������--' + getRadio('checkb').lname))
        {
            $ajax('../customer/customer.do?method=delApplyCustomer&id=' + getRadioValue('checkb'), callBackFun);
        }
    }
}

function addBean()
{
    $l('../customer/customer.do?method=preForAddApplyCustomer');
}


function commonQuery(par)
{
    gobal_guid.p.queryCondition = par;
    
    gobal_guid.grid.populate(true);
}
</script>
</head>
<body onload="load()" class="body_class">
<form>
</form>
<p:cache/>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>