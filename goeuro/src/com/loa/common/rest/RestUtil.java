package com.loa.common.rest;

import java.io.BufferedReader;
 
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
 
import java.util.List;

import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
 
import org.json.JSONArray;
import org.json.JSONObject;
 

 









 
public class RestUtil {
	
	
	
	public static synchronized JSONArray httpGetCall1(String urlString, String jsonArrayName) {
		
        return httpCall(urlString, "GET",  jsonArrayName, null);
    }
	
	public static JSONArray httpGetCall(String urlString, String jsonArrayName) {
        return httpCall(urlString, "GET",  jsonArrayName, null);
    }
	public static JSONArray httpCall(String urlString, String type, String jsonArrayName) {
		return httpCall(urlString, type, jsonArrayName, null);
	}
	public static JSONArray httpCall(String urlString, String type, String jsonArrayName, List<NameValuePair> urlParameters) {
		try {
		//Thread.sleep(500);
			
		//urlString = "https://graph.facebook.com/v2.4/" + urlString;
		//logger.info("Requesting url : " +  urlString + " , request type : " + type + " , Parameters : " + urlParameters);
		System.out.println("Sending request : " + urlString);
        JSONArray result = null;
       HttpClient client = new DefaultHttpClient();
       HttpRequestBase request = new HttpGet(urlString);
       if( type.equals("DELETE")) {
    	   request = new HttpDelete(urlString);
       } else if( type.equals("POST")) {
       	   request = new HttpPost(urlString);
	       	try {
	       		//System.out.println("urlParameters : " + urlParameters);
				((HttpPost) request).setEntity(new UrlEncodedFormEntity(urlParameters));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//logger.error(e.getMessage(),e);
			//	TradeAction.logger.error(e.getMessage(),e);
			}
       }
       
      //request.addHeader("Authorization", "Bearer " + DefaultConstantTradeValue.ACCESS_TOKEN);
      //request.
        try {
            HttpResponse getResponse = client.execute(request);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            getResponse.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            //try {
	            for (String line = null; (line = reader.readLine()) != null;) {
	                builder.append(line).append("\n");
	            }
           // } catch (final ConnectionClosedException ignore) {
           // }
	            client.getClass();
          //  logger.info("Result : " + builder.toString());
       System.out.println(builder.toString());
            if( jsonArrayName != null ) {
            	try {
            		result = new JSONObject(builder.toString()).getJSONArray(jsonArrayName);
            	} catch (Exception e) {
            		result = new JSONArray();
            		JSONObject jobj = new JSONObject(builder.toString());
            		
            		try {
            			result.put(0, new String((String) jobj.get(jsonArrayName)));
            		} catch (Exception e1) {
            			result = new JSONArray();
                    	result.put(0, new JSONObject(builder.toString()));
            		}
            	}
            } else {
            	result = new JSONArray();
            	result.put(0, new JSONObject(builder.toString()));
            }
        } catch (ConnectionClosedException ignore) {
        } catch (Exception e) {
            e.printStackTrace();
         //   logger.error(e.getMessage(),e);
          //  TradeAction.logger.error(e.getMessage(),e);
        }
        return result;
		} catch (Exception e ){
			e.printStackTrace();
		//	logger.error(e.getMessage(),e);
			//TradeAction.logger.error(e.getMessage(),e);
		}
        return null;
    }
	
	
	public static JSONArray httpCall(String urlString, String type ) throws ConnectionClosedException {
		try {
		System.out.println("Sending request : " + urlString);
        JSONArray result = new JSONArray();
       HttpClient client = new DefaultHttpClient();
       HttpRequestBase request = new HttpGet(urlString);
       if( type.equals("DELETE")) {
    	   request = new HttpDelete(urlString);
       } else if( type.equals("POST")) {
       	   request = new HttpPost(urlString);
	        
       }
       try {
            HttpResponse getResponse = client.execute(request);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            getResponse.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
	            for (String line = null; (line = reader.readLine()) != null;) {
	                builder.append(line).append("\n");
	            }
	            client.getClass();
	            //System.out.println(builder.toString());
	            //  new JSONArray(builder.toString());
	            result = new JSONArray(builder.toString());
        } catch (ConnectionClosedException ignore) {
        	ignore.printStackTrace();
        	throw ignore;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
		} catch (Exception e ){
			e.printStackTrace();
		}
        return null;
    }
	
 
	 
	public static void main(String[] args) {
 
	  try {
		  	 
		  JSONArray result = new JSONArray();
		  result =  httpCall(  "http://api.goeuro.com/api/v2/position/suggest/en/Berlin" , "GET");
  		
  		
      	System.out.println(result);
      	
      	
	  } catch (Exception e) {
          e.printStackTrace();
          
      }
 
	}
 
}