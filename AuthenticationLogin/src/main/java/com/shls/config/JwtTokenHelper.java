package com.shls.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenHelper {
    //密钥
    public static final String SECRET = "sdjhakdhajdklsl;o653632";
    //过期时间:秒
    public static final int EXPIRE = 5;

    /**
     * 生成Token
     */
    public String createToken(LoggedUser user) throws Exception {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND, EXPIRE);
        Date expireDate = nowTime.getTime();

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String token = JWT.create()
                .withHeader(map)//头
                .withClaim("id", user.getId())
                .withClaim("type", user.getType())
                .withSubject(user.getLoginAccount()) //sub
                .withIssuedAt(new Date())//签名时间 iat
                .withExpiresAt(expireDate)//过期时间 exp
                .sign(Algorithm.HMAC256(SECRET));//签名
        return token;
    }


    /**
     * 验证Token
     */
    public LoggedUser parseToken(String token)throws Exception{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        LoggedUser user = null;
        try {
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claimMap = jwt.getClaims();

            user = new LoggedUser();
            user.setToken(token);
            user.setId(claimMap.get("id").asLong());
            user.setType(claimMap.get("type").asString());
            user.setLoginAccount(claimMap.get("sub").asString());
        }catch (Exception e){
            throw new RuntimeException("token已过期，请重新登录");
        }

        return user;
    }

    public static void main(String[] args) throws Exception {
        JwtTokenHelper jwt = new JwtTokenHelper();
        String token = jwt.createToken(new LoggedUser(10l, "accoutnt", "CLIENT", null));
        //System.out.println(token);

        LoggedUser user = jwt.parseToken(token);
        System.out.println(user.toString());
    }

}
