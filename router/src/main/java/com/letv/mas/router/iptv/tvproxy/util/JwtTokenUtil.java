package com.letv.mas.router.iptv.tvproxy.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leeco on 18/10/15.
 */
@Component
public class JwtTokenUtil {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-time:3600}")
    private long expireTime = 3600; // 1hr

    private static final String CLAIM_KEY_CODE = "code";
    private static final String CLAIM_KEY_DATE = "date";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * 根据appCode生成签名
     * @param code
     * @return
     */
    public String genToken(String code) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expireTime * 1000);
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put(CLAIM_KEY_CODE, code);
        claims.put(CLAIM_KEY_DATE, expiration);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * 根据token（可直接为http请求头里Authorization项带Bearer的值）及code进行验证
     *
     * @param token: 接入平台为app生成的签名
     * @param code:  接入平台分配的appCode
     * @return
     */
    public boolean validateToken(String token, String code) {
        boolean ret = false;

        if (StringUtils.isBlank(token)) {
            return ret;
        } else {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7, token.length());
            }
        }
        Claims claims = this.getClaimsFromToken(token);
        if (null != claims) {
            if (StringUtils.isNotBlank(code)) {
                ret = claims.get(CLAIM_KEY_CODE).equals(code);
            } else {
                ret = true;
            }
        }

        return ret;
    }

    /**
     * 根据token（可直接为http请求头里Authorization项带Bearer的值）刷新签名
     *
     * @param token: 接入平台为app生成的签名
     * @return
     */
    public String refreshToken(String token) {
        String ret = null;

        try {
            if (StringUtils.isBlank(token)) {
                return ret;
            } else {
                if (token.startsWith("Bearer ")) {
                    token = token.substring(7, token.length());
                }
            }

            final Claims claims = getClaimsFromToken(token);
            if (null != claims) {
                ret = this.genToken((String) claims.get(CLAIM_KEY_CODE));
            }
        } catch (Exception e) {
        }

        return ret;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }

        return claims;
    }
}
