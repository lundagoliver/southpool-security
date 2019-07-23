package com.systems.community.carpooling.southpoolsecurity.controller;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.systems.community.carpooling.southpoolsecurity.entities.SouthPoolMemberHomeToWork;
import com.systems.community.carpooling.southpoolsecurity.entities.SouthPoolMemberWorkToHome;
import com.systems.community.carpooling.southpoolsecurity.persistence.service.PersistenceService;
import com.systems.community.carpooling.southpoolsecurity.props.SouthPoolSettings;
import com.systems.community.carpooling.southpoolsecurity.service.SouthPoolService;

@Component
public class SouthPoolSecurityController extends TelegramLongPollingBot {

	private static final Log log = LogFactory.getLog(SouthPoolSecurityController.class);
	private SouthPoolSettings southPoolSettings;
	
	private PersistenceService persistenceService;	
	private SouthPoolService southPoolService;

	public SouthPoolSecurityController(SouthPoolSettings southPoolSettings, PersistenceService persistenceService,
			SouthPoolService southPoolService) {
		super();
		this.southPoolSettings = southPoolSettings;
		this.persistenceService = persistenceService;
		this.southPoolService = southPoolService;
	}

	@Override
	public void onUpdateReceived(Update update) {
		
		long chatId = update.getMessage().getChatId();
		Integer messageId = update.getMessage().getMessageId();
		String username = update.getMessage().getChat().getUserName();
		String name = update.getMessage().getChat().getFirstName();
		String lastName = update.getMessage().getChat().getLastName();
		
		log.info("chatId : " + chatId);
		log.info("messageId : " + messageId);
		log.info("text : " +  update.getMessage().getText());
		
		if (update.hasMessage() && update.getMessage().hasText()) {
			
			//Check if username is set already. If username is not yet set, show some information how to do it.
			if (username == null) {
				if (chatId == -335962876) {
					log.info("Skip!, this is a bot message. Message id : " + messageId);
				}
				else {
					try {
						southPoolService.sendMessageToAdmin("This user has not yet set his/her username : " + name + " " + lastName ,southPoolSettings.getGroupChatIdAdmins(), southPoolSettings);
					} catch (UnsupportedEncodingException e) {
						log.error("{}", e);
					}	
				}
			}
			else {
				
				SouthPoolMemberHomeToWork southPoolMemberHomeToWork = persistenceService.getMember(username, SouthPoolMemberHomeToWork.class);
				SouthPoolMemberWorkToHome southPoolMemberWorkToHome = persistenceService.getMember(username, SouthPoolMemberWorkToHome.class);
				
				if (southPoolMemberHomeToWork == null || southPoolMemberWorkToHome == null) {
					try {
						southPoolService.sendMessageToAdmin("This user is not registered in Southpool : @" + username + " - " + name + " " + lastName ,southPoolSettings.getGroupChatIdAdmins(), southPoolSettings);
					} catch (UnsupportedEncodingException e) {
						log.error("{}", e);
					}
				}
			}
			
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
