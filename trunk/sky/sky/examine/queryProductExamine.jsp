<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="��Ʒ��������" link="true" guid="true" cal="false" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/oa/queryProductExamine.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

var updatFlag = window.top.topFrame.containAuth('0304') ? '1' : '0';
function load()
{
     preload();
     
     guidMap = {
         title: '��Ʒ�����б�',
         url: '../examine/product.do?method=queryProductExamine',
         colModel : [
             {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lname={name} sstatus={status}>', width : 40, sortable : false, align: 'center'},
             {display: '����', name : 'name', width : '20%', sortable : false, align: 'left'},
             {display: '���˲�Ʒ', name : 'productName', width : '15%', sortable : false, align: 'left'},
             {display: '��Ʒ����', name : 'productCode', width : '10%', sortable : false, align: 'left'},
             {display: '״̬', name : 'status', width : '8%', sortable : false, align: 'left', cc: 'examineStatus'},
             {display: '����ʱ��', name : 'endTime', width : '15%', sortable : true, align: 'left'},
             {display: '�ֹ�˾', name : 'locationName', width : '10%', sortable : false, align: 'left'},
             {display: 'ʱ��', name : 'logTime', width : 'auto', sortable : true, align: 'left'}
             ],
         extAtt: {
             name : {begin : '<a href=../examine/product.do?method=findProductExamine&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'add', bclass: 'add', onpress : addBean, auth: '0304'},
             {id: 'del', bclass: 'delete', onpress : delBean, auth: '0304'},
             {id: 'submits', bclass: 'pass', caption: '�ύ', onpress : submitBean, auth: '0304'},
             {id: 'update2', caption: '���ò�Ʒ������', bclass: 'update', onpress : updateBean, auth: '0304'},
             {id: 'current',  caption: '�鿴����', bclass: 'search', onpress : queryCurrent},
             {id: 'log',  caption: '������־', bclass: 'search', onpress : logBean},
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
 
</script>
</head>
<body onload="load()" class="body_class">
<form>
<p:cache></p:cache>

<div id="logDiv" style="display:none">
<p align='left'><label><font color=""><b>������־:</b></font></label></p>
<p><label>&nbsp;</label></p>
<div id="logD" align='left'>
</div>
<p><label>&nbsp;</label></p>
<p>
<input type='button' id='div_b_cancle' value='&nbsp;&nbsp;�� ��&nbsp;&nbsp;' class='button_class' onclick='$close()'/>
</p>
<p><label>&nbsp;</label></p>
</div>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>