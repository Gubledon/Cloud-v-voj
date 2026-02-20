package fmfi.sbdemo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties("app.hello")
public record HelloConfigProperties(
    @DefaultValue("Hello") String greeting
) { }
