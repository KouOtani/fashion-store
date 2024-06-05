package katachi.spring.exercise.userwithcode;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserWithCode extends User {

    private Integer userId;
    private String eMail;
    private String userName;
    private String phoneNumber;
    private String postalCode;
    private String shippingAddress;

    public UserWithCode(Integer userId, String eMail, String password, Collection<? extends GrantedAuthority> authorities,
            String userName, String phoneNumber, String postalCode,
            String prefectures, String city, String townName, String others) {

        super(eMail, password, authorities);

        this.userId = userId;
        this.eMail = eMail;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
        this.shippingAddress = prefectures + city + townName;

        if (others != null) {
            this.shippingAddress += others;
        }

    }

    public Integer getUserId() {
        return userId;
    }

    public String geteMail() {
        return eMail;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

}
