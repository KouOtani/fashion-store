package katachi.spring.exercise.form;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignupForm {

    @NotBlank(groups = ValidGroup1.class)
    @Email(groups = ValidGroup2.class)
    private String eMail;

    @NotBlank(groups = ValidGroup1.class)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", groups = ValidGroup2.class)
    @Length(min = 8, groups = ValidGroup2.class)
    private String password;

    @NotBlank(groups = ValidGroup1.class)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", groups = ValidGroup2.class)
    @Length(min = 8, groups = ValidGroup2.class)
    private String rePassword;

    @NotBlank(groups = ValidGroup1.class)
    private String userName;

    @NotBlank(groups = ValidGroup1.class)
    private String furigana;

    @NotBlank(groups = ValidGroup1.class)
    @Pattern(regexp = "^\\d{3}-\\d{4}$", groups = ValidGroup2.class)
    private String postalCode;

    @NotNull(groups = ValidGroup1.class)
    private String prefectures;

    @NotBlank(groups = ValidGroup1.class)
    private String city;

    @NotBlank(groups = ValidGroup1.class)
    private String townName;

    private String others;

    @NotBlank(groups = ValidGroup1.class)
    @Pattern(regexp = "^0\\d{9,10}$", groups = ValidGroup2.class)
    private String phoneNumber;

    @NotNull(groups = ValidGroup1.class)
    private Integer gender;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @NotNull(groups = ValidGroup1.class)
    private Date birthday;

}
