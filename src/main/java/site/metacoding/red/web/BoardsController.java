package site.metacoding.red.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.domain.loves.Loves;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.service.BoardsService;
import site.metacoding.red.web.dto.request.boards.UpdateDto;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.CMRespDto;
import site.metacoding.red.web.dto.response.boards.DetailDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;
import site.metacoding.red.web.dto.response.loves.LovesDto;

@Controller
@RequiredArgsConstructor
public class BoardsController {
	
	private final BoardsService boardsService;
	private final HttpSession session;
	
	// 꼭 패키지를 따로 빼서 사용할 필요가 없다.
	// 이런 기능은 작은 기능이기 때문에 상황에 맞게 끼워넣으면 된다.
	// 일반적으로는 빼서 사용하는 것이 좋긴 하다.
	// 어떤 게시글을 누가 좋아하는지 (boardsId, usersId)
	@PostMapping("/s/api/boards/{id}/loves") // 주소의 가독성이 좋아서 id를 넣었다. 프로토콜 대로 하면 /loves로 해서 http의 body로 boardsId를 받아야 한다. 중점을 두는 거에 따라 다르다.
	public @ResponseBody CMRespDto<?> insertLoves(@PathVariable Integer id) {
		Users principal = (Users) session.getAttribute("principal");
		if(principal == null) {
			return new CMRespDto<>(-1, "로그인 되지 않았습니다.", null);
		}
		Loves loves = new Loves(principal.getId(),id);
		boardsService.좋아요(loves);
		return new CMRespDto<>(1, "좋아요 성공", null);
	}
	
	@DeleteMapping("/s/api/boards/{id}/loves")
	public @ResponseBody CMRespDto<?> deleteLoves(@PathVariable Integer id, @RequestBody Loves loves) {
		Users principal = (Users) session.getAttribute("principal");
		Loves deleteLove = new Loves(loves.getUsersId(), loves.getBoardsId());
		
		if(principal == null) {
			return new CMRespDto<>(-1, "로그인 되지 않았습니다.", null);
		}
		boardsService.좋아요취소(deleteLove);
		return new CMRespDto<>(1, "좋아요 취소 성공", null);
	}
	
	@GetMapping("/s/boards/writeForm")
	public String writeForm() {
		Users principal = (Users) session.getAttribute("principal");
		if (principal == null) {
			return "redirect:/loginForm";
		}
		return "boards/writeForm";
	}
	
	@PostMapping("/s/api/boards")
	public @ResponseBody CMRespDto<?> write(@RequestBody WriteDto writeDto) {
		Users principal = (Users) session.getAttribute("principal");
		boardsService.글쓰기(writeDto, principal);
		return new CMRespDto<>(1, "글쓰기 성공", null);
	}
	
	@GetMapping("/boards/{id}")
	public String getBoardsDetail(@PathVariable Integer id, Model model) {
		Users principal = (Users) session.getAttribute("principal");
		if(principal == null) {
			model.addAttribute("detailDto", boardsService.게시글상세보기(0, id));
		} else {
			DetailDto detailDto = boardsService.게시글상세보기(principal.getId(), id);
			model.addAttribute("detailDto", detailDto);
			//session.setAttribute("lovesId", detailDto.getLovesDto().getLovesId());
		}
		return "boards/detail";
	}

	
	@GetMapping({"/","/boards"})
	public String getBoardsList(Model model, Integer page, String keyword) {
		PagingDto pagingDto = boardsService.게시글목록보기(page, keyword);
		model.addAttribute("paging", pagingDto);
		
		Map<String, Object> referrer = new HashMap<>();
		referrer.put("page", pagingDto.getCurrentPage());
		referrer.put("keyword", pagingDto.getKeyword());
		model.addAttribute("keyword", keyword);
		session.setAttribute("referrer", referrer);
		return "boards/main";
	}
	
	@GetMapping("/s/boards/{id}/updateForm")
	public String updateForm(@PathVariable Integer id, Model model) {
		Boards boardsPS = boardsService.게시글수정화면데이터(id);
		model.addAttribute("boards", boardsPS);
		return "boards/updateForm";
	}
	
	@PutMapping("/s/api/boards/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable Integer id, @RequestBody UpdateDto updateDto) {
		boardsService.수정하기(id, updateDto);
		return new CMRespDto<>(1, "게시글 수정 성공", null);
	}
	

	@DeleteMapping("/s/api/boards/{id}")
	public @ResponseBody CMRespDto<?> deleteBoards(@PathVariable Integer id, Integer page, String keyword, Model model) {
		boardsService.삭제하기(id);
		return new CMRespDto<>(1, "게시글 삭제완료", null);
	}
}
