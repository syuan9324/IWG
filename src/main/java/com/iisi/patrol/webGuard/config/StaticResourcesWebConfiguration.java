//package com.iisi.patrol.webGuard.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@Profile("prod")
//public class StaticResourcesWebConfiguration implements WebMvcConfigurer {
//
//    //
//    //在dev模式下透過webpack處理請求路徑，
//    //但在打包成jar檔之後，由於請求全部都是由spring boot處理
//    //路徑全部會404(或是unAuth
//    //加入此設定想嘗試解決vue history 模式的問題，但好像沒有用
//
//    protected static final String[] RESOURCE_LOCATIONS = new String[] {
//            "classpath:/static/",
//            "classpath:/static/content/",
//            "classpath:/static/i18n/",
//    };
//    protected static final String[] RESOURCE_PATHS = new String[] {
//            "/*.js",
//            "/*.css",
//            "/*.svg",
//            "/*.png",
//            "*.ico",
//            "/content/**",
//            "/i18n/*",
//    };
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        ResourceHandlerRegistration resourceHandlerRegistration = appendResourceHandler(registry);
//        initializeResourceHandler(resourceHandlerRegistration);
//    }
//
//    protected ResourceHandlerRegistration appendResourceHandler(ResourceHandlerRegistry registry) {
//        return registry.addResourceHandler(RESOURCE_PATHS);
//    }
//
//    protected void initializeResourceHandler(ResourceHandlerRegistration resourceHandlerRegistration) {
//        resourceHandlerRegistration.addResourceLocations(RESOURCE_LOCATIONS);
//    }
//
//}
