package Ozeki.Libs.Rest.Results.MessageSend;

import Ozeki.Libs.Rest.Message;

public class MessageSendResult {
    public Message message;
    public DeliveryStatus status;
    //public String statusMessage;

    //  String statusMessage
    public MessageSendResult (Message message, DeliveryStatus status) {
        this.message = message;
        this.status = status;
        //this.statusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return String.format("%s, %s", status.toString(), message.toString());
    }
}
