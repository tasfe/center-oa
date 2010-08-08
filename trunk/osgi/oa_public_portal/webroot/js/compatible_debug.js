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

is_chrome = /chrome/i.test(navigator.userAgent);
is_msie = /msie/i.test(navigator.userAgent);
is_safari = /Safari/i.test(navigator.userAgent);

if(window.Node){// 修正Node的DOM 暂时取消
    /* 
                                IE5        MacIE5        Mozilla        Konqueror2.2        Opera5 
    Node.contains                yes        yes            no            no                    yes 
    Node.replaceNode            yes        no            no            no                    no 
    Node.removeNode                yes        no            no            no                    no 
    Node.children                yes        yes            no            no                    no 
    Node.hasChildNodes            yes        yes            yes            yes                    no 
    Node.childNodes                yes        yes            yes            yes                    no 
    Node.swapNode                yes        no            no            no                    no 
    Node.currentStyle            yes        yes            no            no                    no 
     
    */ 
    //Node.prototype.replaceNode=function(Node){// 替换指定节点 
        //this.parentNode.replaceChild(Node,this); 
        //} 
    // removeChildren true false
    Node.prototype.removeNode = function(removeChildren){// 删除指定节点 
    	if (is_msie)
    	{
    		this.removeNode(removeChildren);
    		return;
    	}
    	// friefox chrome safari
    	if (this.parentNode)
        if(removeChildren) 
        {
            return this.parentNode.removeChild(this); 
        }
        else{ 
            var range = document.createRange(); 
            range.selectNodeContents(this); 
            return this.parentNode.replaceChild(range.extractContents(),this); 
            } 
        } 
        
    Node.prototype.swapNode=function(Node){// 交换节点 
        var nextSibling=this.nextSibling; 
        var parentNode=this.parentNode; 
        node.parentNode.replaceChild(this,Node); 
        parentNode.insertBefore(node,nextSibling); 
        } 
    } 

