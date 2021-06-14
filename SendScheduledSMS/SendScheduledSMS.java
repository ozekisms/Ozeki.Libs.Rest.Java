package SendScheduledSMS;

import Ozeki.Libs.Rest.*;
import java.time.LocalDateTime;

public class SendScheduledSMS {
    public static void main(String[] args) {

        var configuration = new Configuration();
        configuration.Username = "http_user";
        configuration.Password = "qwe123";
        configuration.BaseUrl = "http://127.0.0.1:9509/api";

        var msg = new Message();
        msg.ToAddress = "+36201111111";
        msg.Text = "Hello world!";
        msg.TimeToSend = LocalDateTime.parse("2021-06-11T13:52:00");

        var api = new MessageApi(configuration);
        
        var result = api.Send(msg);
        
        System.out.println(result);
    }
}
