package ru.otus.project.masterPass.security.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.otus.project.masterPass.service.EncryptDecryptService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@DependsOn("aesEncryptDecryptService")
@Component
public class GetMasterPassFilter extends OncePerRequestFilter {

    private final EncryptDecryptService encryptDecryptService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String password = request.getParameter("password");
        if (password != null ) {setupBean(request.getParameter("password"));}
        filterChain.doFilter(request, response);
    }

    private void setupBean(String masterPass) {
        encryptDecryptService.setKey(masterPass);
    }

}
