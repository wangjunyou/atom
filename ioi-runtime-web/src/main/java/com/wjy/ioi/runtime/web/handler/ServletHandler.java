package com.wjy.ioi.runtime.web.handler;

import com.wjy.ioi.runtime.web.exception.ServletException;
import com.wjy.ioi.runtime.web.manager.ServletManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class ServletHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Logger LOG = LoggerFactory.getLogger(ServletHandler.class);

    private static final HttpDataFactory DATA_FACTORY = new DefaultHttpDataFactory(false);

    private final ServletManager servletManager;

    public ServletHandler(ServletManager servletManager) {
        this.servletManager = servletManager;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest currentRequest = (FullHttpRequest) msg;
            HttpMethod method = currentRequest.method();
            if (method == HttpMethod.GET || method == HttpMethod.DELETE) {
                QueryStringDecoder decoder = new QueryStringDecoder(currentRequest.uri());
                String path = decoder.path();
                Map<String, List<String>> params = decoder.parameters();

            } else if (method == HttpMethod.POST) {
                /*HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(DATA_FACTORY, currentRequest);
                List<InterfaceHttpData> httpDatas = decoder.getBodyHttpDatas();
                httpDatas.forEach(httpData -> {
                    MemoryAttribute attr = (MemoryAttribute) httpData;
//                    System.out.println();
                });*/
            } else {
                throw new ServletException("only contains GET, DELETE, POST :"+method.name());
            }
        }
    }
}
