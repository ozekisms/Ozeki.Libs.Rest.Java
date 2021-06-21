package Ozeki.Libs.Rest.Results.MessageSend;

public enum DeliveryStatus {
    Failed,
    Success;

    @Override
    public String toString() {
        switch (this){
            case Failed:
                return "Failed";
            default:
                return "Success";
        }
    }
}
