package io.github.hjle.member.auth;

import com.hjle.common.exception.BusinessException;
import com.hjle.common.exception.ErrorCode;
import io.github.hjle.member.MemberRepository;
import io.github.hjle.member.dto.MemberEntity;
import io.github.hjle.member.dto.request.LoginRequest;
import io.github.hjle.member.dto.request.RefreshTokenRequest;
import io.github.hjle.member.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenResponse login(LoginRequest request) {
        MemberEntity member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.LOGIN_INPUT_INVALID));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BusinessException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getEmail(), member.getUserId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail());

        refreshTokenRepository.save(member.getEmail(), refreshToken);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenResponse refresh(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }

        String email = jwtTokenProvider.getEmailFromToken(refreshToken);
        String savedToken = refreshTokenRepository.findByEmail(email);

        if (savedToken == null || !savedToken.equals(refreshToken)) {
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }

        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_TOKEN));

        String newAccessToken = jwtTokenProvider.createAccessToken(member.getEmail(), member.getUserId());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(member.getEmail());

        refreshTokenRepository.save(email, newRefreshToken);

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public void logout(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }

        String email = jwtTokenProvider.getEmailFromToken(refreshToken);
        refreshTokenRepository.deleteByEmail(email);
    }
}
