Ext.define('Admin.view.activity-log.ActivityLog', {
	extend: 'Ext.panel.Panel',
	xtype: 'activityLog',

	requires: [
		'Ext.grid.*',
		'Ext.toolbar.Paging',
		'Ext.layout.container.Border'
	],

	controller: 'allController',
	viewModel: {
		type: 'activityLog'
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
		render: 'onActivityLogRender'
	},
	items: [
		{
			title: 'ACTIVITY LOG',
			tools: [
				{
					xtype: 'button',
					text: 'Collapse all Rows',
					itemId: 'collapseExpandBtn',
					listeners: {
						click: 'onCollapseExpandAllRows'
					}
				},
				{
					type:'refresh',
					tooltip: 'Refresh',
					listeners : {
						click : 'onActivityLogRender'
					}
				}
			],
			collapsible: false,
            region: 'center',
            margin: '5 0 0 0',
			xtype: 'gridpanel',
			reference: 'activityLogGrid',
			height: 0.50 * (window.innerHeight),
			store: 'ActivityLogStore',
	        features: [{
                ftype: 'grouping',
                groupHeaderTpl: '{name} ({children.length})',
                enableNoGroups: true,
                startCollapsed: false,
            }],
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
					width: 200,
					align: 'center',
					filter: {
						type: 'string' 
					}
				},
				{
					xtype: 'gridcolumn',
					text: 'Email',
					dataIndex: 'email',
					width: 200,
					align: 'center',
					filter: {
						type: 'string' 
					}
				},
				{
					xtype: 'gridcolumn',
					text: 'Phone',
					dataIndex: 'phone',
					width: 150,
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
				},
				{
					xtype: 'datecolumn',
					text: 'Last Modification',
					width: 150,
					dataIndex: 'dateModified',
					renderer: Ext.util.Format.dateRenderer('d-M-y h:m:s'),
					filter: {
						type: 'date' 
					}
				},
				{
					xtype: 'gridcolumn',
					text: 'Status',
					dataIndex: 'isActive',
					align: 'center',
					width: 150,
					renderer : function(value,store,rec,rowIndex,colIndex){
						if(rec.data.customerVer == 0){
							return 'CREATED';
						}
						if(value){
							return "ACTIVE";
						}
						return "DELETED";
					}
				}				
			]
		}
	]
});
