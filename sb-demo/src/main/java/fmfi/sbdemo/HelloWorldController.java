package fmfi.sbdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HelloWorldController {

	private final HelloConfigProperties configProperties;

	@GetMapping("/api/hello")
	public String sayHello(
		@RequestParam(name = "subject", defaultValue = "World") String subject
	) {
		return configProperties.greeting() + ", " + subject;
	}
}
