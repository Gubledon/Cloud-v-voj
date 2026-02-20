package fmfi.sbdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class SbDemoApplicationTests {

	@Autowired
	private RestTestClient restClient;

	@Test
	void givenNoSubject_whenHelloCalled_thenShouldReturnHelloWorld() {
		this.restClient.get().uri("/api/hello")
			.exchangeSuccessfully()
			.expectBody(String.class).isEqualTo("Hello, World");
	}

	@Test
	void givenSubjectTest_whenHelloCalled_thenShouldReturnHelloTest() {
		this.restClient.get().uri("/api/hello?subject=test")
			.exchangeSuccessfully()
			.expectBody(String.class).isEqualTo("Hello, test");
	}
}
