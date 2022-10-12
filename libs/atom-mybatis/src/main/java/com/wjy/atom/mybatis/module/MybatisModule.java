package com.wjy.atom.mybatis.module;

import com.github.pagehelper.PageInterceptor;
import com.github.pagehelper.QueryInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import com.wjy.atom.core.finder.PackageFinder;

import java.util.Arrays;
import java.util.Set;

public class MybatisModule extends MyBatisModule {

    private static final String PREFIX = "atom.db.";
    private String environmentId;
    private String mapperPkg;

    public MybatisModule(String environmentId, String mapperPkg) {
        this.environmentId = environmentId;
        this.mapperPkg = mapperPkg;
    }

    @Override
    protected void initialize() {
        environmentId(environmentId);
        bindDataSourceProviderType(MybatisDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);

        if (mapperPkg != null && !"".equals(mapperPkg)) {
            Set<Class<?>> mapperClasses = new PackageFinder()
                    .toPackage(mapperPkg)
                    .getTypesAnnotatedWith(Mapper.class);
            addMapperClasses(mapperClasses);
        }

//        addInterceptorClass(PageInterceptor.class);
        addInterceptorsClasses(Arrays.asList(PageInterceptor.class, QueryInterceptor.class));
    }
}
