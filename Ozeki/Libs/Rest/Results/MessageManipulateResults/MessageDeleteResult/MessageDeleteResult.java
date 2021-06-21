package Ozeki.Libs.Rest.Results.MessageManipulateResults.MessageDeleteResult;

import Ozeki.Libs.Rest.Folder;

public class MessageDeleteResult {
    public Folder Folder;
    public String[] MessageIdsRemoveSucceeded;
    public String[] MessageIdsRemoveFailed;
    public int SuccessCount;
    public int FailedCount;
    public int TotalCount;

    public MessageDeleteResult(Folder folder, String[] messageIdsRemoveSucceeded, String[] messageIdsRemoveFailed) {
        this.Folder = folder;
        this.MessageIdsRemoveSucceeded = messageIdsRemoveSucceeded;
        this.MessageIdsRemoveFailed = messageIdsRemoveFailed;
        this.SuccessCount = messageIdsRemoveSucceeded.length;
        this.FailedCount = messageIdsRemoveFailed.length;
        this.TotalCount = messageIdsRemoveFailed.length + messageIdsRemoveSucceeded.length;
    }

    @Override
    public String toString() {
        return String.format("Total: %d. Success: %d. Failed: %d.", this.TotalCount, this.SuccessCount, this.FailedCount);
    }
}
