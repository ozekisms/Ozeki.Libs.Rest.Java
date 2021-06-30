package SendSms;

import Ozeki.Libs.Rest.*;

public class SendSms {

    public static void main(String[] args) {

        var configuration = new Configuration();
        configuration.Username = "http_user";
        configuration.Password = "qwe123";
        configuration.ApiUrl = "http://127.0.0.1:9509/api";

        var msg = new Message();
        msg.ToAddress = "+36201111111";
        msg.Text = "Hello world";

        var api = new MessageApi(configuration);
        
        var result = api.Send(msg);
        
        System.out.println(result);
    }
}
