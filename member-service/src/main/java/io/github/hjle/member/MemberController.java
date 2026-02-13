package io.github.hjle.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/signup")
    public MemberEntity signup(@RequestBody MemberEntity member) {
        return memberRepository.save(member);
    }

    @GetMapping("/{userId}")
    public MemberEntity getMember(@PathVariable String userId) {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. userId=" + userId));
    }
}
