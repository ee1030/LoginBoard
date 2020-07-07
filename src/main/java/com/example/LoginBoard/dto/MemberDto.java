package com.example.LoginBoard.dto;

import com.example.LoginBoard.domain.entity.MemberEntity;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {
    private Long id;

    @NotBlank(message = "이메일은 반드시 입력해야합니다.")
    @Email(message = "존재하지 않는 이메일 형식입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 반드시 입력해야합니다.")
    @Pattern(regexp = ".{8,20}", message = "비밀번호는 8자에서 20자 사이여야 합니다.")
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .id(id)
                .email(email)
                .password(password)
                .build();
    }

    @Builder
    public MemberDto(Long id, String email, String password, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
