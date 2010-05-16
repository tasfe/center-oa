<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="�ͻ�����" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

var updatFlag = window.top.topFrame.containAuth('0202') ? '1' : '0';
function load()
{
     preload();
     
	 guidMap = {
		 title: '�ͻ��б�',
		 url: '../customer/customer.do?method=querySelfCustomer',
		 colModel : [
		     {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lname={name}>', width : 40, sortable : false, align: 'center'},
		     {display: '�ͻ�', name : 'name', width : '15%', sortable : true, align: 'left', cname: 'id'},
		     {display: '����', name : 'code', width : '8%', sortable : false, align: 'left'},
		     {display: '��ϵ��', name : 'connector', width : '10%', sortable : false, align: 'left'},
		     {display: '����', name : 'selltype', width : '8%', sortable : false, align: 'left', cc: 101},
		     {display: '����', name : 'creditVal', width : '8%', toFixed: 2},
		     {display: '����', name : 'mtype', width : '10%', sortable : false, align: 'left', cc: 107},
		     {display: '���ø���', name : 'creditUpdateTime', width : '8%', sortable : false, align: 'left'},
		     {display: 'ʱ��', name : 'createTime', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../customer/customer.do?method=findCustomer&id={id}&update=' + updatFlag + '>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'update', bclass: 'update', onpress : updateBean, auth: '0202'},
		     {id: 'del', bclass: 'delete', onpress : delBean, auth: '0202'},
		     {id: 'queryHis', caption: '�ͻ��޸���ʷ', bclass: 'history', onpress : queryHis, auth: '0206'},
		     {id: 'table', caption: '�ͻ��ֲ�', bclass: 'table', onpress : queryCustomerDistribute, auth: '0209'},
		     {id: 'table1', caption: '����ְԱ�ͻ��ֲ�', bclass: 'table', onpress : queryAllStafferCustomerDistribute, auth: '0209'},
		     {id: 'syn', caption: '�ͻ��ֹ�˾ͬ��', bclass: 'table', onpress : synAll, auth: '0210'},
		     {id: 'configCredit', caption: '���ÿͻ�����', bclass: 'update', onpress : configCredit},
		     {id: 'queryCreditLog', caption: '���ñ����־', bclass: 'search', onpress : queryCreditLog},
		     {id: 'queryCredit', caption: '������ϸ', bclass: 'search', onpress : queryCredit},
		     {id: 'queryVistor', caption: '�ݷü�¼', bclass: 'search', onpress : queryVistor},
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
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryCustomer');
}

function queryCustomerDistribute()
{
    $l('../customer/customer.do?method=queryCustomerDistribute');
}

function queryAllStafferCustomerDistribute()
{
    $l('../customer/customer.do?method=queryAllStafferCustomerDistribute');
}

function synAll()
{
    if (window.confirm('ȷ��ȫ��ͬ���ͻ��ֹ�˾����?'))
    {
        $.blockUI({ message: 'ͬ����......' });
        $ajax('../customer/customer.do?method=synchronizationAllCustomerLocation', callBackFun);
    }
}


function queryHis()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
         $l('../customer/queryHisCustomer.jsp?id=' + getRadioValue('checkb'));
    }
}

function queryVistor()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
         $l('../customer/queryWorkCustomer.jsp?targerId=' + getRadioValue('checkb'));
    }
    else
    {
        $error();
    }
}

function queryCreditLog()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
         $l('../credit/queryCustomerCreditLog.jsp?targerId=' + getRadioValue('checkb'));
    }
    else
    {
        $error();
    }
}

function queryCredit()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
         $l('../credit/queryCustomerCredit.jsp?targerId=' + getRadioValue('checkb'));
    }
    else
    {
        $error();
    }
}

function delBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('ȷ������ɾ��--' + getRadio('checkb').lname))
        {
            $ajax('../customer/customer.do?method=addDelApplyCustomer&id=' + getRadioValue('checkb'), callBackFun);
        }
    }
}

function callBackFun(data)
{
    $.unblockUI();
    reloadTip(data.msg, data.ret == 0);

    //if (data.ret == 0)
    //commonQuery();
}

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $l('../customer/customer.do?method=findCustomer&update=1&id=' + getRadioValue('checkb'));
    }
}

function configCredit(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $l('../credit/customer.do?method=preForConfigStaticCustomerCredit&cid=' + getRadioValue('checkb'));
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
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>