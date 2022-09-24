package com.wjy.atom.mybatis.module;

import com.wjy.atom.config.AtomConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.sql.DataSource;
import java.util.Properties;

public class MybatisDataSourceProvider implements Provider<DataSource> {

    private static final String PREFIX = "atom.db.";
    @Inject
    private AtomConfig config;

    @Override
    public DataSource get() {
        Properties props = new Properties();
        config.toMap().forEach((k,v) -> {
            String key = k.replace(PREFIX, "");
            props.put(key, v);
        });
        HikariConfig hconfig = new HikariConfig(props);
        return new HikariDataSource(hconfig);
    }
}
