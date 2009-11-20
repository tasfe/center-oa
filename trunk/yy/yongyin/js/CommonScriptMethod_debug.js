 //�������

    var cco = 0;
 	var telNumbers = 11;
    var sortIndex;
    var isAsc = 0;
    function tableSort(cell, num){
    	sortIndex = cell.cellIndex;
    	var rowArray=[];

    	var tr = cell.parentNode;
    	var tbody = tr.parentNode;
    	var tab = tbody.parentNode;
    	var rows = tbody.getElementsByTagName("TR")
    	for(i=1;i<rows.length;i++){
    		var row = rows[i];
    		rowArray[i] = row;
    	}

    	if(isAsc==1)
    	{
    		if (num)
    		{
    			rowArray.sort(sortAscNum);
    		}
    		else
    		{
    			rowArray.sort(sortAsc);
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
    			rowArray.sort(sortDesc);
    		}
    	}

    	try{

    		for(i=1;i<rowArray.length;i++){
    			tr.insertAdjacentElement("afterEnd",rowArray[i]);
    		}

    	} catch(e){
    	}
    	isAsc = (isAsc+1)%2;
    }

    function sortAsc(x,y){

 		if (x.getElementsByTagName("TD")[sortIndex].innerText > y.getElementsByTagName("TD")[sortIndex].innerText)
  			return -1;
 		else if (x.getElementsByTagName("TD")[sortIndex].innerText == y.getElementsByTagName("TD")[sortIndex].innerText)
   			return 0;
 		else
   			return 1;
    }

    function sortDesc(x,y){

 		if (x.getElementsByTagName("TD")[sortIndex].innerText < y.getElementsByTagName("TD")[sortIndex].innerText)
  			return -1;
 		else if (x.getElementsByTagName("TD")[sortIndex].innerText == y.getElementsByTagName("TD")[sortIndex].innerText)
   			return 0;
 		else
   			return 1;
    }

    function sortAscNum(x,y)
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

    function sortDescNum(x,y)
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

    function tableSort1(cell){}


//�޸�����ʱ��������м���
function CheckPwd(obj,num1,num2)
{

	var str = trim(obj.value);
  	var strLength = str.length;

	if(strLength>num2)  //�ȼ�����������ܳ���num2λ
	{
		alert("���벻�ܳ���"+num2+"���ַ���(�������ո�)");//������ʾ�Ի���
		obj.value="";//�����ǰֵ
		obj.focus();//��������������������
		return false;
	}
	else if(strLength<num1)  //�ȼ����������������num1λ
	{
		alert("���벻������"+num1+"���ַ���(�������ո�)");//������ʾ�Ի���
		obj.value="";//�����ǰֵ
		obj.focus();//��������������������
		return false;
	}
	else if(/^[\da-z]+$/i.test(str)) { //���ֻ�����ĸ

		if(/^[\d]+$/i.test(str)) { //ȫ��������
			alert("�������Ϊ�ַ������ֵ���ϣ�");
			return false;
		}
		else if(/^[\a-z]+$/i.test(str)) { //ȫ������ĸ
			alert("�������Ϊ�ַ������ֵ���ϣ�");
			return false;
		}
		else {
			return true;
		}
	}
    else{
	    alert("���뺬�зǷ��ַ���");
	    return false;
	}
}

/**
 * ���һ�������Ƿ����ڹ涨��Χ֮��
 * @param num ����������
   maxRange ���ֵ
   minRange ��Сֵ
   * @return ��Ч���ַ���true
          ��Ч���ַ���false
          */
function checkNumber(num,minVar,maxVar)
{
    num = trim(num);
    if(num == '')
    {
        return true;
    }
    if(isNaN(num))
    {
        return false;
    }
    if(maxVar == 'p' && minVar == 'p')
    {
        return true;
    }

    if (minVar != 'p')
    {
        if (num < minVar)
        {
            return false;
        }
    }
    if (maxVar !='p')
    {
        if (num > maxVar)
        {
            return false;
        }
    }
    return true;
}


