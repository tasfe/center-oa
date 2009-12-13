/**
 * depends on: 
 * prototype.js
 */
function getObj()
{
  var elements = new Array();

  for (var i = 0; i < arguments.length; i++) {
    var element = arguments[i];
    var arg = arguments[i];
    if (typeof element == 'string')
    {
      element = document.getElementById(element);
    }

    var element1;
    if (element == null)
    {
    	element1 = document.getElementsByName(arg);
    	if (element1.length == 1)
    	{
    		element = element1[0];
    	}
    }

    if (arguments.length == 1)
      return element;

    elements.push(element);
  }

  return elements;
}

function $()
{
  var elements = new Array();

  for (var i = 0; i < arguments.length; i++) {
    var element = arguments[i];
    var arg = arguments[i];
    if (typeof element == 'string')
    {
      element = document.getElementById(element);
    }

    if (element == null)
    {
    	var element1 = document.getElementsByName(arg);
    	if (element1.length == 1)
    	{
    		element = element1[0];
    	}
    }

    if (arguments.length == 1)
      return element;

    elements.push(element);
  }

  return elements;
}

function $O()
{
  var elements = new Array();

  for (var i = 0; i < arguments.length; i++) {
    var element = arguments[i];
    var arg = arguments[i];
    if (typeof element == 'string')
    {
      element = document.getElementById(element);
    }

    if (element == null)
    {
        var element1 = document.getElementsByName(arg);
        if (element1.length == 1)
        {
            element = element1[0];
        }
    }

    if (arguments.length == 1)
      return element;

    elements.push(element);
  }

  return elements;
}

function trim(num)
{
    while(true)
    {
        var cI = num.charAt(0) ;
        if(cI != " ")
        {
            break ;
        }
        else
        {
            num = num.substring(1 ,num.length) ;
        }
    }
    while(true)
    {
        var cJ = num.charAt(num.length - 1) ;
        if(cJ != " ")
        {
            break ;
        }
        else
        {
            num = num.substring(0 ,num.length - 1) ;
        }
    }
    return num ;
}


function getRadioValue(name)
{
	var obj = document.getElementsByName(name);
	for (var i = 0; i < obj.length; i++)
	{
		if (obj[i].checked)
		{
			return obj[i].value;
		}
	}

	return '';
}

function getRadio(name)
{
	var obj = document.getElementsByName(name);
	for (var i = 0; i < obj.length; i++)
	{
		if (obj[i].checked)
		{
			return obj[i];
		}
	}

	return null;
}

function resetRadio(name)
{
	var obj = document.getElementsByName(name);
	for (var i = 0; i < obj.length; i++)
	{
		obj[i].checked = false;
	}

	return null;
}


/**
 * ����select��ѡ��
 */
function setSelect(selectObj, value)
{
	var va = value;

	if (!isNoneInCommon(va))
	{
		var os = selectObj.options;

		for (var j = 0; j < os.length; j++)
		{
			if (os[j].value == va)
			{
				os[j].selected = true;
				return true;
			}
		}
	}

	return false;
}

/**
 * ����select��ѡ��
 */
function setSelectIndex(selectObj, index)
{
	index = index + '';

	if (!isNoneInCommon(index))
	{
		index = parseInt(index);

		var os = selectObj.options;

		if (os.length > index)
		{
			selectObj.selectedIndex = index;
		}
	}
}

/**
 * ��firfox������������
 */
function $$E(attArray)
{
	if(window.HTMLElement)
	for (var i = 0; i < attArray.length; i++)
	{
		var att = attArray[i];
		eval("HTMLElement.prototype.__defineGetter__('" + att + "',function(){return this.getAttribute('" + att + "');});");
		eval("HTMLElement.prototype.__defineSetter__('" + att + "',function(sText){this.setAttribute('" + att + "', sText);return sText;});");
	}
}

function concat(arr1, arr2)
{
	for (var i = 0; i < arr2.length; i++)
	{
		arr1.push(arr2[i]);
	}
}

/**
 * ����form�ĳ�ʼ��
 */
