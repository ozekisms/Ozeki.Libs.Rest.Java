package SendSMS;

import Ozeki.Libs.Rest.*;

public class SendMultiSms {

    public static void main(String[] args) {

        var configuration = new Configuration();
        configuration.Username = "http_user";
        configuration.Password = "qwe123";
        configuration.BaseUrl = "http://127.0.0.1:9509/api";

        var msg1 = new Message();
        msg1.ToAddress = "+36201111111";
        msg1.Text = "Hello world 1";

        var msg2 = new Message();
        msg2.ToAddress = "+36202222222";
        msg2.Text = "Hello world 2";

        var msg3 = new Message();
        msg3.ToAddress = "+36203333333";
        msg3.Text = "Hello world 3";

        var api = new MessageApi(configuration);
        
        var result = api.Send(new Message[] { msg1, msg2, msg3 });
        
        System.out.println(result);
    }
}
