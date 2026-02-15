package io.github.hjle.member;

import io.github.hjle.member.dto.MemberEntity;
import io.github.hjle.member.dto.request.SignUpRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberEntity signUp(SignUpRequest request) {
        MemberEntity newEntity = MemberEntity
                .builder()
                .userId(request.getUserId())
                .email(request.getEmail())
                .name(request.getName())
                .build();
        return memberRepository.save(newEntity);
    }

    public MemberEntity getMember(String userId) {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. userId=" + userId));
    }
}
