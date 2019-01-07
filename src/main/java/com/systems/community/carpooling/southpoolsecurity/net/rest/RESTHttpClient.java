package com.systems.community.carpooling.southpoolsecurity.net.rest;

import org.springframework.web.client.RestTemplate;

public interface RESTHttpClient {

	public RestTemplate getDefaultRestTemplate();
}
