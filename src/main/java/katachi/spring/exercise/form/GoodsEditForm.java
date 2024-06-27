package katachi.spring.exercise.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GoodsEditForm {

	private Integer id; // 商品ID

	@NotBlank(groups = ValidGroup1.class)
	private String goodsName; // 商品名

	@NotBlank(groups = ValidGroup1.class)
	private String description; // 商品の説明

	@NotNull(groups = ValidGroup1.class)
	private Integer price; // 商品の価格

	@NotNull(groups = ValidGroup1.class)
	private MultipartFile imageFile; // 商品の画像ファイル

	private String imageUrl; // 商品の画像URL

}
