package aboutBot;

import org.json.JSONException;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import businesslogic.item.savePreferredItems;
import businesslogic.item.saveRatings;
import businesslogic.item.searchItems;
import businesslogic.user.checkExistingUser;
import randomQuotes.randomCategoryQuotes;

import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.File;

public class RecommBot extends TelegramLongPollingBot {

	public static final String BOT_USERNAME = "RecommBot";
	public static final String BOT_TOKEN = "526078856:AAG4GcA22xB8TFgUkYoTiKxiA7njjDK_iJg";
	public String type="";
	public String logUserId="";
	public String city="";
	public String recommendString = "";
	public String firstname="";
	public String lastname="";
	public String email="";
	public String birthyear="";
	public String userId ="";
	public String itemId1 ="";
	public String itemId2 ="";
	public String itemId3 ="";
	public String category="";

	@Override
	public void onUpdateReceived(Update update) {


		// We check if the update has a message and the message has text
		if (update.hasMessage() && update.getMessage().hasText()) {
			long chat_id = update.getMessage().getChatId();


			// Set variables
			String message_text = update.getMessage().getText();
			String user[]=message_text.split(",");
			List<String> list=new LinkedList<>();

			if (message_text.equals("BotUserName")) {
				message_text = getBotUsername();

				SendMessage message = new SendMessage() // Create a message object
						.setChatId(chat_id)
						.setText(message_text);

				try {
					execute(message);

				} catch (TelegramApiException e) {
					e.printStackTrace();
				}



			}else if (message_text.equals("/start")) {
				
				message_text="Please type your details in the format: firstname,lastname,email,birthyear";
				SendMessage message = new SendMessage() // Create a message object
						.setChatId(chat_id)
						.setText(message_text);
				try {
					
					execute(message);
				} catch (TelegramApiException e) {

					e.printStackTrace();
				}
			}			
			else if (message_text.equals("/stop")) {

				
				message_text="Heartfelt thankyou for using our services :-)";
				SendMessage message = new SendMessage() // Create a message object
						.setChatId(chat_id)
						.setText(message_text);

				try {
					
					execute(message);
				} catch (TelegramApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
			else if (message_text.equals("BotToken")) {
				message_text = getBotToken();

				SendMessage message = new SendMessage() // Create a message object
						.setChatId(chat_id)
						.setText(message_text);

				try {
					execute(message);

				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			} 

			else if (user.length==1) {
				List<String> list1=new LinkedList<>();
				String messageList=null;

				if(message_text.contains(".")){
					//TODO save ratings


					String answer=null;

					answer = saveRatings.saveUserRating(userId, itemId1, message_text);
					answer = saveRatings.saveUserRating(userId, itemId2, message_text);
					answer = saveRatings.saveUserRating(userId, itemId3, message_text);

					SendMessage message = new SendMessage() // Create a message object
							.setChatId(chat_id)
							.setText(answer);


					try {
						execute(message);
					} catch (TelegramApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if (type.equalsIgnoreCase("activity")) {

						SendMessage messageNext = new SendMessage() // Create a message object object
								.setChatId(chat_id)
								.setText("Would like to eat something?");

						InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
						List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
						List<InlineKeyboardButton> rowInline = new ArrayList<>();
						rowInline.add(new InlineKeyboardButton().setText("No, thanks!").setCallbackData("stop"));
						rowInline.add(new InlineKeyboardButton().setText("Go to a Restaurant").setCallbackData("cityRestaurantPreference"));
						rowsInline.add(rowInline); // Set the keyboard to the markup

						markupInline.setKeyboard(rowsInline);// Add it to the message
						messageNext.setReplyMarkup(markupInline);


						try {
							execute(messageNext); // Sending our message object to user
						} catch (TelegramApiException e) {
							e.printStackTrace();
						}


					}else  {
						
					SendMessage messageNext = new SendMessage() // Create a message object object
								.setChatId(chat_id)
								.setText("Please click finish button...");

						InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
						List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
						List<InlineKeyboardButton> rowInline = new ArrayList<>();
						rowInline.add(new InlineKeyboardButton().setText("Finish!").setCallbackData("stop"));
						
						rowsInline.add(rowInline); // Set the keyboard to the markup

						markupInline.setKeyboard(rowsInline);// Add it to the message
						messageNext.setReplyMarkup(markupInline);


						try {
							execute(messageNext); // Sending our message object to user
						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
						
						
					}
					
				}

				else {

					String city = message_text;					

					String answer = "Our recommendations for you";
					String answer1="Please select top 3 preferences. To select, please reply with the itemId's of your preferences in this format: itemId1,itemId2,itemId3";

					try {


						userId=checkExistingUser.getUserID(firstname, lastname, email, birthyear); 


						list1=searchItems.listItems(userId,city,type);

						SendMessage message = new SendMessage() // Create a message object
								.setChatId(chat_id)
								.setText(answer);


						execute(message);
						System.out.println(list1.size());
						
						for (int i=0;i<list1.size()-9;i=i+10) { 
							
							messageList=list1.subList(i+1, i+10).toString();
							System.out.println(messageList);
							SendMessage message1 = new SendMessage() // Create a message object
									.setChatId(chat_id).setText(messageList);
							execute(message1);

						}


						SendMessage message2 = new SendMessage() // Create a message object
								.setChatId(chat_id)
								.setText(answer1);


						execute(message2);


					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
				}




			}else if(user.length==3) {

				
				String[] items=message_text.split(",");
				itemId1=items[0];
				itemId2=items[1];
				itemId3=items[2];

				String output=null;
				String rating="Did you like our recommendations? Please rate us on the scale of -1.0 to 1.0";

				output=savePreferredItems.savePreferences(userId, itemId1);
				output =savePreferredItems.savePreferences(userId, itemId2);
				output =savePreferredItems.savePreferences(userId, itemId3);


				SendMessage message = new SendMessage() // Create a message object
						.setChatId(chat_id)
						.setText(output);

				SendMessage messageRating = new SendMessage() // Create a message object
						.setChatId(chat_id)
						.setText(rating);


				try {
					execute(message);
					execute(messageRating);

				} catch (TelegramApiException e) {
					e.printStackTrace();
				}

	




			}

			else if(user.length==4) {
				firstname = user[0];
				lastname = user[1];
				email=user[2];
				birthyear=user[3];

				String result;
				try {
					result = checkExistingUser.checkUser(firstname,lastname,email,birthyear);
					message_text=result;

					SendMessage message = new SendMessage() // Create a message object
							.setChatId(chat_id)
							.setText(message_text);

					try {
						execute(message);

					} catch (TelegramApiException e) {
						e.printStackTrace();
					}



					SendMessage messageNext = new SendMessage() // Create a message object object
							.setChatId(chat_id)
							.setText("What would you prefer? Pick one!");

					InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
					List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
					List<InlineKeyboardButton> rowInline = new ArrayList<>();
					rowInline.add(new InlineKeyboardButton().setText("Go for an activity").setCallbackData("cityActivityPreference"));
					rowInline.add(new InlineKeyboardButton().setText("Go to a Restaurant").setCallbackData("cityRestaurantPreference"));
					rowsInline.add(rowInline); // Set the keyboard to the markup

					markupInline.setKeyboard(rowsInline);// Add it to the message
					messageNext.setReplyMarkup(markupInline);


					try {
						execute(messageNext); // Sending our message object to user
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}



				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

			} 

		}

		else if (update.hasCallbackQuery()) {
			// Set variables
			String call_data = update.getCallbackQuery().getData();
			long chatId = update.getCallbackQuery().getMessage().getChatId();
			List<String> list1=new LinkedList<>();

			if (call_data.equals("cityActivityPreference")) {

				type="activity";
				category="sports";
				String messageList=null;
				String result1=null;
				String result2=null;

				
				try {

					list1=randomCategoryQuotes.getRandomQuotes(category);
						
						
						messageList=list1.subList(7, 8).toString()+list1.subList(9, 10).toString();
						String[] quotes=messageList.split("\"");

						result1=quotes[3].toString();
						result2=quotes[7].toString();
						messageList=result1+" - "+result2;
											
						SendMessage message1 = new SendMessage() // Create a message object
								.setChatId(chatId).setText(messageList);
						execute(message1);

				
				String answer = "Which city would you prefer?";

				SendMessage message2 = new SendMessage() // Create a message object
						.setChatId(chatId)
						.setText(answer);
				
					execute(message2);
				} 

				catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}else if (call_data.equals("cityRestaurantPreference")) {
				type = "restaurant";


				String answer = "Which city would you prefer?";

				SendMessage message1 = new SendMessage() // Create a message object
						.setChatId(chatId)
						.setText(answer);
				try {
					execute(message1);
				}
				catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}else if (call_data.equals("stop")) {
				
					
				String message_text="Heartfelt thankyou for using our services :-)";
				SendMessage message = new SendMessage() // Create a message object
						.setChatId(chatId)
						.setText(message_text);

				try {
					
					execute(message);
				} catch (TelegramApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}


		}

	}
	private Integer toIntExact(long message_id) {
		int id = (int) (long) message_id;
		return id;
	}

	@Override
	public String getBotUsername() {
		// Return bot username
		// If bot username is @RecommBot, it must return 'RecommBot'
		return BOT_USERNAME;
	}

	@Override
	public String getBotToken() {
		// Return bot token from BotFather
		return BOT_TOKEN;
	}



}
