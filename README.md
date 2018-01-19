# RecommBot-UI
Telegram Bot

This is a telegram bot which serves as a user interface for the recommendation system for activities and restaurants available in selected cities of the world.

- Telegram Bot username is RecommBot and it could be found by searching @RecommBot in the telegram app.

At the start of the chat with the bot, it prompts user to input user details. New user is registered to recombee database. SOAP user registration service is used here.
- firstname
- lastname
- email
- birthyear

The bot asks user to chose between activity or restaurant and input a city name for getting recommendations.

The bot shows the recommendation to the user based on-
- If it is an existing user or a new user
- In case of an existing user, if the user has any preferences or ratings previosuly saved

The bot also motivates users who chose to go for an activity. To do so, the bot fetches quotes from an external API called TheySaidSo.

Retrieving from TheySaidSo API:
- category = <could be one of these: sports,funny,inspire,management,life,love,art,students>

Reference:

    TheySaidSo API, Available at: https://quotes.rest/#!/qod/get_qod







