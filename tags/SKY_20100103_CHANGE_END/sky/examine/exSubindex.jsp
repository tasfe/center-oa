<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<p:link title="�ӿ�������" link="true" guid="false" cal="false"/>
<META http-equiv=Content-Type content="text/html; charset=GBK">
<LINK href="../css/tabs/jquery.tabs-ie.css" type=text/css rel=stylesheet>
<LINK href="../css/tabs/jquery.tabs.css" type=text/css rel=stylesheet>
<SCRIPT src="../js/jquery/jquery-1.2.6.js" type=text/javascript></SCRIPT>
<SCRIPT src="../js/jquery/jquery.tabs.js" type=text/javascript></SCRIPT>
<SCRIPT>
function load()
{
    $('#container-1').tabs();
    
    //��ʾcontainer-1
    $('#container-1').css({display:"block"});
}
</SCRIPT>
</HEAD>
<BODY onload=load()>
<div id="container-1" style="display: none;">
<ul>
    <c:if test="${bean.attType == 1}">
	<li><a href="#fragment-1"><span>�¿ͻ�</span></a></li>
	<li><a href="#fragment-2"><span>�Ͽͻ�</span></a></li>
	</c:if>
	<li><a href="#fragment-3"><span>ҵ��</span></a></li>
	<c:if test="${bean.attType == 1}">
	<li><a href="#fragment-4"><span>����ͻ��ɽ�</span></a></li>
	</c:if>
</ul>
<c:if test="${bean.attType == 1}">
<div id="fragment-1"><IFRAME height="100%"
	src="../examine/examine.do?method=queryAllSubNewCustomerExamine&pid=${bean.id}&readonly=${readonly}&look=${look}"
	id="ifr1" frameborder="0" width="100%" scrolling="auto"></IFRAME></div>
<div id="fragment-2"><IFRAME height="100%"
	src="../examine/examine.do?method=queryAllSubOldCustomerExamine&pid=${bean.id}&readonly=${readonly}&look=${look}"
	id="ifr2" frameborder="0" width="100%"
	scrolling="auto"></IFRAME></div>
</c:if>
<div id="fragment-3"><IFRAME height="100%"
	src="../examine/examine.do?method=queryAllSubProfitExamine&pid=${bean.id}&readonly=${readonly}&look=${look}"
	id="ifr3" frameborder="0" width="100%"
	scrolling="auto"></IFRAME></div>
<c:if test="${bean.attType == 1}">
<div id="fragment-4"><IFRAME height="100%"
	src="../examine/examine.do?method=queryAllSubCityProfitExamine&pid=${bean.id}&readonly=${readonly}&look=${look}"
	id="ifr4" frameborder="0" width="100%"
	scrolling="auto"></IFRAME></div>
</c:if>
</div>
</BODY>
</HTML>