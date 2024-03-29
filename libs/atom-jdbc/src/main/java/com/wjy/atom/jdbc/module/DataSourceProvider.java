package com.wjy.atom.jdbc.module;

import com.wjy.atom.config.AtomConfig;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.sql.DataSource;
import java.util.Properties;

public class DataSourceProvider implements Provider<DataSource> {

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
        try {
            return BasicDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
