/**
 * �����̳�
 */
Object.extend = function(destination, source) {
  for (property in source) {
    destination[property] = source[property];
  }
  return destination;
}

Object.extend(String.prototype, {
  /**
   * ɾ���ַ����е�sub�ַ�
   */	
  delSubString: function(sub) 
  {
      var index = this.indexOf(sub);
      
	  if (index == -1)
	  {
		  return this;
	  }
	
	  var ss = this.substring(0, index) +  this.substring(index + sub.length);	
	
	  return ss.delSubString(sub);
  }
});