package io.github.hjle.member.auth;

import io.github.hjle.member.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {
    private static final String KEY_PREFIX = "refresh_token:";
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtProperties jwtProperties;

    public void save(String email, String refreshToken) {
        redisTemplate.opsForValue().set(
                KEY_PREFIX + email,
                refreshToken,
                jwtProperties.getRefreshTokenExpiry(),
                TimeUnit.MILLISECONDS
        );
    }

    public String findByEmail(String email) {
        return redisTemplate.opsForValue().get(KEY_PREFIX + email);
    }

    public void deleteByEmail(String email) {
        redisTemplate.delete(KEY_PREFIX + email);
    }
}