/**
 * ��鹤�������Ƿ���Ч
 * @param workerNo �����Ĺ���
 * @return ��Ч���ŷ���true
          ��Ч���ŷ���false
          */
function checkWorkerNo(workerNo)
{
    if(workerNo == '')
    {
        return true;
    }

    workerNo = trim(workerNo);
    if(workerNo.charAt(0) == 0)
    {
        return false;
    }

    if(workerNo == '')
    {
        return true;
    }

    if(isNaN(workerNo))
    {
        return false;
    }
    if(workerNo>99999 || workerNo <1)
    {
        return false;
    }
    return true;
}

/**
 * �����������Ч�Լ�麯��
 * @param name ����������
 * @return ��Ч��������ture
 *         ��Ч��������false
 */
function checkNameInput(name)
{
    if(name == '')
    {
        return true;
    }
    name = trim(name);

    for(var i = 0;i < name.length;i++)
    {
        if(name.charAt(i) == '\'' || name.charAt(i) == '&'
           || name.charAt(i) == '|' || name.charAt(i) == '?'
           || name.charAt(i) == '*'  || name.charAt(i) == ' '
           || name.charAt(i) == '('  || name.charAt(i) == ')'
           || !isNaN(name.charAt(i))    )
        //���в���ȷ����Щ�ַ�����sql�������ɲ���Ӱ��
    {
            return false;
        }
    }
    return true;
}

/**
 * У���ַ������Ƿ��зǷ��ַ�
 * @param name �������ַ���
 * @return �޷Ƿ��ַ�����ture
 *               �зǷ��ַ�����false
 */
function isValidStr(name)
{
    if(name == '')
    {
        return true;
    }
    name = trim(name);

    for(var i = 0;i < name.length;i++)
    {
        if(name.charAt(i) == '\'' || name.charAt(i) == '&'
           || name.charAt(i) == '\\' || name.charAt(i) == '?'
           || name.charAt(i) == '*'  || name.charAt(i) == '/'
           || name.charAt(i) == '"'  || name.charAt(i) == '~'  )
        //���в���ȷ����Щ�ַ�����sql�������ɲ���Ӱ��
    {
            return false;
        }
    }
    return true;
}

//�������ĵ绰������Ч��
function checkCallNo(num)
{
    num = trim(num);
    if(num == '')
    {
        return true;
    }
    for(var i = 0;i < num.length; i++)
    {

        if(isNaN(num.charAt(i)) && num.charAt(i) != '-')
        {
            return false ;
        }
    }
    return true;
}

/**
 * �ύ������
 * @param form ��Ҫ�ύ�ı�
 */
function submitForm(f)
{
    var errorMeg = ""

    /** VerifyInput()������ʹ��SubmitForm����������ҳ��Ҫ�ж���*/
    errorMeg = VerifyInput();
    if(errorMeg == "")
    {
        f.submit();
    }
    else
    {
        alert(errorMeg);
    }
    return ;
}

/**
 * �������������ֵ�Ƿ���ͬ,�����ͬ�򵯳�������Ϣ,�����ö�Ӧ�����뽹��Ϊ��һ����������Ķ���
 * @param num ��������������
 * @return ��ͬ����true
          ��ͬ����false
          */
function checkEqualsWithAlert(inputObj1,inputObj2)
{
    if(inputObj1.value != inputObj2.value)
    {
        alert(inputObj1.desc+"�������"+inputObj2.desc+"�����벻ͬ");
        inputObj1.focus();
        return false;
    }

    return true;
}

/**
 * ���һ��ֵ�Ƿ��ǹ���,��������Ӧ�Ĵ�����Ϣ�����ö�Ӧ�����뽹��
 * @param num ��������������
 * @return ��Ч���ŷ���true
          ��Ч���ŷ���false
 */
