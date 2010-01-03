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
function load()
{
	 guidMap = {
		 title: '��˿ͻ�����',
		 url: '../customer/customer.do?method=queryApplyCustomerForCredit',
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
		     name : {begin : '<a href=../credit/customer.do?method=preForConfigStaticCustomerCredit&detailapply=1&cid={id} title=����鿴�ͻ�����>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'pass', caption: 'ͨ��',bclass: 'pass', auth: '0203', onpress : doPass},
             {id: 'reject', caption: '����',bclass: 'reject', auth: '0203', onpress : doReject}
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
            $ajax('../credit/customer.do?method=doPassApplyConfigStaticCustomerCredit&cid=' + getRadioValue('checkb'), callBackFun);
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
            $ajax('../credit/customer.do?method=doRejectApplyConfigStaticCustomerCredit&cid=' + getRadioValue('checkb'), callBackFun);
        }
    }
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