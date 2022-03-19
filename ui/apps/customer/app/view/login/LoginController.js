Ext.define('Admin.view.login.LoginController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login-login',

    onSuccess: function(request, response) {

    	var controller 	= request.sender;
		var view 		= controller.view;
		var headerRef 	= request.header.reference;
		var data 		= response.payload[1].payload; 

		if (isMessageBox) {
			Ext.MessageBox.hide();
			isMessageBox = false;
		}

		if(headerRef == 'onLoginButtonClick'){
			if(response.payload[0].payload[0].statusType == 'OK' && response.payload[1].payload){
				var data = response.payload[1].payload;
				if (data.errMsg == null) {
					gUserId 			= data.userKey;
					gLoginName 			= data.loginName;
					gUserName 			= data.name;

					var roleArr = data.roles.split(',');

					for (i = 0; i < roleArr.length; i++) {
						userRoles.add(roleArr[i], roleArr[i]);
					}
					request.sender.getView().destroy();
		    		Ext.widget('app-main');
				}
				else {
					icon = Ext.MessageBox['error'.toUpperCase()];

					Ext.MessageBox.show({
						title: 'Error',
						msg: data.errMsg,
						buttons: Ext.MessageBox.OK,
						animateTarget: this.sender.lookupReference('loginBtnD'),
						scope: this,
						fn: this.showResult,
						icon: icon
					});
				}
			}
			else{
				Ext.MessageBox.alert('Status', 'User Not Found.');
			}
		}		
		if(headerRef == 'onCustomerLoginButtonClick'){
			if(response.payload[0].payload[0].statusType == 'OK' && response.payload[1].payload){
				if (data.errMsg == null) {
					gUserId 			= data.customerKey;
					gLoginName 			= data.loginName;
					gUserName 			= data.name;

					gIsCustomerLogin = true;

					request.sender.getView().destroy();
		    		Ext.widget('app-main');
				}
				else {
					icon = Ext.MessageBox['error'.toUpperCase()];

					Ext.MessageBox.show({
						title: 'Error',
						msg: data.errMsg,
						buttons: Ext.MessageBox.OK,
						animateTarget: this.sender.lookupReference('loginBtnC'),
						scope: this,
						fn: this.showResult,
						icon: icon
					});
				}
			}
			else{
				Ext.MessageBox.alert('Status', 'User Not Found.');
			}
		}
	},

	sendRequest: function(actionName, contentType, payload, header) {

		var component = null;
		
		if (Ext.isEmpty(payload)) {
			payload = new Array();
		} else if (!Ext.isEmpty(payload.reference)) {
			component = payload.reference;
		} else if (!Ext.isEmpty(payload[0]) && payload[0].reference != 'undefined') {
			component = payload[0].reference;
		}

		var request = {
			actionName: actionName,
			contentType: contentType,
			serviceType: contentType,
			requestId: null,
			requestType: null,
			header: header,
			body: payload,
			message: null,
			dispatchType: null,
			sender: this,
			component: component,
			onSuccess: this.onSuccess,
			onError: this.onError
		};

		var requestId = nMessageProcessor.sendRequest(request);
	},

	onKeyPress: function(field, e) {
		if (e.getKey() == e.ENTER) {
			this.onLoginButtonClick(field, e);
		}
	},

	onLoginButtonClick: function(button, e, eOpts) {
		var userId = this.lookupReference('uidD').value;
		var password = this.lookupReference('passD').value;

		var header = {
			reference           : 'onLoginButtonClick'
		};

		var payload = [{
			loginName           : userId,
			password            : password
		}];

		this.sendRequest(appActionType.ACTION_TYPE_LOGIN, appContentType.CONTENT_TYPE_USER, payload, header);				
	},	
	onCustomerLoginButtonClick: function(button, e, eOpts) {
		var userId = this.lookupReference('uidD').value;
		var password = this.lookupReference('passD').value;

		var header = {
			reference           : 'onCustomerLoginButtonClick'
		};

		var payload = [{
			loginName           : userId,
			password            : password
		}];

		this.sendRequest(appActionType.ACTION_TYPE_LOGIN, appContentType.CONTENT_TYPE_CUSTOMER, payload, header);				
	},

	onCancelButtonClick: function(button, e, eOpts) {
		this.lookupReference('loginFormD').reset();
	}
});