function CheckWorkNoWithAlert(workNoObj)
{
    workNo = trim(workNoObj.value);

    if(workNo == "")
    {
        alert("�������벻��Ϊ��");
        workNoObj.focus();
        return false;
    }

    if(workNo.charAt(0) == '0')
    {
        alert("���Ų�����0��ͷ");
        workNoObj.focus();
        return false;
    }

    if(isNaN(workNo))
    {
        alert("���ű���Ϊ1��9999������");
        workNoObj.focus();
        return false;
    }

    if(workNo>9999 || workNo <1)
    {
        alert("���ű���Ϊ1��9999������");
        workNoObj.focus();
        return false;
    }

    return true;
}

function compareTimeT(beginTime,endTime)
{
    if(beginTime == "" || endTime == "")
    {
        return true ;
    }
    var bDate,bHour,bMin,eDate,eHour,eMin ;
    var array0 = beginTime.split(" ")[0] ;
    var array1 = beginTime.split(" ")[1] ;
    var array2 = endTime.split(" ")[0] ;
    var array3 = endTime.split(" ")[1] ;


    bDate = array0 ;
    bHour = array1.split(":")[0] ;
    bMin = array1.split(":")[1] ;

    eDate = array2 ;
    eHour = array3.split(":")[0] ;
    eMin = array3.split(":")[1] ;

    var bDateArray = bDate.split("-");
    var eDateArray = eDate.split("-");
    if(eDate == '' || bDate == '')
    {
        return true ;
    }

    if(bHour == '')
    {
        bHour = 0;
    }

    if(bMin == '')
    {
        bMin = 0;
    }

    if(eHour == '')
    {
        eHour = 0;
    }

    if(eMin == '')
    {
        eMin = 0;
    }


    //bDateArray[0] = parseInt(bDateArray[0]) ;
    //bDateArray[1] = parseInt(bDateArray[1]) ;
    //bDateArray[2] = parseInt(bDateArray[2]) ;
    //eDateArray[0] = parseInt(eDateArray[0]) ;
    //eDateArray[1] = parseInt(eDateArray[1]) ;
    //eDateArray[2] = parseInt(eDateArray[2]) ;

//alert(bDateArray[0]+"***"+bDateArray[1]+"***"+bDateArray[2]+"***"+eDateArray[0]+"***"+eDateArray[1]+"***"+eDateArray[2]+"***");
    if(bDateArray[0] < eDateArray[0])
    {
        return true ;
    }
    if(bDateArray[0] > eDateArray[0])
    {
        return false ;
    }

    if(bDateArray[1] < eDateArray[1])
    {
        return true ;
    }
    if(bDateArray[1] > eDateArray[1])
    {
        return false ;

    }
    if(bDateArray[2] < eDateArray[2])
    {
        return true ;
    }
    if(bDateArray[2] > eDateArray[2])
    {
        return false ;
    }

    if(bHour < eHour)
    {
        return true ;
    }
    if(bHour > eHour)
    {
        return false ;
    }

    if(bMin < eMin)
    {
        return true ;
    }
    if(bMin > eMin)
    {
        return false ;
    }
    return true;
}

/**
 * �ȽϿ�ʼʱ���Ƿ�С�ڽ���ʱ��
 * @param bDate:��ʼ����
 *        bHour:��ʼСʱ
 *        bMin:��ʼ����
 *        eDate:��������
 *        eHour:����Сʱ
 *        eMin:����
 * @return ture:��ʼʱ��С�ڵ��ڽ���ʱ��
 *         false:��ʼʱ����ڽ���ʱ��
 */

