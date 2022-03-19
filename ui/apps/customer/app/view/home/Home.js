Ext.define('Admin.view.home.Home', {
	extend: 'Ext.panel.Panel',
	xtype: 'home',

	requires: [
		'Ext.grid.*',
		'Ext.toolbar.Paging',
		'Ext.layout.container.Border'
	],

	controller: 'allController',
	viewModel: {
		type: 'home'
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
		render: 'onHomeRender'
	},
	items: [
		{
			title: 'ALL CUSTOMER(Double click on item to Update/Delete)',
			tools: [
				{
                    xtype: 'combobox',
                    itemId: 'itemPerPage',
                    reference: 'itemPerPage',
                    width: 250,
                    fieldLabel: 'Show Customer Per Page',
                    labelAlign: 'right',
                    labelWidth: 160,
                    mode: 'local',
                    displayField: 'name',
                    valueField: 'value',
                    value: '15',
                    queryMode: 'local',
                    forceSelection: true,
                    listeners: {
						select: 'onSelectItemPerPage'
					},
                    store: Ext.create('Ext.data.Store', {
                        fields: ['name', 'value'],
                        data: [
                            {
	                            name: '10',
	                            value: '10'
	                        },
	                        {
	                            name: '15',
	                            value: '15'
	                        },
	                        {
	                            name: '20',
	                            value: '20'
	                        }
                        ]
                    }),
                },
				{
					type:'refresh',
					tooltip: 'Refresh',
					listeners : {
						click : 'onHomeRender'
					}
				}
			],
			collapsible: false,
            region: 'center',
            margin: '5 0 0 0',
			xtype: 'gridpanel',
			reference: 'customerDataGrid',
			height: 0.50 * (window.innerHeight),
			store: 'CustomerStore',
			listeners: {
				itemdblclick: 'onCustomerDblClk'
			},
			dockedItems: [
				{
					xtype: 'toolbar',
					dock: 'bottom',
					items: [
						{
		                    xtype : 'displayfield',
		                    reference : 'countMessage',
		                    margin : '0 0 0 50',
		                    value: 'Showing 0 to 0 out of 0'
		                },
						'->',
						{
							xtype: 'button',
							padding: 2,
							margin : '0 0 0 10',
							text: 'Prev',
							reference: 'prevPageBtn',
							listeners: {
								click: 'loadPrevPageCustomerGridData'
							}
						},
						{
		                    xtype : 'displayfield',
		                    reference : 'currentPage',
		                    margin : '0 10 0 10',
		                    value: '0'
		                },
						{
							xtype: 'button',
							padding: 2,
							margin : '0 10 0 0',
							text: 'Next',
							reference: 'nextPageBtn',
							listeners: {
								click: 'loadNextPageCustomerGridData'
							}
						},
						'->'
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
