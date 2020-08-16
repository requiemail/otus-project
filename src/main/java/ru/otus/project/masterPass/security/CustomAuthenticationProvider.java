package ru.otus.project.masterPass.security;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.otus.project.masterPass.service.impl.AesEncryptDecryptService;


@Component
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String password = authentication.getCredentials().toString();
        try {
            createBean(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.authenticate(authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private void createBean(String masterPass) throws Exception {
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();

        BeanDefinitionBuilder b =
                BeanDefinitionBuilder.rootBeanDefinition(AesEncryptDecryptService.class)
                        .addConstructorArgValue(masterPass+"123456789012345678901234567"); // Hardcode до 32 байт длины, masterPass = ADMIN

        beanFactory.registerBeanDefinition("encryptDecryptService", b.getBeanDefinition());

       AesEncryptDecryptService bean = beanFactory.getBean(AesEncryptDecryptService.class);

    }
}