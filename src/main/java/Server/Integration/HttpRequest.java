package Server.Integration;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpRequest {
    private final String method;
    private final String url;
    private final Map<String, String> headers;
    private final byte[] body;

    public HttpRequest(String method, String url, Map<String, String> headers, String body){
        this.method = method;
        this.url = url;
        this.headers = headers;
        this.body = body.getBytes(StandardCharsets.UTF_8);
    }

    public String toString(){
        return "method: " + method + "\n" +
                "url: " + url + "\n";
    }

    public String getUrl() {
        return url;
    }

    public String getMethod(){
        return method;
    }

    public int getContentLength(){
        String header =  headers.get("Content-Length:");

        if(header == null){
            return 0;
        }else{
            return Integer.valueOf(header);
        }
    }

    public byte[] getBody(){
        return body;
    }

}
