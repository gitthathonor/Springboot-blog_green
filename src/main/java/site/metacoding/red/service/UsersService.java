package site.metacoding.red.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.domain.users.UsersDao;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;
import site.metacoding.red.web.dto.request.users.UpdateDto;


@RequiredArgsConstructor
@Service
public class UsersService {
	
	private final UsersDao usersDao;
	private final BoardsDao boardsDao;
	
	public void 회원가입(JoinDto joinDto) { // Dto(username, password, email)
		// 1. DTO를 Entity로 변경하는 코드
		Users users = joinDto.toEntity();
		// 2. Entity로 DB 수행
		usersDao.insert(users);
	}
	
	public Users 로그인(LoginDto loginDto) { // Dto(username, password)
		Users usersPS = usersDao.findByUsername(loginDto.getUsername());
		
		if(usersPS == null) {
			return null;
		}
		
		// if로 usersPS의 password와 Dto password 비교
		if(!(usersPS.getPassword().equals(loginDto.getPassword()))) {
			return null;
		}
		return usersPS;
	}
	
	public Users 회원수정(Integer id, UpdateDto updateDto) { // id, Dto(password, email)
		// 1. 영속화
		Users usersPS = usersDao.findById(id);
		// 2. 영속화된 객체 변경
		usersPS.updateUsers(updateDto);
		// 3. DB 수행
		usersDao.update(usersPS);
		
		return usersPS;
	}
	
	// Users-delete, Boards - update
	@Transactional(rollbackFor = RuntimeException.class)
	public void 회원탈퇴(Integer id) {  
		usersDao.deleteById(id);
		// boardsDao.해당회원이적은 글을 모두 찾아서 usersId를 null로 업데이트(); // MyBatis에서 for문을 돌리는 구문을 확인해보자
		boardsDao.updateNullData(id);
	} 
	
	//DB에 접근할 필요가 없는 기능이라서 Controller에 작성한다. 물론 DB와 상관없는 기능이라고 Service에 다 안 쓰지는않는다.
	//HttpServletRequest와 HttpServletResponse 객체를 통해서 하는데 그 책임은 Controller에게 있다. (Session관리 포함)
	//public void 로그아웃() {} 
	
	public boolean 유저네임중복확인(String username) {
		Users usersPS = usersDao.findByUsername(username);
		
		// 있으면 true, 없으면 false를 리턴
		if(usersPS == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public Users 회원정보보기(Integer id) {
		Users usersPS = usersDao.findById(id);
		return usersPS;
	}
	
}

