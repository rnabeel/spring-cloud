package com.mfsys.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserId implements Serializable {
    private static final long serialVersionUID = 1L;
    private String porOrgacode;
    private String email;
    private String userName;

}
