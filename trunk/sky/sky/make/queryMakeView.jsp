<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="���Ʋ�Ʒ����" link="true" guid="true" cal="true" dialog="true" />
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
         title: '��ʷ���Ʋ�Ʒ�б�',
         url: '../make/make.do?method=queryMakeView',
         colModel : [
             {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status} lposition={position}>', width : 40, align: 'center'},
             {display: '��ʶ', name : 'id', width : '15%'},
             {display: '����', name : 'title', width : '15%'},
             {display: '����', name : 'token', content: '��{status}��', width : '10%'},
             {display: '����', name : 'statusName', width : '10%'},
             {display: 'λ��', name : 'positionName', width : '8%'},
             {display: '������', name : 'createrName', width : '8%'},
             {display: '������', name : 'handerName', width : '8%'},
             {display: '����', name : 'type', width : '8%', cc: 'makeType'},
             {display: '����ʱ��', name : 'logTime', sortable : true, width : 'auto'}
             ],
         extAtt: {
             title : {begin : '<a href=../make/make.do?method=findMake&id={id}>', end : '</a>'},
             id : {begin : '<a href=../make/make.do?method=findMake&id={id}>', end : '</a>'}
         },
         buttons : [
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
    
    highlights($("#mainTable").get(0), ['����'], 'blue');
}


function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryMakeView');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../make/make.do" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>