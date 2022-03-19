Ext.define('Admin.model.Customer', {
	extend: 'Admin.model.Base',

	fields: [
		{name: 'customerKey'},
		{name: 'customerVer'},
		{name: 'name'},
		{name: 'email'},
		{name: 'phone'},
		{name: 'loginName'},
		{name: 'password'},
		{name: 'userModKey'},
		{name: 'dateModified'},
		{name: 'isActive'}
	]
});