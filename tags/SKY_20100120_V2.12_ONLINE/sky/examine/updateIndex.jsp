<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<p:link title="��������" link="true" guid="false" cal="false"/>
<META http-equiv=Content-Type content="text/html; charset=GBK">
<LINK href="../css/tabs/jquery.tabs-ie.css" type=text/css rel=stylesheet>
<LINK href="../css/tabs/jquery.tabs.css" type=text/css rel=stylesheet>
<SCRIPT src="../js/jquery/jquery.js" type=text/javascript></SCRIPT>
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
<div id="container-1">
<ul>
	<li><a href="#fragment-1"><span>ҵ��</span></a></li>
	<li><a href="#fragment-2"><span>����ͻ��ɽ�</span></a></li>
</ul>

<div id="fragment-1"><IFRAME height="95%"
	src="../examine/updateProfitItem.jsp?&id=${param.id}"
	id="ifr3" frameborder="0" width="100%"
	scrolling="auto"></IFRAME></div>

<div id="fragment-2"><IFRAME height="95%"
	src="../examine/examine.do?method=queryCityProfitExamine&pid=${param.id}&readonly=0"
	id="ifr4" frameborder="0" width="100%"
	scrolling="auto"></IFRAME></div>

</div>
</BODY>
</HTML>
