package com.systems.community.carpooling.southpoolsecurity.service;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import com.systems.community.carpooling.southpoolsecurity.net.rest.RESTHttpClient;
import com.systems.community.carpooling.southpoolsecurity.props.SouthPoolSettings;

@Service
public class SouthPoolService {
	
	private static final Log log = LogFactory.getLog(SouthPoolService.class);

	private RESTHttpClient restHttpClient;
	
	public SouthPoolService(RESTHttpClient restHttpClient) {
		super();
		this.restHttpClient = restHttpClient;
	}
	
	@Async
	public void sendMessage(String text, String chatId, SouthPoolSettings southPoolSettings) throws UnsupportedEncodingException {
		String url = MessageFormat.format(southPoolSettings.getTelegramApiUrl(), southPoolSettings.getGroupchatSenderBotToken());
		String result = null;
		try {
			String sendRequest = UriComponentsBuilder.fromUriString(url).queryParam("chat_id", chatId)
					.queryParam("text",text.replace("&", "and")).build().toUriString();
			log.info(sendRequest);
			result = restHttpClient.getDefaultRestTemplate().exchange(sendRequest, HttpMethod.GET, null, String.class).getBody();
		} catch (RestClientException e) {
			log.error(e);
		}
		log.info(result);
	}
}
