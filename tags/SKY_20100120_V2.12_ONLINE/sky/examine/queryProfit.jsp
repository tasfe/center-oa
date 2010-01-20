<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="�ͻ��������" link="true" guid="true" cal="false"/>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script type="text/javascript">
var guidMap;
var thisObj;
function load()
{
	 guidMap = {
		 title: '�ͻ������б�',
		 url: '../examine/city.do?method=queryProfit',
		 colModel : [
		     {display: 'ʱ��', name : 'orgDate', width : '15%', sortable : true, align: 'left'},
		     {display: '�ͻ�����', name : 'customerCode', width : '10%', sortable : false, align: 'left'},
		     {display: '�ͻ�����', name : 'customerName', width : '25%', sortable : false, align: 'left'},
		     {display: 'ְԱ', name : 'stafferName', width : '15%', sortable : false, align: 'left'},
		     {display: '����', name : 'profit', toFixed : 2, width : '10%', sortable : true, align: 'left'},
		     {display: '����ʱ��', name : 'logTime', width : 'auto', sortable : true}
		     ],
		 extAtt: {
		     //cityName : {begin : '<a href=../examine/city.do?method=findCityConfig&id={cityId}&update=1 title=�鿴��ϸ>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', bclass: 'add', caption: '��������', onpress : importProfit, auth: '0307'},
		     {id: 'del', bclass: 'del', caption: 'ɾ������', onpress : delBean, auth: '0307'},
		     {id: 'search', bclass: 'search', onpress : doSearch}
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
 }
 
function doSearch()
{
    window.common.qmodal('../admin/query.do?method=popCommonQuery&key=queryProfit');
}

function delBean(opr, grid)
{
    $.blockUI({ message: $('#dataDiv'),css:{width: '40%'}});
}

function importProfit()
{
    $l('../examine/uploadProfit.jsp');
}

function $process()
{
    if (window.confirm('ȷ��ɾ������?'))
    {
        $ajax('../examine/city.do?method=delProfit&year=' 
                + $$('year') + '&month=' + $$('month'), callBackFun);
        
        $.unblockUI();
    }
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../examine/city.do" method="post">
<p:cache></p:cache>
</form>
<div id="dataDiv" style="display:none">
<p align='left'><label><font color=""><b>������ɾ��������</b></font></label></p>
<p><label>&nbsp;</label></p>
��ݣ�<select name="year" style="width: 85%">
<c:forEach begin="2008" end="2100" var="item">
<option value="${item}">${item}</option>
</c:forEach>
</select>
<br>
�·ݣ�<select name="month" style="width: 85%">
<c:forEach begin="1" end="12" var="item">
<option value="${item}">${item}</option>
</c:forEach>
</select>
<p><label>&nbsp;</label></p>
<p>
<input type='button' value='&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;' id='div_b_ok1' class='button_class' onclick='$process()'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' id='div_b_cancle' value='&nbsp;&nbsp;ȡ ��&nbsp;&nbsp;' class='button_class' onclick='$close()'/>
</p>
<p><label>&nbsp;</label></p>
</div>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
</body>