function CompareTime(bDate,bHour,bMin,eDate,eHour,eMin)
{
    var bDateArray = bDate.split("-");
    var eDateArray = eDate.split("-");
    if(eDate == '' || bDate == '')
    {
        return true ;
    }

    if(bHour == '')
    {
        bHour = 0;
    }

    if(bMin == '')
    {
        bMin = 0;
    }

    if(eHour == '')
    {
        eHour = 0;
    }

    if(eMin == '')
    {
        eMin = 0;
    }


    //bDateArray[0] = parseInt(bDateArray[0]) ;
    //bDateArray[1] = parseInt(bDateArray[1]) ;
    //bDateArray[2] = parseInt(bDateArray[2]) ;
    //eDateArray[0] = parseInt(eDateArray[0]) ;
    //eDateArray[1] = parseInt(eDateArray[1]) ;
    //eDateArray[2] = parseInt(eDateArray[2]) ;

    if(bDateArray[0] < eDateArray[0])
    {
        return true ;
    }
    if(bDateArray[0] > eDateArray[0])
    {
        ;
        return false ;
    }

    if(bDateArray[1] < eDateArray[1])
    {
        return true ;
    }
    if(bDateArray[1] > eDateArray[1])
    {
        return false ;

    }
    if(bDateArray[2] < eDateArray[2])
    {
        return true ;
    }
    if(bDateArray[2] > eDateArray[2])
    {
        return false ;
    }

    if(bHour < eHour)
    {
        return true ;
    }
    if(bHour > eHour)
    {
        return false ;
    }

    if(bMin < eMin)
    {
        return true ;
    }
    if(bMin > eMin)
    {
        return false ;
    }
    return true;
}

/**
 * ��ʱ�����ݷ�װΪһ���ַ���
 * @param��date ����
 *         hour  Сʱ
 *         min  ����
 * @return:  �������ַ���
 */
function timeToString(date,hour,min)
{
    if(hour == '')
    {
        hour = "00";
        min = "00";
    }
    if(min == '')
    {
        min = "00";
    }
    var str = "";
    str += date + " ";
    str += hour + ":";
    str += min + ":";
    str += "00";
    return str;

}

/**
 * ȥ��������β�Ŀո�
 * @param num ���������
 * @return ��������
 */

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

/**
 * �򿪶���ҳ�洰��
 * @author ������
 */
function openDZWindow(wurl,wname)
{
    //var wx=790;
    //var wy=540;
    var x = window.screen.availWidth;
    var y = window.screen.availHeight;
    //var wtop =(x-wx)/2-4;
    //var wleft =(y-wy)/2-14;
    //alert(wtop);
    //alert(wleft);
    //window.open(wurl,wname,'left='+wleft+',top='+wtop+',toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width='+wx+',height='+wy+';,resizable=true');
    window.open(wurl,wname,'left='+0+',top='+0+',toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width='+x+',height='+(y-20)+';,resizable=true');
}

function openMonitorWindow(wurl,wname)
{
    var wx=620;
    var wy=180;
    var x = window.screen.availWidth;
    var y = window.screen.availHeight;
    var wtop =(x-wx)/2;
    var wleft =(y-wy)/2-54;
    //alert(wtop);
    //alert(wleft);
    window.open(wurl,wname,'left='+wleft+',top='+wtop+',toolbar=no,location=no,status=no,menubar=no,scrollbars=auto,resizable=no,width='+wx+',height='+wy+';,resizable=true');
}
function openPreview(wurl,wname)
{
    var wx=580;
    var wy=270;
    var x = window.screen.availWidth;
    var y = window.screen.availHeight;
    var wtop =(x-wx)/2-4;
    var wleft =(y-wy)/2-14;
    //alert(wtop);
    //alert(wleft);
    window.open(wurl,wname,'left='+wleft+',top='+wtop+',toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,width='+wx+',height='+wy+';,resizable=false');
}

