package com.wjy.atom.jdbc.module;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.wjy.atom.jdbc.manager.EntityManager;
import com.wjy.atom.jdbc.manager.impl.EntityManagerImpl;

import javax.sql.DataSource;

public class JdbcModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DataSource.class).toProvider(DataSourceProvider.class).in(Scopes.SINGLETON);
        bind(EntityManager.class).to(EntityManagerImpl.class);
    }

}
