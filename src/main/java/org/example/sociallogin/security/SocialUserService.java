package org.example.sociallogin.security;

import lombok.extern.log4j.Log4j2;
import org.example.sociallogin.dto.MemberDTO;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

// DefaultOAuth2UserService 상속받으면 OAuth2 로그인에 필요한 기본 기능을 재구현할 필요 없이, 필요한 부분만 오버라이드하여 사용
@Component
@Log4j2
public class SocialUserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("----------------");
        log.info(userRequest);

        // 클라이언트 애플리케이션의 이름을 가져오는 부분 (kakao)
        String service = userRequest.getClientRegistration().getClientName();

        // OAuth2 서버로부터 사용자 정보 로드
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 사용자 정보를 담고 있는 속성 맵 가져옴
        java.util.Map<String, Object> attributes = oAuth2User.getAttributes();

        // 사용자 정보의 각 속성과 값을 로그에 기록 (세부사항 확인 가능)
        attributes.forEach((key, value) -> {
            log.info("key : " + key + " value : " + value);
        });

        log.info("----------------");

        log.info(service);

        // 사용자 정보에서 kakao_account 속성을 LinkedHashMap으로 가져옴
        LinkedHashMap kakaoAccount = (LinkedHashMap) attributes.get("kakao_account");

        // kakaoAccount에서 email 정보를 추출
        String email = (String) kakaoAccount.get("email");

        log.info("email : " + email);

        log.info("----------------");

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMid(email);
        memberDTO.setMpw("$2a$12$0CQpyYK9qPj6BzIwC3u5k.kZFc28XNFNrdYE3JuKxilFSPnFLZyEy");
        memberDTO.setRoles(List.of("USER"));
        memberDTO.setProps(attributes);

        return memberDTO;
    }
}
