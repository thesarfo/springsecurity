package dev.thesarfo.springsecurity.model;


import dev.thesarfo.springsecurity.common.BaseDomainModel;
import dev.thesarfo.springsecurity.model.enums.UserStatus;
import dev.thesarfo.springsecurity.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseDomainModel {

    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    @Builder.Default
    private UserType userType = UserType.USER;

    @Builder.Default
    private UserStatus userStatus = UserStatus.ACTIVE;

}
