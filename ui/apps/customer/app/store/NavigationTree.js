Ext.define('Admin.store.NavigationTree', {
    extend: 'Ext.data.TreeStore',

    storeId: 'NavigationTree',

    fields: [{
        name: 'text'
    }],

    root: {
        expanded: true,
        children: [
            {
                text: 'Home',
                viewType: 'home',
                iconCls: 'x-fa fa-desktop',
                leaf: true
            },
            {
                text: 'Customer',
                viewType: 'customer',
                iconCls: 'x-fa fa-desktop',
                leaf: true
            },
            {
                text: 'Activity Logs',
                viewType: 'activityLog',
                iconCls: 'x-fa fa-desktop',
                leaf: true
            },
        ]
    }
});
