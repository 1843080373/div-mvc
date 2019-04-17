package org.spring.springboot.webflux.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import reactor.core.publisher.Mono;

@Controller
public class CityWebFluxController {

	
	@GetMapping("/client")
	public Mono<String> websocketClient(final Model model) {
		String path = "websocket-client";
		return Mono.create(monoSink -> monoSink.success(path));
	}


}