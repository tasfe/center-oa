<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="��Ʒ������" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '��Ʒ���ͳ���б�',
         url: '../product/product.do?method=queryProductStat',
         colModel : [
             {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status}}>', width : 40, align: 'center'},
             {display: '��Ʒ', name : 'productName', width : '20%'},
             {display: '����', name : 'productCode', width : '10%'},
             {display: '״̬', name : 'status', width : '5%', cc: 'productStatStatus'},
             {display: 'ȱ��', name : 'subtractAmount', width : '8%'},
             {display: '�վ�����', name : 'sailAvg', sortable : true, width : '8%', toFixed: 2},
             {display: '15��������', name : 'sailAmount', width : '8%'},
             {display: '���', name : 'inventoryAmount', width : '8%'},
             {display: '����', name : 'orderAmount', width : '8%'},
             {display: 'ͳ��ʱ��', name : 'logTime', sortable : true, cname: 'logTime',  width : 'auto'}
             ],
         extAtt: {
             //title : {begin : '<a href=../product/product.do?method=findMake&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'replied', caption: '����', onpress : exportBean, auth: '1001'},
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
         def: allDef,
         callBack: $callBack
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['Ԥ��̬'], 'red');
    highlights($("#mainTable").get(0), ['����̬'], 'blue');
}

//���������2������
function exportBean()
{
    $l('../product/product.do?method=export');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryProductStat');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>