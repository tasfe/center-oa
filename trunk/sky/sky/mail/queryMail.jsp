<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="�ʼ�����" link="true" guid="true" cal="false"/>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/oa/defined.js"></script>
<script type="text/javascript">
var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
	 guidMap = {
		 title: '�ҵ��ռ���',
		 url: '../mail/mail.do?method=queryMail',
		 colModel : [
		     {display: '<input type=checkbox id=flexi_Check onclick=checkAll(this)>ѡ��', name : 'check', content : '<input type=checkbox name=checkb value={id} lname={name}>', width : 55, align: 'center'},
		     {display: '״̬', name : 'status', width : '35', sortable : true, cc: 'mailStatusIco', align: 'center'},
		     {display: '����', name : 'title', width : '45%'},
		     {display: '������', name : 'senderName', sortable : true, width : '7%'},
		     {display: '����', name : 'attachment', width : '35', sortable : true, cc: 'mailAttachmentDisplay', align: 'center'},
		     {display: '�ظ�', name : 'feeback', width : '35', cc: 'mailFeebackIco', align: 'center'},
		     {display: 'ʱ��', name : 'logTime', sortable : true, width : 'auto'}
		     ],
		 extAtt: {
		     title : {begin : '<a href=../mail/mail.do?method=findMail&id={id} title=�鿴�ʼ�����>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', bclass: 'add',  caption: '�½�', onpress : addBean},
		     {id: 'update', bclass: 'replied', caption: '�ظ�', onpress : replyBean},
		     {id: 'update1', bclass: 'replied', caption: '�ظ�����', onpress : replyAllBean},
		     {id: 'update2', bclass: 'forward', caption: 'ת��', onpress : forwardBean},
		     {id: 'del', bclass: 'del',  onpress : delBean},
		     {id: 'search', bclass: 'search',  onpress : doSearch}
		     ],
		 usepager: true,
		 useRp: true,
		 queryMode: 1,
		 auth: window.top.topFrame.gAuth,
		 cache: 0,
		 height: 'page',
		 queryCondition: null,
		 showTableToggleBtn: true,
		 def: joinMap2(allDef),
		 rp: 20,
		 rpOptions: [20,40,80,100],
		 callBack: loadForm
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }

function addBean(opr, grid)
{
    $l('../mail/addMail.jsp');
}

function delBean(opr, grid)
{
    var clis = getCheckBox('checkb');
    
    if (clis.length > 0)
    {
        var str = '';
        
        for (var i = 0; i < clis.length; i++)
        {
            str += clis[i].value + ';';
        }
        
        if (window.confirm('ȷ��ɾ��ѡ�е��ʼ�?'))
        {
            $ajax('../mail/mail.do?method=deleteMail&ids=' + str, callBackFun);
        }
    }
}


function replyBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $l('../mail/mail.do?method=findMail&operation=2&id=' + getRadioValue('checkb'));
    }
}


function replyAllBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $l('../mail/mail.do?method=findMail&operation=3&id=' + getRadioValue('checkb'));
    }
}

function forwardBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $l('../mail/mail.do?method=findMail&operation=1&id=' + getRadioValue('checkb'));
    }
}

function doSearch()
{
    window.common.qmodal('../admin/query.do?method=popCommonQuery&key=queryMail');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../mail/mail.do" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>
