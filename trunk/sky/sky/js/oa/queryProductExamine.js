function doSearch()
{
	$modalQuery('../admin/query.do?method=popCommonQuery2&key=queryProductExamine');
}

function addBean(opr, grid)
{
   $l('../examine/product.do?method=preForAddProductExamine');
}

function delBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (getRadio('checkb').sstatus != 0 && getRadio('checkb').sstatus != 2)
        {
            alert('ֻ�г�ʼ�Ŀ�������ɾ��');
            
            return false;
        }
        
        if (window.confirm('ȷ��ɾ����Ʒ����--' + getRadio('checkb').lname))
        {
            $ajax('../examine/product.do?method=delProductExamine&id=' + getRadioValue('checkb'), callBackFun);
        }
    }
}

function submitBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (getRadio('checkb').sstatus != 0 && getRadio('checkb').sstatus != 2)
        {
            alert('ֻ�г�ʼ�Ŀ��������ύ');
            
            return false;
        }
        
        if (window.confirm('ȷ���ύ����--' + getRadio('checkb').lname))
        $ajax('../examine/product.do?method=submitProductExamine&id=' + getRadioValue('checkb'), callBackFun);
    }
}

function updateBean()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (getRadio('checkb').sstatus != 0 && getRadio('checkb').sstatus != 2)
        {
            alert('ֻ�г�ʼ�Ŀ��������޸�');
            
            return false;
        }
        
        $l('../examine/product.do?method=queryProductExamineItem&pid=' + getRadioValue('checkb'));
    }
}

function queryCurrent()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $l('../examine/product.do?method=queryProductExamineItem&look=1&pid=' + getRadioValue('checkb'));
    }
}

function callBackFun(data)
{
    reloadTip(data.msg, data.ret == 0);

    if (data.ret == 0)
    commonQuery();
}

function logBean()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $ajax('../examine/examine.do?method=queryExaminLog&id=' + getRadioValue('checkb'), callBackFunLog);
    }
}

function callBackFunLog(data)
{
    $O('logD').innerHTML = '';
    
    var logs = data.msg;
    
    var htm = '';
    for(var i = 0; i < logs.length; i++)
    {
        var item = logs[i];
        
        var llog = item.logTime + ' / ' + item.operation + ' / ' +  item.log + '<br>';
        
        htm += llog;
    }
    
    $O('logD').innerHTML = htm;
    
    $.blockUI({ message: $('#logDiv'),css:{width: '40%'}});
}