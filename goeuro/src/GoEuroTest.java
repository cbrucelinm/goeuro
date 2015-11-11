

import java.util.ArrayList;
import java.util.Date;

import org.apache.http.ConnectionClosedException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.loa.common.file.CsvUtil;
import com.loa.common.rest.RestUtil;

public class GoEuroTest {
	public static void main(String[] args) {
		try {
			if( args == null || args.length != 1 ) {
				usage("Invalid Input");
			}
			JSONArray result =  RestUtil.httpCall(  "http://api.goeuro.com/api/v2/position/suggest/en/" + args[0] , "GET");
			//JSONArray result = new JSONArray();
			//JSONArray  result =   RestUtil.httpCall(  "http://api.goeuro.com/api/v2/position/suggest/en/" + args[0] , "GET");
			  System.out.println("Result : " + result);
			CsvUtil csv = new CsvUtil();
			ArrayList<String> headings = new ArrayList<String>();
			headings.add("_id");
			headings.add("name");
			headings.add("type");
			headings.add("latitude");
			headings.add("longitude");
			csv.setHeadings(headings);
			
			int size = result.length();
			
			ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
			
			for(int i = 0 ; i < size ; i++  ) {
				ArrayList<String> data= new ArrayList<String>();
				JSONObject obj = result.getJSONObject(i);
				data.add(""+obj.opt("_id"));
				data.add(""+obj.getString("name"));
				data.add(""+obj.getString("type"));
				JSONObject position =  obj.getJSONObject("geo_position");
				data.add(""+position.opt("latitude"));
				data.add(""+position.opt("longitude"));
				values.add(data);
			}
			
			String fileName = "goEuroOutput_" + new Date().getTime() + ".csv";
			csv.setValues(values);
			csv.write(fileName);
		} catch (ConnectionClosedException e1) {
			System.out.println("Unable to connect to the server : "+ e1.getMessage());
			e1.printStackTrace();
		} catch ( Exception e) {
			//System.out.println("Unable to connect to the server : "+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void usage(String msg) {
		System.out.println(msg);
		System.out.println("Usage : java -jar GoEuroTest.jar <CITY_NAME>" );
		System.exit(1);
	}
}