function isPhoneNum(str)
{
    //���绰�����Ƿ�����0755(3λ����4λ)-12345678(7λ����8λ)��ʽ
    //var re1 = /^\s*(\d*[-��]?\d*)?\d*\s*$/;
    //���绰�����Ƿ��ֻ���13*
    //var re2 = /^\s*0?13\d{9}\s*$/
    //���µ�159��ͷ���ֻ�����
    //var re3 = /^\s*0?159\d{8}\s*$/

    // ���û�����ŵĹ̶��绰
    var re1 = /^\d{7,8}$/;
    // ��������ŵĹ̶��绰
    var re2= /^0\d{2,3}-?\d{7,8}$/;
    // ����ֻ������Ƿ��ֻ���13*
    var re3=/^0?13\d{9}$/;
    // ������µ�159��ͷ���ֻ�����
    var re4=/^0?159\d{8}$/

    return re1.test(str)||re2.test(str)||re3.test(str)||re4.test(str);
}

function isEmail(str)
{
    var re1=/^\s*\w+@\w+(\.\w+)+\s*$/;
    return re1.test(str);
}

function isEmail2(str)
{
    var re1=/^\w[A-Za-z0-9_\.]*@\w+(\.\w+)+\s*$/;
    return re1.test(str);
}

//add by Yuhongliang begin
function checkTime(timeStr)
{
    //ʱ���ʽ������ʽ����ʽΪ"yyyy-MM-dd hh:mm:ss"
    var re = /^\d{4}-[0-1][0-9]-[0-3][0-9]\s[0-2][0-9]:[0-5][0-9]:[0-5][0-9]$/;
    return re.test(timeStr)
}

function checkDate(dateStr)
{
    //���ڸ�ʽ������ʽ����ʽΪ"yyyy-MM-dd"
    var re = /^\d{4}-[0-1][0-9]-[0-3][0-9]$/;
    return re.test(dateStr)
}

/**
 * ��֤�ַ����Ƿ��������ĸ������ 2005-09-17
 */
function isNumOrLetter(str)
{
    var reg = /^[A-Za-z0-9]*$/;
    return reg.test(str);
}

/**
 * ��֤�ٷֱ����ָ�ʽ�Ƿ���ȷ�����ܴ��ڵ���100%��
 * С������5λ���֡�
 */
function isPercent(str)
{
    var reg = /^[1-9]\d?([.]\d{1,5})?[%]$/;
    return reg.test(str);
}
//add end

/////////////////////////////////e///////////////////////
// ȡ�õ�ǰ����,��ʽyyyy-mm-dd
////////////////////////////////////////////////////////
function GetCurrentDate()
{
    var Year=0;
        var Month=0;
        var Day=0;
        var CurrentDate = new Date();

        return ChangeDateToString(CurrentDate);
}

////////////////////////////////////////////////////////
// ȡ�õ�ǰ����,������ǰ�������,��ʽyyyy-mm-dd
////////////////////////////////////////////////////////
function GetDate(day)
{
    var ms=0;
        var CurrentDate = new Date();
    ms= CurrentDate.getTime();
    ms= ms + day*24*60*60*1000;
        var NewDate = new Date(ms);
    var sNewDate;
        sNewDate= ChangeDateToString(NewDate);
        return sNewDate;
}

////////////////////////////////////////////////////////
// ����������ת�����ַ����͸�ʽyyyy-mm-dd
////////////////////////////////////////////////////////
function ChangeDateToString(DateIn)
{
    var Year=0;
        var Month=0;
        var Day=0;
        var CurrentDate="";

        //��ʼ��ʱ��
        Year      = DateIn.getYear();
        Month     = DateIn.getMonth()+1;
        Day       = DateIn.getDate();

        CurrentDate = Year + "-";
        if (Month >= 10 )
        {
            CurrentDate = CurrentDate + Month + "-";
        }
        else
        {
            CurrentDate = CurrentDate + "0" + Month + "-";
        }
        if (Day >= 10 )
        {
            CurrentDate = CurrentDate + Day ;
        }
        else
        {
            CurrentDate = CurrentDate + "0" + Day ;
        }

        return CurrentDate;
}

