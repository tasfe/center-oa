<%@ page contentType="text/html;charset=GBK" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="FLOW" />
<link rel="stylesheet" href="../js/plugin/accordion/accordion.css" />
<script type="text/javascript" src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/accordion/jquery.accordion.js"></script>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>

<script language="javascript">
jQuery().ready(function(){
    
    jQuery('#flowDiv').accordion({
        autoheight: false
    });
    
    var accordions = jQuery('#flowDiv');
    
    accordions.accordion("activate", 0);
});

</script>

</head>
<body class="body_class">
<div class="basic" id="flowDiv">
<a><font><b>1����μ���OFFICE���߱༭�ؼ�</b></font></a>
<div>
<pre>
<img src="../images/help/flow/dll.png">
<font color="red">ע�⣺������IE</font>
</pre>
</div>

<a><font ><b>2����δ������̶���</b></font></a>
<div>
<pre>
<font color="blue">1)���ȵ���������Ա����������������Ȩ��</font>
<img src="../images/help/flow/operation.png">

<font color="blue">2)��������ģ��[����ģ��-->����](��ǰ��֧��WORD��EXCEL)</font>

<font color="blue">3)�������̶���[���̶���-->����]</font>
<img src="../images/help/flow/addflow.png">

<font color="blue">4)�������̻���[���̶���-->���û���]</font>
<img src="../images/help/flow/configtoken.png">
<img src="../images/help/flow/special.png">
<font color="red">ע�⣺ѡ��ģ��ģʽ����Ҫ���ø߼�����,����ģ���޷��༭</font>

<font color="blue">5)�������̲���[���̶���-->���ò���](���û�в���,���Բ�����)</font>
<img src="../images/help/flow/configview.png">

<font color="blue">6)���̶������</font>
<img src="../images/help/flow/flowlist.png">
</pre>
</div>

<a><font ><b>3����δ�������ʵ��</b></font></a>
<div>
<pre>
<font color="blue">1)ѡ��һ�����̶��壬���"��������ʵ��"[��������ʵ��-->��������ʵ��]</font>
<img src="../images/help/flow/createinstance.png">

<font color="blue">2)��д������Ϣ,�������ģ�廹�������߱༭</font>
<img src="../images/help/flow/edittemplate.png">

<font color="blue">3)ѡ����һ��������,�ύʵ��,���̾Ϳ��԰��ն��������ȥ</font>

</pre>
</div>

</div>
</body>
</html>

