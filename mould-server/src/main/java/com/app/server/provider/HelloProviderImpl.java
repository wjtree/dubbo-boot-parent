package com.app.server.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.app.api.exception.ApiException;
import com.app.api.internal.ApiCode;
import com.app.api.provider.HelloProvider;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

@Log4j2
@Service(version = "1.0")
public class HelloProviderImpl implements HelloProvider {
    @Override
    public String sayHello(String name) throws Exception {
        if (StringUtils.equalsIgnoreCase(name, "ddd"))
            throw new ApiException(ApiCode.UNAUTHORIZED);
        else if (StringUtils.equalsIgnoreCase(name, "eee"))
            throw new RuntimeException("运行时异常");
        else if (StringUtils.equalsIgnoreCase(name, "fff"))
            throw new Exception("待检查异常，需捕获处理");

        if (log.isInfoEnabled())
            log.info("调用 sayHello 接口参数：{}", name);
        return "Hello " + name + " !";
    }
}