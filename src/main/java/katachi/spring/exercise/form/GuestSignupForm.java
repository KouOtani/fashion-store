package katachi.spring.exercise.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class GuestSignupForm {

	@NotBlank(groups = ValidGroup1.class)
	@Email(groups = ValidGroup2.class)
	private String eMail;

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

}
