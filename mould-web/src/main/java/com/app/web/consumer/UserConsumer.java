package com.app.web.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.app.api.model.User;
import com.app.api.provider.UserProvider;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class UserConsumer {
    @Reference(version = "1.0")
    private UserProvider userProvider;

    public Object signIn(String username, String password) {
        return userProvider.signIn(username, password);
    }

    public Object signUp(User user) {
        String password = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);

        if (log.isDebugEnabled())
            log.debug("用户注册接口-密码加密，加密后密码：{}", password);

        return userProvider.signUp(user);
    }

    public Object searchUser(String username) {
        return userProvider.searchUser(username);
    }
}