function loadForm()
{
	//alert('dd');
    var elements = [];
    
    var tem = document.getElementsByTagName("button");
    
    concat(elements, tem);
    
    tem = document.getElementsByTagName("input");
    
    concat(elements, tem);
    
    tem = document.getElementsByTagName("select");
    
    concat(elements, tem);
    
    tem = document.getElementsByTagName("textarea");
    
    concat(elements, tem);

    var ele = null;
    var rIndex = 0;
    var bb = true;
    for (var i = 0; i < elements.length; i++)
	{
		ele = elements[i];
		
		if(window.HTMLElement)
		{
			var atts = ele.attributes;
			
			for (var k = 0; k < atts.length; k++)
			{
				//Firefox only support lowerCase letter
				var aname = atts[k].name;
				
				if (ele[aname] == undefined)
				//if (aname.indexOf('ext') == 0)
				{
					$$E([aname])
				}
			}
		}
		
		if (ele.type.toLowerCase() == 'select-one')
		{
			var va = ele.getAttribute('values');

			setSelect(ele, va);

			var index = ele.getAttribute('index');

			setSelectIndex(ele, index);

			var quick = ele.getAttribute('quick');

			if (quick == "true")
			{
				ele.style.imeMode="disabled";
				
				quickSelect(ele);
			}

			setSelectIndex(ele, index);

			//process readonly
			va = ele.getAttribute('readonly');

			if (va == 'true')
			{
				var ii = 0;
				for (ii = ele.options.length - 1; ii >= 0; ii--)
				{
					if (!ele.options[ii].selected)
					ele.remove(ii);
				}
			}
		}

		if (ele.type.toLowerCase() == 'text')
		{
		    var va = ele.getAttribute('ime');

		    if (va == "false")
			{
				ele.style.imeMode="disabled";
			}
		}

		if (ele.type.toLowerCase() == 'radio' && bb)
		{
			var va = ele.getAttribute('values');

			var index = ele.getAttribute('index');

			if(index == '')
			{
				ele.checked = true;
				bb = false;
			}

			index = index + '';

			if (!isNoneInCommon(va))
			{
				if (ele.value == va)
				{
					ele.checked = true;
				}
			}

			if (!isNoneInCommon(index))
			{
				var index = parseInt(index);

				if (index == rIndex)
				{
					ele.checked = true;
				}
			}

			rIndex++;
		}
	}
}

/**
 * set the index of radio or select
 */
function $Set(name, index)
{
	var obj = document.getElementsByName(name);

	for (var i = 0; i < obj.length; i++)
	{
		if (index == i)
		{
			obj[i].checked = true;
		}
	}
}


/**
 * $F
 */
function $$(name)
{
	var obj = document.getElementsByName(name);

	if (obj == null || obj.length == 0)
	{
		var aar = [];
		
		aar[0] = document.getElementById(name);
		
		obj = aar;
		
		if (aar[0] == null)
		{
			return null;
		}
	}

	if (obj[0].type == 'select-one' || obj[0].type == 'select')
	{
		return getOption(obj[0]).value;
	}

	if (obj[0].type == 'radio' || obj[0].type == 'checkbox')
	{
		for (var i = 0; i < obj.length; i++)
		{
			if (obj[i].checked)
			{
				return obj[i].value;
			}
		}

		return null;
	}

	return obj[0].value;
}

function $f(obj)
{
	 if (typeof obj == 'string')
	 {
	 	obj = $(obj);
	 }

	 obj.focus();
	 //obj.select();
}

/**
 * ��Element������Attribute(IE AND Firefox)
 */
function $a(oElem, att)
{
	if (oElem == null)
	{
		return '';
	}

	if (typeof oElem == 'string')
	{
		oElem = $(oElem);
	}

	if (att == 'value')
	{
		return oElem.value;
	}

	return oElem.getAttribute(att);
}

function getCheckBox(name)
{
	var arr = new Array();
	var k =0;
	var obj = document.getElementsByName(name);
	for (var i = 0; i < obj.length; i++)
	{
		if (obj[i].checked)
		{
			arr[k++] = obj[i];
		}
	}

	return arr;
}

function isNoneInCommon(obj)
{
	if (isNullInCommon(obj))
	{
		return true;
	}

	if (typeof obj == 'string')
	{
		if (obj == '')
		{
			return true;
		}
	}

	return false;
}

