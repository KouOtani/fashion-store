package katachi.spring.exercise.domain.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import katachi.spring.exercise.domain.user.model.ExtendedUser;
import katachi.spring.exercise.domain.user.model.MUser;
import katachi.spring.exercise.domain.user.service.ShoppingService;

/**
 * Spring SecurityのUserDetailsServiceを実装して、ユーザー情報を取得するサービスクラスです。
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private ShoppingService shoppingService;

	@Override
	public UserDetails loadUserByUsername(String eMail) throws UsernameNotFoundException {

		//ユーザー情報
		MUser loginUser = shoppingService.getLoginUserByEmail(eMail);

		//ユーザーが存在しない場合
		if (loginUser == null) {
			throw new UsernameNotFoundException("user not found");
		}

		//権限List作成
		GrantedAuthority authority = new SimpleGrantedAuthority(loginUser.getRole());
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(authority);

		//UserDetail生成
		UserDetails userDetails = (UserDetails) new ExtendedUser(
				loginUser.getId(),
				loginUser.getEMail(),
				loginUser.getPassword(),
				authorities,
				loginUser.getUserName(),
				loginUser.getPhoneNumber(),
				loginUser.getPostalCode(),
				loginUser.getPrefectures(),
				loginUser.getCity(),
				loginUser.getTownName(),
				loginUser.getOthers());

		return userDetails;

	}
}
