package Ozeki.Libs.Rest;

import Ozeki.Libs.Rest.*;
import Ozeki.Libs.Rest.Folder;
import Ozeki.Libs.Rest.Results.MessageManipulateResults.MessageDeleteResult.MessageDeleteResult;
import Ozeki.Libs.Rest.Results.MessageManipulateResults.MessageMarkResult.MessageMarkResult;
import Ozeki.Libs.Rest.Results.MessageReceiveResult.MessageReceiveResult;
import Ozeki.Libs.Rest.Results.MessageSend.DeliveryStatus;
import Ozeki.Libs.Rest.Results.MessageSend.MessageSendResult;
import Ozeki.Libs.Rest.Results.MessageSend.MessageSendResults;
import Ozeki.Libs.Rest.org.json.*;

import java.net.http.*;
import java.net.URI;
import java.util.*;

public class MessageApi {

    public Configuration _configuration;

    public MessageApi(Configuration configuration){
        _configuration = configuration;
    }

    //Function to create the Authorization header
    private String createAuthHeader(String username, String password) {
        try {
            var usernamePassword = username + ":" + password;
            var encodedUsernamePassword = usernamePassword.getBytes();
            return String.format("Basic %s", Base64.getEncoder().encodeToString(encodedUsernamePassword));
        }
        catch (Exception e){
            return "";
        }
    }

    //Function to create create request body out of multiple messages
    private JSONObject createRequestBody (Message[] messages) {

        var Messages = new Ozeki.Libs.Rest.org.json.JSONObject();
        var arrayoOfMessages = new JSONArray();

        for (var message : messages) {
            arrayoOfMessages.put(message.jsonVal());
        }

        Messages.put("messages", arrayoOfMessages);

        return Messages;
    }

    //Function to create request body string out of one message
    private JSONObject createRequestBody (Message message) {

        var Messages = new Ozeki.Libs.Rest.org.json.JSONObject();

        Messages.append("messages", message.jsonVal());

        return Messages;
    }

    //Function to create request body string to manipulate out of one message
    private JSONObject createRequestBodyToManipulate(Folder folder, Message message){
        var result = new JSONObject();
        var messageIds = new JSONArray();
        messageIds.put(message.ID);
        result.put("folder", folder.toString());
        result.put("message_ids", messageIds);
        return result;
    }

    //Function to create request body string to manipulate out of multiple messages
    private JSONObject createRequestBodyToManipulate(Folder folder, Message[] messages){
        var result = new JSONObject();
        var messageIds = new JSONArray();
        for (var message : messages) {
            messageIds.put(message.ID);
        }
        result.put("folder", folder.toString());
        result.put("message_ids", messageIds);
        return result;
    }

    //Function to create request body string to manipulate out of multiple messages
    private JSONObject createRequestBodyToManipulate(Folder folder, ArrayList<Message> messages){
        var result = new JSONObject();
        var messageIds = new JSONArray();
        for (var message : messages) {
            messageIds.put(message.ID);
        }
        result.put("folder", folder.toString());
        result.put("message_ids", messageIds);
        return result;
    }

