package helpinghand.helpinghand2.forUser;



public class Professional {
    String id;


    String name;
    String number;
    String address;
    String longitude;
    String latitude;
    String rate;
    String rating;
    String category;
    String district;
    String zone;
    String fburl;
    String address2, address3, address4;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVoter() {
        return voter;
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }

    String voter;

    public Professional(String name, String address, String number,
                        String latitude, String longitude, String rate,
                        String fburl) {
        this.name = name;
        this.number = number;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.rate = rate;
        this.fburl = fburl;
    }

    public String getFburl() {
        return fburl;
    }

    public void setFburl(String fburl) {
        this.fburl = fburl;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;

    public Professional(String id, String name, String number, String address, String address2, String address3, String address4, String longitude,
                        String latitude, String rate, String rating, String category,
                        String district, String zone, String fburl, String voter) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.address = address;
        this.address2 = address2;
        this.address3 = address3;
        this.address4 = address4;
        this.longitude = longitude;
        this.latitude = latitude;
        this.rate = rate;
        this.rating = rating;
        this.category = category;
        this.district = district;
        this.zone = zone;
        this.fburl = fburl;
        this.voter = voter;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Professional(String mname, String maddress, String mnumber) {
        name = mname;
        number = mnumber;
        address = maddress;
    }

    public Professional(String mname, String maddress, String mnumber, String mlongitude,
                        String mlatitude, String mrate, String mrating, String mcateogory) {
        name = mname;
        number = mnumber;
        address = maddress;
        latitude = mlatitude;
        longitude = mlongitude;
        rate = mrate;
        rating = mrating;
        category = mcateogory;
    }

    public Professional(String mlongitude, String mlatitude) {

        latitude = mlatitude;
        longitude = mlongitude;
    }

    public Professional() {
        System.out.println("no Constructo argument");
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String phone) {
        this.number = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }
}
