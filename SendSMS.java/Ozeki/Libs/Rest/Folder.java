package Ozeki.Libs.Rest;

public enum Folder {
    Inbox,
    Outbox,
    Sent,
    NotSent,
    Deleted;

    @Override
    public String toString() {
        switch (this){
            case Inbox:
                return "inbox";
            case Outbox:
                return "outbox";
            case Sent:
                return "sent";
            case NotSent:
                return "notsent";
            case Deleted:
                return "deleted";
            default:
                return "";
        }
    }
}
