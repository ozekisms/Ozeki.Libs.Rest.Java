package ReceiveSms;

import Ozeki.Libs.Rest.*;

public class ReceiveSms {

    public static void main(String[] args) {

        var configuration = new Configuration();
        configuration.Username = "http_user";
        configuration.Password = "qwe123";
        configuration.ApiUrl = "http://127.0.0.1:9509/api";

        var api = new MessageApi(configuration);
        
        var result = api.DownloadIncoming();
        
        System.out.println(result);
    }
}
