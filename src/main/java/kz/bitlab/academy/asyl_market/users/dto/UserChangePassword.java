package kz.bitlab.academy.asyl_market.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePassword {

    private String oldPassword;
    private String newPassword;
    private String reNewPassword;

}

