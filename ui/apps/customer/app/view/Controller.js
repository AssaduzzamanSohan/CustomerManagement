
var allCustomerArr = new Array();
var itemPerPage = 15;
var currentPage = -1;
var totalPage = 0;

Ext.define('Admin.view.Controller', {
	extend: 'Ext.app.ViewController',
	alias: 'controller.allController',

	onSuccess: function(request, response) {

		var controller 	= request.sender;
		var view 		= controller.view;
		var headerRef 	= request.header.reference;
		var data 		= response.payload[1].payload; 

		if (isMessageBox) {
			Ext.MessageBox.hide();
			isMessageBox = false;
		}

		if(headerRef == 'onHomeRender'){
			Ext.getBody().unmask();

			allCustomerArr = data;
			controller.startOverPagination();
		}

		if(headerRef == 'onCustomerSearch'){
			Ext.getBody().unmask();
			loadDataInGlobalStore(data, 'CustomerSrcStore');
		}
		if(headerRef == 'onCurrentCustomerSearch'){
			Ext.getBody().unmask();
			loadDataInGlobalStore(new Array(data), 'CustomerSrcStore');
		}
		if(headerRef == 'onCustomerSave'){
			Ext.getBody().unmask();
			createMessageBox('Status', 'Customer Saved.');
			controller.getView().close();
		}
		if(headerRef == 'onCustomerUpdate'){
			Ext.getBody().unmask();
			createMessageBox('Status', 'Customer Updated.');
			controller.getView().close();
		}
		if(headerRef == 'onCustomerDelete'){
			Ext.getBody().unmask();
			createMessageBox('Status', 'Customer Deleted.');
			controller.getView().close();
		}
		if(headerRef == 'onActivityLogRender'){
			Ext.getBody().unmask();
			loadDataInGlobalStore(data, 'ActivityLogStore');
		}

	},

	sendRequest: function(actionName, contentType, payload, header) {
		
		var component = null;
		
		if (Ext.isEmpty(payload)) {
			payload = new Array();
		}
		else if(!Ext.isEmpty(payload.reference)){
			component = payload.reference;
		}
		else if(!Ext.isEmpty(payload[0]) && payload[0].reference != 'undefined'){
			component = payload[0].reference;
		}

		var request = {
			actionName      : actionName,
			contentType     : contentType,
			serviceType     : contentType,
			requestId       : null,
			requestType     : null,
			header          : header,
			body            : payload,
			message         : null,
			dispatchType    : null,
			sender          : this,
			component       : component,
			onSuccess       : this.onSuccess,
			onError         : this.onError
		};

		var requestId = nMessageProcessor.sendRequest(request); 
	},

	onActivityLogRender: function(btn, eOpts){
		var header = {
			reference  : 'onActivityLogRender'
		};

		var payload = [{
			userModkey		: gUserId
		}];

		Ext.getBody().mask('Please wait...');

		this.sendRequest(appActionType.ACTION_TYPE_SELECT_ALL_AUDIT, appContentType.CONTENT_TYPE_CUSTOMER, payload, header);
	},

	onHomeRender: function(btn, eOpts){
		var header = {
			reference  : 'onHomeRender'
		};

		var payload = [{
			userModkey		: gUserId
		}];

		Ext.getBody().mask('Please wait...');

		this.sendRequest(appActionType.ACTION_TYPE_SELECT_ALL, appContentType.CONTENT_TYPE_CUSTOMER, payload, header);
	},

	startOverPagination: function(){
		currentPage = -1;
		this.setTotalPageCount();
		this.loadNextPageCustomerGridData();
	},

	setTotalPageCount: function(){
		totalPage = Math.ceil(allCustomerArr.length / itemPerPage);
	},

	loadPrevPageCustomerGridData: function(){
		if(currentPage <= 0){
			createMessageBox('Status', 'You are on first page.');
			return;
		}
		currentPage = currentPage - 1;
		var start = currentPage * itemPerPage;
		var end = currentPage * itemPerPage + itemPerPage;

		var countMessage = 'Showing ' + (start+1) + ' to ' + end + ' out of ' + allCustomerArr.length;

		loadDataInGlobalStore(allCustomerArr.slice(start , end), 'CustomerStore');

		this.lookupReference('countMessage').setValue(countMessage);
		this.lookupReference('currentPage').setValue(currentPage + 1);
	},	
	loadNextPageCustomerGridData: function(){
		if(currentPage+1 >= totalPage){
			createMessageBox('Status', 'You are on last page.');
			return;
		}
		currentPage = currentPage + 1;
		var start = currentPage * itemPerPage;
		var end = currentPage * itemPerPage + itemPerPage;
		if(currentPage+1 >= totalPage){
			end = allCustomerArr.length;
		}

		var countMessage = 'Showing ' + (start+1) + ' to ' + end + ' out of ' + allCustomerArr.length;

		loadDataInGlobalStore(allCustomerArr.slice(start , end), 'CustomerStore');

		this.lookupReference('countMessage').setValue(countMessage);
		this.lookupReference('currentPage').setValue(currentPage + 1);
	},

	onCustomerSearch: function(btn, eOpts){
		var me = this;
		var loginName = this.lookupReference('loginName4Src').value;

		if(!loginName){
			createMessageBox('Error', 'Please, provide Login Name to search.');
			return;
		}

		var header = {
			reference  : 'onCustomerSearch'
		};

		var payload = [{
			userModkey		: gUserId,
			userModifiedId	: gUserId,
			loginName		: loginName
		}];

		Ext.getBody().mask('Please wait...');

		me.sendRequest(appActionType.ACTION_TYPE_SEARCH, appContentType.CONTENT_TYPE_CUSTOMER, payload, header);
	},	
	onCurrentCustomerSearch: function(btn, eOpts){
		var me = this;

		var header = {
			reference  : 'onCurrentCustomerSearch'
		};

		var payload = [{
			userModkey		: gUserId,
			customerKey	: gUserId
		}];

		Ext.getBody().mask('Please wait...');

		me.sendRequest(appActionType.ACTION_TYPE_SELECT, appContentType.CONTENT_TYPE_CUSTOMER, payload, header);
	},
	onChangeDobOfPg: function (cmp, newValue, oldValue, eOpts) {
		if(this.lookupReference('customerKey')) return;
		custDtlsForm.lookupReference('loginName').setValue(newValue);
	},
	onCustomerDblClk: function(view, rec, item, index, e){
		var custDtlsForm = Ext.create('Admin.view.customer.CustomerDetails');

		custDtlsForm.lookupReference('customerKey').setValue(rec.data.customerKey);
		custDtlsForm.lookupReference('customerVer').setValue(rec.data.customerVer);
		custDtlsForm.lookupReference('loginName').setValue(rec.data.loginName);
		custDtlsForm.lookupReference('loginName').setReadOnly(true);
		custDtlsForm.lookupReference('name').setValue(rec.data.name);
		custDtlsForm.lookupReference('email').setValue(rec.data.email);
		custDtlsForm.lookupReference('phone').setValue(rec.data.phone);
		custDtlsForm.lookupReference('password').setValue(rec.data.password);

		custDtlsForm.show();
	},
	onClickAddNewCustomer: function(){
		var custDtlsForm = Ext.create('Admin.view.customer.CustomerDetails');
		custDtlsForm.show();
	},
	onActivateCustDetails: function(cmp, eOpts){
		if(this.lookupReference('customerKey').value){
			this.lookupReference('custUpdate').setHidden(false);
			if(!gIsCustomerLogin){
				this.lookupReference('custDelete').setHidden(false);
			}
			this.getView().setTitle('Customer');
		}
		else{
			this.lookupReference('custSave').setHidden(false);
		}
	},
	onCustomerSave: function(btn){
		if(!validateCustomer(this)) return;
		var header = {
			reference  : 'onCustomerSave'
		};
		
		Ext.getBody().mask('Please wait...');
		this.sendRequest(appActionType.ACTION_TYPE_NEW, appContentType.CONTENT_TYPE_CUSTOMER, getCustomerPayload(this), header);
	},
	onCustomerUpdate: function(btn){
		if(!validateCustomer(this)) return;
		var header = {
			reference  : 'onCustomerUpdate'
		};

		Ext.getBody().mask('Please wait...');
		this.sendRequest(appActionType.ACTION_TYPE_UPDATE, appContentType.CONTENT_TYPE_CUSTOMER, getCustomerPayload(this), header);
	},
	onCustomerDelete: function(btn){

		var header = {
			reference  : 'onCustomerDelete'
		};

		Ext.getBody().mask('Please wait...');
		this.sendRequest(appActionType.ACTION_TYPE_DELETE, appContentType.CONTENT_TYPE_CUSTOMER, getCustomerPayload(this), header);
	},
	onCollapseExpandAllRows: function (button, e, opts){
        var grid = this.lookupReference('activityLogGrid');

        if(button.text == 'Expand All Rows'){
            button.setText('Collapse all Rows');
            grid.view.features[0].expandAll();
        }
        else{ 
            button.setText('Expand All Rows');
            grid.view.features[0].collapseAll();
        }
    },
    onSelectItemPerPage: function(combo, record, index){
    	itemPerPage = record.data.value;
    	this.startOverPagination();
    },

    onCustomerRender: function(){
    	var me = this;
    	if(gIsCustomerLogin){
    		me.lookupReference('addNewCustomer').setHidden(true);
    		me.lookupReference('loginName4Src').setHidden(true);
    		me.lookupReference('customerSrc').setHidden(true);
    		me.onCurrentCustomerSearch();
    	}
    },
    onCustomerRefresh: function(btn, eOpts){
    	var me = this;
		if(gIsCustomerLogin){
			me.onCurrentCustomerSearch();
    	}
    	else{
    		me.onCustomerSearch(btn, eOpts);
    	}
    }
});

