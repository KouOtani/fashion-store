package katachi.spring.exercise.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVCの設定を行うためのJavaConfigクラスです。
 * WebMvcConfigurerを実装することで、Spring MVCのデフォルト設定をカスタマイズします。
 */
@Configuration
public class JavaConfig implements WebMvcConfigurer {

	/**
	 * ModelMapperのインスタンスをBeanとして定義します。
	 *
	 * @return ModelMapperのインスタンス
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
