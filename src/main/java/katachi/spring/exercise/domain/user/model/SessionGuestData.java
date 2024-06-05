package katachi.spring.exercise.domain.user.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import katachi.spring.exercise.form.GuestSignupForm;
import lombok.Data;

/**
 * ゲスト情報をセッション内で保持するためのクラスです。
 */
@Data
@Component
@SessionScope
public class SessionGuestData implements Serializable {

	private static final long serialVersionUID = 1L;

	private GuestSignupForm guestData; // ゲスト情報

}