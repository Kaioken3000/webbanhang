package com.mywebsite.webbanhang.login_register.successHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mywebsite.webbanhang.login_register.model.CustomOAuth2User;
import com.mywebsite.webbanhang.login_register.model.Provider;
import com.mywebsite.webbanhang.login_register.model.Role;
import com.mywebsite.webbanhang.login_register.model.User;
import com.mywebsite.webbanhang.login_register.repository.RoleRepository;
import com.mywebsite.webbanhang.login_register.repository.UserRepository;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

        String firstName = oauthUser.getFamilyName();
        String lastName = oauthUser.getGivenName();
        String email = oauthUser.getEmail();

        User userFind = userRepository.getUserByUsername(email);

        if(userFind==null){
            Role role = roleRepository.findById((long) 1).get();
            Set<Role> setRole = new HashSet<Role>();
            setRole.add(role);

            User user = new User();
            user.setEmail(email);
            user.setFirstname(firstName);
            user.setLastname(lastName);
            user.setProvider(Provider.GOOGLE);
            user.setRoles(setRole);

            userRepository.save(user);
        }

        response.sendRedirect("/");
    }
}
