package katachi.spring.exercise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * Spring Securityの設定を行うためのJavaConfigクラスです。
 * EnableWebSecurityアノテーションを使用してSpring Securityを有効にし、セキュリティ設定を行います。
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/**
	 * パスワードのエンコードを行うためのPasswordEncoderのBeanを定義します。
	 * BCryptPasswordEncoderを使用してパスワードのエンコードを行います。
	 *
	 * @return BCryptPasswordEncoderのインスタンス
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * MvcRequestMatcher.BuilderのBeanを定義します。
	 * MvcRequestMatcher.Builderは、HTTPリクエストのパターンに基づいてマッチングを行います。
	 *
	 * @param introspector HandlerMappingIntrospectorのBean
	 * @return MvcRequestMatcher.Builderのインスタンス
	 */
	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
		return new MvcRequestMatcher.Builder(introspector);
	}

	/**
	 * ログアウト成功時の処理を行うためのLogoutSuccessHandlerのBeanを定義します。
	 * CustomLogoutSuccessHandlerクラスを使用してカスタムのログアウト成功ハンドラを提供します。
	 *
	 * @return CustomLogoutSuccessHandlerのインスタンス
	 */
	@Bean
	LogoutSuccessHandler logoutSuccessHandler() {
		return new CustomLogoutSuccessHandler();
	}

	/**
	 * セキュリティフィルターチェーンを構築するためのBeanを定義します。
	 * HttpSecurityを使用して、ログインやログアウトなどの各種セキュリティ設定を行います。
	 *
	 * @param http HttpSecurityのインスタンス
	 * @param mvc MvcRequestMatcher.Builderのインスタンス
	 * @return SecurityFilterChainのインスタンス
	 * @throws Exception セキュリティ設定に関する例外
	 */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
		// 各種セキュリティ設定を行います

		// ログイン不要ページの設定
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(mvc.pattern("/admin/**")).hasAuthority("ROLE_ADMIN")
				.requestMatchers(mvc.pattern("/account/**")).authenticated()
				.requestMatchers(mvc.pattern("/h2-console/**")).hasAuthority("ROLE_ADMIN") // H2コンソールへのアクセス制限
				.anyRequest().permitAll());

		// ログイン処理の設定
		http.formLogin(login -> login
				.loginProcessingUrl("/login")
				.loginPage("/login")
				.failureUrl("/login?error")
				.usernameParameter("eMail")
				.passwordParameter("password")
				.defaultSuccessUrl("/redirect")
				.permitAll());

		// ログアウト処理の設定
		http.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				.logoutSuccessHandler(logoutSuccessHandler())
				.invalidateHttpSession(true)
				.permitAll());

		// H2コンソールのためにCSRFを無効化
		http.csrf(csrf -> csrf.ignoringRequestMatchers(mvc.pattern("/h2-console/**")));

		// H2コンソールのフレームオプション無効化
		http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

		return http.build();
	}

}
