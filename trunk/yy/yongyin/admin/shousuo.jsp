<%@ page contentType="text/html;charset=GBK" language="java"%>
<%@include file="./common.jsp"%>
<%@page
	import="java.util.Random"%>
<html>
<head>
<p:link title="��ʾ�˵�" />
<link href="../css/shousuo.css" type=text/css rel=stylesheet>
<script type=text/javascript><!--
var LastLeftID = "";
function menuFix() {
var obj = document.getElementById("nav").getElementsByTagName("li");

for (var i=0; i<obj.length; i++) {
   obj[i].onmouseover=function() {
    this.className+=(this.className.length>0? " ": "") + "sfhover";
   }
   obj[i].onMouseDown=function() {
    this.className+=(this.className.length>0? " ": "") + "sfhover";
   }
   obj[i].onMouseUp=function() {
    this.className+=(this.className.length>0? " ": "") + "sfhover";
   }
   obj[i].onmouseout=function() {
    this.className=this.className.replace(new RegExp("( ?|^)sfhover\\b"), "");
   }
}
}
function DoMenu(emid)
{
var obj = document.getElementById(emid);
obj.className = (obj.className.toLowerCase() == "expanded"?"collapsed":"expanded");
if((LastLeftID!="")&&(emid!=LastLeftID)) //�ر���һ��Menu
{
   document.getElementById(LastLeftID).className = "collapsed";
}
LastLeftID = emid;
}
function GetMenuID()
{
var MenuID="";
var _paramStr = new String(window.location.href);
var _sharpPos = _paramStr.indexOf("#");

if (_sharpPos >= 0 && _sharpPos < _paramStr.length - 1)
{
   _paramStr = _paramStr.substring(_sharpPos + 1, _paramStr.length);
}
else
{
   _paramStr = "";
}

if (_paramStr.length > 0)
{
   var _paramArr = _paramStr.split("&");
   if (_paramArr.length>0)
   {
    var _paramKeyVal = _paramArr[0].split("=");
    if (_paramKeyVal.length>0)
    {
     MenuID = _paramKeyVal[1];
    }
   }
   /*
   if (_paramArr.length>0)
   {
    var _arr = new Array(_paramArr.length);
   }

   //ȡ����#����ģ��˵�ֻ���õ�Menu
   //for (var i = 0; i < _paramArr.length; i++)
   {
    var _paramKeyVal = _paramArr[i].split('=');

    if (_paramKeyVal.length>0)
    {
     _arr[_paramKeyVal[0]] = _paramKeyVal[1];
    }
   }
   */
}

if(MenuID!="")
{
   DoMenu(MenuID)
}
}


function load()
{
	GetMenuID(); //*������function��˳��Ҫע��һ�£���Ȼ��Firefox��GetMenuID()����Ч��
	menuFix();
}
--></script>
</head>
<body class="tree_class" onload="load()">
<table>
	<tr height="10">
		<td colspan="2"></td>
	</tr>

	<tr height="10">
		<td width="15"></td>
		<td><font color="blue"
			style="font-family: arial, ����, serif; size: 12px"><B>[${GLocationName}]
		${user.stafferName} ��½[3.11]</B></font></td>
	</tr>

	<tr height="10">
		<td colspan="2"></td>
	</tr>
</table>
<div id="PARENT">
<ul id="nav">
	<c:forEach var="item" items="${menuRootList}" varStatus="vs">
		<li><a href="#Menu=ChildMenu${vs.index}"
			onclick="DoMenu('ChildMenu${vs.index}')">${item.menuItemName}</a>
		<ul id="ChildMenu${vs.index}" class="collapsed">
			<c:forEach var="item1" items="${menuItemMap[item.id]}"
				varStatus="vs1">
				<li><a href="${item1.url}" target="main" id="a_${item1.id}">${item1.menuItemName}</a></li>
			</c:forEach>
		</ul>
		</li>
	</c:forEach>
	
	<li><a href="../admin/modifyPassword.jsp" target="main">�޸�����</a></li>
	<c:if test="${user.role == 'NETASK'}">
	 <li><a href="../admin/logoutAsk.do" target="_parent">�˳�</a></li>
	</c:if>
	
	<c:if test="${user.role != 'NETASK'}">
   
    <li><a href="../admin/logout.do" target="_parent">�˳�</a></li>
    </c:if>
</ul>
</div>


</body>
</html>
