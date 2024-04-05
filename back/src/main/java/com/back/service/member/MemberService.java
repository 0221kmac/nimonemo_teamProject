package com.back.service.member;

import com.back.domain.member.Member;
import com.back.dto.CartItemListDTO;
import com.back.dto.member.MemberJoinDTO;
import com.back.dto.member.MemberModifyDTO;
import com.back.dto.member.MemberSecurityDTO;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


//access Token을 파라미터로 받아서 로그인처리에 사용하는 MemverDTO를
//반환하는 getKakaoMember();
public interface MemberService {



    void join(MemberJoinDTO memberJoinDTO) ;

    MemberSecurityDTO getKakaoMember(String accessToken);

    void modifyMember(MemberModifyDTO memberModifyDTO); // 추가되었음

    default MemberSecurityDTO entityToDTO(Member member){

        MemberSecurityDTO dto = new MemberSecurityDTO(
        member.getEmail(),
                member.getPw(),
                member.getName(),
                member.getNumber(),
                member.getZipCode(),
                member.getNickname(),
                member.getStreetAddress(),
                member.getDetailAddress(),
                member.getMemberRoleList()
                        .stream()
                        .map(memberRole -> memberRole.name()).collect(Collectors.toList()));
                return dto;
    }

    public Map<String, String> validateHandling(Errors errors);


    Member getMemberInfo(String email,String pw);

    boolean checkNickname(String nickname);

    boolean checkEmail(String email);
}