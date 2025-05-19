package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import Util.ConnectionUtil;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

     private AccountService accountService;
     private final MessageService messageService;

    // We need to add the connection 
    public SocialMediaController(){
        Connection conn = ConnectionUtil.getConnection();
        this.accountService = new AccountService(conn);
        this.messageService = new MessageService(conn);
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerAccount);
        app.post("/login", this::loginAccount);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessage);
        app.get("/messages/{message_id}", this::getMessageByID);
        

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerAccount(Context ctx){
        Account data = ctx.bodyAsClass(Account.class);
        String username = data.getUsername();
        String password = data.getPassword();

        try {
            Account account = accountService.registerAccount(username, password);

            ctx.status(200).json(account);

        } catch (Exception e) {
            // TODO: handle exception
            ctx.status(400);
        }
    }

    private void loginAccount(Context ctx){
        Account data = ctx.bodyAsClass(Account.class);
        String username = data.getUsername();
        String password = data.getPassword();

        try {
            Account account = accountService.loginAccount(username, password);

            ctx.status(200).json(account);

        } catch (Exception e) {
            // TODO: handle exception
            ctx.status(401);
        }
    }

    private void createMessage(Context ctx){
        Message data = ctx.bodyAsClass(Message.class);
        int postedBy = data.getPosted_by();
        String messageText = data.getMessage_text();
        long timePostedEpoch = data.getTime_posted_epoch();

        try{
            Message message = messageService.createMessage(postedBy, messageText, timePostedEpoch);

            ctx.status(200).json(message);
        }catch(Exception e){
            ctx.status(400);
        }
    }

    private void getAllMessage(Context ctx) {
        try {
            List<Message> messages = messageService.getAllMessage();
            ctx.status(200).json(messages);
        } catch (Exception e) {
            // TODO: handle exception
            ctx.status(200).json(new ArrayList<>());
        }
    }

    private void getMessageByID(Context ctx){
        try {
            int messageID = Integer.parseInt(ctx.pathParam("message_id"));
            Message message = messageService.getMessage(messageID);
            ctx.status(200).json(message);
        } catch (Exception e) {
            // TODO: handle exception
            ctx.status(200).json(null);
        }
    }
}