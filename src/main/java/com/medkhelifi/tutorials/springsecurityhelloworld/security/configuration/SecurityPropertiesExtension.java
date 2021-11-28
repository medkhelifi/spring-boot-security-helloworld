package com.medkhelifi.tutorials.springsecurityhelloworld.security.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Add security com.medkhelifi.configuration properties files, the properties files is to be defined.
 */
@Data
@Component
@ConfigurationProperties(prefix = "security")
public class SecurityPropertiesExtension {

    private Roles roles = new Roles();

    private Jwt jwt = new Jwt();



    @Getter
    @Setter
    public static class Roles {
        private Map<String, List<String>> hierarchy = new LinkedHashMap<String, List<String>>();
    }

    @Getter
    @Setter
    public static class Jwt {
        private String header;
        private String headerPrefix;
        private String secret;
        private Long expiration;
    }

}

