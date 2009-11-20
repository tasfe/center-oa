<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="�ɷ���ͻ������б�" link="true" guid="true" cal="false"/>
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
		 title: '�ɷ���ͻ������б�',
		 url: '../customer/customer.do?method=queryAssignApply',
		 colModel : [
		     {display: 'ѡ��', name : 'check', content : '<input type=checkbox name=checkb id={id} value={id} lname={name}>', width : 40, sortable : false, align: 'center'},
		     {display: '����ͻ�', name : 'customerName', width : '40%', sortable : false, align: 'left'},
		     {display: '�ͻ�����', name : 'customerCode', width : '10%', sortable : false, align: 'left'},
		     {display: '�ͻ�����', name : 'customerSellType', width : '15%', sortable : false, align: 'left', cc: 101},
		     {display: '����ְԱ', name : 'stafferName', width : 'auto', sortable : false, align: 'left'}
		     ],
		 extAtt: {
		     customerName : {begin : '<a href=../customer/customer.do?method=findCustomer&id={customerId}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'pass', caption: 'ͨ��',bclass: 'pass', auth: '0203', onpress : doPass},
             {id: 'reject', caption: '����',bclass: 'reject', auth: '0203', onpress : doReject}
		     ],
		 usepager: true,
		 useRp: true,
		 queryMode: 0,
		 cache: 0,
         cacheEle: $O('cacheEle'),
         cacheFlag: $O('cacheFlag'),
		 auth: window.top.topFrame.gAuth,
		 showTableToggleBtn: true,
		 def: allDef,
		 height: DEFAULT_HEIGHT,
		 callBack: loadForm
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }
 
function doPass()
{
   doAjax("0");
}

function doReject()
{
   doAjax("1");
}

function doAjax(opr)
{
    var clis = getCheckBox('checkb');
    
    if (clis.length > 0)
    {
        var str = '';
        for (var i = 0; i < clis.length; i++)
        {
            str += clis[i].value + '~';
        }
        
        if (window.confirm('ȷ���������ѡ�еĿͻ�?'))
        {
            $ajax('../customer/customer.do?method=processAssignApply&cids=' + str + "&opr=" + opr, callBackFun);
        }
    }
}

function callBackFun(data)
{
    reloadTip(data.msg, data.ret == 0);
    
    if (data.ret == 0)
    commonQuery();
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
<input type="hidden" name="cacheFlag" id="cacheFlag" value="0"/>
<input type="hidden" name="cacheEle" id="cacheEle" value=""/>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>