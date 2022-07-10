package com.wjy.ioi.runtime.web.manager;

import com.wjy.ioi.runtime.web.http.router.RouterGraph;
import com.wjy.ioi.runtime.web.http.router.RouterMapping;
import com.wjy.ioi.runtime.web.http.router.RouterParameter;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ServerletManagerTest {

    @Test
    public void forPackage() {
        ServerletManager serverletManager = new ServerletManager();
        serverletManager.forPackages("com.wjy.ioi.runtime.web.http.*");
        RouterGraph routerGraph = serverletManager.router("/login/delete");
        List<RouterMapping> routerMappings = routerGraph.getRouterMappings();
        RouterMapping mapping = routerGraph.getMapping("/login/delete");
        System.out.println(mapping.getMethod().getName());
        RouterParameter[] parameters = mapping.getParameters();
        for (RouterParameter parameter : parameters) {
            System.out.println(parameter.getParamName()+"=="+parameter.getIndex()+"=="+parameter.getType().getName());
        }
    }
}
