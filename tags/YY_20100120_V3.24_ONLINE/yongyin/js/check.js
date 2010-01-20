function MyCheck()
{}

function isNone(obj)
{
	if (isNull(obj))
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

function isNull(obj)
{
	if (obj == undefined || obj == null)
	{
		return true;
	}
	
	return false;
}

function trimInCheck(num)
{
    if (isNone(num))
    {
        return '';
    }
    
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

/**
 * ���һ���ַ�������ʵ���ȣ�һ��������2���ֽڣ�
 */
function getLengthInCheck(str)
{
    var length2 = str.length;
    for(var i = 0; i < str.length; i++)
    {
		if(str.charAt(i)>'~' || str.charAt(i)<' ')
		{
		    length2++;
		}
    }
	
    return length2;
}

function getNowDateInCheck()
{
	var crtDate = new Date();
	var tmpMonth = crtDate.getMonth() + 1;
	var tmpDay = crtDate.getDate();
	if  (tmpMonth < 10)
	{
		tmpMonth = '0' + tmpMonth;
	}
	if (tmpDay < 10)
	{
		tmpDay = '0' + tmpDay;
	}
	
	return crtDate.getFullYear() + '-' + tmpMonth + '-' + tmpDay;
}

function checkDateInCheck(dateStr)
{
    //���ڸ�ʽ������ʽ����ʽΪ"yyyy-MM-dd"
    var re = /^\d{4}-[0-1][0-9]-[0-3][0-9]$/;
    return re.test(dateStr)
}

MyCheck.prototype = ( 
{
	//�ڲ�value����
	innerObj : null,
	
	//�Ƿ�alert��Ϣ
	show: true,
    
    index: 'value',
    
    bingFun: null,
    
    head: 'head',
	
	//getvalue
	getValue:
	function()
	{
		if (this.innerObj.type.toLowerCase() == 'select')
		{
			return this.innerObj.options[this.innerObj.selectedIndex][this.index];
		}
		
		if (this.innerObj.type.toLowerCase() == 'radio' || this.innerObj.type.toLowerCase() == 'checkbox')
		{
			var obj = document.getElementsByName(this.innerObj.name);
			for (var i = 0; i < obj.length; i++)
			{
				if (obj[i].checked)
				{
					return obj[i][this.index];
				}
			}
			
			return "";
		}
		
		if (isNull(this.innerObj[this.index]))
		{
			return null;
		}
		else
		{
			return this.innerObj[this.index];
		}
	},
	
	//getHead
	getHead:
	function()
	{
		if (isNull(this.innerObj[this.head]))
		{
			return '';
		}
		else
		{
			return this.innerObj[this.head];
		}
	},
	
	innerCheck:
	function()
	{
		//���valueû��ֵ
		if (isNull(this.getValue()))
		{
			alert('û��value����');
			return false;
		}
		
		return true;
	},
	
	//alertĬ�ϻ����Զ������ʾ
	showMessage:
	function(defaultMessage, args)
	{
		if (this.show)
		if (isNull(args) || isNull(args[0]) || args[0] == true)
		{
			if (!isNone(this.innerObj.message))
			{
				alert(this.innerObj.message);
			}
			else
			{
				if (!isNone(defaultMessage))
				{
					alert(this.getHead() + ' ' + defaultMessage);
				}
			}
		}		
	},
	
	//�Ƿ�Ϊ��
	notNone : 
	function()
	{
		//ȥ�պ�
		if (trimInCheck(this.getValue()) == '')
		{
			var defaultMessage = '����Ϊ��';
			
			this.showMessage(defaultMessage, arguments);
			
			return false;
		}
		else
		{
			return true;
		}
	},
	
	//����У��
	isReg:
	function(reg, defaultMessage)
	{
		var value = trimInCheck(this.getValue());
		
		var arr = [true];
		
		if (!isNull(arguments[2]))
		{
			arr[0] = arguments[2];
		}
		
		if (!reg.test(value))
		{
			this.showMessage(defaultMessage, arr);
			
			return false;
		}
		else
		{
			return true;
		}
	},
	
	//����������(������ȷ)
	isNumber:
	function()
	{
		var reg = /^[0-9]*$/;
		
		var defaultMessage = 'ֻ��������';
		
		return this.isReg(reg, defaultMessage);
	},
	
	//����������(�������)
	isOnlyNumber:
	function()
	{
		var reg = /^[0-9]+$/;
		
		var defaultMessage = 'ֻ��������';
		
		return this.isReg(reg, defaultMessage);
	},
	
	//����������(�������,�����и���)
	isMathNumber:
	function()
	{
		var reg = /^[0-9]+$/;
    	var reg1 = /^-{1}[0-9]+$/;
		
		var defaultMessage = 'ֻ��������';
		
		if (this.isReg(reg, null, false) || this.isReg(reg, null, false))
		{
			return true;
		}
		else
		{
			this.showMessage(defaultMessage, arguments);
			return false;
		}
	},
	
	maxLength:
	function(parmters)
	{
		var length;
		if (typeof parmters == 'number')
		{
			length = parmters;
		}
		else
		{
			alert('����������ֵ');
			return false;
		}
		
		if (getLengthInCheck(this.getValue()) > length)
		{
			var defaultMessage = '���Ȳ��ܳ���' + length;
			
			this.showMessage(defaultMessage);
			
			return false;
		}
		else
		{
			return true;
		}
	},
	
	isChecked:
	function()
	{
		var flag = false;
		if (this.innerObj.type.toLowerCase() == 'radio' || this.innerObj.type.toLowerCase() == 'checkbox')
		{
			var obj = document.getElementsByName(this.innerObj.name);
			for (var i = 0; i < obj.length; i++)
			{
				if (obj[i].checked)
				{
					flag = true;
				}
			}
		}
		
		if (!flag)
		{
			var defaultMessage = 'ѡ����Ϊ��';
			
			this.showMessage(defaultMessage, arguments);
			
			return false;
		}
		else
		{
			return true;
		}
	},
	
	isCommonChar:
	function()
	{
		var regText = /^[^`~@#\$%\^&\*=\!\+\\\/\|<>\?;\:\.'"\{\}\[\]��, ]*$/;
		if (!isNone(arguments[0]))
		{
			if (typeof arguments[0] == 'string')
			{
				regText = arguments[0];
			}
		}
		
		var defaultMessage = '���ܰ��������ַ�: ,./<>?\';:~!`#$%^&*()-=+\\|{}[]"';
		
		return this.isReg(regText, defaultMessage);
	},
	
	// ��֤�ַ����Ƿ��������ĸ������
	isNumberOrLetter:
	function()
	{
		var regText = /^[A-Za-z0-9]*$/;
		
		var defaultMessage = 'ֻ�������ֻ���ĸ';
		
		return this.isReg(regText, defaultMessage);
	},
	
	// ��֤�ַ����Ǹ���
	isFloat:
	function()
	{
		var regText = /^[0-9]*(.)?[0-9]+$/;
		
		var defaultMessage = 'ֻ���Ǹ�����';
		
		return this.isReg(regText, defaultMessage);
	},
	
	// ��С����
	minLength:
	function(min)
	{
		var length;
		if (typeof parmters == 'number')
		{
			length = parmters;
		}
		else
		{
			alert('����������ֵ');
			return false;
		}
		
		if (getLengthInCheck(this.getValue()) < length)
		{
			var defaultMessage = '���Ȳ���С��' + length;
			
			this.showMessage(defaultMessage);
			
			return false;
		}
		else
		{
			return true;
		}
	},
	
	// ����С�ڵ�ǰʱ�� 
	cnow:
	function(cc, dir, format)
	{
		var cint = '==';
		if (!isNull(cc))
		{
			cint = cc;
		}
		
		
		//��ʱ��֧�ָ�ʽ���Ƚ�
		var ff = 'yyyy-MM-dd';
		var current;
		
		//��ȡʱ��
		var src = trimInCheck(this.getValue());
		
		if (isNull(dir))
		{
			//��ǰʱ��
			current = getNowDateInCheck();
		}
		else
		{
			//ָ���Ƚ�ʱ��
			current = dir;
		}
		
		if (!checkDateInCheck(src))
		{
			alert(src + '����' + ff + '��ʽ��');
			return false;
		}
		
		if (!checkDateInCheck(current))
		{
			alert(current + '����' + ff + '��ʽ��');
			return false;
		}
		
		var cresult;
		var selfMessage;
		
		// -2С�ڵ���
		if (cint == '<=')
		{
			cresult = (src <= current);
			selfMessage = "���ܴ�������: " + current;
		}
		
		if (cint == '==')
		{
			cresult = (src == current);
			selfMessage = "�����������: " + current;
		}
		
		if (cint == '<')
		{
			cresult = (src < current);
			selfMessage = "���ܴ��ڵ�������: " + current;
		}
		
		if (cint == '>=')
		{
			cresult = (src >= current);
			selfMessage = "����С������: " + current;
		}
		
		if (cint == '>')
		{
			cresult = (src > current);
			selfMessage = "����С�ڵ�������: " + current;
		}
		
		 
		if (!cresult)
		{
			var defaultMessage = selfMessage;
			
			this.showMessage(defaultMessage);
			
			return false;
		}
		else
		{
			return true;
		}
	},
	
	noEquals:
	function(str)
	{
		if (this.getValue() == str)
		{
			var defaultMessage = 'ֵ���ܵ���:' + str;
			
			this.showMessage(defaultMessage);
			
			return false;
		}
		else
		{
			return true;
		}
	},
	
	equals:
	function(str)
	{
		if (this.getValue() != str)
		{
			var defaultMessage = 'ֵ�������:' + str;
			
			this.showMessage(defaultMessage);
			
			return false;
		}
		else
		{
			return true;
		}
	},
	
	//��Χ�Ƚ�>= <= �շ���true
	range:
	function(begin, end)
	{
		//�ر���ʾ
		if (this.notNone(false))
		{
			var defaultMessage = '';
			
			//ǰ��Ƚϵ�
			if (!isNull(begin) && !isNull(end))
			{
				defaultMessage = 'ֵ������ڵ���:' + begin + '��С�ڵ���' + end;
				
				var num = parseInt(this.getValue(), 10);
				if (num >= begin && num <= end)
				{
					return true;
				}
				else
				{
					this.showMessage(defaultMessage);
					return false;
				}
			}
			
			//ǰ�Ƚϵ�
			if (!isNull(begin) && isNull(end))
			{
				defaultMessage = 'ֵ������ڵ���:' + begin;
				
				var num = parseInt(this.getValue(), 10);
				if (num >= begin)
				{
					return true;
				}
				else
				{
					this.showMessage(defaultMessage);
					return false;
				}
			}
			
			//��Ƚϵ�
			if (isNull(begin) && !isNull(end))
			{
				defaultMessage = 'ֵ����С�ڵ���:' + end;
				
				var num = parseInt(this.getValue(), 10);
				if (num <= end)
				{
					return true;
				}
				else
				{
					this.showMessage(defaultMessage);
					return false;
				}
			}
			
			//Ĭ��û��
			defaultMessage = '�Ƚϲ��Ϸ�����ȷ��';
			this.showMessage(defaultMessage);
			return false;
			
		}
		else
		{
			return true;
		}
	}
});

var myCheck = new MyCheck();
