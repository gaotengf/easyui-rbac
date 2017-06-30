package cn.gson.crm;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.gson.crm.interceptor.AuthInterceptor;
import cn.gson.crm.interceptor.LoginInterceptor;

@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

	/**
	 * 添加拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		// 注册登录拦截器
		InterceptorRegistration loginReg = registry.addInterceptor(new LoginInterceptor());
		loginReg.addPathPatterns("/**");
		loginReg.excludePathPatterns("/login","/error");

		// 注册权限拦截器
		InterceptorRegistration authReg = registry.addInterceptor(new AuthInterceptor());
		authReg.addPathPatterns("/**");
		// 不受权限控制的请求
		authReg.excludePathPatterns("/", "/login","/error");

		super.addInterceptors(registry);
	}

}
