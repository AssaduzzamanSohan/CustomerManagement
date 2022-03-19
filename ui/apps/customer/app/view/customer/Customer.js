Ext.define('Admin.view.customer.Customer', {
	extend: 'Ext.panel.Panel',
	xtype: 'customer',

	requires: [
		'Ext.grid.*',
		'Ext.toolbar.Paging',
		'Ext.layout.container.Border'
	],

	controller: 'allController',
	viewModel: {
		type: 'customer'
	},
	
	layout: 'border',
    width: 0.80 * (window.innerWidth),
    height: 0.90 * (window.innerHeight),
    cls: Ext.baseCSSPrefix + 'shadow',

    bodyBorder: false,

    defaults: {
        collapsible: true,
        split: true,
        bodyPadding: 10
    },

    listeners: {
		render: 'onCustomerRender'
	},

	items: [
		{
			title: 'CUSTOMER(Search and Double click on item to Update/Delete)',
			tools: [
				{
					type:'refresh',
					tooltip: 'Refresh',
					listeners : {
						click : 'onCustomerRefresh'
					}
				}
			],
			collapsible: false,
            region: 'center',
            margin: '5 0 0 0',
			xtype: 'gridpanel',
			reference: 'customerSrcGrid',
			height: 0.50 * (window.innerHeight),
			store: 'CustomerSrcStore',
			listeners: {
				itemdblclick: 'onCustomerDblClk'
			},
			dockedItems: [
				{
					xtype: 'toolbar',
					dock: 'top',
					items: [
						{
							xtype: 'button',
							cls: 'add-new-customer',
							text: 'Add New Customer',
							reference: 'addNewCustomer',
							listeners: {
								click: 'onClickAddNewCustomer'
							}
						},
						'->',
						{
							xtype: 'textfield',
							reference: 'loginName4Src',
							fieldLabel: 'Login Name',
							emptyText: 'Enter Login Name To Search',
							labelWidth: 100,
							width: 350,
							margin : '0 0 0 5',
						},
						{
							xtype: 'button',
							padding: 2,
							margin : '0 0 0 10',
							text: 'Search',
							reference: 'customerSrc',
							listeners: {
								click: 'onCustomerSearch'
							}
						},
						'->',
					]
				}
			],
			columns: [
				{ 
					xtype: 'gridcolumn',
					text: 'Customer ID',
					dataIndex: 'customerKey',
					hidden: true,
					filter: {
						type: 'string' 
					}
				}, 				
				{ 
					xtype: 'gridcolumn',
					text: 'Customer Ver',
					dataIndex: 'customerVer',
					hidden: true,
					filter: {
						type: 'string' 
					}
				}, 
				{
					xtype: 'gridcolumn',
					text: 'Name',
					dataIndex: 'name',
					width: 300,
					align: 'center',
					filter: {
						type: 'string' 
					}
				},
				{
					xtype: 'gridcolumn',
					text: 'Email',
					dataIndex: 'email',
					width: 300,
					align: 'center',
					filter: {
						type: 'string' 
					}
				},
				{
					xtype: 'gridcolumn',
					text: 'Phone',
					dataIndex: 'phone',
					width: 300,
					align: 'center',
					filter: {
						type: 'string' 
					}
				},				
				{
					xtype: 'gridcolumn',
					text: 'Login Name',
					dataIndex: 'loginName',
					align: 'center',
					flex: 1,
					filter: {
						type: 'string' 
					}
				}
			]
		}
	]
});
