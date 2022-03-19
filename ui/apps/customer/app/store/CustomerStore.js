Ext.define('Admin.store.CustomerStore', {
    extend: 'Ext.data.Store',

    storeId: 'CustomerStore',
    model: 'Admin.model.Customer'
});

Ext.define('Admin.store.CustomerSrcStore', {
    extend: 'Ext.data.Store',

    storeId: 'CustomerSrcStore',
    model: 'Admin.model.Customer'
});

Ext.define('Admin.store.ActivityLogStore', {
    extend: 'Ext.data.Store',

    storeId: 'ActivityLogStore',
    model: 'Admin.model.Customer',

    groupField:'loginName',
    groupDir: 'ASC',
});
