package Util;

public class Restaurant {
    /**
     * Here: all the needed class members and their getters and setters
     */
    String id;
    String name;
    String image_url;
    Integer review_count;
    Double rating;
    String price;
    String phone;
    //combine array 0 and 1 for location
    Location location;
    
//    public String getLocation()
//    {
//    	return location.display_address[0] + ", " + location.display_address[1];
//    }
    Categories[] categories;
    
    String url;
    
    
    public String getCategories() {
    String result = "";
    int size = categories.length;
      for(int i=0; i<size;i++)
      {
          result += categories[i].title;
          if(!(i==size-1)) {
              result += ", ";
          }
      }
      return result;
    }
}



//    public String getName() {
//        return name;
//    }
//    public String getimage_url() {
//        return image-;
//    }
//    public String getPhone() {
//        return phone;
//    }
//    public String getAddress() {
//        return address;
//    }
//    public int getChiralFrequency() {
//        return chiralFrequency;
//    }
//    public boolean getTimefall() {
//        return timefall;
//    }

    /**
     * overriding comparator for sorting
     */

//    @Override
//    public int compareTo(TimefallShelter compShelter) {
//        /* For Ascending order*/
//        return 0;
//    }

    /**
     * String representation of a shelter
     */
//    @Override
//    public String toString() {
//        String timefallinfo;
//        if(this.getTimefall() == false)
//        {
//            timefallinfo = "None";
//        }
//        else{
//            timefallinfo = "Current";
//        }
//        String representation =
//        "Shelter information:" + '\n' +
//        "- Chiral frequency: " + this.getChiralFrequency() + '\n' +
//        "- Timefall: " + timefallinfo + '\n' +
//        "- GUID: " + this.getGuid() + '\n' +
//        "- Name: " + this.getName() + '\n' +
//        "- Phone: " + this.getPhone() + '\n' +
//        "- Address: " + this.getAddress() + '\n';
//        return representation;
//    }
//}


