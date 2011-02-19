<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="处理发货单" cal="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="javascript">
function sub()
{
    if (formCheck())
    {
        if (window.confirm('确定审核通过发货单?'))
        {
            outForm.submit();
        }
    }
}

function sub1()
{
    if (formCheck())
    {
        if (window.confirm('确定修改发货信息?'))
        {
            $O('method').value = 'reportConsign';
            outForm.submit();
        }
    }
}

function load()
{
	loadForm();
    change();
    loadForm();
    
    <c:if test="${consignBean.reprotType > 0 || readonly}">
    setAllReadOnly($O('showTable'));
    $detail($O('showTable'), ['transport1', 'transport']);
    </c:if>
}

var jmap = new Object();
<c:forEach items="${transportList}" var="item">
    jmap['${item.id}'] = "${map[item.id]}";
</c:forEach>

function change()
{
    if ($O('transport') == null)
    {
        return;
    }
    
    removeAllItem($O('transport'));
    
    setOption($O('transport'), '', '--');
    var items = jmap[$$('transport1')];
    
    if (isNoneInCommon(items))
    {
        return;
    }
    
    var ss = items.split('#');
    for (var i = 0; i < ss.length; i++)
    {
        if (!isNoneInCommon(ss[i]))
        {
            var kk = ss[i].split('~');
            setOption($O('transport'), kk[1], kk[0]);
        }
    }
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="outForm" action="../sail/transport.do"><input
	type="hidden" name="method" value="passConsign">
<input type=hidden name="fullId" value="${out.fullId}" /> 
<input type="hidden" value="3" name="statuss">
<input type="hidden" value="${consignBean.currentStatus}" name="currentStatus">
<p:navigation
	height="22">
	<td width="550" class="navigation">发货单</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>发货单信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1" id="showTable">
			  <tr class="content1">
                        <td width="15%">销售单号：</td>
                            <td colspan="3"><a title="点击查看明细" href="../sail/out.do?method=findOut&fow=99&outId=${out.fullId}">${out.fullId}</a></td>
                        </tr>
						
					<c:if test="${consignBean.currentStatus < 2}">
						
                        <tr class="content1">
                            <td>处理：</td>
                            <td colspan="3"><textarea rows="3" cols="55" name="applys" head="处理"
                                oncheck="notNone">${consignBean.applys}</textarea></td>
                        </tr>
                    </c:if>

                    <c:if test="${consignBean.currentStatus >= 2}">
                        <tr class="content1">
                            <td>运输方式：</td>
                            <td colspan="3"><select name="transport1"
                                values="${tss1.id}" onchange="change()">
                                <option value="">--</option>
                                <c:forEach items="${transportList}" var="item">
                                    <option value="${item.id}">${item.name}</option>
                                </c:forEach>
                            </select>&nbsp;&nbsp; <select name="transport"
                                values="${consignBean.transport}">
                                <option value="">--</option>
                            </select></td>
                        </tr>
                        
                        <tr class="content2">
                             <td>发货单号：</td>
                            <td colspan="3"><input type="text" name="transportNo" id="transportNo" value="${consignBean.transportNo}" class="input_class"></td>
                        </tr>

                        <tr class="content2">
                             <td>检验人：</td>
                            <td colspan="3"><input type="text" name="checker" value="${consignBean.checker}" class="input_class"></td>
                        </tr>
                        
                        <tr class="content1">
                             <td>打包人：</td>
                            <td colspan="3"><input type="text" name="packager" value="${consignBean.packager}" class="input_class"></td>
                        </tr>
                        
                        <tr class="content2">
                             <td>打包时间：</td>
                            <td colspan="3"><p:plugin name="packageTime" type="1" value="${consignBean.packageTime}"></p:plugin>
                            </td>
                        </tr>
                        
                        <tr class="content1">
                             <td>包装件数：</td>
                            <td colspan="3"><input type="text" name="packageAmount" value="${consignBean.packageAmount}" class="input_class"></td>
                        </tr>
                        
                        <tr class="content2">
                             <td>包装重量：</td>
                            <td colspan="3"><input type="text" name="packageWeight" value="${consignBean.packageWeight}" class="input_class"></td>
                        </tr>
                        
                        <tr class="content2">
                             <td>回访时间：</td>
                            <td colspan="3"><p:plugin name="visitTime" type="1" value="${consignBean.visitTime}"></p:plugin>
                            </td>
                        </tr>
                        
                        <tr class="content2">
                            <td>是否满意服务：</td>
                            <td colspan="3"><select name="promitType"
                                values="${consignBean.reprotType}">
                                <p:option type="consignPromitType"></p:option>
                            </select></td>
                        </tr>
                    </c:if>

                    <c:if test="${consignBean.reprotType == 0 && consignBean.currentStatus >= 2}">
                        <tr class="content1">
                            <td>收货类型：</td>
                            <td colspan="3"><select name="reprotType"
                                values="${consignBean.reprotType}">
                                <option value="">--</option>
                                <option value="1">正常收货</option>
                                <option value="2">异常收货</option>
                            </select></td>
                        </tr>
                    </c:if>
                    
                    <c:if test="${consignBean.reprotType != 0}">
                        <tr class="content1">
                            <td>收货类型：</td>
                            <td colspan="3">${consignBean.reprotType == 1 ? "正常收货" : "异常收货"}</td>
                        </tr>
                    </c:if>
                    
                    <c:if test="${consignBean.currentStatus >= 2}">
                    
                      <tr class="content1">
                            <td>处理：</td>
                            <td colspan="3"><textarea rows="3" cols="55" name="applys"
                                >${consignBean.applys}</textarea></td>
                       </tr>
                       
                    </c:if>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right" id="pr">
		    <c:if test="${!readonly}">
			    <c:if test="${init}">
	            <input type="button" class="button_class" onclick="sub()"
	                value="&nbsp;&nbsp;审核通过&nbsp;&nbsp;">&nbsp;&nbsp;
	            </c:if><c:if test="${consignBean.reprotType == 0 && consignBean.currentStatus >= 2}">
	            <input type="button" class="button_class" onclick="sub1()"
	                value="&nbsp;&nbsp;修改发货信息&nbsp;&nbsp;">&nbsp;&nbsp;
	            </c:if> 
            </c:if> 
            <input type="button" class="button_class"
            onclick="javascript:history.go(-1)"
            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
	</p:button>

	<p:message2/>
</p:body>
</form>
</body>
</html>

