package com.app.web.internal;

import com.alibaba.dubbo.config.annotation.Reference;
import com.app.api.model.User;
import com.app.api.provider.UserProvider;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService implements UserDetailsService {
    @Reference(version = "1.0")
    private UserProvider userProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Object obj = userProvider.searchUser(username);
        if (obj == null)
            throw new UsernameNotFoundException("用户不存在");

        // 获取数据库中的用户名和密码
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        User user = (User) obj;
        String dbName = user.getName();
        String dbPassword = user.getPassword();
//        dbPassword = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(dbPassword);

        if (log.isDebugEnabled())
            log.debug("[JWT] 查询数据库中的用户信息，用户名：{}，密码：{}", dbName, dbPassword);

        return new org.springframework.security.core.userdetails.User(dbName, dbPassword, Lists.newArrayList(authority));
    }
}