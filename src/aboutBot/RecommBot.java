package aboutBot;

import org.json.JSONException;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import process.item.Feedback;
import process.item.RestaurantRecommendation;
import process.item.ActivityRecommendation;
import process.item.DailyQuotes;
import process.user.UserClient;
import process.user.UserData;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class RecommBot extends TelegramLongPollingBot {

	public static final String BOT_USERNAME = "BotUserName";
	public static final String BOT_TOKEN = "BotToken";
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


	@Override
	public void onUpdateReceived(Update update) {


		// We check if the update has a message and the message has text
		if (update.hasMessage() && update.getMessage().hasText()) {
			long chat_id = update.getMessage().getChatId();


			// Set variables
			String message_text = update.getMessage().getText();
			String user[]=message_text.split(",");


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
				//String recommend=null;
				String city = message_text;
				String item=null;

				String answer = "Recommendations for you";
				
				String answerRating="Please choose the item you liked the most and give a rating on the scale of -1.0 to 1.0 in the format: itemId,rating";
				try {


					userId=UserData.getUserID(firstname, lastname, email, birthyear); 

					if (type.equalsIgnoreCase("activity")) {
						list1=ActivityRecommendation.listItems(userId,city);

					} else {
						list1=RestaurantRecommendation.listItems(userId, city);

					}

					SendMessage message = new SendMessage() // Create a message object
							.setChatId(chat_id)
							.setText(answer);

					if (list1.size()==1) {
						if (type.equalsIgnoreCase("activity")) {
							String activityCity = "Please select another city! Example: Toscana, Lipomo, Rome";
							SendMessage messageActivityCity = new SendMessage() // Create a message object
									.setChatId(chat_id)
									.setText(activityCity);

							execute(messageActivityCity);
						}
						else{
							String restaurantCity="Please select another city! Example: Paris, Mysore, Roma";
							SendMessage messageRestaurantCity = new SendMessage() // Create a message object
									.setChatId(chat_id)
									.setText(restaurantCity);

							execute(messageRestaurantCity);

						}

					}
					else {

						execute(message);

						String[] items=list1.toString().split("},");

						
						for (int i=0;i<items.length;i++) { 

							messageList=items[i].toString()+"}";
							SendMessage message1 = new SendMessage() // Create a message object
									.setChatId(chat_id).setText(messageList);
							execute(message1);

						}


						SendMessage messageRating = new SendMessage() // Create a message object
								.setChatId(chat_id)
								.setText(answerRating);

						execute(messageRating);

					}

				} catch (TelegramApiException e) {
					e.printStackTrace();
				}



			} else if (user.length==2) {

				String answer=null;
				String rating =null;
				String itemIdPrefer=null;

				String[] itemsRating=message_text.split(",");
				itemIdPrefer=itemsRating[0];
				rating=itemsRating[1];

				answer = Feedback.saveUserRating(userId, itemIdPrefer, rating);

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
					rowInline.add(new InlineKeyboardButton().setText("Go to a Restaurant").setCallbackData("cityRestaurantPreference"));
					rowInline.add(new InlineKeyboardButton().setText("No, thanks!").setCallbackData("stop"));
					
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


			else if(user.length==4) {
				firstname = user[0];
				lastname = user[1];
				email=user[2];
				birthyear=user[3];

				String result=null;
				try {
					result = UserClient.registerUser(firstname,lastname,email,birthyear);


					SendMessage message = new SendMessage() // Create a message object
							.setChatId(chat_id)
							.setText(result);

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

			String quote="";


			if (call_data.equals("cityActivityPreference")) {

				type="activity";

				quote=DailyQuotes.getRandomQuotes(type);


				SendMessage messageQuote = new SendMessage() // Create a message object
						.setChatId(chatId).setText(quote);


				String answer = "Which city would you prefer for an activity?";

				SendMessage messageCity = new SendMessage() // Create a message object
						.setChatId(chatId)
						.setText(answer);
				try {	
					execute(messageQuote);

					execute(messageCity);
				} 

				catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}else if (call_data.equals("cityRestaurantPreference")) {

				type = "restaurant";

				quote=DailyQuotes.getRandomQuotes(type);


				SendMessage messageQuote = new SendMessage() // Create a message object
						.setChatId(chatId).setText(quote);

				String answer = "Which city would you prefer for restaurant?";

				SendMessage messageCity = new SendMessage() // Create a message object
						.setChatId(chatId)
						.setText(answer);
				try {
					execute(messageQuote);
					execute(messageCity);
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