if(window.HTMLElement){ 
    HTMLElement.prototype.__defineGetter__("all",function(){ 
        var a=this.getElementsByTagName("*"); 
        var node=this; 
        a.tags=function(sTagName){ 
            return node.getElementsByTagName(sTagName); 
            } 
        return a; 
        }); 
    HTMLElement.prototype.__defineGetter__("parentElement",function(){ 
        if(this.parentNode==this.ownerDocument)return null; 
        return this.parentNode; 
        }); 
    HTMLElement.prototype.__defineGetter__("children",function(){ 
        var tmp=[]; 
        var j=0; 
        var n; 
        for(var i=0;i<this.childNodes.length;i++){ 
            n=this.childNodes[i]; 
            if(n.nodeType==1){ 
                tmp[j++]=n; 
                if(n.name){ 
                    if(!tmp[n.name]) 
                        tmp[n.name]=[]; 
                    tmp[n.name][tmp[n.name].length]=n; 
                    } 
                if(n.id) 
                    tmp[n.id]=n; 
                } 
            } 
        return tmp; 
        }); 
    HTMLElement.prototype.__defineGetter__("currentStyle", function(){ 
        return this.ownerDocument.defaultView.getComputedStyle(this,null); 
        }); 
    HTMLElement.prototype.__defineSetter__("outerHTML",function(sHTML){ 
        var r=this.ownerDocument.createRange(); 
        r.setStartBefore(this); 
        var df=r.createContextualFragment(sHTML); 
        this.parentNode.replaceChild(df,this); 
        return sHTML; 
        }); 
    HTMLElement.prototype.__defineGetter__("outerHTML",function(){ 
        var attr; 
        var attrs=this.attributes; 
        var str="<"+this.tagName; 
        for(var i=0;i<attrs.length;i++){ 
            attr=attrs[i]; 
            if(attr.specified) 
                str+=" "+attr.name+'="'+attr.value+'"'; 
            } 
        if(!this.canHaveChildren) 
            return str+">"; 
        return str+">"+this.innerHTML+"</"+this.tagName+">"; 
        }); 
    HTMLElement.prototype.__defineGetter__("canHaveChildren",function(){ 
        switch(this.tagName.toLowerCase()){ 
            case "area": 
            case "base": 
            case "basefont": 
            case "col": 
            case "frame": 
            case "hr": 
            case "img": 
            case "br": 
            case "input": 
            case "isindex": 
            case "link": 
            case "meta": 
            case "param": 
                return false; 
            } 
        return true; 
        }); 

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
    HTMLElement.prototype.__defineGetter__("oncheck",function(){ 
        return this.getAttribute("oncheck"); 
        }); 
    HTMLElement.prototype.__defineSetter__("oncheck",function(sText){ 
        this.setAttribute('oncheck', sText);
        return sText; 
        });
        
    HTMLElement.prototype.__defineGetter__('values',function(){ 
		return this.getAttribute('values'); 
		}); 

	HTMLElement.prototype.__defineSetter__('values',function(){ 
		this.setAttribute('values', sText);
		return sText; 
		}); 
	HTMLElement.prototype.__defineGetter__('index',function(){ 
		return this.getAttribute('index'); 
		}); 
	
	HTMLElement.prototype.__defineSetter__('index',function(){ 
		this.setAttribute('index', sText);
		return sText; 
		}); 
	HTMLElement.prototype.__defineGetter__('quick',function(){ 
		return this.getAttribute('quick'); 
		}); 
	
	HTMLElement.prototype.__defineSetter__('quick',function(){ 
		this.setAttribute('quick', sText);
		return sText; 
		}); 
	HTMLElement.prototype.__defineGetter__('readonly',function(){ 
		return this.getAttribute('readonly'); 
		}); 
	
	HTMLElement.prototype.__defineSetter__('readonly',function(){ 
		this.setAttribute('readonly', sText);
		return sText; 
		}); 
	HTMLElement.prototype.__defineGetter__('ime',function(){ 
		return this.getAttribute('ime'); 
		}); 
	
	HTMLElement.prototype.__defineSetter__('ime',function(){ 
		this.setAttribute('ime', sText);
		return sText; 
		}); 
	HTMLElement.prototype.__defineGetter__('ext_a',function(){ 
		return this.getAttribute('ext_a'); 
		}); 
	
	HTMLElement.prototype.__defineSetter__('ext_a',function(){ 
		this.setAttribute('ext_a', sText);
		return sText; 
		}); 
	HTMLElement.prototype.__defineGetter__('ext_b',function(){ 
		return this.getAttribute('ext_b'); 
		}); 
	
	HTMLElement.prototype.__defineSetter__('ext_b',function(){ 
		this.setAttribute('ext_b', sText);
		return sText; 
		}); 
	HTMLElement.prototype.__defineGetter__('ext_c',function(){ 
		return this.getAttribute('ext_c'); 
		}); 
	
	HTMLElement.prototype.__defineSetter__('ext_c',function(){ 
		this.setAttribute('ext_c', sText);
		return sText; 
		}); 
	HTMLElement.prototype.__defineGetter__('ext_d',function(){ 
		return this.getAttribute('ext_d'); 
		}); 
	
	HTMLElement.prototype.__defineSetter__('ext_d',function(){ 
		this.setAttribute('ext_d', sText);
		return sText; 
		}); 
	HTMLElement.prototype.__defineGetter__('ext_e',function(){ 
		return this.getAttribute('ext_e'); 
		}); 
	
	HTMLElement.prototype.__defineSetter__('ext_e',function(){ 
		this.setAttribute('ext_e', sText);
		return sText; 
		}); 
	HTMLElement.prototype.__defineGetter__('ext_f',function(){ 
		return this.getAttribute('ext_f'); 
		}); 
	
	HTMLElement.prototype.__defineSetter__('ext_f',function(){ 
		this.setAttribute('ext_f', sText);
		return sText; 
		}); 
	HTMLElement.prototype.__defineGetter__('ext_g',function(){ 
		return this.getAttribute('ext_g'); 
		}); 
	
	HTMLElement.prototype.__defineSetter__('ext_g',function(){ 
		this.setAttribute('ext_g', sText);
		return sText; 
		}); 
    
    HTMLElement.prototype.__defineSetter__("outerText",function(sText){ 
        var parsedText=document.createTextNode(sText); 
        this.outerHTML=parsedText; 
        return parsedText; 
        }); 
    HTMLElement.prototype.__defineGetter__("outerText",function(){ 
        var r=this.ownerDocument.createRange(); 
        r.selectNodeContents(this); 
        return r.toString(); 
        }); 
    
    HTMLElement.prototype.contains=function(Node){// 是否包含某节点 
        do if(Node==this)return true; 
        while(Node=Node.parentNode); 
        return false; 
        } 
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
    HTMLElement.prototype.insertAdjacentHTML=function(where,htmlStr){ 
        var r=this.ownerDocument.createRange(); 
        r.setStartBefore(this); 
        var parsedHTML=r.createContextualFragment(htmlStr); 
        this.insertAdjacentElement(where,parsedHTML); 
        } 
    HTMLElement.prototype.insertAdjacentText=function(where,txtStr){ 
        var parsedText=document.createTextNode(txtStr); 
        this.insertAdjacentElement(where,parsedText); 
        } 
    
    } 