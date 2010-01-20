<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="�ͻ���Ϣ���" link="true" guid="true" cal="false"/>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

var updatFlag = window.top.topFrame.containAuth('021602') ? '1' : '0';
function load()
{
	 guidMap = {
		 title: '�ͻ���Ϣ���',
		 url: '../customer/check.do?method=queryCheckItem&look=${param.look}&id=${param.id}',
		 colModel : [
		     {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status} lcustomerId = {customerId} lret={ret} ldec="{description}">',
		               width : 40, sortable : false, align: 'center'},
		     {display: '�ͻ�', name : 'customerName', width : '15%'},
		     {display: '����', name : 'customerCode', width : '8%'},
		     {display: '��ʶ', name : 'parentId', width : '8%'},
		     {display: '����ְԱ', name : 'stafferName', width : '8%'},
		     {display: '�˶Խ��', name : 'ret', width : '5%', cc: 'commonResult'},
		     {display: '�˶���Ϣ', name : 'description', content: '<p title={description}>{description}</p>', width : '25%'},
		     {display: '״̬', name : 'status', width : '5%', cc: 'commonStatus'},
		     {display: 'ʱ��', name : 'logTime', width : 'auto', sortable : true}
		     ],
		 extAtt: {
		     customerName : {begin : '<a href=../customer/customer.do?method=findCustomer&id={customerId}&update=3&linkId={id}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'pass', bclass: 'pass', caption: '��Ϣ����', onpress : passBean, auth: '021601'},
		     {id: 'reject', bclass: 'reject', caption: '��Ϣ����', onpress : rejectBean, auth: '021601'},
		     {id: 'search', bclass: 'search', onpress : doSearch}
		     ],
		 usepager: true,
		 useRp: true,
		 queryMode: 0,
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
    window.common.qmodal('../admin/query.do?method=popCommonQuery&key=queryCheckItem');
}

function callBackFun(data)
{
    reloadTip(data.msg, data.ret == 0);

    if (data.ret == 0)
    commonQuery();
}

function passBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       if (getRadio('checkb').lstatus == 0)
       {
           if (window.confirm('�ͻ���Ϣ��ȷ, ȷ��ͨ��?')) 
           $ajax('../customer/check.do?method=passItem&id=' + getRadioValue('checkb'), callBackFun);
       }
       else
       {
           $error('���ܲ���');
       }
    }
}

function rejectBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (getRadio('checkb').lstatus == 0)
        {
            $O('reason').value = '';
            $.blockUI({ message: $('#dataDiv'),css: defaultCSS});
        }
        else
        {
            $error('���ܲ���');
        }
    }
}

function queryReason(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (getRadio('checkb').lret == 2)
        { 
            $O('logD').innerHTML = getRadio('checkb').ldec;
            $.blockUI({ message: $('#logDiv'),css: centerCSS});
        }
    }
}


function $close()
{
    $.unblockUI();
}

function $process()
{
    if (eCheck([$O('reason')]))
    {
        if (window.confirm('ȷ���ͻ���Ϣ����?'))
        {
            $ajax('../customer/check.do?method=rejectItem&id=' + getRadioValue('checkb') + '&reason=' + $$('reason'), callBackFun);
            
            $.unblockUI();
        }
    }
}
</script>
</head>
<body onload="load()" class="body_class">
<form>
<p:cache></p:cache>
</form>
<div id="dataDiv" style="display:none">
<p align='left'><label><font color=""><b>�����뱸ע</b></font></label></p>
<p><label>&nbsp;</label></p>
��ע��<textarea name="reason" value="" rows="4" oncheck="notNone;maxLength(100)" style="width: 80%"></textarea>
<p><label>&nbsp;</label></p>
<p>
<input type='button' value='&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;' id='div_b_ok1' class='button_class' onclick='$process()'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' id='div_b_cancle' value='&nbsp;&nbsp;�� ��&nbsp;&nbsp;' class='button_class' onclick='$close()'/>
</p>
<p><label>&nbsp;</label></p>
</div>

<div id="logDiv" style="display:none">
<p align='left'><label><font color=""><b>�˶���Ϣ:</b></font></label></p>
<p><label>&nbsp;</label></p>
<div id="logD" align='left'>
</div>
<p><label>&nbsp;</label></p>
<p>
<input type='button' id='div_b_cancle' value='&nbsp;&nbsp;�� ��&nbsp;&nbsp;' class='button_class' onclick='$close()'/>
</p>
<p><label>&nbsp;</label></p>
</div>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>