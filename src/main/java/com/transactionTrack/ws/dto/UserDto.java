package com.transactionTrack.ws.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "Email Kısmı Boş Olamaz!")
    @Email(message = "Geçerli bir email giriniz!")
    private String email;

    @NotBlank(message = "Parola Zorunludur!")
    @Size(min = 6, message = "Parola en az 6 karakterden oluşmalıdır!")
    private String password;

    @NotBlank(message = "İsim Zorunludur!")
    private String firstName;

    @NotBlank(message = "Soyisim Zorunludur!")
    private String lastName;

}
