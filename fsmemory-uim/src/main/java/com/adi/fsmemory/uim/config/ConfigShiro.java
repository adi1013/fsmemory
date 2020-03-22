package com.adi.fsmemory.uim.config;

import com.adi.fsmemory.uim.auth.CustomizedModularRealmAuthenticator;
import com.adi.fsmemory.uim.auth.CustomizedRealmName;
import com.adi.fsmemory.uim.constant.RoleEnum;
import com.adi.fsmemory.uim.auth.realms.SysAdminRealm;
import com.adi.fsmemory.uim.auth.realms.SysAuthorizingRealm;
import com.adi.fsmemory.uim.auth.realms.SysUserRealm;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ConfigShiro {

    //配置校验规则
    @Bean("credentialsMatcher")
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(10);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    @Bean("sysUserRealm")
    @Autowired
    public SysUserRealm sysUserRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
        SysUserRealm sysUserRealm = new SysUserRealm(matcher);
        CustomizedRealmName customizedRealmName = new CustomizedRealmName();
        customizedRealmName.setRealmName(RoleEnum.USER);
        sysUserRealm.setCustomizedRealmName(customizedRealmName);
        return sysUserRealm;
    }

    @Bean("sysAdminRealm")
    @Autowired
    public SysAdminRealm sysAdminRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
        SysAdminRealm sysAdminRealm = new SysAdminRealm(matcher);
        CustomizedRealmName customizedRealmName = new CustomizedRealmName();
        customizedRealmName.setRealmName(RoleEnum.ADMIN);
        sysAdminRealm.setCustomizedRealmName(customizedRealmName);
        return sysAdminRealm;
    }



    //配置验证用户和管理员的realm
    @Bean
    @Autowired
    public Authenticator authenticator(SysUserRealm sysUserRealm, SysAdminRealm sysAdminRealm ) {
        CustomizedModularRealmAuthenticator authenticator = new CustomizedModularRealmAuthenticator();
        List<Realm> realms = new ArrayList<>();
        realms.add(sysUserRealm);
        realms.add(sysAdminRealm);
        authenticator.setRealms(realms);
        return authenticator;
    }

    @Bean
    public SysAuthorizingRealm sysAuthorizingRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
        SysAuthorizingRealm realm = new SysAuthorizingRealm(matcher);
        return realm;
    }

    //配置授权realm
    @Bean
    @Autowired
    public Authorizer authorizer(SysAuthorizingRealm sysAuthorizingRealm) {
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        List<Realm> realms = new ArrayList<>();
        realms.add(sysAuthorizingRealm);
        authorizer.setRealms(realms);
        return authorizer;
    }

    //配置shiro的心脏 securityManager
    @Bean("securityManager")
    @Autowired
    public SecurityManager securityManager(Authenticator authenticator,
                                           Authorizer authorizer,
                                           SessionManager sessionManager,
                                           SubjectDAO subjectDAO) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator(authenticator);
        securityManager.setAuthorizer(authorizer);
        securityManager.setSubjectDAO(subjectDAO);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    //session处理禁用
    @Bean
    public SessionManager sessionManager() {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }
    @Bean
    public DefaultSessionStorageEvaluator sessionStorageEvaluator() {
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }
    @Bean
    @Autowired
    public DefaultSubjectDAO subjectDAO(SessionStorageEvaluator sessionStorageEvaluator) {
        DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
        defaultSubjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        return defaultSubjectDAO;
    }

    //配置shiro过滤链
    @Bean
    @Autowired
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new HashMap<>();
        //暂时所有都不拦截
        map.put("/**","anon");
        //登出
//        map.put("/user/logout", "logout");
//        //对所有用户认证
//        map.put("/**", "authc");
//        map.put("/user/test","anon");
//        map.put("/admin/login","anon");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/user/login");
        //首页
//        shiroFilterFactoryBean.setSuccessUrl("/index");
        //错误页面，认证不通过跳转
//        shiroFilterFactoryBean.setUnauthorizedUrl("/sys/error");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    //加入注解的使用
    @Bean
    @Autowired
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authAdvisor = new AuthorizationAttributeSourceAdvisor();
        authAdvisor.setSecurityManager(securityManager);
        return authAdvisor;
    }
}
