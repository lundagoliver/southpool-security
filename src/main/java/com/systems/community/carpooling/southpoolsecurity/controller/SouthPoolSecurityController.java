package com.systems.community.carpooling.southpoolsecurity.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.systems.community.carpooling.southpoolsecurity.persistence.service.PersistenceService;
import com.systems.community.carpooling.southpoolsecurity.props.SouthPoolSettings;
import com.systems.community.carpooling.southpoolsecurity.service.SouthPoolService;

@Component
public class SouthPoolSecurityController extends TelegramLongPollingBot {

	private static final Log log = LogFactory.getLog(SouthPoolSecurityController.class);
	private SouthPoolSettings southPoolSettings;

	public SouthPoolSecurityController(SouthPoolSettings southPoolSettings) {
		super();
		this.southPoolSettings = southPoolSettings;
	}

	@Override
	public void onUpdateReceived(Update update) {
		
		long chatId = update.getMessage().getChatId();
		Integer messageId = update.getMessage().getMessageId();
		
		
		log.info("chatId : " + chatId);
		log.info("messageId : " + messageId);
		log.info("messageId : " +  update.getMessage().getText());
		
		if (update.hasMessage() && update.getMessage().hasText()) {
			
			DeleteMessage deleteMessage = new DeleteMessage();
			deleteMessage.setChatId(chatId);
			deleteMessage.setMessageId(messageId);
			sendMessage(deleteMessage);	
		}
		
	}

	@Override
	public String getBotUsername() {
		return southPoolSettings.getTelegramBotUsername();
	}

	@Override
	public String getBotToken() {
		return southPoolSettings.getTelegramBotToken();
	}

	protected void sendMessage(SendMessage message) {
		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	protected void sendMessage(DeleteMessage message) {
		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
