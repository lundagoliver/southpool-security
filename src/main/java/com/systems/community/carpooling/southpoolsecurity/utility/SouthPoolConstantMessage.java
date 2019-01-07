package com.systems.community.carpooling.southpoolsecurity.utility;

public class SouthPoolConstantMessage {

	private SouthPoolConstantMessage(){}
	
	public static String securityMessage(String username) {
		StringBuilder messageBuilder = new StringBuilder();
		messageBuilder.append("Hi! ").append(username).append("!\n");
		messageBuilder.append("The system detected that you are not a registered member of SOUTHPOOL telegram group community.").append("\n");
		messageBuilder.append("SOUTHPOOL is implementing a policy, that all members must register their information.").append("\n");
		messageBuilder.append("To register just click or tap >> @southpoolservicebot and it will bring you to its chat window.\n");
		messageBuilder.append("It is required that ALL members of SOUTHPOOL must use @southpoolservicebot to post their request in SOUTHPOOL telegram group.\n");
		messageBuilder.append("If you have questions about the bot.\n");
		messageBuilder.append("Please contact: @OliverDela_cruzLundag\n");
		messageBuilder.append("Thank you for your cooperation.\n");
		return messageBuilder.toString();
	}

}
