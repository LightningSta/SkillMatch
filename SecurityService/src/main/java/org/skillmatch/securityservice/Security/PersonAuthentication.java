package org.skillmatch.securityservice.Security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonAuthentication{

    private String userName;
    private String role;
    private Boolean isActive;

}
