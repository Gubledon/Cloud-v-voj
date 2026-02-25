package fmfi.sbdemo.adapter.integration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@Configuration
@lombok.RequiredArgsConstructor
public class IntegrationAdapterConfiguration {

    private final IntegrationFeesConfigProperties feesConfigProperties;

    @Bean
    RestClient feesRestClient() {
        return RestClient.builder()
                .baseUrl(feesConfigProperties.baseUri())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + feesConfigProperties.encodedBasicAuth())
                .build();
    }

    @ConfigurationProperties("app.integration.fees")
    public record IntegrationFeesConfigProperties(
            String baseUri,
            String username,
            String password
    ) {
        String encodedBasicAuth() {
            return HttpHeaders.encodeBasicAuth(username, password, null);
        }
    }
}
