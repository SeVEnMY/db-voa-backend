package com.nqz.voa.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableSpringHttpSession
public class SessionConfig {
  @Bean
  public CookieSerializer cookieSerializer() {
    DefaultCookieSerializer serializer = new DefaultCookieSerializer();
    serializer.setCookieName("JSESSIONID");
    // match localhost、127.0.0.1 ...
    serializer.setDomainNamePattern("^.+?\\\\.(\\\\w+\\\\.[a-z]+)$");
    // root for cookie
    serializer.setCookiePath("/");
    // set if only server could change it
    serializer.setUseHttpOnlyCookie(false);
    // set the life time for cookie
    serializer.setCookieMaxAge(60*60*2);
    return serializer;
  }
  @Bean
  public MapSessionRepository sessionRepository() {
    return new MapSessionRepository(new ConcurrentHashMap<>());
  }
}
