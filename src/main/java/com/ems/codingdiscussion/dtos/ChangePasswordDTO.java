package com.ems.codingdiscussion.dtos;

import lombok.Data;

@Data
public class ChangePasswordDTO {

    private String email;

    private String oldPassword;

    private String newPassword;

}
