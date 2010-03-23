<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="�û�����" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

function load()
{
     preload();
     
	 guidMap = {
		 title: '�û��б�',
		 url: '../admin/user.do?method=queryUser',
		 colModel : [
		     {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lname={name} role={roleId}>', width : 40, sortable : false, align: 'center'},
		     {display: '�û�', name : 'name', width : '15%', sortable : false, align: 'left'},
		     {display: 'ְԱ', name : 'stafferName', width : '15%', sortable : false, align: 'left'},
		     {display: '�ֹ�˾', name : 'locationName', width : '15%', sortable : false, align: 'left'},
		     {display: '״̬', name : 'status', width : '10%', sortable : false, align: 'left', cc: 'userStatus'},
		     {display: '�����¼', name : 'loginTime', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../admin/role.do?method=findRole&update=1&id={roleId}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', bclass: 'add', onpress : addBean, auth: '010401'},
		     {id: 'update1', caption: '�޸�Ȩ��', bclass: 'update', onpress : updateRole, auth: '010401'},
		     {id: 'del', bclass: 'delete', onpress : delBean, auth: '010401'},
		     {id: 'unlock', caption: '����', bclass: 'update', onpress : unlock, auth: '010401'},
		     {id: 'init', caption: '��ʼ������', bclass: 'update', onpress : initPassword, auth: '010401'},
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
		 callBack: $callBack //for firefox load ext att
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
}

function $callBack()
{
    loadForm();
    
    $.highlight($("#mainTable").get(0), '����', 'red');
}
 
function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryUser');
}

function addBean(opr, grid)
{
    $l('../admin/user.do?method=preForAddUser');
}

function delBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('ȷ��ɾ��--' + getRadio('checkb').lname))
        {
            $ajax('../admin/user.do?method=delUser&id=' + getRadioValue('checkb'), callBackFun);
        }
    }
}

function initPassword()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('ȷ����ʼ������--' + getRadio('checkb').lname))
        {
            $ajax('../admin/user.do?method=initPassword&id=' + getRadioValue('checkb'), callBackFun);
        }
    }
}

function unlock()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('ȷ������--' + getRadio('checkb').lname))
        {
            $ajax('../admin/user.do?method=unlock&id=' + getRadioValue('checkb'), callBackFun);
        }
    }
}

function callBackFun(data)
{
    reloadTip(data.msg, data.ret == 0);
    
    if (data.ret == 0)
    commonQuery();
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $l('../admin/user.do?method=findUser&id=' + getRadioValue('checkb'));
    }
}

function updateRole(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $l('../admin/role.do?method=findRole&update=1&id=' + getRadio('checkb').role);
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
<p:cache/>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>