    //Function to perform a POST request to the API
    private JSONObject DoRequestPost(String url, String authorizationHeader, String messages)
    {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .POST(HttpRequest.BodyPublishers.ofString(messages))
                .headers("Authorization", authorizationHeader, "Content-Type", "application/json", "Accept", "application/json")
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body());
        } catch (Exception e) {
            System.out.println(e);
        }
        return new JSONObject();
    }

    //Function to perform a GET request to the API
    private String DoRequestGet(String url, String authorizationHeader)
    {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .headers("Authorization", authorizationHeader)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return "";
        }
    }

    //Function to create an url to send  a message
    private String createUriToSendMessage(String url) {
        var ApiUrl = url.split("\\?")[0];
        return String.format("%s?action=sendmsg", ApiUrl);
    }

    //Function to create an url to delete a message
    private String createUriToDeleteMessage(String url) {
        var ApiUrl = url.split("\\?")[0];
        return String.format("%s?action=deletemsg", ApiUrl);
    }

    //Function to create an url to mark a message
    private String createUriToMarkMessage(String url) {
        var ApiUrl = url.split("\\?")[0];
        return String.format("%s?action=markmsg", ApiUrl);
    }

    //Function to create an url to receive a message
    private String createUriToReceiveMessage(String url, Folder folder) {
        var ApiUrl = url.split("\\?")[0];
        return String.format("%s?action=receivemsg&folder=%s", ApiUrl, folder.toString());
    }

    private MessageSendResults getMessageResponse(JSONObject response){
        var data = response.getJSONObject("data");
        if (data.getInt("total_count") == 1) {
            var messages = data.getJSONArray("messages");
            var message = new Message(messages.getJSONObject(0));
            var msgresult = new MessageSendResult(message, DeliveryStatus.Success);
            ArrayList<MessageSendResult> results = new ArrayList<MessageSendResult>(1);
            results.add(msgresult);
            return new MessageSendResults(data.getInt("total_count"), data.getInt("success_count"), data.getInt("failed_count"), results);
        } else if (data.getInt("total_count") > 1) {
            var messages = data.getJSONArray("messages");
            ArrayList<MessageSendResult> results = new ArrayList<MessageSendResult>(messages.length());
            for (int i = 0; i < messages.length(); i++) {
                var msg = new Message(messages.getJSONObject(i));
                var msgresult = new MessageSendResult(msg, DeliveryStatus.Success);
                results.add(msgresult);
            }
            return new MessageSendResults(data.getInt("total_count"), data.getInt("success_count"), data.getInt("failed_count"), results);
        } else {
            return new MessageSendResults(0, 0, 0, new ArrayList<MessageSendResult>(0));
        }
    }

    private MessageDeleteResult getMessageResponseDelete(JSONObject response, Message[] messages){
        var msgs = response.getJSONObject("data").getJSONArray("message_ids");
        var folder = response.getJSONObject("data").get("folder").toString();
        if (response.getString("response_code").equals("SUCCESS")) {
            var messages_failed = new String[messages.length-msgs.length()];
            var messages_success = new String[msgs.length()];
            var succIndx = 0;
            var failIndx = 0;
            for (var message : messages) {
                var success = false;
                for (var msg : msgs) {
                    if (msg.toString().equals(message.ID)) {
                        success = true;
                    }
                }
                if (success) {
                    messages_success[succIndx] = message.ID;
                    succIndx += 1;
                } else {
                    messages_failed[failIndx] = message.ID;
                    failIndx += 1;
                }
            }
            return new MessageDeleteResult(Folder.Null.parseFolder(folder), messages_success, messages_failed);
        }
        return new MessageDeleteResult(Folder.Null.parseFolder(folder), new String[]{}, new String[]{});
    }

    private Boolean getMessageResponseDelete(JSONObject response, Message message) {
        var messages = response.getJSONObject("data").getJSONArray("message_ids");
        if (response.getString("response_code").equals("SUCCESS")) {
            if (messages.length() == 1 && message.ID.equals(messages.get(0).toString())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private MessageMarkResult getMessageResponseMark(JSONObject response, Message[] messages){
        var msgs = response.getJSONObject("data").getJSONArray("message_ids");
        var folder = response.getJSONObject("data").get("folder").toString();
        if (response.getString("response_code").equals("SUCCESS")) {
            var messages_failed = new String[messages.length-msgs.length()];
            var messages_success = new String[msgs.length()];
            var succIndx = 0;
            var failIndx = 0;
            for (var message : messages) {
                var success = false;
                for (var msg : msgs) {
                    if (msg.toString().equals(message.ID)) {
                        success = true;
                    }
                }
                if (success) {
                    messages_success[succIndx] = message.ID;
                    succIndx += 1;
                } else {
                    messages_failed[failIndx] = message.ID;
                    failIndx += 1;
                }
            }
            return new MessageMarkResult(Folder.Null.parseFolder(folder), messages_success, messages_failed);
        }
        return new MessageMarkResult(Folder.Null.parseFolder(folder), new String[]{}, new String[]{});
    }

    private Boolean getMessageResponseMark(JSONObject response, Message message) {
        var messages = response.getJSONObject("data").getJSONArray("message_ids");
        if (response.getString("response_code").equals("SUCCESS")) {
            if (messages.length() == 1 && message.ID.equals(messages.get(0).toString())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //Function to send messages
    public MessageSendResults Send(Message[] messages) {
        var authorizationHeader = createAuthHeader(_configuration.Username, _configuration.Password);
        var requestBody = createRequestBody(messages);
        return this.getMessageResponse(DoRequestPost(createUriToSendMessage(_configuration.ApiUrl), authorizationHeader, requestBody.toString()));
    }

    //Function to send message
    public MessageSendResult Send(Message message) {
        var authorizationHeader = createAuthHeader(_configuration.Username, _configuration.Password);
        var requestBody = createRequestBody(message);
        return this.getMessageResponse(DoRequestPost(createUriToSendMessage(_configuration.ApiUrl), authorizationHeader, requestBody.toString())).results.get(0);
    }

    //Function to delete message
    public Boolean Delete(Folder folder, Message message) {
        var authHeader = createAuthHeader(_configuration.Username, _configuration.Password);
        var requestBody = createRequestBodyToManipulate(folder, message);
        return this.getMessageResponseDelete(this.DoRequestPost(createUriToDeleteMessage(_configuration.ApiUrl), authHeader, requestBody.toString()), message);
    }

    //Function to delete multiple message
    public MessageDeleteResult Delete(Folder folder, Message[] messages) {
        var authHeader = createAuthHeader(_configuration.Username, _configuration.Password);
        var requestBody = createRequestBodyToManipulate(folder, messages);
        return this.getMessageResponseDelete(DoRequestPost(createUriToDeleteMessage(_configuration.ApiUrl), authHeader, requestBody.toString()), messages);
    }

    //Function to mark message
    public Boolean Mark(Folder folder, Message message) {
        var authHeader = createAuthHeader(_configuration.Username, _configuration.Password);
        var requestBody = createRequestBodyToManipulate(folder, message);
        return this.getMessageResponseMark(DoRequestPost(createUriToMarkMessage(_configuration.ApiUrl), authHeader, requestBody.toString()), message);
    }

    //Function to mark multiple message
    public MessageMarkResult Mark(Folder folder, Message[] messages) {
        var authHeader = createAuthHeader(_configuration.Username, _configuration.Password);
        var requestBody = createRequestBodyToManipulate(folder, messages);
        return this.getMessageResponseMark(DoRequestPost(createUriToMarkMessage(_configuration.ApiUrl), authHeader, requestBody.toString()), messages);
    }

    //Function to delete message
    public MessageReceiveResult DownloadIncoming() {
        var authHeader = createAuthHeader(_configuration.Username, _configuration.Password);
        var response = new JSONObject(DoRequestGet(createUriToReceiveMessage(_configuration.ApiUrl, Folder.Inbox), authHeader));
        var limit = response.getJSONObject("data").getString("limit");
        var folder = response.getJSONObject("data").getString("folder");
        var messages = response.getJSONObject("data").getJSONArray("data");
        var msgs = new Message[messages.length()];
        var msgsIndex = 0;
        for (int i = 0; i < messages.length(); i++) {
            var msg = new Message(messages.getJSONObject(i));
            msgs[msgsIndex] = msg;
            msgsIndex += 1;
        }
        Delete(Folder.Inbox, msgs);
        return new MessageReceiveResult(Folder.Null.parseFolder(folder), limit, msgs);
    }
}