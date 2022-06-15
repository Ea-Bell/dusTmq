package com.example.dusTmq.service.oauth;

import com.example.dusTmq.domain.oauth.PrincipalDetails;
import com.example.dusTmq.domain.oauth.User;
import com.example.dusTmq.domain.user.Member;
import com.example.dusTmq.domain.user.Role;
import com.example.dusTmq.repository.member.MemberRepository;
import com.example.dusTmq.repository.oauth.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
//
//    private final MemberRepository memberRepository;
//    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder encoder;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//        String provider = userRequest.getClientRegistration().getRegistrationId(); // 현재 들어오는게 무언인지(test goole로 접속 할거임)
//        String providerId = oAuth2User.getAttribute("sub").toString(); // ???
//        String username = provider + "_" + providerId;          //사용자가 입력한 적은 없지만 만들어준다.
//
//        String uuid = UUID.randomUUID().toString().substring(0, 6);
//        String password = encoder.encode("패스워드" + uuid);
//
//        String email = oAuth2User.getAttribute("email").toString();
//        Role role = Role.ROLE_USER;
//
//        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("일치하는 값이 없습니다."));
//        User user = User.oauth2Register()
//                .username(username).password(password).email(email).role(role)
//                .provider(provider).providerId(providerId)
//                .build();
//        userRepository.save(user);
//
//
//        return new PrincipalDetails(user, oAuth2User.getAttributes());
//    }
}
