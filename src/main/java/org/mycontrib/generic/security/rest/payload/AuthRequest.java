package org.mycontrib.generic.security.rest.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class AuthRequest {
	private String username;
	private String password;
	
}
