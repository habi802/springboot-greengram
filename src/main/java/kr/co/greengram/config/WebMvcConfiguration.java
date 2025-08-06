package kr.co.greengram.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final String uploadPath;

    public WebMvcConfiguration(@Value("${constants.file.upload-directory}") String uploadPath) {
        this.uploadPath = uploadPath;
        log.info("upload path: {}", uploadPath);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pic/**")
                .addResourceLocations("file:" + uploadPath);
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                //.allowedOrigins("*")
//                .allowedOriginPatterns("*")
//                .allowedMethods("*")
//                .allowCredentials(true);
//    }
}