function getCustomerPayload(cmp){
	var customerKey = cmp.lookupReference('customerKey').value;
	var customerVer = cmp.lookupReference('customerVer').value;
	var name = cmp.lookupReference('name').value;
	var email = cmp.lookupReference('email').value;
	var loginName = cmp.lookupReference('loginName').value;
	var phone = cmp.lookupReference('phone').value;
	var password = cmp.lookupReference('password').value;

	var payload = [{
		customerKey:  customerKey ? customerKey : null,
		customerVer:  customerVer ? customerVer : null,
		name:  name ? name : null,
		email:  email ? email : null,
		loginName:  loginName ? loginName : null,
		phone:  phone ? phone : null,
		password:  password ? password : null,
		userModkey : gUserId 
	}]

	return payload;
}

function validateCustomer(cmp){
	var email = cmp.lookupReference('email').value;
	var loginName = cmp.lookupReference('loginName').value;
	var password = cmp.lookupReference('password').value;

	if(!validEmailCheck(email)) return false;
	if(!loginName){
		Ext.MessageBox.alert('Missing', 'Email Can not be Empty.');
		return false;
	}
	if(!password){
		Ext.MessageBox.alert('Missing', 'Password Can not be Empty.');
		return false;
	}

	return true;
}

function createMessageBox(title, message){
  Ext.create('Ext.window.MessageBox', {
      alwaysOnTop: true,
      closeAction: 'destroy'
  }).show({
      title: 'title',
      buttons: Ext.Msg.OK,
      message: message
  });
}

function validEmailCheck(email) {
	var emailValidator = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	if (!email.match(emailValidator)) {
		Ext.MessageBox.alert('Invalid Email', 'Please Give Valid Email.');
		return false;
	}
	return true;
}