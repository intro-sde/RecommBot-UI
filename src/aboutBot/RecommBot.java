package aboutBot;

import org.json.JSONException;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import businesslogic.item.searchItems;
import businesslogic.user.checkExistingUser;
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


	@Override
	public void onUpdateReceived(Update update) {


		// We check if the update has a message and the message has text
		if (update.hasMessage() && update.getMessage().hasText()) {
			long chat_id = update.getMessage().getChatId();
			// Set variables
			String message_text = update.getMessage().getText();

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
				//String caption = 
				SendPhoto msg = new SendPhoto().setNewPhoto(getPhoto()).setChatId(chat_id);
				//		.setCaption(caption);

				try {
					message_text="Let's get started :-)";
					sendPhoto(msg);
				} catch (TelegramApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
			else if (message_text.equals("/stop")) {
				//String caption = 
				SendPhoto msg = new SendPhoto().setNewPhoto(getLastPhoto()).setChatId(chat_id);		//		.setCaption(caption);

				try {
					message_text="Heartfelt thankyou for using our services :-)";
					sendPhoto(msg);
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
			} else if (message_text.equals("")) {



			}


			String user[]=message_text.split(",");
			List<String> list=new LinkedList<>();
			if(user.length==4) {
				String firstname = user[0];
				String lastname = user[1];
				String email=user[2];
				String birthyear=user[3];

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

			else if (user.length==6) {



				SendMessage message = new SendMessage() // Create a message object
						.setChatId(chat_id)
						.setText(message_text);

				try {
					execute(message);

				} catch (TelegramApiException e) {
					e.printStackTrace();
				}

				//TODO display recommendations of similar items from recombee database
			}

			else if (user.length==3){
				/*
				list=searchItems.listItems();
				for (int i=0;i<list.size();i++) {
					message_text=list.get(i).toString();
					SendMessage message = new SendMessage() // Create a message object
							.setChatId(chat_id).setText(message_text);
					try {
						execute(message);
					} catch (TelegramApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				//message_text="List of all registered users has been displayed above!";
			}
				 */


			}

		}
			else if (update.hasCallbackQuery()) {
				// Set variables
				String call_data = update.getCallbackQuery().getData();
				long message_id = update.getCallbackQuery().getMessage().getMessageId();
				long chatId = update.getCallbackQuery().getMessage().getChatId();
				List<String> list1=new LinkedList<>();

				if (call_data.equals("cityActivityPreference")) {

					/*				

				SendMessage message = new SendMessage() // Create a message object object
						.setChatId(chatId)
						.setText("Which city would you prefer?");
				try {
					execute(message);

				} catch (TelegramApiException e) {
					e.printStackTrace();
				}

				// TODO: change activity cities and callbackData 
				InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
				List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
				List<InlineKeyboardButton> rowInline = new ArrayList<>(); 
				rowInline.add(new InlineKeyboardButton().setText("Brussels").setCallbackData("brusselsRestaurant"));
				rowInline.add(new InlineKeyboardButton().setText("Milan").setCallbackData("milanRestaurant"));
				rowInline.add(new InlineKeyboardButton().setText("Rome").setCallbackData("romeRestaurant"));
				rowInline.add(new InlineKeyboardButton().setText("Mysore").setCallbackData("mysoreRestaurant"));
				rowInline.add(new InlineKeyboardButton().setText("Paris").setCallbackData("parisRestaurant"));
				rowInline.add(new InlineKeyboardButton().setText("Anywhere").setCallbackData("anywhereRestaurant"));
				rowsInline.add(rowInline); // Set the keyboard to the markup

				markupInline.setKeyboard(rowsInline);// Add it to the message

				SendMessage messageNext = new SendMessage() // Create a message object object
						.setChatId(chatId)
						.setText("What would you prefer? Pick one!");

				messageNext.setReplyMarkup(markupInline);



				try {
					execute(messageNext); // Sending our message object to user
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}


					 */	
					String answer = "Please select top 3 activities of your choice";

					SendMessage message1 = new SendMessage() // Create a message object
							.setChatId(chatId)
							.setText(answer);
					try {
						execute(message1);
					}
					/*       
         //   EditMessageText new_message = new EditMessageText()
          //          .setChatId(chatId).setMessageId(toIntExact(message_id))
          //          .setText(answer);
            try {
                execute(new_message);
            }*/

					catch (TelegramApiException e) {
						e.printStackTrace();
					}
				}else if (call_data.equals("cityRestaurantPreference")) {

					InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
					List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
					List<InlineKeyboardButton> rowInline = new ArrayList<>();
					rowInline.add(new InlineKeyboardButton().setText("Brussels").setCallbackData("brusselsRestaurant"));
					rowInline.add(new InlineKeyboardButton().setText("Milan").setCallbackData("milanRestaurant"));
					rowInline.add(new InlineKeyboardButton().setText("Rome").setCallbackData("romeRestaurant"));
					rowInline.add(new InlineKeyboardButton().setText("Mysore").setCallbackData("mysoreRestaurant"));
					rowInline.add(new InlineKeyboardButton().setText("Paris").setCallbackData("parisRestaurant"));
					rowInline.add(new InlineKeyboardButton().setText("Any city").setCallbackData("anywhereRestaurant"));
					rowsInline.add(rowInline); // Set the keyboard to the markup

					markupInline.setKeyboard(rowsInline);// Add it to the message

					SendMessage messageNext = new SendMessage() // Create a message object object
							.setChatId(chatId)
							.setText("Which city would you prefer?");

					messageNext.setReplyMarkup(markupInline);

					try {
						execute(messageNext); // Sending our message object to user
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}


				} else if (call_data.equals("brusselsRestaurant")) {
					String city = "Brussels";
					String type = "restaurant";
					String answer = "Please select top 3 restaurants of your choice";
					SendMessage message = new SendMessage() // Create a message object
							.setChatId(chatId)
							.setText(answer);
					try {
						execute(message);
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
				} else if (call_data.equals("milanRestaurant")) {
					String city = "Milano";
					String type = "restaurant";
					String answer = "Please select top 3 restaurants of your choice";
					SendMessage message = new SendMessage() // Create a message object
							.setChatId(chatId)
							.setText(answer);
					try {
						execute(message);
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
				} else if (call_data.equals("romeRestaurant")) {
					String city = "Roma";
					String type = "restaurant";
					String answer = "Please select top 3 restaurants of your choice";
					SendMessage message = new SendMessage() // Create a message object
							.setChatId(chatId)
							.setText(answer);
					try {
						execute(message);
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
				} else if (call_data.equals("mysoreRestaurant")) {
					String city = "Mysore";
					String type = "restaurant";
					String answer = "Please select top 3 restaurants of your choice";

					SendMessage message = new SendMessage() // Create a message object
							.setChatId(chatId)
							.setText(answer);
					try {
						execute(message);


						String message2=null;

						list1=searchItems.listItems(city,type);
						//String user[]=message_text.split(",");
						for (int i=0;i<list1.size();i++) {
							message2=list1.get(i).toString();
							SendMessage message1 = new SendMessage() // Create a message object
									.setChatId(chatId).setText(message2);
							
							execute(message1);


						}



					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
				}else if (call_data.equals("anywhereRestaurant")) {
					//String city = "";
					String type = "restaurant";
					String answer = "Please select top 3 restaurants of your choice";
					SendMessage message = new SendMessage() // Create a message object
							.setChatId(chatId)
							.setText(answer);
					try {
						execute(message);
					} catch (TelegramApiException e) {
						e.printStackTrace();//anywhereRestaurant
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


		public File getPhoto() {


			BufferedImage image = null;
			java.io.File f = null;
			int width = 963;    //width of the image
			int height = 640;   //height of the image
			File photo = null;
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

			try {

				f = new File("th.jpeg"); //image file path
				image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				image = ImageIO.read(f);

				photo = new File("th.jpeg");

			}catch (IOException e) {
				e.printStackTrace();

			}

			return photo;
		}
		public File getLastPhoto() {


			BufferedImage image = null;
			java.io.File f = null;
			int width = 963;    //width of the image
			int height = 640;   //height of the image
			File photo = null;
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

			try {

				f = new File("Thankyou.jpeg"); //image file path
				image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				image = ImageIO.read(f);

				photo = new File("Thankyou.jpeg");

			}catch (IOException e) {
				e.printStackTrace();

			}

			return photo;
		}


	}
