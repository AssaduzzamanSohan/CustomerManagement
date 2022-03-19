Ext.define('customer.message.MessageDispatcher', {
	alias: 'nMessageDispatcher',
	alternateClassName: 'nMessageDispatcher',
	statics: {

		dispatch : function(request, dispatchType) {

			if (Ext.isEmpty(dispatchType)) {
				dispatchType = appConstants.DISPATCH_TYPE_AJAX;
			}

			if (dispatchType === appConstants.DISPATCH_TYPE_AJAX) {
				this.dispatchAjaxRequest(request);
			} 
			else {
				Ext.MessageBox.alert('Warn', 'Unknown dispatch type ' + dispatchType);
			}
		},

		dispatchAjaxRequest : function(appRequest) {

			if (!Ext.isEmpty(appRequest.message))
			{
				var urlDestination = SERVER_URL;
				if(appRequest.header) {
					
					if(appRequest.header.destination == 'login')
					{
						urlDestination = LOGIN_URL;
					}
				}
				var payLoadString= appRequest.message.toString();
				/*if(appRequest.header) {
					
					if(appRequest.header.destination)
					{
						urlDestination = appConstants.SERVER_URL;					
						payLoadString=appRequest.message.toString().replace("payLoad","payload");
					}
				}*/
				Ext.Ajax.request({

					url			: urlDestination,
					method		: appConstants.REQUEST_METHOD_POST,
					jsonData	: payLoadString,
					//timeout 	: ajaxRequestTimeout,
					success: function(result, request) {
						var response = Ext.JSON.decode(result.responseText);
						
						nMessageProcessor.processResponse(appRequest, response);						
					},
					failure: function(result, request) {
						Ext.MessageBox.alert('Server Error', 'Server Error');
					}
				});
			}
		}
	}
});