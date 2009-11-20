<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="�ɷ���ͻ��б�" link="true" guid="true" cal="false"/>
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
		 title: '�ɷ���ͻ��б�(ͨ���������Բ�ѯ�ն˿ͻ�)',
		 url: '../customer/customer.do?method=queryCanAssignCustomer',
		 colModel : [
		     {display: 'ѡ��', name : 'check', content : '<input type=checkbox name=checkb value={id} lname={name}>', width : 40, sortable : false, align: 'center'},
		     {display: '�ͻ�', name : 'name', width : '20%', sortable : false, align: 'left'},
		     {display: '����', name : 'code', width : '10%', sortable : false, align: 'left'},
		     {display: '����', name : 'selltype', width : '10%', sortable : false, align: 'left', cc: 101},
		     {display: '����', name : 'qqtype', width : '10%', sortable : false, align: 'left', cc: 104},
		     {display: '����', name : 'rtype', width : '10%', sortable : false, align: 'left', cc: 105},
		     {display: '�ֹ�˾', name : 'locationName', width : '10%', sortable : false, align: 'left'},
		     {display: '״̬', name : 'status', width : '10%', sortable : false, align: 'left', cc: 'realCustomerStatus'},
		     {display: 'ʱ��', name : 'loginTime', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../customer/customer.do?method=findCustomer&id={id}&update=1>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', caption: '�������', bclass: 'add', onpress : addBean},
		     {id: 'search', bclass: 'search', onpress : doSearch},
		     {separator: true}
		     ],
		 usepager: true,
		 useRp: true,
		 queryMode: 0,
		 queryCondition: null,
		 showTableToggleBtn: true,
		 height: DEFAULT_HEIGHT,
		 def: allDef,
		 callBack: loadForm
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }

function addBean()
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
            $ajax('../customer/customer.do?method=addAssignApply&cids=' + str, callBackFun);
        }
    }
}

function doSearch()
{
    window.common.qmodal('../admin/query.do?method=popCommonQuery&key=queryCanAssignCustomer');
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
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>