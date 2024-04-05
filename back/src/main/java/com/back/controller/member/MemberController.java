package com.back.controller.member;


import com.back.domain.Cart;
import com.back.domain.member.Member;
import com.back.dto.CartItemListDTO;
import com.back.dto.member.MailDTO;
import com.back.dto.member.MemberJoinDTO;
import com.back.dto.member.MemberModifyDTO;
import com.back.dto.member.MemberSecurityDTO;
import com.back.repository.CartItemRepository;
import com.back.repository.CartRepository;
import com.back.repository.MemberRepository;
import com.back.service.CartService;
import com.back.service.ProductService;
import com.back.service.member.MailService;
import com.back.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final MailService mailService;

    private final CartItemRepository cartItemRepository;

    private final CartRepository cartRepository;


    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public ResponseEntity<Map<String, String>> join(@Valid MemberJoinDTO memberJoinDTO, Errors errors) {
        if (errors.hasErrors()) {
            /* 유효성 통과 못한 필드와 메시지를 핸들링 */
            Map<String, String> validatorResult = memberService.validateHandling(errors);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validatorResult);
        }
        memberService.join(memberJoinDTO);

        return ResponseEntity.ok().build();
    }


    @PutMapping("/modify")
    public ResponseEntity<Map<String, String>> modify(@Valid MemberModifyDTO memberModifyDTO, Errors errors) {

        if (errors.hasErrors()) {
            /* 유효성 통과 못한 필드와 메시지를 핸들링 */
            Map<String, String> validatorResult = memberService.validateHandling(errors);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validatorResult);

        }

        //수정처리 완료
        memberService.modifyMember(memberModifyDTO);

        return ResponseEntity.ok().build();

    }


    @PostMapping("/sendCode")
    public ResponseEntity<String> checkMember(@RequestParam("email") String email) {
        boolean memberExists = mailService.existsByEmail(email); // 이메일을 확인한다
        if (memberExists) {//이메일이 존재할경우 인증코드 전송.
            MailDTO dto = mailService.createMail(email);
            mailService.mailSend(dto);
            return ResponseEntity.ok("이메일로 인증코드가 발급되었습니다.");
        } else {//이메일이 없다면 안함
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("입력하신 이메일에 해당하는 회원이 없습니다.");
        }
    }

    @PostMapping("/resetPw")
    @ResponseBody
    public ResponseEntity<String> resetPw(@RequestParam("email") String email,
                                          @RequestParam("verificationCode") String verificationCode,
                                          @RequestParam("newPassword") String newPassword) {
        // 이메일과 인증코드가 동시에 일치하는 회원이 존재하는지 확인
        boolean memberExists = memberRepository.existsByEmailAndVerificationCode(email, verificationCode);
        if (memberExists) {
            // 회원이 존재한다면 해당 회원을 찾아서 비밀번호를 변경
            Member member = memberRepository.findByEmailAndVerificationCode(email, verificationCode);
            member.changePw(passwordEncoder.encode(newPassword));
            member.changeVerificationCode(""); // 인증코드 초기화
            // 변경된 비밀번호를 저장
            memberRepository.save(member);
            log.info(email + "의 비밀번호가 변경되었습니다.");
            return ResponseEntity.ok("비밀번호가 성공적으로 재설정되었습니다.");
        } else {
            // 인증코드가 올바르지 않은 경우 UNAUTHORIZED 상태 코드와 메시지 반환
            log.info(email + "의 비밀번호가 변경 실패.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증코드가 올바르지 않습니다.");
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam("email") String email, @RequestParam("password") String password) {
        boolean emailAndPw = memberRepository.existsByEmailAndPw(email, password);
        if (emailAndPw) {
            Member member = memberRepository.findByEmail(email);
            Cart cart = cartRepository.findByOwner(member);
            log.info("-----------------------------------------------------");
            log.info(cart);

                if(cart != null) {
                    // 1. 해당 회원의 장바구니에 연결된 모든 장바구니 항목을 삭제
                    cartItemRepository.deleteAllByCart(cart);

                    // 2. 회원의 장바구니를 삭제
                    cartRepository.delete(cart);
                }

            // 3. 회원을 삭제
            memberRepository.delete(member);

            return ResponseEntity.ok("회원 탈퇴되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("비밀번호가 올바르지 않습니다.");
        }
    }







    @GetMapping("/check/nickname/{nickname}")
    public ResponseEntity<?> checkNickname(@PathVariable("nickname") String nickname) {
    log.info("Auth Service's Controller Layer :: Call checkNickname Method!");

        if (memberService.checkNickname(nickname)) {
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @GetMapping("/check/email/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable("email") String email) {
    log.info("Auth Service's Controller Layer :: Call checkNickname Method!");

        if (memberService.checkEmail(email)) {
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @PostMapping("/modifyPw")
    @ResponseBody
    public ResponseEntity<String> modifyPw(@RequestParam("email")String email,
                                          @RequestParam("pw")String pw,
                                          @RequestParam("newPw")String newPw) {
        // 이메일과 일치하는 회원이 존재하는지 확인
        Member member = memberRepository.findByEmail(email);
        // 비밀번호 검사하기
        if (passwordEncoder.matches(pw,member.getPw())) {
            member.changePw(passwordEncoder.encode(newPw));
            memberRepository.save(member);
            log.info(email + "의 비밀번호가 변경되었습니다.");
            return ResponseEntity.ok("비밀번호가 성공적으로 재설정되었습니다.");
        } else {
            log.info(email + "의 비밀번호가 변경 실패.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("기존 비밀번호가 일치하지 않습니다.");
        }
    }}