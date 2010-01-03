<%@ page contentType="text/html;charset=GBK" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<HTML><HEAD>
<TITLE>我的桌面</TITLE>
<SCRIPT type="text/javascript" src="../js/jquery/jquery.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/plugin/interface/interface.js"></SCRIPT>
<STYLE type="text/css" media="all">
html
{
	height: 95%;
}
img{
	border: none;
}
body
{
	background: #fff;
	height: 100%;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
}
.groupWrapper
{
	width: 48%;
	float: left;
	margin-right: 1%;
	min-height: 400px;
}
.serializer
{
	clear: both;
}
.groupItem
{
	margin-bottom: 20px;
}
.groupItem .itemHeader
{
	line-height: 28px;
	background-color: #DAFF9F;
	border-top: 2px solid #B5EF59;
	color: #000;
	padding: 0 10px;
	cursor: move;
	font-weight: bold;
	font-size: 16px;
	height: 28px;
	position: relative;
}

.groupItem .itemHeader a
{
	position: absolute;
	right: 10px;
	top: 0px;
	font-weight: normal;
	font-size: 12px;
	text-decoration: none;
}
.sortHelper
{
	border: 3px dashed #666;
	width: auto !important;
}
.groupWrapper p
{
	height: 1px;
	overflow: hidden;
	margin: 0;
	padding: 0;
}

</STYLE>
</HEAD><BODY>
<DIV id="sort1" class="groupWrapper">
	<DIV id="newsFeeder" class="groupItem" style="left: -14px; top: -56px; display: block; position: static; ">
		<DIV class="itemHeader" style="-webkit-user-select: none; ">我的邮件<A href="javaScript:void(0)" class="closeEl">[-]</A></DIV>
		<DIV class="itemContent" style="display: block; overflow-x: visible; overflow-y: visible; ">
			<c:forEach items="${mailList}" var="item">
			<UL>
				<LI><a href="../mail/mail.do?method=findMail&id=${item.id}" title="${item.title}">
				[${item.logTime}] ${my:truncateString(item.title, 0, 45)}</a></LI>
			</UL>
		    </c:forEach>
		</DIV>
	</DIV>
	
	<P>&nbsp;</P>
</DIV>
<DIV id="sort2" class="groupWrapper">
	<DIV id="news" class="groupItem" style="left: 371px; top: -102px; display: block; position: static; ">
		<DIV class="itemHeader" style="-webkit-user-select: none; ">待我审批<A href="javaScript:void(0)" class="closeEl">[-]</A></DIV>
		<DIV class="itemContent" style="display: block; overflow-x: visible; overflow-y: visible; ">
			<c:forEach items="${flowList}" var="item">
			<UL>
                <LI><a href="../flow/instance.do?method=findFlowInstance&id=${item.instanceId}">
                [${item.logTime}] ${my:truncateString(item.title, 0, 45)}
                </a></LI>
			</UL>
            </c:forEach>
		</DIV>
	</DIV>
	
	<P>&nbsp;</P>
</DIV>
<SCRIPT type="text/javascript">
$(document).ready(
	function () {
		$('a.closeEl').bind('click', toggleContent);
		$('div.groupWrapper').Sortable(
			{
				accept: 'groupItem',
				helperclass: 'sortHelper',
				activeclass : 	'sortableactive',
				hoverclass : 	'sortablehover',
				handle: 'div.itemHeader',
				tolerance: 'pointer',
				onChange : function(ser)
				{
				},
				onStart : function()
				{
					$.iAutoscroller.start(this, document.getElementsByTagName('body'));
				},
				onStop : function()
				{
					$.iAutoscroller.stop();
				}
			}
		);
	}
);
var toggleContent = function(e)
{
	var targetContent = $('div.itemContent', this.parentNode.parentNode);
	if (targetContent.css('display') == 'none') {
		targetContent.slideDown(10);
		$(this).html('[-]');
	} else {
		targetContent.slideUp(10);
		$(this).html('[+]');
	}
	return false;
};

</SCRIPT>
</BODY></HTML>