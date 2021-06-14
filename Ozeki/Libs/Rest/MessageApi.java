package Ozeki.Libs.Rest;

import Ozeki.Libs.Rest.org.json.*;
import java.net.http.*;
import java.net.URI;
import java.util.*;

public class MessageApi {

    Configuration _configuration;

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
    private Ozeki.Libs.Rest.org.json.JSONObject createRequestBody (Message[] messages) {

        var Messages = new Ozeki.Libs.Rest.org.json.JSONObject();
        var arrayoOfMessages = new JSONArray();

        for (var message : messages) {
            arrayoOfMessages.put(message.jsonVal());
        }

        Messages.put("messages", arrayoOfMessages);

        return Messages;
    }

    //Function to create request body string out of one message
    private Ozeki.Libs.Rest.org.json.JSONObject createRequestBody (Message message) {

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
    private String DoRequestPost(String url, String authorizationHeader, String messages, boolean manipulate)
    {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .POST(HttpRequest.BodyPublishers.ofString(messages))
                .headers("Authorization", authorizationHeader, "Content-Type", "application/json", "Accept", "application/json")
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (manipulate) {
                if (getMessageResponseManipulate(response)) {
                    return "Your request have been received";
                }  else {
                    return "An error occurred during the request..";
                }
            } else {
                return getMessageResponse(response);
            }
        } catch (Exception e) {
            return "";
        }
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
        return String.format("%s?action=sendmsg", url);
    }

    //Function to create an url to delete a message
    private String createUriToDeleteMessage(String url) {
        return String.format("%s?action=deletemsg", url);
    }

    //Function to create an url to mark a message
    private String createUriToMarkMessage(String url) {
        return String.format("%s?action=markmsg", url);
    }

    //Function to create an url to receive a message
    private String createUriToReceiveMessage(String url, Folder folder) {
        return String.format("%s?action=receivemsg&folder=%s", url, folder.toString());
    }

    private String getMessageResponse(HttpResponse<String> response){
        var jsonObject = new Ozeki.Libs.Rest.org.json.JSONObject(response.body());
        var data = jsonObject.getJSONObject("data");
        if (data.getInt("total_count") == 1) {
            var messages = data.getJSONArray("messages");
            var message = messages.getJSONObject(0);
            return String.format("Success, ->%s  '%s'", message.getString("to_address"), message.getString("text"));
        } else if (data.getInt("total_count") > 1) {
            return String.format("Total: %d. Success: %d. Failed: %d.", data.getInt("total_count"), data.getInt("success_count"), data.getInt("failed_count"));
        } else {
            return "Total: 0. Success: 0. Failed: 0.";
        }
    }

    private boolean getMessageResponseManipulate(HttpResponse<String> response){
        var jsonObject = new Ozeki.Libs.Rest.org.json.JSONObject(response.body());
        return jsonObject.getString("response_code").equals("SUCCESS");
    }

    //Function to send messages
    public String Send(Message[] messages) {
        var authorizationHeader = createAuthHeader(_configuration.Username, _configuration.Password);
        var msgs = createRequestBody(messages);
        return DoRequestPost(createUriToSendMessage(_configuration.BaseUrl), authorizationHeader, msgs.toString(), false);
    }

    //Function to send message
    public String Send(Message message) {
        var authorizationHeader = createAuthHeader(_configuration.Username, _configuration.Password);
        var msg = createRequestBody(message);
        return DoRequestPost(createUriToSendMessage(_configuration.BaseUrl), authorizationHeader, msg.toString(), false);
    }

    //Function to delete message
    public String Delete(Folder folder, Message message) {
        var authHeader = createAuthHeader(_configuration.Username, _configuration.Password);
        var requestBody = createRequestBodyToManipulate(folder, message);
        return DoRequestPost(createUriToDeleteMessage(_configuration.BaseUrl), authHeader, requestBody.toString(), true);
    }

    //Function to delete multiple message
    public String Delete(Folder folder, Message[] messages) {
        var authHeader = createAuthHeader(_configuration.Username, _configuration.Password);
        var requestBody = createRequestBodyToManipulate(folder, messages);
        return DoRequestPost(createUriToDeleteMessage(_configuration.BaseUrl), authHeader, requestBody.toString(), true);
    }

    //Function to delete messages from an Array
    private void Delete(ArrayList<Message> messages) {
        var authHeader = createAuthHeader(_configuration.Username, _configuration.Password);
        var requestBody = createRequestBodyToManipulate(Folder.Inbox, messages);
        DoRequestPost(createUriToDeleteMessage(_configuration.BaseUrl), authHeader, requestBody.toString(), true);
    }

    //Function to delete message
    public String Mark(Folder folder, Message message) {
        var authHeader = createAuthHeader(_configuration.Username, _configuration.Password);
        var requestBody = createRequestBodyToManipulate(folder, message);
        return DoRequestPost(createUriToMarkMessage(_configuration.BaseUrl), authHeader, requestBody.toString(), true);
    }

    //Function to delete multiple message
    public String Mark(Folder folder, Message[] messages) {
        var authHeader = createAuthHeader(_configuration.Username, _configuration.Password);
        var requestBody = createRequestBodyToManipulate(folder, messages);
        return DoRequestPost(createUriToMarkMessage(_configuration.BaseUrl), authHeader, requestBody.toString(), true);
    }

    //Function to delete message
    public String DownloadIncoming() {
        var result = "";
        var authHeader = createAuthHeader(_configuration.Username, _configuration.Password);
        var response = DoRequestGet(createUriToReceiveMessage(_configuration.BaseUrl, Folder.Inbox), authHeader);
        var jsonObject = new JSONObject(response);
        var data = jsonObject.getJSONObject("data");
        var messages = data.getJSONArray("data");
        var messageIds = new ArrayList<Message>();
        result += String.format("There are %d messages in your Inbox folder:\n", messages.length());
        for (int i = 0; i < messages.length(); i++) {
            var msg = messages.getJSONObject(i);
            var messageToDelete = new Message();
            messageToDelete.ID =  msg.getString("message_id");
            messageIds.add(messageToDelete);
            result += String.format("From: %s - Text: %s \n", msg.getString("from_address"), msg.getString("text"));
        }
        Delete(messageIds);
        return result;
    }
}