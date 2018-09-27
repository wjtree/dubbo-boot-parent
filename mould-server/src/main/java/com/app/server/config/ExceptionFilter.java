package com.app.server.config;

import com.alibaba.dubbo.rpc.*;

/**
 * @Description
 * @author: xiaozhonghseng
 * @create: 2018-09-19 16:24
 **/
public class ExceptionFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        return null;
    }
}
