package com.wjy.atom.mybatis.module;

import com.github.pagehelper.PageInterceptor;
import com.google.inject.name.Names;
import com.wjy.atom.config.AtomConfig;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.hikaricp.HikariCPProvider;

import javax.inject.Inject;
import java.util.Properties;

public class MybatisModule extends MyBatisModule {

    private static final String PREFIX = "atom.db.";
    private String environmentId;
    private String mapperPkg;

    @Inject
    private AtomConfig config;

    public MybatisModule(String environmentId, String mapperPkg) {
        this.environmentId = environmentId;
        this.mapperPkg = mapperPkg;
    }

    @Override
    protected void initialize() {
        Properties props = new Properties();
        config.toMap().forEach((k,v) -> {
            String key = k.replace(PREFIX, "");
            props.put(key, v);
        });
        Names.bindProperties(binder(), props
        );
        environmentId(environmentId);
        bindDataSourceProviderType(HikariCPProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        addMapperClasses(mapperPkg);
        addInterceptorClass(PageInterceptor.class);
    }
}
