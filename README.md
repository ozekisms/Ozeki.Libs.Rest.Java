# Java sms library to send sms with http/rest/json

This Java sms library enables you to **send**, **receive** and **delete** with http requests.
The library uses HTTP Post requests and JSON encoded content to send the text
messages to the mobile network1. It connects to the HTTP SMS API of 
[Ozeki SMS gateway](https://ozeki-sms-gateway.com). This repository is better
for implementing SMS solutions then other alternatives, because it allows
you to use the same code to send SMS through an Android mobile, through
a high performance IP SMS connection or a GSM modem or modem pool. This
library gives you SMS service provider independence.

## What is Ozeki SMS Gateway 

The Ozeki SMS Gateway is a high performance (1000SMS/second) SMS Gateway software. It is very reliable and easy to master.
It runs on your own maching which results in high security for your data. This software can be installed on Window or Linux or an Android mobile phone. It provides an HTTP SMS API, that allow yout to connect to it from local or remote programs.The reason why companies use Ozeki SMS Gateway as their first point
of access to the mobile network, is because it provides service provider independence.Ozeki provides direct access
to the mobile network through wireless connections.

Download: [Ozeki SMS Gateway download page](https://ozeki-sms-gateway.com/p_727-download-sms-gateway.html)

Tutorial: [Java send sms sample and tutorial](https://ozeki-sms-gateway.com/p_834-java-send-sms-with-the-http-rest-api-code-sample.html)

## How to send sms from Java: 

**To send sms from Java**
1. [Download Ozeki SMS Gateway](https://ozeki-sms-gateway.com/p_727-download-sms-gateway.html)
2. [Connect Ozeki SMS Gateway to the mobile network](https://ozeki-sms-gateway.com/p_70-mobile-network-connections.html)
3. [Create an HTTP SMS API user](https://ozeki-sms-gateway.com/p_2102-create-an-http-sms-api-user-account.html)
4. Apache NetBeans
5. Put the code into a newly created Main.java file
6. Create the SMS by creating a new Message object
7. Use the Send method to send your message
8. Read the response message on the console
9. Check the logs in the SMS gateway

## How to use the code

To use the code you need to import the Ozeki.Libs.Rest sms library. This
sms library is also included in this repositry with it's full source code.
After the library is imported with the using statiment, you need to define
the username, password and the api url. You can create the username and 
password when you install an HTTP API user in your Ozeki SMS Gateway system.

The URL is the default http api URL to connect to your SMS gateway. If you
run the SMS gateway on the same computer where your Java code is runing, you
can use 127.0.0.1 as the ip address. You need to change this if you install
the sms gateway on a different computer (or mobile phone).


```
import Ozeki.Libs.Rest.*;
 
public class Main {
 
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
```

## Manual / API reference

To get a better understanding of the above **SMS code sample**, it is a good
idea to visit the webpage that explains this code in a more detailed way.
You can find videos, explanations and downloadable content on this URL.

Link: [How to send sms from Java](https://ozeki-sms-gateway.com/p_834-java-send-sms-with-the-http-rest-api-code-sample.html)


## How to send sms through your Android mobile phone

If you wish to [send SMS through your Android mobile phone from Java](https://android-sms-gateway.com/), 
you need to [install Ozeki SMS Gateway on your Android](https://ozeki-sms-gateway.com/p_2847-how-to-install-ozeki-sms-gateway-on-android.html) 
mobile phone. It is recommended to use an Android mobile phone with a minimum of 
4GB RAM and a quad core CPU. Most devices today meet these specs. The advantage
of using your mobile, is that it is quick to setup and it often allows you
to [send sms free of charge](https://android-sms-gateway.com/p_246-how-to-send-sms-free-of-charge.html).

[Android SMS Gateway](https://android-sms-gateway.com)

## Get started now

Don't waste any time, download the repository now, and send your first SMS!
