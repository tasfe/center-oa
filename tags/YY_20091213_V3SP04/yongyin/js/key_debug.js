//˫���Ҽ�����(����ESC) ��¼��������������¼������������500ms 
/**
 * How to use:
 * in body tag, add this content��onmouseup="listenerMouse(event, goProduct)"��
 * if you only want to go back, you just do that do nothing
 */
var interval = 500;
var mouseClick1 = 0;
var mouseClick2 = 0;

function listenerMouse(evt, url)
{
	//�Ҽ�
	if (evt.button == 2)
	{
		if (mouseClick2 == 0)
		{
			mouseClick2 = new Date().getTime();
			return;
		}
		
		if (mouseClick1 == 0)
		{
			mouseClick1 = new Date().getTime();
			
			
			cgoBack(mouseClick1, mouseClick2, url);
			
			return;
		}
		
		//����12���Ѿ����ˣ���ô��ʼ1��2��
		mouseClick2 = mouseClick1;
		
		mouseClick1 = new Date().getTime();
		
		cgoBack(mouseClick1, mouseClick2, url);
		
		return;
	}
	else
	{
		mouseClick1 = 0;
		mouseClick2 = 0;
	}
	
}

function cgoBack(l1, l2, url)
{
	if (l1 - l2 < interval)
	{
		if (url)
		{
			if (typeof url == 'function')
			{
				url.call(this);
			}
			
			if (typeof url == 'string')
			{
				document.location.href = url;
			}
			else
			{
				// TODO other
			}
		}
		else
		{
			window.history.go(-1);
		}
	}
}
/**
 * ���event����
 */
function getEvent()
{
	// IE
	if (window.event)
   	{
   		return window.event;
   	}
    	
    var func = this.getEvent;
    
    while(func != null)
    {
    	//���ص��ú����ġ�arguments.callee��(callee��arguments��һ������ֵ)
    	//callee:��������ִ�е� Function ����,�������func
    	//caller:����һ���Ժ���������
    	
        var eve = func.arguments.callee.arguments[0];
        
        if (eve != null && eve.constructor && eve.constructor.toString().indexOf('Event') != -1)
        {
        	return eve;
        }
        
        func = func.caller;
    }
    
    return null;
}

//���Ҽ�
document.onmouseup = function ()
{
	//��ֹbody.onmouseup�Ѿ���
	if (document.body.onmouseup == null)
	{
		var event = getEvent();
    	listenerMouse(event);
    }
}

//�����Ҽ�
document.oncontextmenu = function()
{
	if (document.body.oncontextmenu == null)
	{
		 return false;
	}	
}

//ESC ����
document.onkeydown = function ()
{
	//��ֹbody.onmouseup�Ѿ���
	if (document.body.onkeydown == null)
	{
		var event = getEvent();
		if (event.keyCode == 27)
		{
    		window.history.go(-1);
    		
    		return false;
		}
    }
}

