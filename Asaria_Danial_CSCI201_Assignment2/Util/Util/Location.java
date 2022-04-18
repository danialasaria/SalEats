package Util;

public class Location {
    String address1;
    String address2;
    String address3;
    String city;
    String zip_code;
    String country;
    String state;
    String[] display_address;
    String completeaddress;
    
    public String getLocation()
    {
    	String location = "";
    	for(int i=0;i<display_address.length;i++)
    	{
    		location += display_address[i];
    		if(i!=display_address.length-1)
    		{
        		location += ", ";
    		}    		
    	}
    	return location;
    }
}
