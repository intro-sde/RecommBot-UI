# RecommBot-UI

This is a telegram bot which serves as a user interface for the recommendation system for activities and restaurants available in selected cities of the world.

Telegram Bot username is RecommBot and it could be found by searching @RecommBot in the telegram app.

At the start of the chat with the bot, it prompts user to input user details. New user is registered to Recombee database if user is not in the system. 
- firstname
- lastname
- email
- birthyear

The bot asks user to chose between activity or restaurant and input a city name for getting recommendations.
The bot also motivates users with quotes about either sports or restaurants.

The bot shows 5 recommendations to the user based on the rules implemented in the recommendation business logic service:
- when the user has no ratings we use item based recommendation based on one random item from the user's preferences, and if they don't have preference we just give 5 random items with given type and city
- when the user has ratings we use user based recommendation with respect to user's ratings and preferences
we always take city and activity type into consideration

The user is asked to select one item out of these five that they liked most and provide a rating from -1.0 to 1.0.

If the user chose activity then in the end of the process the bot offers to also recommend restaurants or to finish the process.

Execution:

Step 1. Clone the github repository in your local machine.

Step 2. Import the project into Eclipse IDE.

Step 3. Add Ivy jars (Right click on the ivy.xml and select 'Add Ivy Library...').

Step 4. Add 'telegrambots-3.5-jar-with-dependencies.jar' to the build path of the project. This jar has been downloaded from the URL:
          https://github.com/rubenlagus/TelegramBots/releases/tag/v3.5
          
Step 5. Export the project as runnable jar.

Step 6. Execute the jar from the terminal using below code.
          java -jar <runnable_jar_name>.jar
          
Step 7. Bot is registered and is ready to use.

Step 8. Open telegram app in your phone, search for the bot "@RecommBot" and start using our services.
    










