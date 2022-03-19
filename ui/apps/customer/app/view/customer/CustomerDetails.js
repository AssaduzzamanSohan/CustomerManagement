var screenWidth = 550;
var screenHeight = (window.innerHeight > 0) ? window.innerHeight : screen.height;

Ext.define('Admin.view.customer.CustomerDetails', {
	extend : 'Ext.window.Window',
	alias: 'widget.customerDetails',
	reference: 'customerDetails',
	requires: [
		'Ext.*',
		'appConstants'
	],

	controller: 'allController',
    viewModel: {
        type: 'customer'
    },	

	width : 0.70*screenWidth,
    height: 0.80*screenHeight,

    title: 'New Customer(Use Email to Login)',
	modal: true,
    centered: true,    
    hideOnMaskTap: true,
    fullscreen: true,
	border : true,
	closable : true,
	scrollable: true,
	layout: {
		type: 'fit',
		align: 'middle',
		pack: 'center'
	},
	
	initComponent: function() {

		var me = this;

		Ext.applyIf(me, {
			items: [
				{
					xtype: 'form',
					bodyPadding: 5,
					reference: 'customerDetailsForm',
					border: 'true',
					bind: {
						scrollable: 'true'
					},
					layout: 'column',
            		columnWidth: 1,
					items: [
						{
                            xtype: 'displayfield',
                            reference: 'customerKey',
							fieldLabel: 'Customer Key',
                            columnWidth: 1,
                            labelAlign: 'left',
                            labelWidth: 85,
                            margin : '5 5 5 5',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            reference: 'customerVer',
                            fieldLabel: 'Customer Ver',
                            columnWidth: 1,
                            labelAlign: 'left',
                            labelWidth: 85,
                            margin : '5 5 5 5',
                            hidden: true
                        },
                        {
                            xtype: 'textfield',
                            reference: 'name',
                            fieldLabel: 'Name',
                            columnWidth: 1,
                            labelAlign: 'left',
                            labelWidth: 85,
                            margin : '5 5 5 5',
                        },
                        {
                            xtype: 'textfield',
                            reference: 'loginName',
                            fieldLabel: 'loginName',
                            columnWidth: 1,
                            labelAlign: 'left',
                            labelWidth: 85,
                            margin : '5 5 5 5',
                        },
                        {
                            xtype: 'textfield',
                            reference: 'email',
							fieldLabel: '<span class="req" style="color:red">*</span>' + 'Email',
                            columnWidth: 1,
                            labelAlign: 'left',
                            labelWidth: 85,
                            margin : '5 5 5 5',
                        },
                        {
                            xtype: 'textfield',
                            reference: 'phone',
							fieldLabel: 'Phone',
                            columnWidth: 1,
                            labelAlign: 'left',
                            labelWidth: 85,
                            margin : '5 5 5 5',
                        }, 
                        {
                            xtype: 'textfield',
                            reference: 'password',
							fieldLabel: '<span class="req" style="color:red">*</span>' + 'Password',
                            columnWidth: 1,
                            labelAlign: 'left',
                            labelWidth: 85,
                            margin : '5 5 5 5',
                        }
					],
                    listeners: {
                        afterrender: 'onActivateCustDetails'
                    },
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'bottom',
                            items: [
                                '->',
                                {
                                    xtype: 'button',
                                    padding: 2,
                                    margin : '0 0 0 10',
                                    text: 'Save',
                                    reference: 'custSave',
                                    hidden: true,
                                    listeners: {
                                        click: 'onCustomerSave'
                                    }
                                },{
                                    xtype: 'button',
                                    padding: 2,
                                    margin : '0 0 0 10',
                                    text: 'Update',
                                    reference: 'custUpdate',
                                    hidden: true,
                                    listeners: {
                                        click: 'onCustomerUpdate'
                                    }
                                },{
                                    xtype: 'button',
                                    padding: 2,
                                    margin : '0 0 0 10',
                                    text: 'Delete',
                                    reference: 'custDelete',
                                    hidden: true,
                                    listeners: {
                                        click: 'onCustomerDelete'
                                    }
                                },
                                '->'
                            ]
                        }
                    ],
				}
			]
		});
		me.callParent(arguments);
	}
});