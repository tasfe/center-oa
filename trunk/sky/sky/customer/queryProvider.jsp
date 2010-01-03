<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="��Ӧ�̹���" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

var updatFlag = window.top.topFrame.containAuth('0213') ? '1' : '0';
function load()
{
     preload();
     
	 guidMap = {
		 title: '��Ӧ���б�',
		 url: '../customer/provider.do?method=queryProvider',
		 colModel : [
		     {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lname={name}>', width : 40, sortable : false, align: 'center'},
		     {display: '����', name : 'name', width : '20%', sortable : false, align: 'left'},
		     {display: '����', name : 'code', width : '10%', sortable : false, align: 'left'},
		     {display: '����', name : 'type', width : '10%', sortable : false, align: 'left', cc: 109},
		     {display: 'ѯ���û�', name : 'loginName', width : '8%'},
		     {display: '����', name : 'typeName', width : '20%'},
		     {display: 'ʱ��', name : 'logTime', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../customer/provider.do?method=findProvider&id={id}&update=' + updatFlag + '>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', bclass: 'add', onpress : addBean, auth: '0213'},
		     {id: 'update', bclass: 'update', onpress : updateBean, auth: '0213'},
		     {id: 'update0', bclass: 'edit', caption: '�󶨷���', onpress : bingType, auth: '0213'},
		     {id: 'update1', bclass: 'edit', caption: '���µ�¼�û�', onpress : updateUserBean, auth: '0213'},
		     {id: 'update2', bclass: 'edit', caption: '��������', onpress : updateUserPassword, auth: '0213'},
		     {id: 'del', bclass: 'delete', onpress : delBean, auth: '0213'},
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
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryProvider');
}


function delBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('ȷ��ɾ��--' + getRadio('checkb').lname))
        {
            $ajax('../customer/provider.do?method=delProvider&id=' + getRadioValue('checkb'), callBackFun);
        }
    }
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $l('../customer/provider.do?method=findProvider&update=1&id=' + getRadioValue('checkb'));
    }
}

function updateUserBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $l('../customer/provider.do?method=findProviderUser&update=1&id=' + getRadioValue('checkb'));
    }
}

function updateUserPassword()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('ȷ������' + getRadio('checkb').lname + '���û�����?'))
        {
            $ajax('../customer/provider.do?method=updateUserPassword&id=' + getRadioValue('checkb'), callBackFun);
        }
    }
}

function bingType()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $l('../customer/provider.do?method=preForBing&id=' + getRadioValue('checkb'));
    }
}

function addBean(opr, grid)
{
   $l('../customer/addProvider.jsp');
}
</script>
</head>
<body onload="load()" class="body_class">
<form>
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>