/**
 * 
 */
function addTr()
{
	for (var i = 0; i < 5; i++)
	{
		addTrInner();
	}
}

function addTrInner()
{
	var table = $("tables");
	
	var tr = $("trCopy");
	
	trow = 	table.insertRow(-1);
	
	if (length % 2 == 1)
	{
		trow.className = 'content2';
	}
	else
	{
		trow.className = 'content1';
	}
	
	for (var i = 0; i < tr.cells.length - 1; i++)
	{
		var tcell = document.createElement("td");
		
		tcell.innerHTML = tr.cells[i].innerHTML;
		
		trow.appendChild(tcell);
	}
	
	tcell = document.createElement("td");
	
	tcell.innerHTML = '<input type=button value="ɾ��" class=button_class onclick="removeTr(this)">';
	
	trow.appendChild(tcell);
	
	return trow;
}

function removeTr(obj)
{
	obj.parentNode.parentNode.removeNode(true);
	//rows
	var table = $("tables");
	
	for (var i = 2; i < table.rows.length; i++)
	{
		if (i % 2 == 1)
		{
			table.rows[i].className = 'content1';
		}
		else
		{
			table.rows[i].className = 'content2';
		}
	}
}

function lverify()
{
	var bts = document.getElementsByName('beginTime');
	var ets = document.getElementsByName('endTime');
	var rts = document.getElementsByName('realValue');
	
	if (bts.length <= 1)
	{
		alert('����ӿ�����Ϣ');
		return false;
	}
	
	var total = 0;
	
	var bList = [];
	var eList = [];
	for (var i = 0; i < bts.length; i++)
	{
		if (!(isNoneInCommon(bts[i].value) && isNoneInCommon(ets[i].value) && isNoneInCommon(rts[i].value)))
		{
			if (isNoneInCommon(bts[i].value) || isNoneInCommon(ets[i].value) || isNoneInCommon(rts[i].value))
			{
				alert('��'+(i + 1)+'�����ݲ��걸');
				return false;
			}
			
			bList.push(bts[i].value);
			eList.push(ets[i].value);
			
			total++;
		}
	}
	
	if (total == 0)
	{
		alert('����ӿ�����Ϣ');
		return false;
	}
	
	for (var i = 0; i < bList.length; i++)
	{
		var bf = bList[i];
		var ef = eList[i];
		
		if (bf >= ef)
		{
			alert('��ʼʱ�䲻�ܴ��ڽ���ʱ��:' + bf);	
			return false;
		}
		
		if (bf < min_b)
		{
			alert('��ʼʱ�䲻��С����ȿ�ʼʱ��:' + min_b);	
			return false;
		}
		
		if (ef > max_e)
		{
			alert('����ʱ�䲻�ܴ�����Ƚ���ʱ��:' + max_e);	
			return false;
		}
		
		for (var j = i + 1; j < bList.length; j++)
		{
			var bs = bList[j];
			var es = eList[j];
			
			if (bs < bf && es > ef)
			{
				alert('ʱ����ȫ����:' + bf + ' / ' + bs);	
				return false;
			}
			
			if (bs > bf && es < ef)
			{
				alert('ʱ����ȫ����:' + bf + ' / ' + bs);	
				return false;
			}
			
			if (bf == bs)
			{
				alert('��ʼʱ�䲻�ܵ�����һ����ʼʱ��:' + bf);	
				return false;
			}
			
			if (bs >= es)
			{
				alert('��ʼʱ�䲻�ܴ��ڽ���ʱ��:' + bs);	
				return false;
			}
			
			if (bf < bs && bs <= ef)
			{
				alert('ʱ���غ�:' + bf + ' / ' + bs);	
				return false;
			}
			
			if (bf > bs && bs >= ef)
			{
				alert('ʱ����غ�:' + bf + ' / ' + bs);	
				return false;
			}
		}
	}
	
	return true;
}

 