/**
 * ����mFormObj������������ΪboxName��chekcboxԪ��ȫ����ѡ�л�ȫ������ѡ��
 * @param mFormObj ������
 * @param boxName checkboxԪ�ص�����,�ַ�������
 * @param checked true(ѡ��)/false(��ѡ��)
 */
function setBoxChecked(mFormObj ,boxName ,checked)
{
    if(mFormObj == null) return ;
    var count = mFormObj.elements.length ;
    for(var i=0 ;i<count ;i++)
    {
        var e = mFormObj.elements[i] ;
        if (e.type=="checkbox" && e.name == boxName)
        {
            e.checked = checked ;
        }
    }
}

/**
 *��ʾ�����ز�����Ϣ
 * @param obj ��<td> �����õĶ���
 */
function dispInfo(obj)
{
  if(obj.style.display=='none')
    obj.style.display='';
  else
    obj.style.display='none';
}

/**
 *���ĳ����ť�ȸ����ذ�ť��ֵ���ύ��
 * @param actionID
 */
function clickBtn(actionID)
{
    document.form1.actionType.value=actionID;
    document.form1.submit();
}

/*
 *�������б��֮�䣬��ѡ��Ĳ��ݽ������
 * @param1 fromSelect ԭʼSelsect��������
 * @param2 fromSelect Ŀ��Selsect��������
 */
function removeItem(fromSelect, toSelect) {
	if (fromSelect.selectedIndex != -1) {
		for (i=0; i<fromSelect.options.length; i++) {
			if (fromSelect.options(i).selected) {
				var oOption = document.createElement("OPTION");
				oOption.text = fromSelect.options[i].text;
				oOption.value= fromSelect.options[i].value;
				toSelect.add(oOption);
			}
		}
		for (i=fromSelect.options.length-1; i>=0; i--) {
			if (fromSelect.options(i).selected) {
				fromSelect.remove(i);
			}
		}
		fromSelect.blur;
		toSelect.blur;
	}
}


/*
 *�������б��֮�䣬��������Ŀ�������
 * @param1 fromSelect ԭʼSelsect��������
 * @param2 fromSelect Ŀ��Selsect��������
 */
function removeAllItem(fromSelect, toSelect) {
		for (i=0; i<fromSelect.options.length; i++) {

				var oOption = document.createElement("OPTION");
				oOption.text = fromSelect.options[i].text;
				oOption.value= fromSelect.options[i].value;
				toSelect.add(oOption);
		}
		for (i=fromSelect.options.length-1; i>=0; i--) {

				fromSelect.remove(i);
		}
		fromSelect.blur;
		toSelect.blur;
}

/*
 *ɾ���б����������Ŀ
 * @param1 fromSelect ԭʼSelsect��������
 */
function removeItemAll(fromSelect) {
	for (i=fromSelect.options.length-1; i>=0; i--) {
		fromSelect.remove(i);
	}
	fromSelect.blur;
}


/*
 *�ж��ַ����Ƿ����
 * @param1 allStr ��"$"Ϊ�ָ����ŵ��ַ���
 * @param2 comStr ��Ҫ�ж��Ƿ���ڵ��ַ���
 */
function isExist(allStr, comStr)
{
    ret = false;
	var tmpStr = allStr.split("$");
	for (j=0; j<tmpStr.length; j++)
	{
          if (tmpStr[j]==comStr)
		  {
		     ret = true;
			 break;
		  }
	}
    return ret;
}

/*
 *�ж�ĳ�������Ƿ�ѡ��
 * @param1 obj ��������
 */
function isSelected(obj)
{
	if (!obj)
	{
 		alert("�Բ���û������ļ�¼!");
		return false;
	}

	var selectFlag = 0;
	if(obj.length>1)
	{
		for (var i=0;i<obj.length;i++){
			if(obj[i].checked){
				selectFlag = 1;
				break;
			}
		}
	}
	else
	{
		if(obj.checked){
			selectFlag = 1;
		}
	}

	if(selectFlag == 0)
	{
		alert("�Բ�������ѡ������ļ�¼!");
		return false;
	}

	return true;
}

