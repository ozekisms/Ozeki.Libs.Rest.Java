package Ozeki.Libs.Rest;

public enum Folder {
    Inbox,
    Outbox,
    Sent,
    NotSent,
    Deleted,
    Null;

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
                return "null";
        }
    }

    public Folder parseFolder(String folder) {
        switch (folder){
            case "inbox":
                return Folder.Inbox;
            case "outbox":
                return Folder.Outbox;
            case "sent":
                return Folder.Sent;
            case "notsent":
                return Folder.NotSent;
            case "deleted":
                return Folder.Deleted;
            default:
                return Folder.Null;
        }
    }
}