package DeleteSms;

import Ozeki.Libs.Rest.*;

public class DeleteSms {

    public static void main(String[] args) {

        var configuration = new Configuration();
        configuration.Username = "http_user";
        configuration.Password = "qwe123";
        configuration.ApiUrl = "http://127.0.0.1:9509/api";

        var msg = new Message();
        msg.ID = "73538ac0-f27c-4eaf-ba4c-6193aebe477c";

        var api = new MessageApi(configuration);
        
        var result = api.Delete(Folder.Inbox, msg);
        
        System.out.println(result);
    }
}
