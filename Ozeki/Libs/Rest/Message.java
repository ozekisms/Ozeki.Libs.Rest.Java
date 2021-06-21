package Ozeki.Libs.Rest;


import java.time.format.DateTimeFormatter;
import Ozeki.Libs.Rest.org.json.*;
import java.time.LocalDateTime;
import java.util.*;

public class Message {

    public String ID;

    public String FromConnection;
    public String FromAddress;
    public String FromStation;

    public String ToConnection;
    public String ToAddress;
    public String ToStation;

    public String Text;

    public LocalDateTime CreateDate;
    public LocalDateTime ValidUntil;
    public LocalDateTime TimeToSend;

    public boolean IsSubmitReportRequested;
    public boolean IsDeliveryReportRequested;
    public boolean IsViewReportRequested;

    public HashMap<String, String> Tags = new HashMap<String, String>();

    public JSONArray getTags() {
        var tags = new JSONArray();
        var TagsIterator = Tags.entrySet().iterator();
        while (TagsIterator.hasNext()) {
            var element  = (Map.Entry)TagsIterator.next();
            String key = ((String)element.getKey());
            String value = ((String)element.getValue());
            var msg = new JSONObject();
            msg.put(key, value);
            tags.put(msg);
        }
        return tags;
    }

    public void addTag(String key, String value) {
        Tags.put(key, value);
    }

    public Message()
    {
        ID = String.valueOf(UUID.randomUUID());
        CreateDate = LocalDateTime.now();
        TimeToSend = LocalDateTime.now();
        ValidUntil = LocalDateTime.now().plusDays(7);
        IsSubmitReportRequested = true;
        IsDeliveryReportRequested = true;
        IsViewReportRequested = true;
    }

    public Message(JSONObject message) {

        if (message.has("message_id") && message.getString("message_id").length() != 0) {
            this.ID = message.getString("message_id");
        }
        if (message.has("from_connection") && message.getString("from_connection").length() != 0) {
            this.FromConnection = message.getString("from_connection");
        }
        if (message.has("from_address") && message.getString("from_address").length() != 0) {
            this.FromAddress = message.getString("from_address");
        }
        if (message.has("from_station") && message.getString("from_station").length() != 0) {
            this.FromStation = message.getString("from_station");
        }
        if (message.has("to_connection") && message.getString("to_connection").length() != 0) {
            this.ToConnection = message.getString("to_connection");
        }
        if (message.has("to_address") && message.getString("to_address").length() != 0) {
            this.ToAddress = message.getString("to_address");
        }
        if (message.has("to_station") && message.getString("to_station").length() != 0) {
            this.ToStation = message.getString("to_station");
        }
        if (message.has("text") && message.getString("text").length() != 0) {
            this.Text = message.getString("text");
        }
        if (message.has("create_date") && message.getString("create_date").length() != 0) {
            this.CreateDate = LocalDateTime.parse(message.getString("create_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        if (message.has("valid_until") && message.getString("valid_until").length() != 0) {
            this.ValidUntil = LocalDateTime.parse(message.getString("valid_until"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        if (message.has("time_to_send") && message.getString("time_to_send").length() != 0) {
            this.TimeToSend = LocalDateTime.parse(message.getString("time_to_send"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        if (message.has("submit_report_requested")) {
            this.IsSubmitReportRequested = message.getBoolean("submit_report_requested");
        }
        if (message.has("view_report_requested")) {
            this.IsViewReportRequested = message.getBoolean("view_report_requested");
        }
        if (message.has("delivery_report_requested")) {
            this.IsDeliveryReportRequested = message.getBoolean("delivery_report_requested");
        }
        if (message.has("tags")) {
            var tags = message.getJSONArray("tags");
            for (int i = 0; i < tags.length(); i++) {
                var object = tags.getJSONObject(i);
                this.Tags.put(object.getString("name"), object.getString("value"));
            }
        }
    }

    public Message(String messageID) {
        if (!messageID.equals("")) {
            this.ID = messageID;
        }
    }

    @Override
    public String toString() {
        return String.format("->%s '%s'", ToAddress, Text);
    }

    //Create the json format for the API
    public JSONObject jsonVal() {

        var jsonObject = new JSONObject();

        var DateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (this.ID != null) {
            jsonObject.put("message_id", this.ID);
        }
        if (this.FromConnection != null) {
            jsonObject.put("from_connection", this.FromConnection);
        }
        if (this.FromAddress != null) {
            jsonObject.put("from_address", this.FromAddress);
        }
        if (this.FromStation != null) {
            jsonObject.put("from_station", this.FromStation);
        }
        if (this.ToConnection != null) {
            jsonObject.put("to_connection", this.ToConnection);
        }
        if (this.ToAddress != null) {
            jsonObject.put("to_address", this.ToAddress);
        }
        if (this.ToStation != null) {
            jsonObject.put("to_station", this.ToStation);
        }
        if (this.Text != null) {
            jsonObject.put("text", this.Text);
        }
        if (this.CreateDate != null) {
            jsonObject.put("create_date", this.CreateDate.format(DateFormat));
        }
        if (this.ValidUntil != null) {
            jsonObject.put("valid_until", this.ValidUntil.format(DateFormat));
        }
        if (this.TimeToSend != null) {
            jsonObject.put("time_to_send", this.TimeToSend.format(DateFormat));
        }
        jsonObject.put("submit_report_requested", this.IsSubmitReportRequested);
        jsonObject.put("delivery_report_requested", this.IsDeliveryReportRequested);
        jsonObject.put("view_report_requested", this.IsViewReportRequested);
        if (!this.Tags.isEmpty()) {
            jsonObject.put("tags", this.getTags().toString());
        }
        return jsonObject;
    }
}
