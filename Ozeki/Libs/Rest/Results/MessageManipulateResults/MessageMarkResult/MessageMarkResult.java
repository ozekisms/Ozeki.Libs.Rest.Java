package Ozeki.Libs.Rest.Results.MessageManipulateResults.MessageMarkResult;

import Ozeki.Libs.Rest.Folder;

public class MessageMarkResult {
    public Ozeki.Libs.Rest.Folder Folder;
    public String[] MessageIdsMarkSucceeded;
    public String[] MessageIdsMarkFailed;
    public int SuccessCount;
    public int FailedCount;
    public int TotalCount;

    public MessageMarkResult(Folder folder, String[] messageIdsMarkSucceeded, String[] messageIdsMarkFailed) {
        this.Folder = folder;
        this.MessageIdsMarkSucceeded = messageIdsMarkSucceeded;
        this.MessageIdsMarkFailed = messageIdsMarkFailed;
        this.SuccessCount = messageIdsMarkSucceeded.length;
        this.FailedCount = messageIdsMarkFailed.length;
        this.TotalCount = messageIdsMarkFailed.length + messageIdsMarkSucceeded.length;
    }

    @Override
    public String toString() {
        return String.format("Total: %d. Success: %d. Failed: %d.", this.TotalCount, this.SuccessCount, this.FailedCount);
    }
}
