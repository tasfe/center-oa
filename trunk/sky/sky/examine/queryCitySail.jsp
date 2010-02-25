<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="������������" link="true" guid="true" cal="false" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/JCheck.js"></script>
<script type="text/javascript">
var guidMap;
var thisObj;
function load()
{
     preload();
     
	 guidMap = {
		 title: '���������б�',
		 url: '../examine/city.do?method=queryCitySail',
		 colModel : [
		     {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lname={name} lamount={amount} lmonth={month}>',
                    width : 40, sortable : false, align: 'center'},
		     {display: '����ָ��', name : 'name', width : '30%', sortable : false, align: 'left'},
		     {display: '������', name : 'amount', width : '30%', sortable : false, align: 'left'},
		     {display: '��������', name : 'month', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     //
		 },
		 buttons : [
		     {id: 'add', bclass: 'add', onpress : addSail, auth: '0302'},
		     {id: 'update', bclass: 'update', caption: '�޸�', onpress : updateBean, auth: '0302'}
		     ],
		 usepager: true,
		 useRp: true,
		 queryMode: 1,
		 auth: window.top.topFrame.gAuth,
		 cache: 0,
		 height: DEFAULT_HEIGHT,
		 queryCondition: null,
		 showTableToggleBtn: true,
		 callBack: loadForm
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
	 
	 $('#dlg').dialog({
                //iconCls: 'icon-save',
                modal:true,
                closed:true,
                buttons:{
                    'ȷ ��':function(){
                        submitBean();
                    },
                    'ȡ ��':function(){
                        $('#dlg').dialog({closed:true});
                    }
                }
     });
     
     $ESC('dlg');
 }
 
function updateBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $O('id').value = getRadio('checkb').value;
        $O('name').value = getRadio('checkb').lname;
        $O('amount').value = getRadio('checkb').lamount;
        setSelect($O('month'), getRadio('checkb').lmonth);
        $O('method').value = 'updateCitySail';
        $('#dlg').dialog({closed:false});
    }
}

function addSail()
{
    $('#dlg').dialog({closed:false});
    $O('id').value = '';
    $O('name').value = '';
    $O('amount').value = '';
    $O('method').value = 'addCitySail';
}

function submitBean()
{
   if (formCheck())
   $ajax('../examine/city.do?method=' + $$('method') + '&id=' 
                    + $$('id') + '&name=' + $$('name') + '&amount=' + $$('amount') + '&month=' + $$('month'), callBackFun2);
}

function callBackFun2(data)
{
    $('#dlg').dialog({closed:true});
    callBackFun(data);
}
</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../admin/staffer.do" method="post">
<input type="hidden" name="method" value=""/>
<input type="hidden" name="stafferId" value=""/>
<input type="hidden" name="id" value=""/>
<p:cache></p:cache>

<div id="dlg" title="��������" style="width:320px;">
    <div style="padding:20px;height:200px;" id="dialog_inner2" title="">
    
    ����ָ�꣺<input type="text" name="name" value="" oncheck="notNone"/><br/><br/>
    ��������&nbsp;&nbsp;&nbsp;<input type="text" name="amount" value="" oncheck="notNone;isInt"/><br/><br/>
    ������&nbsp;&nbsp;��
    <select name="month" class="select_class">
    <option value="1">һ����</option>
    <option value="2">������</option>
    </select>
    <br/>
   </div>
</div>

</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>