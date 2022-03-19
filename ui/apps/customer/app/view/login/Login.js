
Ext.define('Admin.view.login.Login',{
	extend: 'Ext.panel.Panel',

	requires: [
		'appConstants',
		'Ext.window.MessageBox',
		'Admin.view.login.LoginController',
		'Ext.form.field.plugin.CapsDetector'
	],

	xtype: 'login',
	reference:'dashLogin',

	controller: 'login-login',
	viewModel: {
		type: 'login-login'
	},
	
	closable: false,
	draggable: false,
	resizable: false,
	bodyStyle: "background-image:url(resources/images/lock-screen-background.jpg)",

	layout: {
		type: 'vbox',
		align: 'middle',
		pack: 'center'
	},
	items: [
		{
			xtype: 'panel',
			layout: 'form',
			items: [
				{
					xtype: 'form',
					reference: 'loginFormD',
					bodyBorder: false,                  
					title: 'Login',
					iconCls: 'icon_padlock',
					items: [
						{
							xtype: 'tbspacer',
							height: 5
						},
						{
							reference: 'uidD',
							xtype: 'textfield',
							emptyText : 'User Name',
							width: 400,
							allowBlank: false,
							listeners: {
								specialkey: 'onKeyPress'
							}
						},
						{
							xtype: 'tbspacer',
							height: 5
						},
						{
							reference: 'passD',
							xtype: 'textfield',
							emptyText : 'Password',
							width: 400,
							inputType: 'password',
							listeners: {
								specialkey: 'onKeyPress'
							},
							allowBlank: false,
							plugins: [
								{
									ptype: 'capslockdetector',
									title: '<span style="color : red">Caps lock is on</span>',
									message: 'Having caps lock on may cause you to enter your password incorrectly.'
								}
							]
						},
						{
							xtype: 'tbspacer',
							height: 5
						}	
					],
					buttons: [
						{
							xtype: 'button',
							reference: 'loginBtnC',
							style: 'border: groove',
							icon: 'resources/images/admin.png',
							text: '<div style="margin-left:0px">Customer Login</div>',
							listeners: {
								click: 'onCustomerLoginButtonClick'
							}
						},
						'->',
						{
							xtype: 'button',
							reference: 'loginBtnD',
							style: 'border: groove',
							icon: 'resources/images/admin.png',
							text: '<div style="margin-left:0px">User Login</div>',
							listeners: {
								click: 'onLoginButtonClick'
							}
						},
						{
							xtype: 'button',
							style: 'border: groove',
							margin: '0 0 0 15',
							icon: 'resources/images/cleaning_brush.png',
							text: '<div style="margin-left:0px">Clear</div>',
							listeners: {
								click: 'onCancelButtonClick'
							}
						}
					]
				}
			]
		}
	]
});
