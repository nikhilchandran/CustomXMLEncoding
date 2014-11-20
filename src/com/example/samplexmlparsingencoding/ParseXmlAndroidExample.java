package com.example.samplexmlparsingencoding;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.example.samplexmlparsingencoding.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class ParseXmlAndroidExample extends Activity {
	
    private String xmlInput;
    private TextView output;
     
    
    static Map<String,String> html_specialchars_table = new Hashtable<String,String>();
    static {
            html_specialchars_table.put("&lt;","<");
            html_specialchars_table.put("&gt;",">");
            html_specialchars_table.put("&amp;","&");
            html_specialchars_table.put("&quot;","\"");
    }
    static String htmlspecialchars_decode_ENT_NOQUOTES(String s){
            Enumeration en = ((Hashtable<String, String>) html_specialchars_table).keys();
            while(en.hasMoreElements()){
                    String key = (String) en.nextElement();
                    String val = html_specialchars_table.get(key);
                    s = s.replaceAll(key, val);
            }
            return s;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         
        output = (TextView) findViewById(R.id.output);
       
        String XMLData =
                     "<cs> <o n=\"authenticationcode\" v=\"0ac2c000fdef106588dc65129e94baa3\"></o> <c n=\"my_Orders\"></c> </cs>" ;
        
        
        Ion.with(getApplicationContext())
        .load("http://beta.emiratesgas.ae/remoteconnect/default.asp")
        .setHeader("Content-Type", "application/x-www-form-urlencoded")
        .setBodyParameter("xml", XMLData)
        .setBodyParameter("r", "2")
        .asString()
        .setCallback(new FutureCallback<String>() {
			
			@Override
			public void onCompleted(Exception arg0, String arg1) {
				// TODO Auto-generated method stub
				if(arg0!=null){
					
					Log.e("error downloading", arg0.toString());
				}else {

//					StringEscapeUtils.unescapeHtml() 
					//xmlInput = 	URLDecoder.encode(arg1, "UTF-8");
				xmlInput = 	htmlspecialchars_decode_ENT_NOQUOTES(arg1);
//					xmlInput = URLDecoder.decode(xmlInput);
					
//						try {
//							xmlInput = new String(arg1.getBytes("UTF-8"),"ASCII");
//						} catch (UnsupportedEncodingException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						
					
					String dataToBeParsed = "Click on button to parse XML.\n\n XML DATA : \n\n"+xmlInput;
			        output.setText(dataToBeParsed);
				}
				
			}
		});
                    
    }
}