function isNullInCommon(obj)
{
	if (obj == undefined || obj == null)
	{
		return true;
	}

	return false;
}

function getOption(obj)
{
	return obj.options[obj.selectedIndex];
}

function getOptionText(obj)
{
	if (typeof obj == 'string')
	{
		obj = $(obj);
	}

	return obj.options[obj.selectedIndex].text;
}


function setOption(obj, value, text)
{
	var oOption = document.createElement("OPTION");
	oOption.text = text;
	oOption.value= value;
	obj.add(oOption);
}

function $Index(name)
{
	var list = document.getElementsByName(name);

	for (var i = 0; i < list.length; i++)
	{
		if (list[i].checked == true)
		{
			return i;
		}
	}
	
	return -1;
}

function $N(name)
{
	return document.getElementsByName(name);
}

function $d(name, f)
{
	var obj = getObj(name);

	if (obj != null)
	{
		if (f != null)
		{
			obj.disabled = f;
		}
		else
		{
			obj.disabled = true;
		}
	}
}

function $v(name, f)
{
	var obj = getObj(name);

	if (obj != null)
	{
		if (f)
		obj.style.display = 'inline';
		else
		obj.style.display = 'none';
	}
}

function $hide(name, f)
{
	var obj = getObj(name);

	if (obj != null)
	{
		if (f != null)
		{
			obj.disabled = f;
		}
		else
		{
			obj.disabled = true;
		}
	}

	if (obj != null)
	{
		if (!f)
		obj.style.display = 'inline';
		else
		obj.style.display = 'none';
	}
}

function modal(url)
{
	var sFeatures="dialogHeight: " + 800 + "px;dialogWidth:" + 900 + "px;";
    window.showModalDialog(url,
        [window],
        sFeatures
        );
}

function mul(n1, n2)
{
    var m=0, s1=n1.toString(), s2=n2.toString();
    try
    {
        m += s1.split(".")[1].length;
    }
    catch(e)
    {}

    try
    {
        m += s2.split(".")[1].length
    }
    catch(e)
    {}

    return Number(s1.replace(".","")) * Number(s2.replace(".","")) / Math.pow(10, m);
}

function add(n1, n2)
{
    var m=0, n=0,s1=n1.toString(), s2=n2.toString();
    try
    {
        m = s1.split(".")[1].length
    }
    catch(e)
    {}

    try
    {
        n = s2.split(".")[1].length
    }
    catch(e)
    {}

    var oo = Math.max(m, n);

    //���string���͵�����ֵ
    s1 = s1.replace(".","") + getLength0(oo - m);
    s2 = s2.replace(".","") + getLength0(oo - n);

    return (Number(s1) + Number(s2)) / Math.pow(10, oo);
}

/**
 * ��֤�ַ����Ƿ������
 */
function isNumbers(str)
{
    var reg = /^[0-9]+$/;
    var reg1 = /^-{1}[0-9]+$/;
    return reg.test(str) || reg1.test(str);
}

/**
 * ��֤�ַ����Ƿ������
 */
function isLetter(str)
{
   var reg = /^[A-Za-z]*$/;
    return reg.test(str);
}

function isFloat(num)
{
	var reg = /^[0-9]*(.)?[0-9]+$/;

	return reg.test(num);
}

function isFloatValue(oo)
{
	var reg = /^[0-9]*(.)?[0-9]+$/;

	if (reg.test(oo.value))
	{
		return true;
	}
	else
	{
		alert('��д��ȷ�Ľ��');
	}
}


//��ʽ������ ��������
function formatNum(num, length)
{
	 var reg = /[0-9]*(.)?[0-9]*$/;

	 if (!reg.test(num))
	 {
	 	reg = /[0-9]*.$/;
	 	if (!reg.test(num))
	 	{
	 		return num;
	 	}
	 }

	 num += '';
	 if (num.indexOf('.') == -1)
	 {
	 	return num + '.' + getLength0(length);
	 }

	 var hou = num.substring(num.indexOf('.') + 1);

	 if (hou.length <= length)
	 {
	 	return num + getLength0(length - hou.length);
	 }

	 //���� ָ������������
	 var ins = parseFloat(num.substring(0, num.indexOf('.') + 1) + hou.substring(0, length));

	 var last = parseInt(hou.charAt(length));
	 var add;

	 if (last >= 5)
	 {
	 	add = '0.' + getLength0(length - 1) + '1';
	 }
	 else
	 {
	 	add = '0.' + getLength0(length);
	 }

	 var result =  ins + parseFloat(add);

	 return result + '';
}

