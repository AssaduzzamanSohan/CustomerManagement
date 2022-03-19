Ext.define('customer.constant.Constants', {
	alias: 'appConstants',
	alternateClassName: 'appConstants',
	requires: [
		'customer.constant.ActionType',
		'customer.constant.ContentType',
		'customer.constant.StatusType',
		'customer.constant.Type'
	],
	statics: {
  		APP_NAME: 'Customer-server',
		APP_VER: '1.0.0.0',
		SERVER_URL: 'http://localhost:8080/Customer-server/jsonRequest',
		SOURCE: 'client',
		DESTINATION: 'Customer-server',
        LIBRARY_VER: "1.0.0",
        METHOD_POST: "POST",
        DISPATCH_TYPE_AJAX: "Ajax"
	}
});