package helpinghand.helpinghand2;



public class Booking {
    String id;
    String username;
    String professionalname;
    String userAddress;
    String userphone;
    String date,time,hour;
    public Booking(String id, String username, String professionalname, String userAddress, String userphone,
                   String status, String date, String time, String hour) {
        this.id=id;
        this.username = username;
        this.professionalname = professionalname;
        this.userAddress = userAddress;
        this.userphone=userphone;
        this.status=status;
        this.date=date;
        this.hour=hour;
        this.time=time;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }



    public Booking() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfessionalname() {
        return professionalname;
    }

    public void setProfessionalname(String professionalname) {
        this.professionalname = professionalname;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}