function getLength0(length)
{
	var s = '';
	for (var i = 0; i < length; i++)
	{
		s += '0';
	}

	return s;
}


function isNull(obj)
{
	if (obj == undefined || obj == null)
	{
		return true;
	}

	return false;
}

var tempDiv = document.createElement('DIV');

//afterBegin beforeEnd afterEnd befor after
function appendTable(tab, htm, pos, consult, order)
{
	if (htm.toUpperCase().indexOf('<TABLE>') == -1)
	{
		htm = '<table>' + htm + '</table>';
	}

	tempDiv.innerHTML = htm;

	//���trs
	var trs = tempDiv.getElementsByTagName('TR');

	var insertPos;
	if (isNoneInCommon(arguments[2]))
	{
		insertPos = 'afterEnd';
	}
	else
	{
		insertPos = pos;
	}

	var ord;
	if (isNull(order))
	{
		ord = false;
	}
	else
	{
		ord = true;
	}

	//tr.insertAdjacentElement("afterEnd",rowArray[i])

	//���뵽������ǰ��
	var element;
	var top = tab.getElementsByTagName('TR')[0];
	for (var i = 0; i < trs.length;)
	{
		element = trs[i];

		if (insertPos == 'beforeBegin')
		{
			//�����ʱ����
			if (ord)
			{
				if (isNoneInCommon(consult))
				{
					consult = tab.getElementsByTagName('TR')[0].insertAdjacentElement(insertPos, element);
				}
				else
				{
					consult = consult.insertAdjacentElement(insertPos, element);
				}
			}
			else
			{
				top.insertAdjacentElement(insertPos, element);
			}
		}

		if (insertPos == 'afterEnd')
		{
			if (isNoneInCommon(consult))
			{
				tab.getElementsByTagName('TR')[0].insertAdjacentElement(insertPos, element);
			}
			else
			{
				consult = consult.insertAdjacentElement(insertPos, element);
			}
		}

		if (insertPos == 'befor')
		{
			if (ord)
			{
				consult = consult.insertAdjacentElement('beforeBegin', element);
			}
			else
			{
				consult.insertAdjacentElement('beforeBegin', element);
			}
		}

		if (insertPos == 'after')
		{
			if (!ord)
			{
				consult = consult.insertAdjacentElement('afterEnd', element);
			}
			else
			{
				consult.insertAdjacentElement('afterEnd', element);
			}
		}
	}

	tempDiv.removeNode(false);
}

//ѡ���Զ�����radio
function hrefAndSelect(obj)
{
	//$Set(radio, obj.indexs);
	var tr = getTrObject(obj);

	if (tr != null)
	{
		var rad = tr.getElementsByTagName('input');

		for (i = 0; i < rad.length; i++)
		{
			if (rad[i].type.toLowerCase() == 'radio')
			{
				rad[i].checked = true;
			}
		}
	}
}

function getTrObject(obj)
{
	var i = 0;
	var par;
	par = obj;
	while (par.tagName.toLowerCase() != "tr")
	{
		par = par.parentNode;
		if (i++ > 15)
		{
			return null;
		}
	}

	return par;
}

//�������

var cco = 0;
var telNumbers = 11;
var sortIndex;
var isAsc = 0;
/**
 * �������
 * cell TD
 * num true:num other:ASC
 */
