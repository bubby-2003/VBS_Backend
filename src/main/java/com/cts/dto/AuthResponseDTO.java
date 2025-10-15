
package com.cts.dto;

import com.cts.enums.AuthRole;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String email;
    private AuthRole role;
}
