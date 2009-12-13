<%@ page contentType="text/html;charset=GBK" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="TOP" />
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/prototype.js"></script>
<script language="JavaScript" src="../js/buffalo.js"></script>
<script language="JavaScript" src="../js/oa/city.js"></script>
<script language="JavaScript" src="../js/oa/enc.js"></script>
<STYLE type="text/css">
body
{
    font-size: 12px;
}
</STYLE>
<script language="javascript">
var END_POINT="${pageContext.request.contextPath}/bfapp";

var gbuffalo = new Buffalo(END_POINT);

function sho()
{
	var targerWindow = window.top.main;
	
	var doc = targerWindow.document;
	
	var baseT = doc.getElementsByTagName("base");
	
	if (baseT && baseT.length > 0 && baseT[0].href)
	{
		var links = baseT[0].href;
		
		var jsp = links.substring(links.lastIndexOf('/') + 1);
		
		window.common.clipboard(jsp); 
	}
}

var allDef = JSON.parse('<p:def/>');

//var cmap = JSON.parse('{jsStrJSON}');

//var pList = JSON.parse('{pStrJSON}');

var uAuth = JSON.parse('${authJSON}');

var gAuth = [];

function load()
{
    for(var i = 0; i < uAuth.length; i++)
    {
        gAuth[i] = uAuth[i].authId;
    }
    
    <c:if test="${hasEncLock}">
    setTimeout("checkLock()", 30000);
    </c:if>
}

function containAuth(authId)
{
    for(var i = 0; i < gAuth.length; i++)
    {
        if (gAuth[i] == authId)
        {
            return true;
        }
    }
    
    return false;
}

var faileCount = 0;

function checkLock()
{
    var str = checkLockExist();
    
    if (str == '' || str != '${gkey}')
    {
        alert('��������ʧ��[' + (faileCount + 1) + '��],��������������ϵͳ�Զ��˳�!');
        
        faileCount++;
        
        if (faileCount >= 3)
	    {
	        window.top.location = '../admin/logout.do';
	        
	        return;
	    }
    }
    else
    {
        faileCount = 0;
    }
    
    
    setTimeout("checkLock()", 30000);
}
</script>
</head>
<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" onload="load()"
	marginheight="0">

<table width="100%" border="0" cellspacing="0" cellpadding="0" background="../images/login/vistablue.jpg" style="height: 63px">
  <tr ondblclick="sho()" background="../images/login/vistablue.jpg">
    <td ondblclick="sho()"></td>
    <td ondblclick="sho()"><font color="#FFFFFF" size="2"><b>&nbsp;OAϵͳ/SKY�����V2.0.1108.Beta��</b></font></td>
    
    
    <td   ondblclick="sho()" align="right">
    <font color="#FFFFFF">��¼�ߣ�${g_stafferBean.name}</font>
    <a href="../pers/persional.do?method=queryPersionalDeskTop" target="main" title="�ҵ�����"><img src="../images/oa/desk.png" width="20px" height="20px" border="0"/></a>
    <a target="_blank" href="../help/main.jsp" title="��������"><img src="../images/oa/help.png" width="20px" height="20px" border="0"/></a>
    <a href="../admin/logout.do" target="_parent" title="�˳���¼"><img src="../images/oa/logout.gif" width="20px" height="20px" border="0"/></a>
    </td>
    <td ondblclick="sho()" width="2%"></td>
    
  </tr>
</table>
</body>
</html>
