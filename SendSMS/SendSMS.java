package SendSMS;

import Ozeki.Libs.Rest.*;
//In this video, I'm going to show you what happens if the Ozeki.Libs.Rest.Java repository gets updated.
public class SendSMS {

    public static void main(String[] args) {

        var configuration = new Configuration();
        configuration.Username = "http_user";
        configuration.Password = "qwe123";
        configuration.BaseUrl = "http://127.0.0.1:9509/api";

        var msg = new Message();
        msg.ToAddress = "+36201111111";
        msg.Text = "Hello world";

        var api = new MessageApi(configuration);
        
        var result = api.Send(msg);
        
        System.out.println(result);
    }
}
