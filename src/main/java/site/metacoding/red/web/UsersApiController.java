package site.metacoding.red.web;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.domain.users.UsersDao;
import site.metacoding.red.service.UsersService;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;
import site.metacoding.red.web.dto.request.users.UpdateDto;

@RequiredArgsConstructor
@RestController
public class UsersApiController {

	private final UsersService usersService;
	private final HttpSession session;
	
	@GetMapping("/api/users/{id}")
	public Users getUsers(@PathVariable Integer id) {
		return usersService.회원정보보기(id);
	}
	
	@PostMapping("/api/join")
	public String join(String username, JoinDto joinDto) {
		boolean CheckingId = usersService.아이디중복확인(username);
		if(CheckingId == true) {
			return "아이디가 중복됩니다";
		}
		usersService.회원가입(joinDto);
		return "회원가입완료";
	}
	
	@PostMapping("/api/login") 
	public String login(LoginDto loginDto) {
		Users usersPS = usersService.로그인(loginDto);
		if(usersPS == null) {
			return "로그인 실패";
		}
		session.setAttribute("principal", usersPS);
		return "로그인 완료";
	}

	@PutMapping("/users/{id}/update")
	public String update(@PathVariable Integer id, UpdateDto updateDto) {
//		Users usersPS = (Users)session.getAttribute("principal");
//		if(id != usersPS.getId()) {
//			return "회원수정 하실 수 없습니다.";
//		}
		usersService.회원수정(id, updateDto);
		return "회원수정 완료";
	}
	
	
	@DeleteMapping("/api/users/{id}/delete")
	public String delete(@PathVariable Integer id) {
//		Users usersPS = (Users)session.getAttribute("principal");
//		if(id != usersPS.getId()) {
//			return "회원탈퇴 하실 수 없습니다.";
//		}
		usersService.회원탈퇴(id);
		return "회원탈퇴 완료";
	}
	
}
