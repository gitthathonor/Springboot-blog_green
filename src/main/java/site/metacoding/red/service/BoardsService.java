package site.metacoding.red.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.web.dto.request.boards.UpdateDto;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.boards.MainDto;

//Transaction을 관리해준다.
@Service
@RequiredArgsConstructor
public class BoardsService {
	
	private final BoardsDao boardsDao;
	
	public void 글쓰기(Integer usersId, WriteDto writeDto) {
		// 1. DTO를 Entity화 시키기
		Boards boards = writeDto.toEntity(usersId);
		// 2. Entity를 DB에 넣기
		boardsDao.insert(boards);
	}
	
	public List<MainDto> 게시글목록보기() {
		List<MainDto> boardsList = boardsDao.findMainBoards();
		return boardsList;
	}
	
	public List<Boards> 게시글목록보기2() {
		List<Boards> boardsList = boardsDao.findAll();
		return boardsList;
	}
	
	public Boards 게시글상세보기(Integer id) {
		Boards boards = boardsDao.findById(id);
		return boards;
	}
	
	
	public void 글수정하기(Integer id, UpdateDto updateDto) {
		Boards boardsPS = boardsDao.findById(id);
		boardsPS.updateBoards(updateDto);
		boardsDao.update(boardsPS);
	}
	
	
	public void 글삭제하기(Integer id) {
		boardsDao.deleteById(id);
	}
	
}
