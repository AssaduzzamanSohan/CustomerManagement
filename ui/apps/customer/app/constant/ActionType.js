String.prototype.replaceAll = function(search, replacement) {
    var target = this;
    return target.split(search).join(replacement);
};

Ext.define('customer.constant.ActionType', {
	alias: 'appActionType',
	alternateClassName: 'appActionType',
	statics: {
		ACTION_TYPE_NEW: 'NEW',
		ACTION_TYPE_UPDATE: 'UPDATE',
		ACTION_TYPE_SELECT: 'SELECT',
		ACTION_TYPE_DELETE: 'DELETE',
		ACTION_TYPE_LOGIN: 'LOGIN',
		ACTION_TYPE_LOGOUT: 'LOGOUT',
		ACTION_TYPE_SELECT_ALL: 'SELECT_ALL',
		ACTION_TYPE_SEARCH: 'SEARCH',
		ACTION_TYPE_SELECT_ALL_AUDIT: 'SELECT_ALL_AUDIT',
		ACTION_TYPE_SEARCH_AUDIT: 'SEARCH_AUDIT',
	}
});