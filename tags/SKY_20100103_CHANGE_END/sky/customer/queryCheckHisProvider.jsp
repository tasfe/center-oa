<%@page contentType="text/html; charset=GBK" errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="�˶Թ�Ӧ���޸�" link="true" guid="true" cal="false"/>
<script src="../js/json.js"></script>
<script src="../js/common.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/public.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
	 guidMap = {
		 title: '�˶Թ�Ӧ��',
		 url: '../customer/provider.do?method=queryCheckHisProvider',
		 colModel : [
		     {display: '<input type=checkbox id=flexi_Check onclick=checkAll(this)>ѡ��', name : 'check', content : '<input type=checkbox name=checkb value={id} lname={name}>', width : 55, sortable : false, align: 'center'}, 
		     {display: '��Ӧ��', name : 'name', width : '20%', align: 'left'},
             {display: '����', name : 'code', width : '10%', align: 'left'},
             {display: '��ϵ��', name : 'connector', width : '20%', align: 'left'},
             {display: '����', name : 'type', width : '10%', align: 'left', cc: 109},
             {display: 'ʱ��', name : 'logTime', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../customer/provider.do?method=findHisProvider&id={id}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'back', caption: '�˶�', bclass: 'pass', onpress : checkHis},
		     {id: 'search', bclass: 'search', onpress : doSearch}
		     ],
		 usepager: true,
		 useRp: true,
		 queryMode: 1,
		 cache: 0,
		 showTableToggleBtn: true,
		 height: DEFAULT_HEIGHT,
		 def: allDef,
		 callBack: loadForm
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
}

function doSearch()
{
    window.common.qmodal('../admin/query.do?method=popCommonQuery&key=queryCheckHisProvider');
}
 
function checkHis(opr)
{
    var clis = getCheckBox('checkb');
    
    if (clis.length > 0)
    {
        var str = '';
        for (var i = 0; i < clis.length; i++)
        {
            str += clis[i].value + '~';
        }
        
        if (window.confirm('ȷ���˶�ѡ�еĹ�Ӧ��?'))
        {
            $ajax('../customer/provider.do?method=checkHisProvider&cids=' + str, callBackFun);
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
<body class="body_class" onload="load()">
<form>
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>