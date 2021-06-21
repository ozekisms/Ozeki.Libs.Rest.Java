package Ozeki.Libs.Rest.Results.MessageSend;

import java.util.ArrayList;

public class MessageSendResults {
    public int totalCount;
    public int successCount;
    public int failedCount;
    public ArrayList<MessageSendResult> results;

    public MessageSendResults(int total_count, int success_count, int failed_count, ArrayList<MessageSendResult> results) {
        this.totalCount = total_count;
        this.successCount = success_count;
        this.failedCount = failed_count;
        this.results = results;
    }

    @Override
    public String toString() {
        return String.format("Total %d. Success %d. Failed: %d", this.totalCount, this.successCount, this.failedCount);
    }
}
