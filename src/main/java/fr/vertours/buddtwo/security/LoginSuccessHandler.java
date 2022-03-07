package fr.vertours.buddtwo.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws
            ServletException, IOException {

        MyUserDetails myUD = (MyUserDetails) authentication.getPrincipal();
        String redirectURL = request.getContextPath();

        if (myUD.hasRole("ADMIN")) {
            redirectURL = "admin";
        } else {
            redirectURL = "home";
        }
        response.sendRedirect(redirectURL);

    }
}
