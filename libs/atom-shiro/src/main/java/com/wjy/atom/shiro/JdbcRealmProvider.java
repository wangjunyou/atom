package com.wjy.atom.shiro;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.sql.DataSource;


public class JdbcRealmProvider implements Provider<Realm> {

    private String authenticationQuery = "select password from shiro_user where username = ?";
    private String userRolesQuery = "select role from shiro_role where username = ?";
    private String permissionsQuery = "select permission from shiro_permission where role = ?";

    @Inject
    private DataSource dataSource;

    @Override
    public Realm get() {
        JdbcRealm realm = new JdbcRealm();
        realm.setDataSource(dataSource);
        realm.setSaltStyle(JdbcRealm.SaltStyle.NO_SALT);
        realm.setAuthenticationQuery(authenticationQuery);
        realm.setUserRolesQuery(userRolesQuery);
        realm.setPermissionsQuery(permissionsQuery);
        realm.setSaltIsBase64Encoded(true);
        realm.setPermissionsLookupEnabled(true);
        return realm;
    }
}