function tableSort(cell, num)
{
    sortIndex = cell.cellIndex;
    var rowArray=[];

    var tr = cell.parentNode;
    var tbody = tr.parentNode;
    var tab = tbody.parentNode;
    var rows = tbody.getElementsByTagName("TR")
    for(i=1;i<rows.length;i++)
    {
        var row = rows[i];

        if (row.id != 'TR_LAST' && row.style.display != "none")
        rowArray[i] = row;
    }
    
    addInsertAdjacentElement(tr);

    if(isAsc==1)
    {
        if (num)
        {
            rowArray.sort(sortAscNum);
        }
        else
        {
            rowArray.sort(sortAscChinese);
        }
    }
    else
    {
        if (num)
        {
            rowArray.sort(sortDescNum);
        }
        else
        {
            rowArray.sort(sortDescChinese);
        }
    }

    try
    {
        for(i=1;i<rowArray.length;i++)
        {
            tr.insertAdjacentElement("afterEnd", rowArray[i]);
        }

    }
    catch(e)
    {
    }

    rows = tbody.getElementsByTagName("TR");
	for (i = 1; i < rows.length; i++)
	{
		if (i % 2 == 0)
		{
			rows[i].className  = 'content2';
		}
		else
		{
			rows[i].className  = 'content1';
		}
	}

    isAsc = (isAsc+1)%2;
}

function addInsertAdjacentElement(obj)
{
	if(window.HTMLElement)
	{
		if (!obj.insertAdjacentElement)
		HTMLElement.prototype.insertAdjacentElement=function(where,parsedNode){ 
	        switch(where){ 
	            case "beforeBegin": 
	                this.parentNode.insertBefore(parsedNode,this); 
	                break; 
	            case "afterBegin": 
	                this.insertBefore(parsedNode,this.firstChild); 
	                break; 
	            case "beforeEnd": 
	                this.appendChild(parsedNode); 
	                break; 
	            case "afterEnd": 
	                if(this.nextSibling) 
	                    this.parentNode.insertBefore(parsedNode,this.nextSibling); 
	                else 
	                    this.parentNode.appendChild(parsedNode); 
	                break; 
	            } 
	        }
	        
	     
	     if (!obj.innerText)
	     {
		     HTMLElement.prototype.__defineSetter__("innerText",function(sText){ 
	        var parsedText=document.createTextNode(sText); 
	        this.innerHTML=parsedText; 
	        return parsedText; 
	        }); 
		    HTMLElement.prototype.__defineGetter__("innerText",function(){ 
		        var r=this.ownerDocument.createRange(); 
		        r.selectNodeContents(this); 
		        return r.toString(); 
		        }); 
	     }
	}     
}

function sortAsc(x, y)
{

    if (x.getElementsByTagName("TD")[sortIndex].innerText > y.getElementsByTagName("TD")[sortIndex].innerText)
    return -1;
    else if (x.getElementsByTagName("TD")[sortIndex].innerText == y.getElementsByTagName("TD")[sortIndex].innerText)
    return 0;
    else
    return 1;
}

function sortDesc(x, y)
{

    if (x.getElementsByTagName("TD")[sortIndex].innerText < y.getElementsByTagName("TD")[sortIndex].innerText)
    return -1;
    else if (x.getElementsByTagName("TD")[sortIndex].innerText == y.getElementsByTagName("TD")[sortIndex].innerText)
    return 0;
    else
    return 1;
}

function sortAscNum(x, y)
{
    try
    {
        if (parseInt(x.getElementsByTagName("TD")[sortIndex].innerText) > parseInt(y.getElementsByTagName("TD")[sortIndex].innerText))
        return -1;
        else if (parseInt(x.getElementsByTagName("TD")[sortIndex].innerText) == parseInt(y.getElementsByTagName("TD")[sortIndex].innerText))
        return 0;
        else
        return 1;
    }
    catch(e)
    {
        return 1;
    }
}

function sortDescNum(x, y)
{
    try
    {
        if (parseInt(x.getElementsByTagName("TD")[sortIndex].innerText) < parseInt(y.getElementsByTagName("TD")[sortIndex].innerText))
        return -1;
        else if (parseInt(x.getElementsByTagName("TD")[sortIndex].innerText) == parseInt(y.getElementsByTagName("TD")[sortIndex].innerText))
        return 0;
        else
        return 1;
    }
    catch(e)
    {
        return 1;
    }
}

function sortAscChinese(x, y)
{
    try
    {
    	return sortChinese(x.getElementsByTagName("TD")[sortIndex].innerText, y.getElementsByTagName("TD")[sortIndex].innerText);;
    }
    catch(e)
    {
        return 1;
    }
}

