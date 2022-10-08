package com.wjy.atom.shiro;

import com.google.inject.Scopes;
import org.apache.shiro.guice.ShiroModule;

public class AtomModule extends ShiroModule {
    @Override
    protected void configureShiro() {
        bindRealm().toProvider(JdbcRealmProvider.class).in(Scopes.SINGLETON);
    }
}
