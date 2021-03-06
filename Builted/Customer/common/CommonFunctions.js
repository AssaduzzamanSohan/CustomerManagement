
//  -- var --
var isMessageBox = false;

// ----------
function isEmpty(value){
	return Ext.isEmpty(value) ? null : value;
}

function replacer(key, value) {

	if (typeof value === 'number' && !isFinite(value)) {
		return String(value);
	}

	return value;
}

function showProcessMessage(message){
	Ext.MessageBox.show({
		title: 'Status',
		msg : message,
		progressText : message,
		zIndex: 99999,
		buttons : Ext.window.MessageBox.OK,
		width : 300,
		progress:true,
		floating:true,
		modal: true,
		wait:false,
		fn : function() {
			Ext.MessageBox.hide();
		}
	});
	Ext.defer(function() {
					Ext.Msg.toFront()
				}, 20);

	isMessageBox = true;
}

function formatDateOnly(value){
	return value ? Ext.Date.dateFormat(value, 'M d, Y') : '';
}

function formatDate(value){
	return value ? Ext.Date.dateFormat(value, 'M d, Y') : '';
}

function formatDateTime(value){
	return value ? ((Ext.Date.dateFormat(value, 'Ymd') != '19700101') ? Ext.Date.dateFormat(value, 'M d, Y h:i:s a') : '') : '';
}

// function for creating captcha manually
function Captcha(){

    var alpha = new Array('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','1','2','3','4','5','6','7','8','9','0');
    
    var i;
    
    for (i=0;i<6;i++){
	    var a = alpha[Math.floor(Math.random() * alpha.length)];
	    var b = alpha[Math.floor(Math.random() * alpha.length)];
	    var c = alpha[Math.floor(Math.random() * alpha.length)];
	    var d = alpha[Math.floor(Math.random() * alpha.length)];
	    var e = alpha[Math.floor(Math.random() * alpha.length)];
    }

    var code = a + ' ' + b + ' ' + ' ' + c + ' ' + d + ' ' + e;

    return code;
}

function ValidCaptcha(str1, str2){

	var string1 = removeSpaces(str1);
	var string2 = removeSpaces(str2);
	
	if (string1 == string2){
		return true;
	}
  	else{        
    	return false;
  	}
}

function removeSpaces(string){
	return string.split(' ').join('');
}

function between(x, min, max) {
  return x >= min && x <= max;
}

function getGlobalStore(storeId){
  return Ext.data.StoreManager.lookup(storeId);
}

function loadDataInGlobalStore(data, storeId){
  var store = getGlobalStore(storeId);
  store.clearData();
  store.loadData(data);
}