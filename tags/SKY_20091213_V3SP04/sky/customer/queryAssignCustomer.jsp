<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="�ͻ����չ���" link="true" guid="true" cal="false"/>
<script src="../js/common.js"></script>
<script src="../js/cnchina.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;

var lbuffalo = window.top.topFrame.gbuffalo;

var guidMap;
var thisObj;

function load()
{
    guidMap = {
         title: '�ͻ��б�',
         url: '../customer/customer.do?method=queryCustomerAssign',
         colModel : [
             {display: 'ѡ��', name : 'check', content : '<input type=checkbox id={customerId} name=checkb value={customerId}>', width : 40, sortable : false, align: 'center'},
             {display: '�ͻ�', name : 'customerName', width : '20%', sortable : false, align: 'left'},
             {display: '����', name : 'customerCode', width : '10%', sortable : false, align: 'left'},
             {display: '����ְԱ', name : 'stafferName', width : '10%', sortable : false, align: 'left'},
             {display: '����', name : 'sellType', width : '10%', sortable : false, align: 'left', cc: 101},
             {display: 'ʱ��', name : 'loginTime', width : 'auto', sortable : true, align: 'left'}
             ],
         extAtt: {
             customerName : {begin : '<a href=../customer/customer.do?method=findCustomer&id={customerId}>', end : '</a>'}
         },
         buttons : [
             {id: 'del', caption: '���տͻ�', bclass: 'delete', onpress : reclaimBeans, auth: '0211'},
             {id: 'queryHis', caption: '����ְԱ���пͻ�', bclass: 'history', onpress : queryHis, auth: '0211'},
             {id: 'table', caption: 'ְԱ�ͻ��ֲ�', bclass: 'table', onpress : queryCustomerDistribute, auth: '0209'},
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
    window.common.qmodal('../admin/query.do?method=popCommonQuery&key=queryCustomerAssign');
}

function queryCustomerDistribute()
{
    $l('../customer/customer.do?method=queryStafferCustomerDistribute');
}

function reclaimBeans()
{
    var clis = getCheckBox('checkb');
    
    if (clis.length > 0)
    {
        var str = '';
        for (var i = 0; i < clis.length; i++)
        {
            str += clis[i].value + '~';
        }
        
        if (window.confirm('ȷ������ѡ��Ŀͻ�?'))
        {
            $ajax('../customer/customer.do?method=reclaimAssignCustomer&customerIds=' + str, callBackFun);
        }
    }
}


function queryHis()
{
    var id = '${user.locationId}';
    
    if ($O('staffer').options.length > 0)
    {
        $.blockUI({ message: $('#stafferList'),css:{width: '40%'}});
        return;
    }
    
    lbuffalo.remoteCall("commonManager.queryStafferByLocationId",[id], function(reply) {
                var result = reply.getResult();
                
                var sList = result;
                
                removeAllItem($O('staffer'));
                
                $O('staffer').size = 10;
                
                for (var i = 0; i < sList.length; i++)
                {
                    setOption($O('staffer'), sList[i].id,  sList[i].name);
                }
                
                $.blockUI({ message: $('#stafferList'),css: { width: '40%'}});
        });
}

function $process(flag)
{
    var sid = $$('stafferId');
    
    if (sid == '')
    {
        alert('��ѡ����Ҫ���յ�ְԱ');
        return;
    }
    
    if (window.confirm('ȷ������ѡ��ְԱ�Ŀͻ�?'))
    {
	    $ajax('../customer/customer.do?method=reclaimStafferCustomer&stafferId=' + sid + '&flag=' + flag, callBackFun);
	    
	    $.unblockUI();
    }
}

function $close()
{
    $.unblockUI();
}

function callBackFun(data)
{
    $.unblockUI();
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
<p:cache></p:cache>
</form>
<div id="stafferList" style="display:none">
<p align='left'><label><font color=""><b>��ѡ����Ҫ���տͻ���ϵְԱ</b></font></label></p>
<p><label>&nbsp;</label></p>
<select name="stafferId" id="staffer" quick="true" style="width: 85%"></select>
<p><label>&nbsp;</label></p>
<p>
<input type='button' value='&nbsp;&nbsp;ȫ������&nbsp;&nbsp;' id='div_b_ok1' class='button_class' onclick='$process(0)'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' value='&nbsp;&nbsp;������չ&nbsp;&nbsp;' id='div_b_ok2' class='button_class' onclick='$process(1)'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' value='&nbsp;&nbsp;�����ն�&nbsp;&nbsp;' id='div_b_ok3' class='button_class' onclick='$process(2)'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' id='div_b_cancle' value='&nbsp;&nbsp;�� ��&nbsp;&nbsp;' class='button_class' onclick='$close()'/>
</p>
<p><label>&nbsp;</label></p>
</div>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>