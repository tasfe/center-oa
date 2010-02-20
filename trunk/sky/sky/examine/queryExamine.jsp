<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="ҵ��������" link="true" guid="true" cal="false" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/oa/queryExamine.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

var updatFlag = window.top.topFrame.containAuth('0304') ? '1' : '0';
function load()
{
     preload();
     
	 guidMap = {
		 title: 'ҵ�񿼺��б�',
		 url: '../examine/examine.do?method=queryExamine',
		 colModel : [
		     {display: 'ѡ��', name : 'check', content : '<input type=radio name=checkb value={id} lname={name} sstatus={status} atttype={attType} abs={abs}>', width : 40, sortable : false, align: 'center'},
		     {display: '����', name : 'name', width : '20%', sortable : false, align: 'left'},
		     {display: '��������', name : 'stafferName', width : '8%', sortable : false, align: 'left'},
		     {display: '���', name : 'year', width : '5%', sortable : true, align: 'left'},
		     {display: '����', name : 'attType', width : '10%', sortable : false, align: 'left', cc: 'attType'},
		     {display: '״̬', name : 'status', width : '10%', sortable : false, align: 'left', cc: 'examineStatus'},
		     {display: 'ҵ��', name : 'totalProfit', width : '8%', sortable : false},
		     {display: '������', name : 'parentName', width : '10%', sortable : false},
		     {display: '����', name : 'type', width : '8%', sortable : false, cc: 'examineType'},
		     {display: 'ʱ��', name : 'logTime', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=../examine/examine.do?method=findExamine&id={id}>', end : '</a>'}
		 },
		 buttons : [
		     {id: 'add', bclass: 'add', onpress : addBean, auth: '0304'},
		     {id: 'update', bclass: 'update', onpress : updateBean, auth: '0304'},
		     {id: 'submits', bclass: 'pass', caption: '�ύ', onpress : submitBean, auth: '0304'},
		     {id: 'del', bclass: 'delete', onpress : delBean, auth: '0304'},
		     {id: 'update1', caption: '���ÿ�����', bclass: 'edit', onpress : configYear, auth: '0304'},
		     {id: 'update3', caption: '����ȫ���ӿ���', bclass: 'edit', onpress : configAllSubItem, auth: '0305'},
		     {id: 'update4', caption: '�������', bclass: 'edit', onpress : changeItem, auth: '0304'},
		     {id: 'update5', caption: '��������', bclass: 'delete', onpress : delBean2, auth: '0304'},
		     {id: 'current',  caption: '�鿴����', bclass: 'search', onpress : queryCurrent},
		     {id: 'current1',  caption: '�鿴�ӿ��˽���', bclass: 'search', onpress : queryAllSubCurrent},
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
<div id="dataDiv" style="display:none">
<p align='left'><label><font color=""><b>�������˻�ԭ��</b></font></label></p>
<p><label>&nbsp;</label></p>
<textarea name="reason" value="" rows="4" oncheck="notNone;maxLength(100)" style="width: 85%"></textarea>
<p><label>&nbsp;</label></p>
<p>
<input type='button' value='&nbsp;&nbsp;ȷ ��&nbsp;&nbsp;' id='div_b_ok1' class='button_class' onclick='$process()'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type='button' id='div_b_cancle' value='&nbsp;&nbsp;�� ��&nbsp;&nbsp;' class='button_class' onclick='$close()'/>
</p>
<p><label>&nbsp;</label></p>
</div>

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