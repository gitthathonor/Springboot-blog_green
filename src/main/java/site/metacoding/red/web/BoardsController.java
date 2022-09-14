package site.metacoding.red.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.service.BoardsService;
import site.metacoding.red.web.dto.request.boards.UpdateDto;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;

@Controller
@RequiredArgsConstructor
public class BoardsController {
	
	private final BoardsService boardsService;
	private final HttpSession session;
	
	@GetMapping("/boards/writeForm")
	public String writeForm() {
		Users principal = (Users) session.getAttribute("principal");
		if (principal == null) {
			return "redirect:/loginForm";
		}
		return "boards/writeForm";
	}
	
	@PostMapping("/boards/write")
	public String write(WriteDto writeDto) {
		Users principal = (Users) session.getAttribute("principal");
		if (principal == null) {
			return "redirect:/loginForm";
		}
		boardsService.글쓰기(writeDto, principal);
		return "redirect:/";
	}
	
	@GetMapping("/boards/{id}")
	public String getBoardsDetail(@PathVariable Integer id, Model model) {
		Boards boards = boardsService.게시글상세보기(id);
		model.addAttribute("boards", boards);
		return "boards/detail";
	}

	
	@GetMapping({"/","/boards"})
	public String getBoardsList(Model model, Integer page, String keyword) {
		PagingDto pagingDto = boardsService.게시글목록보기(page, keyword);
		model.addAttribute("paging", pagingDto);
		model.addAttribute("keyword", keyword);
		return "boards/main";
	}
	
	
	@PutMapping("/boards/{id}")
	public String update(@PathVariable Integer id, UpdateDto updateDto) {
		boardsService.수정하기(id, updateDto);
		return "redirect:/boards/" + id;
	}

	@GetMapping("/boards/{id}/updateForm")
	public String updateForm(@PathVariable Integer id, Model model) {
		Boards boardsPS = boardsService.게시글상세보기(id);
		model.addAttribute("boards", boardsPS);
		return "boards/updateForm";
	}

	@DeleteMapping("/boards/{id}")
	public String deleteBoards(@PathVariable Integer id) {
		boardsService.삭제하기(id);
		return "redirect:/";
	}

}
