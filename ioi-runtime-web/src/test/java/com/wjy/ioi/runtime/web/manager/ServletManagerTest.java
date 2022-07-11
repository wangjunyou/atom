package com.wjy.ioi.runtime.web.manager;

import com.wjy.ioi.runtime.web.http.router.RouterGraph;
import com.wjy.ioi.runtime.web.http.router.RouterMapping;
import com.wjy.ioi.runtime.web.http.router.RouterParameter;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ServletManagerTest {

    @Test
    public void forPackage() {
        ServletManager servletManager = new ServletManager();
        servletManager.forPackages("com.wjy.ioi.runtime.web.http.*");
        RouterGraph routerGraph = servletManager.router("/login/delete");
        List<RouterMapping> routerMappings = routerGraph.getRouterMappings();
        RouterMapping mapping = routerGraph.getMapping("/delete");
        System.out.println(mapping.getMethod().getName());
        RouterParameter[] parameters = mapping.getParameters();
        for (RouterParameter parameter : parameters) {
            System.out.println(parameter.getParamName()+"=="+parameter.getIndex()+"=="+parameter.getType().getName());
        }
    }
}
