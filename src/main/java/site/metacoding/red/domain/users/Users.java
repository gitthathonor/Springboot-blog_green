package site.metacoding.red.domain.users;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.red.web.dto.request.users.UpdateDto;

@Setter
@Getter
@AllArgsConstructor
public class Users {
	private Integer id;
	private String username;
	private String password;
	private String email;
	private Timestamp createdAt;
	
	public Users(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public void updateUsers(UpdateDto updateDto) {
		this.password = updateDto.getPassword();
		this.email = updateDto.getEmail();
	}
}	