function sortDescChinese(x, y)
{
    try
    {
    	return sortChinese(y.getElementsByTagName("TD")[sortIndex].innerText, x.getElementsByTagName("TD")[sortIndex].innerText);;
    }
    catch(e)
    {
        return 1;
    }
}

function sortChinese(s, s1)
{
	//s = s.escapeText();
	//s1 = s1.escapeText();
    var  strGB = ' ! \ " #$%&`()*+,-    ./:;<=>?@[\]^_\'{|}~0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz �������������������������������������������������������������������°ðİŰưǰȰɰʰ˰̰ͰΰϰаѰҰӰ԰հְװذٰڰ۰ܰݰް߰������������������������������������������������������������������������������������������������������������±ñıűƱǱȱɱʱ˱̱ͱαϱбѱұӱԱձֱױرٱڱ۱ܱݱޱ߱������������������������������������������������������������������������������������������������������������²òĲŲƲǲȲɲʲ˲̲ͲβϲвѲҲӲԲղֲײزٲڲ۲ܲݲ޲߲������������������������������������������������������������������������������������������������������������³óĳųƳǳȳɳʳ˳̳ͳγϳгѳҳӳԳճֳ׳سٳڳ۳ܳݳ޳߳������������������������������������������������������������������������������������������������������������´ôĴŴƴǴȴɴʴ˴̴ʹδϴдѴҴӴԴմִ״شٴڴ۴ܴݴ޴ߴ������������������������������������������������������������������������������������������������������������µõĵŵƵǵȵɵʵ˵̵͵εϵеѵҵӵԵյֵ׵صٵڵ۵ܵݵ޵ߵ������������������������������������������������������������������������������������������������������������¶öĶŶƶǶȶɶʶ˶̶Ͷζ϶жѶҶӶԶնֶ׶ضٶڶ۶ܶݶ޶߶������������������������������������������������������������������������������������������������������������·÷ķŷƷǷȷɷʷ˷̷ͷηϷзѷҷӷԷշַ׷طٷڷ۷ܷݷ޷߷������������������������������������������������������������������������������������������������������������¸øĸŸƸǸȸɸʸ˸̸͸θϸиѸҸӸԸոָ׸ظٸڸ۸ܸݸ޸߸������������������������������������������������������������������������������������������������������������¹ùĹŹƹǹȹɹʹ˹̹͹ιϹйѹҹӹԹչֹ׹عٹڹ۹ܹݹ޹߹������������������������������������������������������������������������������������������������������������ºúĺźƺǺȺɺʺ˺̺ͺκϺкѺҺӺԺպֺ׺غٺںۺܺݺ޺ߺ������������������������������������������������������������������������������������������������������������»ûĻŻƻǻȻɻʻ˻̻ͻλϻлѻһӻԻջֻ׻ػٻڻۻܻݻ޻߻������������������������������������������������������������������������������������������������������������¼üļżƼǼȼɼʼ˼̼ͼμϼмѼҼӼԼռּ׼ؼټڼۼܼݼ޼߼������������������������������������������������������������������������������������������������������������½ýĽŽƽǽȽɽʽ˽̽ͽνϽнѽҽӽԽսֽ׽ؽٽڽ۽ܽݽ޽߽������������������������������������������������������������������������������������������������������������¾þľžƾǾȾɾʾ˾̾;ξϾоѾҾӾԾվ־׾ؾپھ۾ܾݾ޾߾������������������������������������������������������������������������������������������������������������¿ÿĿſƿǿȿɿʿ˿̿ͿοϿпѿҿӿԿտֿ׿ؿٿڿۿܿݿ޿߿���������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿������������������������������������������������������������������������������������������������������������������������������áâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ������������������������������������������������������������������������������������������������������������������������������ġĢģĤĥĦħĨĩĪīĬĭĮįİıĲĳĴĵĶķĸĹĺĻļĽľĿ������������������������������������������������������������������������������������������������������������������������������šŢţŤťŦŧŨũŪūŬŭŮůŰűŲųŴŵŶŷŸŹźŻżŽžſ������������������������������������������������������������������������������������������������������������������������������ơƢƣƤƥƦƧƨƩƪƫƬƭƮƯưƱƲƳƴƵƶƷƸƹƺƻƼƽƾƿ������������������������������������������������������������������������������������������������������������������������������ǡǢǣǤǥǦǧǨǩǪǫǬǭǮǯǰǱǲǳǴǵǶǷǸǹǺǻǼǽǾǿ������������������������������������������������������������������������������������������������������������������������������ȡȢȣȤȥȦȧȨȩȪȫȬȭȮȯȰȱȲȳȴȵȶȷȸȹȺȻȼȽȾȿ������������������������������������������������������������������������������������������������������������������������������ɡɢɣɤɥɦɧɨɩɪɫɬɭɮɯɰɱɲɳɴɵɶɷɸɹɺɻɼɽɾɿ������������������������������������������������������������������������������������������������������������������������������ʡʢʣʤʥʦʧʨʩʪʫʬʭʮʯʰʱʲʳʴʵʶʷʸʹʺʻʼʽʾʿ������������������������������������������������������������������������������������������������������������������������������ˡˢˣˤ˥˦˧˨˩˪˫ˬ˭ˮ˯˰˱˲˳˴˵˶˷˸˹˺˻˼˽˾˿������������������������������������������������������������������������������������������������������������������������������̴̵̶̷̸̡̢̧̨̣̤̥̦̩̪̫̬̭̮̯̰̱̲̳̹̺̻̼̽̾̿������������������������������������������������������������������������������������������������������������������������������ͣͤͥͦͧͨͩͪͫͬͭͮͯ͢͡ͰͱͲͳʹ͵Ͷͷ͸͹ͺͻͼͽ;Ϳ������������������������������������������������������������������������������������������������������������������������������Ρ΢ΣΤΥΦΧΨΩΪΫάέήίΰαβγδεζηθικλμνξο������������������������������������������������������������������������������������������������������������������������������ϡϢϣϤϥϦϧϨϩϪϫϬϭϮϯϰϱϲϳϴϵ϶ϷϸϹϺϻϼϽϾϿ������������������������������������������������������������������������������������������������������������������������������СТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмноп������������������������������������������������������������������������������������������������������������������������������ѡѢѣѤѥѦѧѨѩѪѫѬѭѮѯѰѱѲѳѴѵѶѷѸѹѺѻѼѽѾѿ������������������������������������������������������������������������������������������������������������������������������ҡҢңҤҥҦҧҨҩҪҫҬҭҮүҰұҲҳҴҵҶҷҸҹҺһҼҽҾҿ������������������������������������������������������������������������������������������������������������������������������ӡӢӣӤӥӦӧӨөӪӫӬӭӮӯӰӱӲӳӴӵӶӷӸӹӺӻӼӽӾӿ������������������������������������������������������������������������������������������������������������������������������ԡԢԣԤԥԦԧԨԩԪԫԬԭԮԯ԰ԱԲԳԴԵԶԷԸԹԺԻԼԽԾԿ������������������������������������������������������������������������������������������������������������������������������աբգդեզէըթժիլխծկհձղճմյնշոչպջռսվտ������������������������������������������������������������������������������������������������������������������������������ְֱֲֳִֵֶַָֹֺֻּֽ֢֣֤֥֦֧֪֭֮֡֨֩֫֬֯־ֿ������������������������������������������������������������������������������������������������������������������������������סעףפץצקרשת׫׬׭׮ׯװױײ׳״׵׶׷׸׹׺׻׼׽׾׿��������������������������������������������������������������������������������������������������������������������&#59408;&#59409;&#59410;&#59411;&#59412;ءآأؤإئابةتثجحخدذرزسشصضطظعغػؼؽؾؿ������������������������������������������������������������������������������������������������������������������������������١٢٣٤٥٦٧٨٩٪٫٬٭ٮٯٰٱٲٳٴٵٶٷٸٹٺٻټٽپٿ������������������������������������������������������������������������������������������������������������������������������ڡڢڣڤڥڦڧڨکڪګڬڭڮگڰڱڲڳڴڵڶڷڸڹںڻڼڽھڿ������������������������������������������������������������������������������������������������������������������������������ۣۡۢۤۥۦۧۨ۩۪ۭ۫۬ۮۯ۰۱۲۳۴۵۶۷۸۹ۺۻۼ۽۾ۿ������������������������������������������������������������������������������������������������������������������������������ܡܢܣܤܥܦܧܨܩܪܫܬܭܮܯܱܴܷܸܹܻܼܾܰܲܳܵܶܺܽܿ������������������������������������������������������������������������������������������������������������������������������ݡݢݣݤݥݦݧݨݩݪݫݬݭݮݯݰݱݲݳݴݵݶݷݸݹݺݻݼݽݾݿ������������������������������������������������������������������������������������������������������������������������������ޡޢޣޤޥަާިީުޫެޭޮޯްޱ޲޳޴޵޶޷޸޹޺޻޼޽޾޿������������������������������������������������������������������������������������������������������������������������������ߡߢߣߤߥߦߧߨߩߪ߲߫߬߭߮߯߰߱߳ߴߵ߶߷߸߹ߺ߻߼߽߾߿����������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������';
    var i,p,l=strGB.length;

    var length = Math.min(s.length, s1.length);

    for (i = 0; i < length; i++)
    {
    	if (s.charAt(i) == s1.charAt(i))
    	{
    		continue;
    	}

    	if (strGB.indexOf(s.charAt(i) + '') > strGB.indexOf(s1.charAt(i) + ''))
    	{
    		return 1;
    	}
    	else
    	{
    		return -1;
    	}
    }

    return s.length - s1.length;
}

