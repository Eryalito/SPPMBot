# Skeleton for a new bot developed on Java. 

Main features:

1. Easy to create new commands.
2. Connection with SQLDatabase.
3. Admin management.
4. Multiple states (Easy changes between states. Different states could have different commands).
5. Small CLI.
	
---

## Admin vs Non-Admin

The bot can differentiate between two level of users: Admin and Non-Admin. Each one has its own commands flow. It's supposed that if the Admin does not make any action (as admin) it will want to do an action as Non-Admin.
To do that, the bot search in the database the admin's chat id and check if the request has been done by an admin.

A bit of code:

In principal.RespuestaConsulta.run() we can find the differentiation between admin and non-admin. If its and Admin it will call the acction in AccionAdmin and if it is not done (is not an Admin request command) it will call the action on AccionNoAdmin.
This allow admins to have different states/commands like commands reserved for admins but let the run commands as non-admin without duplicate code.

	
---

## New command

1. Create a new class in comandos.usuario or comandos.administrador depending on if it's for an admin or a non-admin that implements Command.
2. Write all the code on the addMessages method.
3. We should not recreate the ArrayList, we should just add new elements and return them.

If you need an example go to comandos.usuario.Help.

---

## New State

States allow us to have different commands for the same user at different times.
For example: we are expecting the user to send us a text. We ask him to send the text and change the state to "InputTextState". The next time that the user sends a request We'll call the "InputTextState" where We'll store that text and reset the state to "General".

### Create a new state

First of all we've to create a new ID for the state. It's represented by an int. Create new variable in estados.Estado (interface), It's recommended to use a descriptive name for the state.
Now we're going to create and implement the state:

1. Create a new class in estados that implements Estado.
2. Fill the response method with the appropiate calls to the commands.
3. We should not recreate the ArrayList, we should just add new elements and return them.

If you need an example go to Estados.EstadoGeneral.

---

## MySQL Database

Set the appropriate variables on dataBase.Basics and parameters.json to connect to the database.

### Must have:

1. A 'person' table with id, name, surname, username, lang, satate and admin and log flags. *CREATE TABLE IF NOT EXISTS `person`(`id` BIGINT NOT NULL AUTO_INCREMENT, `telegramId` BIGINT NOT NULL,`name` VARCHAR(45) NULL,`surname` VARCHAR(45) NULL,`username` VARCHAR(45) NULL,`lang` VARCHAR(5) NULL,`state` INT NULL DEFAULT 0,`admin` TINYINT(1) NOT NULL DEFAULT 0,`send_log` TINYINT(1) NOT NULL DEFAULT 0, PRIMARY KEY (`id`))*

---

## Setup

To run the bot We must get a bot from the Bot Father ( @BotFather ). It will give an '@' for the bot (always ending in 'bot') and a token. We need that @ and token to get access to the bot functions through the API. Once we have it we must set them into the parameters.json file.