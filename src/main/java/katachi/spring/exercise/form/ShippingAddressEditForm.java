package katachi.spring.exercise.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ShippingAddressEditForm {

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

    private Boolean isEverytime;

}
