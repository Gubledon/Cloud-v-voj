package fmfi.sbdemo.adapter.integration;

import org.mockserver.integration.ClientAndServer;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.mock.OpenAPIExpectation.openAPIExpectation;

@org.springframework.stereotype.Component
@org.springframework.context.annotation.Profile("mock")
@lombok.RequiredArgsConstructor
public class IntegrationMockServer {

    private final ClientAndServer mockServer = startClientAndServer(1080);

    @jakarta.annotation.PostConstruct
    public void startServer() {
        mockServer.upsert(openAPIExpectation("fee-service_v2.yaml"));
    }

    @jakarta.annotation.PreDestroy
    public void stopServer() {
        mockServer.stop();
    }
}
