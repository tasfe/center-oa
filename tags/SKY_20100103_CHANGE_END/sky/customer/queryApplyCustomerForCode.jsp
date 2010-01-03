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
		 title: '����ͻ�����',
		 url: '../customer/customer.do?method=queryApplyCustomerForCode',
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
		     name : {begin : '<a href=../customer/customer.do?method=findApplyCustomer&id={id}>', end : '</a>'}
		 },
		 buttons : [
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
 

function callBackFun(data)
{
    reloadTip(data.msg, data.ret == 0);
    
    if (data.ret == 0)
    commonQuery();
}


function doAssignCode()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $l('../customer/customer.do?method=findApplyCustomer&updateCode=1&id=' + getRadioValue('checkb'));
    }
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