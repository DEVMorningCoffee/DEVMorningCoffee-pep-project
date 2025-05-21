package Controller;

import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerAccount);
        app.post("/login", this::loginAccount);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessage);
        app.delete("/messages/{message_id}", this::deleteMessage);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("/accounts/{account_id}/messages", this::accountMessages);

        return app;
    }

    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerAccount(Context ctx) {
        try {
            Account accountData = ctx.bodyAsClass(Account.class);
            Account newAccount = accountService.registerAccount(accountData);

            if (newAccount == null) {
                throw new Exception("Invalid account data");
            }

            ctx.status(200).json(newAccount);
        } catch (Exception e) {
            ctx.status(400);
        }
    }

    private void loginAccount(Context ctx) {
        try {
            Account accountData = ctx.bodyAsClass(Account.class);
            Account accessAccount = accountService.loginAccount(accountData);

            if (accessAccount == null) {
                throw new Exception("Invalid login");
            }

            ctx.status(200).json(accessAccount);
        } catch (Exception e) {
            ctx.status(401);
        }
    }

    private void createMessage(Context ctx) {
        try {
            Message messageData = ctx.bodyAsClass(Message.class);
            Message newMessage = messageService.createMessage(messageData);

            if (newMessage == null) {
                throw new Exception("Invalid message data");
            }

            ctx.status(200).json(newMessage);
        } catch (Exception e) {
            ctx.status(400);
        }
    }

    private void getAllMessages(Context ctx) {
        try {
            List<Message> messages = messageService.getAllMessages();
            ctx.status(200).json(messages != null ? messages : new ArrayList<>());
        } catch (Exception e) {
            ctx.status(200).json(new ArrayList<>());
        }
    }

    private void getMessage(Context ctx) {
        try {
            int messageID = Integer.parseInt(ctx.pathParam("message_id"));
            Message message = messageService.getMessage(messageID);

            ctx.status(200).json(message);
        } catch (Exception e) {
            ctx.status(200);
        }
    }

    private void deleteMessage(Context ctx) {
        try {
            int messageID = Integer.parseInt(ctx.pathParam("message_id"));
            Message deletedMessage = messageService.deleteMessage(messageID);

            ctx.status(200).json(deletedMessage);
        } catch (Exception e) {
            ctx.status(200);
        }
    }

    private void updateMessage(Context ctx) {
        try {
            int messageID = Integer.parseInt(ctx.pathParam("message_id"));
            String messageText = ctx.bodyAsClass(Message.class).getMessage_text();

            Message updatedMessage = messageService.updateMessage(messageID, messageText);
            ctx.status(200).json(updatedMessage);
        } catch (Exception e) {
            ctx.status(400);
        }
    }

    private void accountMessages(Context ctx) {
        try {
            int accountID = Integer.parseInt(ctx.pathParam("account_id"));

            List<Message> messages = messageService.accountMessages(accountID);
            ctx.status(200).json(messages != null ? messages : new ArrayList<>());
        } catch (Exception e) {
            ctx.status(200).json(new ArrayList<>());
        }
    }
}
