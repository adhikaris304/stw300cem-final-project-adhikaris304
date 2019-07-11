package helpinghand.helpinghand2.BLL;

import com.google.android.gms.common.api.Response;

public class LoginBBL {
    private String email;
    private String password;
    boolean isSuccess= false;

    public  LoginBBL(String email, String password){
        this.email=email;
        this.password=password;
    }

//    public boolean checkUser(){
//
//
//        try{
//            Response<LoginSignupResponse> imageResponseResponse = usersCall.execute();
//            if (imageResponseResponse.body().getSuccess()){
//                Url.Cookie=imageResponseResponse.headers();
//            }
//    }
}