/**
 * ɾ�����е�selectԪ��
 */
function removeAllItem(fromSelect)
{
	for (i=fromSelect.options.length-1; i>=0; i--)
	{
		fromSelect.remove(i);
	}

	fromSelect.blur;
}

function $Dbuttons(f)
{
    var bus = document.getElementsByTagName('input');
    
    for (var i = 0; i < bus.length; i++)
    {
        if (bus[i].type.toLowerCase() == 'button' ||
            bus[i].type.toLowerCase() == 'submit' ||
            bus[i].type.toLowerCase() == 'reset')
        {
            dinner(bus[i], f);
        }
    }
}

function dinner(obj, disable){

  if (window.event)
  {
  	  obj.disabled = disable;
  	  return;
  }

  if(disable){
    obj.setAttribute('color_bak', obj.style.color);
    obj.style.color="gray";
  }
  else{
    obj.style.color=obj.getAttribute("color_bak");
  }

  obj.disabled = disable;
}

/**
 * �Ƚ�ʱ��
 */
function compareDays(date1, date2)
{
	var s1 = date1.split('-');
	var s2 = date2.split('-');

	var year1 = parseInt(s1[0], 10);

	var year2 = parseInt(s2[0], 10);

	var month1 = parseInt(s1[1], 10);

	var month2 = parseInt(s2[1], 10);

	var day1 = parseInt(s1[2], 10);

	var day2 = parseInt(s2[2], 10);

	return Math.abs((year2 - year1) * 365 + (month2 - month1) * 30 + (day2 - day1));
}

