package io.github.hjle.member;

import com.hjle.common.dto.response.ApiResponse;
import io.github.hjle.member.dto.MemberEntity;
import io.github.hjle.member.dto.request.SignUpRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemberEntity> signUp(@Valid @RequestBody SignUpRequest request) {
        MemberEntity response = memberService.signUp(request);
        return ApiResponse.success(response);
    }

    @GetMapping("/{id}")
    public ApiResponse<MemberEntity> getMember(@PathVariable String id) {
        MemberEntity response = memberService.getMember(id);
        return ApiResponse.success(response);
    }
}