/*
 *�ж�ĳ�������Ƿ�ѡ��,������ָ������ʾ��Ϣ
 * @param1 obj     ��������
 * @param2 message ������ʾ��Ϣ
 */
function isChecked(obj,message)
{
	if (!obj)
	{
 		alert(message);
		return false;
	}

	var selectFlag = 0;
	if(obj.length>1)
	{
		for (var i=0;i<obj.length;i++){
			if(obj[i].checked){
				selectFlag = 1;
				break;
			}
		}
	}
	else
	{
		if(obj.checked){
			selectFlag = 1;
		}
	}

	if(selectFlag == 0)
	{
		alert(message);
		return false;
	}

	return true;
}

/*
 *����ָ��ҳ���������
 * @param2 urlStr    ҳ���ַ����
 */
function goParent(urlStr)
{
	parent.document.window.location.href = urlStr;
}

/*
 *��ʾ�����ؿ�����Ĺ��ܲ˵�
 *
 */
function hiddenFrm()
{
    if(this.top.document.all.tags("frameset")[1]  &&  this.parent.document.all.tags("frameset")[0] && document.all("resizeBtn")){
		if (document.all("resizeBtn").value == "<<"){
			this.top.document.all.tags("frameset")[1].cols="0,*";
			this.parent.document.all.tags("frameset")[0].cols="35%,65%";
			this.resizeBtn.value = ">>";
		}
		else{
			this.top.document.all.tags("frameset")[1].cols="171,*";
			this.parent.document.all.tags("frameset")[0].cols="25%,75%";
			this.resizeBtn.value = "<<";
		}
	}
}


/*
 *���õ�ǰ���裬ʹ������������ȷ��ʾ����
 *
 */
function setCurrstep(currStep)
{
  var obj = top.document.all.tags("frame")[3];
  if(obj){
    obj.src ="/sysManage/guide.jsp?currStep=" + currStep;
  }
  else{
    //alert("no frame[3]");
  }
}
/************�滻�ַ���*******************
 *���������
 *         fullS  ԭʼ�ַ���
 *         oldS   Ҫ�滻�����ַ���
 *         newS   �滻���µ����ַ���
 *���������
 *         fullS  �滻��Ľ���ַ���
 ****************************************/
function replaceString(fullS,oldS,newS)
{
  // Replaces oldS with newS in the string fullS
  for (var i=0; i<fullS.length; i++)
  {
    if (fullS.substring(i,i+oldS.length) == oldS)
    {
      fullS = fullS.substring(0,i)+newS+fullS.substring(i+oldS.length,fullS.length)
    }
  }
  return fullS;
}

/**
 * ���һ������(ҵ���������)�б��ַ������Ƿ��зǷ�����
 * @param obj ĳ��ѯ�ؼ�����
 *
 */
function checkWorkNoList(obj)
{
	var staffList = obj.value.split("$");
	for (var i=0;i<staffList.length;i++){
		if(staffList[i].length>10){
			alert("�Բ����������ĳ����Ż����ƹ�����");
			obj.focus();
			return false;
		}
	}
	return true;
}

/**
 * ��ʾ��ǰ�����б���б�ѡ�е���Ŀ����
 * @param obj ĳ�б��ؼ�����
 *
 */
function getTitle(obj){

	var i = obj.selectedIndex;
	var title ="";
	if(i == -1){
		return;
	}

	if (obj.options(i).selected) {
		title = obj.options[i].text;
	}

	curTitle.innerHTML = "<font color=red>" + title + "</font>";
}

/**
 * �Ŷμ��
 * @param workArea
 *
 */
function checkWorknoArea(worknoAreaStr)
{
  var re=/^[\w]+-[\w]/i;
  if(re.test(worknoAreaStr))
  {
   return true;
  }
  else
  {
   alert("��Ч�ĺŶ�!\r\n��ȷ�ĸ�ʽ�磺800-900");
   return false;
  }
}