function displayTime()
{
    var today = new Date();
    var date = today.getYear() + "-";
	if ( today.getMonth() < 9 ) date += "0";
	date += today.getMonth() + 1 + "-";
	if ( today.getDate() < 10 ) date += "0";
	date += today.getDate() + " ";
	if ( today.getHours() < 10 ) date += "0";
	date += today.getHours() + ":";
	if ( today.getMinutes() < 10 ) date += "0";
	date += today.getMinutes() + ":";
	if ( today.getSeconds() < 10 ) date += "0";
	date += today.getSeconds();
	document.getElementById("timer").innerHTML=date;
	setTimeout("displayTime()",990);
}

/**
 * ���ٰ�select������ƴ����
 */
function quickSelect(element)
{
	if (typeof element == 'string')
    {
        element = document.getElementById(element);
    }

	window.common.addEvent(element, 'keydown', spellList);
}

function $l(href)
{
	$Dbuttons(true);
	
	document.location.href = href;
}

function addEventCommon(el, evname, func)
{
	if (el.attachEvent)
    {
        // IE
        el.attachEvent("on" + evname, func);
    }
    else if (el.addEventListener)
    {
        // Gecko / W3C
        el.addEventListener(evname, func, true);
    }
    else
    {
        el["on" + evname] = func;
    }
}


if (window.addEventCommon)
window.addEventCommon(window, 'load', loadForm);

