function heji(obj)
{
	var os = obj.parentNode.parentNode;
	//os.cells.length
	var m = os.cells[2].childNodes[0].value;
	var p = os.cells[3].childNodes[0].value;
	
	obj.value = mul(m, p);
}

function ccs(obj)
{
	var os = obj.parentNode.parentNode;
	
	var m = os.cells[2].childNodes[0].value;
	var p = os.cells[3].childNodes[0].value;
	
	os.cells[4].childNodes[0].value = mul(m, p);
	
	var tem = os.cells[4].childNodes[0].value;
	
	if (tem == 'NaN')
	{
		os.cells[4].childNodes[0].value = '';
		return;
	}
	
	tem = tem + "";
	
	os.cells[4].childNodes[0].value = formatNum(tem, 2);
	
	total();
	
}

function cc(obj)
{
	ccs(obj);
	total();
}

function blu(obj)
{
	blus(obj);
	ccs(obj);
}



function blus(obj)
{
	obj.value = trim(obj.value);
	var tem = obj.value;
	
	if (tem.indexOf('.') == -1)
	{
		if (tem == '')
		{
			obj.value = '0.00';
			return;
		}
		obj.value = tem + '.00';
		return;
	}
	
	tem += '00';
	obj.value = tem.substring(0, tem.indexOf('.') + 3);
}

function addTr()
{
	var length = document.getElementsByName("productName").length;
	
	if (length > 10)
	{
		alert('���ֻ����10����Ʒ');
		return null;
	}
	
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
	
	total();
	
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
	
	total();
}

function distinc(ox)
{
	//productId
	var plist = document.getElementsByName('productName');
	
	var arr1 = new Array();
	for(var i = 0; i < plist.length; i++)
	{
		arr1[i] = plist[i].productId;
	}
	
	var arr = new Array();
	var n = 0;
	for(var k = 0; k < ox.length; k++)
	{
		var ff = false;
		for(var i = 0; i < arr1.length; i++)
		{
			if (arr1[i] == ox[k].value)
			{
				ff = true;
			}
		}
		
		if (!ff)
		{
			arr1.push(ox[k].value);
			arr[n++] = ox[k];
		}
	}
	
	return arr;
}

function clears()
{
	document.getElementById('unAmount').value = '';
	document.getElementById('unAmount').title = '';
	document.getElementById('unPrice').value = '';
	document.getElementById('unProductName').value = '';
	document.getElementById('unDesciprt').value = '';
}

function getProduct(ox, selectLocationId)
{
	ox = distinc(ox);
	
	if (selectLocationId)
	{
		if ($('location'))
		{
			rs = setSelect($('location'), selectLocationId);
			
			if (!rs)
			{
				alert('��Ʒѡ����������������ѡ��');
				return;
			}
		}
	}
	
	if (ox.length <= 0)
	{
		return;
	}
	
	if (false && ox.length == 1)
	{
		oo.value = ox[0].productName;
		oo.productId = ox[0].value;
		var os = oo.parentNode.parentNode;
		os.cells[2].childNodes[0].title = '��ǰ��Ʒ���������:' + ox[0].num;
	}
	else
	{
		var indes = 0;
		if (oo.value == '' || oo.value == null)
		{
			indes = 1;
			oo.value = ox[0].productName;
			oo.productId = ox[0].value;
			var os = oo.parentNode.parentNode;
			os.cells[2].childNodes[0].title = '��ǰ��Ʒ���������:' + ox[0].num;
		}
		
		for(var i = indes; i < ox.length; i++)
		{
			var newTr = addTr();
			
			if (newTr == null)
			{
				break;
			}
			var inps = newTr.getElementsByTagName('INPUT');
			
			inps[0].value = ox[i].productName;
			inps[0].productId = ox[i].value;
			
			inps[1].title = '��ǰ��Ʒ���������:' + ox[i].num;
		}
	}
}
