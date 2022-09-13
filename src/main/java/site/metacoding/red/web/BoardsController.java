package site.metacoding.red.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.service.BoardsService;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;

@Controller
@RequiredArgsConstructor
public class BoardsController {
	
	private final BoardsService boardsService;
	private final HttpSession session;
	
	@PostMapping("/boards/{usersId}/write")
	public String write(@PathVariable Integer usersId, WriteDto writeDto) {
		boardsService.글쓰기(usersId, writeDto);
		return "글쓰기 완료";
	}
	
	@GetMapping("/boards/{id}")
	public String getBoardsDetail(@PathVariable Integer id, Model model) {
		Boards boards = boardsService.게시글상세보기(id);
		model.addAttribute("boards", boards);
		return "boards/detail";
	}

	
	@GetMapping({"/","/boards"})
	public String getBoardsList(Model model, Integer page, String keyword) {
		System.out.println("controller성공");
		PagingDto pagingDto = boardsService.게시글목록보기(page, keyword);
		System.out.println("service성공");
		model.addAttribute("paging", pagingDto);
		model.addAttribute("keyword", keyword);
		return "boards/main";
	}
	
}
