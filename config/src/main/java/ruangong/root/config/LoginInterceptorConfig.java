package ruangong.root.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ruangong.root.interceptor.LoginInterceptor;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoginInterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        HandlerInterceptor interceptor = new LoginInterceptor();

        List<String> patterns =new ArrayList<>();
        patterns.add("/users/register");
        patterns.add("/users/login");
        patterns.add("/users/logout");

        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(patterns);
    }
}
