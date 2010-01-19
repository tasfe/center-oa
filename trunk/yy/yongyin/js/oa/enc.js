var digitArray = new Array('0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F');

function toHex( n ) {

        var result = ''
        var start = true;

        for ( var i=32; i>0; ) {
                i -= 4;
                var digit = ( n >> i ) & 0xf;

                if (!start || digit != 0) {
                        start = false;
                        result += digitArray[digit];
                }
        }

        return ( result == '' ? '0' : result );
}

function checkLockExist()
{
	try
    {
		var tax = new ActiveXObject("Syunew6A.s_simnew6");
		
	    DevicePath = tax.FindPort(0);
	    
	    if( tax.LastError!= 0 )
	    {
	        return '';
	    }
	    else
	    {
	    	var ret = toHex(tax.GetID_1(DevicePath))+toHex(tax.GetID_2(DevicePath));
	    	
	    	tax = null;
	    	
	    	return ret;
	    }
    }
    catch(e)
    {
    	return '';
    }
}


function checkEnc(encStr) 
{
     try
     {
     	$('jiamiRand').value = '';
        $('key').value = '';
        
        var DevicePath,mylen,ret;
        var s_simnew31 = new ActiveXObject("Syunew6A.s_simnew6");
        DevicePath = s_simnew31.FindPort(0);
        if( s_simnew31.LastError!= 0 )
        {
            window.alert ( "δ���ּ�����������������");
            
            return false;
        }
        else
        {
            //s_simnew31.SetCal_2("12345678901234567890123456xxxxx", DevicePath);
            $('jiamiRand').value = s_simnew31.EncString(encStr, DevicePath);
            
            //��ȡ����ID
            $('key').value = toHex(s_simnew31.GetID_1(DevicePath))+toHex(s_simnew31.GetID_2(DevicePath));
        }
      }
     catch(err)  
      {  
          txt="����,ԭ����" + err.description + "\n\n"  
          txt+="�����Ƿ�װ��������"
          alert(txt)  
          
          $('jiamiRand').value = '';
          $('key').value = '';
          
          return false;
      }  
      
      return true;
}