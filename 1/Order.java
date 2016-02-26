import java.util.*;

public class Order{
	
	private String type;
	private String shareName;
	private int number;
	private int price;
	private String response;
	
	Order(String rawInput){
		response = "DECLINED";

		if (rawInput == null) return;

		StringTokenizer st = new StringTokenizer(rawInput , " ");
		type = st.nextToken().toUpperCase();
		if(type.equals("SELL") || type.equals("BUY") ){
			shareName = st.nextToken();
			number = Integer.parseInt(st.nextToken());
			price = Integer.parseInt(st.nextToken());
		}
	}

	public String getType(){
		return type;
	}

	public void edit(ArrayList <SharesInfo> rawData ){
		response = "";
		for ( SharesInfo shi : rawData) {
			response += "(";
			response += shi.getName();
			response += ", ";
			response += Integer.toString(shi.getPrice());
			response += ")";
		}
	}
	
	public void checkValidatePrice(int recomendedPrice ){
		int dif = Math.abs( price - recomendedPrice);
		if(dif < (recomendedPrice/10))
			response = "APPROVED";
		else
			response = "DECLINED";
	}

	public String getShareName(){
		return shareName;
	}

	public String getResponse(){
		return response;
	}

}

