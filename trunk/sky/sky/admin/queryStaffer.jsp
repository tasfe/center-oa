<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="��Ա����" link="true" guid="true" cal="false"/>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">
var guidMap;
var thisObj;

var allDef = window.top.topFrame.allDef;

function load()
{
	 guidMap = {
		 title: '��Ա�б�',
		 url: '../admin/staffer.do?method=queryStaffer',
		 colModel : [
		     {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lname={name}>', width : 40, sortable : false, align: 'center'},
		     {display: '����', name : 'code', width : '8%', sortable : false, align: 'left'},
		     {display: '����', name : 'name', width : '10%', sortable : false, align: 'left'},
		     {display: '��˾', name : 'locationName', width : '10%', sortable : false, align: 'left'},
		     {display: '����', name : 'departmentName', cname : 'departmentId', width : '15%', sortable : true, align: 'left'},
		     {display: '��λ', name : 'principalshipName', width : '12%', sortable : false, align: 'left'},
		     {display: '����', name : 'examType', width : '5%', sortable : false, align: 'left', cc : 'examType'},
		     {display: '״̬', name : 'status', width : '5%', sortable : false, align: 'left', cc : 'stafferStatus'},
		     {display: '����', name : 'enc', width : '8%'},
		     {display: '�绰', name : 'handphone', width : 'auto', sortable : false, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../admin/staffer.do?method=findStaffer&id={id}&update=1 title=�鿴��ϸ>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', bclass: 'add', onpress : addBean},
		     {id: 'update', bclass: 'update', onpress : updateBean},
		     {id: 'update', bclass: 'update',caption: '����������', onpress : updateKey},
		     {id: 'del', bclass: 'delete',caption: '����', onpress : delBean},
		     {id: 'search', bclass: 'search', onpress : doSearch},
		     {separator: true}
		     ],
		 usepager: true,
		 useRp: true,
		 queryMode: 0,
		 cache: 0,
		 def: allDef,
		 queryCondition: null,
		 showTableToggleBtn: true,
		 callBack: $callBack //for firefox load ext att
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }

function $callBack()
{
    loadForm();
    
    $.highlight($("#mainTable").get(0), '����', 'blue');
    
    $.highlight($("#mainTable").get(0), 'δ����', 'red');
}
 
function doSearch()
{
    window.common.qmodal('../admin/query.do?method=popCommonQuery&key=queryStaffer');
}

function addBean()
{
    $l('../admin/staffer.do?method=preForAddStaffer');
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('ȷ��ɾ��--' + getRadio('checkb').lname))
        {
            document.mainForm.stafferId.value = getRadioValue('checkb');
            
            document.mainForm.method.value = 'delStaffer';
            
            document.mainForm.submit();
        }
    }
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $l('../admin/staffer.do?method=findStaffer&id=' + getRadioValue('checkb') + '&update=1');
    }
}

function updateKey(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $l('../admin/staffer.do?method=preForSetpwkey&id=' + getRadioValue('checkb'));
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
<form name="mainForm" action="../admin/staffer.do" method="post">
<input type="hidden" name="method" value=""/>
<input type="hidden" name="stafferId" value=""/>
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>