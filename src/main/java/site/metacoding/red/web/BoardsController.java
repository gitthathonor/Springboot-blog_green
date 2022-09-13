package site.metacoding.red.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.service.BoardsService;
import site.metacoding.red.web.dto.request.boards.UpdateDto;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.boards.MainDto;

@RestController
@RequiredArgsConstructor
public class BoardsController {
	
	private final BoardsService boardsService;
	private final HttpSession session;
	
	@GetMapping("/boards/{id}")
	public Boards getBoardsDetail(@PathVariable Integer id) {
		return boardsService.게시글상세보기(id);
	}
	
	@GetMapping("/boards")
	public List<MainDto> getBoardsList() {
		return boardsService.게시글목록보기();
	}
	
	@PostMapping("/boards/{usersId}/write")
	public String write(@PathVariable Integer usersId, WriteDto writeDto) {
		boardsService.글쓰기(usersId, writeDto);
		return "글쓰기 완료";
	}
	
	
	@PutMapping("/boards/{id}/update")
	public String updateBoards(@PathVariable Integer id, UpdateDto updateDto) {
		boardsService.글수정하기(id, updateDto);
		return "글수정 완료";
	}
	
	@DeleteMapping("boards/{id}/delete")
	public String deleteBoards(@PathVariable Integer id) {
		boardsService.글삭제하기(id);
		return "글삭제 완료";
	}
}