/*
 *�ı䱳��ɫ
 */
var olda=null;
var oldColor=null;
function changeBK(){
  var obj=event.srcElement;
  if(!obj){
     return;
  }
  var a=obj;
  while(a.tagName!="A"){
     a=a.parentNode;
  }
  //curRowIndex = tr.rowIndex;

  if(olda&&olda!=a){
    olda.className="";
    olda.className=oldColor;
  }
  if(olda!=a){
     oldColor=a.className;
  }
  olda=a;
  a.className="table_bg";
}

/**
 *  �Ƿ�Ϊ>0������
 */
function isNaN2(num)
{
  if(isNaN(num) || num <= 0)
  {
  	return true;
  }
  return false;
}

/**
 * ��һ���������EMAIL���ַ���������Ч���
 */
function checkEmailList(checkStr,splitStr){
	var checkList = checkStr.split(splitStr);
	var returnFlag = true;
	for (i=0;i<checkList.length;i++){
		if(!isEmail(checkList[i])) {
			returnFlag =false;
			break;
		}
  }
  return returnFlag;
}

/**
 * ��һ����������绰���ַ���������Ч���
 */
function checkPhoneList(checkStr,splitStr){
	var checkList = checkStr.split(splitStr);
	var returnFlag = true;
	for (i=0;i<checkList.length;i++){
		if(!isPhoneNum(checkList[i])) {
			returnFlag =false;
			break;
		}
  }
  return returnFlag;
}

/**
 *���һ���ַ�������ʵ���ȣ�һ��������2���ֽڣ�
 */
function length2(str)
{
  var length2 = str.length;
  for(var i=0;i<str.length;i++)
		if(str.charAt(i)>'~' || str.charAt(i)<' ')length2++;
  return length2;
}

String.prototype.length2 = function()
{
  return length2(this);
}

/**
 * ���һ���ַ�������ʵ���ȣ�һ��������2���ֽڣ�
 */
function getLength(str)
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

/************add by zhuhongqi****************/

/**
 * ���ַ��Ƚ�ʱ�䣨ǰ���ַ�Ϊyyyy-MM-dd��
 */
function compareDate(dateStr1, dateStr2)
{
    //�����һ��ʱ�䲻��������Ϊ��ȷ
    if (trim(dateStr1) == "" || trim(dateStr2) == "")
    {
        return true;
    }
    if (trim(dateStr1) < trim(dateStr2))
    {
        return true;
    }

    return false;
}



//��֤����������Ƿ�Ϸ�(�������ģ����ĵķ��Ų���ȥ��)
function isUnlawfulChar(str)
{
    var reg = /^[^`~@#\$%\^&\*\(\)=\!\+\\\/\|<>\?;\:\.'"\{\}\[\]��, ]*$/;
    return reg.test(str);
}

//��֤����������Ƿ�Ϸ�(�������롰()���������ģ����ĵķ��Ų���ȥ��)
function isUnlawfulCharForSuit(str)
{
    var reg = /^[^`~@#\$%\^&\*=\!\+\\\/\|<>\?;\:\.'"\{\}\[\]��, ]*$/;
    return reg.test(str);
}

/**
 * ��֤�ַ����Ƿ������
 */
function isNumber(str)
{
    var reg = /^[0-9]+$/;
    var reg1 = /^-{1}[0-9]+$/;
    return reg.test(str) || reg1.test(str);
}

//У���Ƿ�Ϊ��ҳ��ʽ
function isWebFormat(str)
{
   if (trim(str) == "")
   {
       return true;
   }
   var reg = /^http:\/\/\.*/;
   var reg1 = /^https:\/\/\.*/;
   return (reg.test(str) || (reg1.test(str)));
}

//��õ�ǰ������ yyyy-MM-dd
function getCurrentStringDate()
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

