package com.Project.BackEnd.Jwt.DTO;


import lombok.*;

@Builder
@Data
@AllArgsConstructor // 생성자 생성.
@Getter // 보안에 대해서 취약하지 않을까 생각이 듦.
@Setter // 이것도.
public class JwtDTO {
    private String accessToken; // 접근 토큰
    private String refreshToken;
}
