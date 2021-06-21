package Ozeki.Libs.Rest.Results.MessageReceiveResult;

import Ozeki.Libs.Rest.Folder;
import Ozeki.Libs.Rest.Message;

public class MessageReceiveResult {
    public Folder Folder;
    public String Limit;
    public Message[] Messages;

    public MessageReceiveResult(Folder folder, String limit, Message[] messages) {
        this.Folder = folder;
        this.Limit = limit;
        this.Messages = messages;
    }

    @Override
    public String toString() {
        return String.format("Message count: %d.", this.Messages.length);
    }
}
