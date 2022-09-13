package site.metacoding.red.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.web.dto.request.boards.UpdateDto;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.boards.MainDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;

//Transaction을 관리해준다.
@Service
@RequiredArgsConstructor
public class BoardsService {
	
	private final BoardsDao boardsDao;
	
	public void 글쓰기(Integer usersId, WriteDto writeDto) {
		boardsDao.insert(writeDto.toEntity(usersId));
	}
	
	public PagingDto 게시글목록보기(Integer page, String keyword) {
		if (page == null) {
			page = 0;
		}
		System.out.println("page : " + page);
		int startNum = page * 3;
		
		List<MainDto> boardsList = boardsDao.findAll(startNum, keyword);
		System.out.println("list가져오기 완료");
		
		PagingDto pagingDto = boardsDao.paging(page, keyword);
		System.out.println("PagingDto가져오기 완료");
		
		if (boardsList.size() == 0) {
			pagingDto.setFirst(true);
			pagingDto.setLast(true);
		}
		System.out.println(boardsList.size());
		
		pagingDto.setMainDtos(boardsList);
		
		return pagingDto;
	}
	
	
	public Boards 게시글상세보기(Integer id) {
		return boardsDao.findById(id);
	}
	
	
	public void 수정하기(Integer id, UpdateDto updateDto) {
		Boards boardsPS = boardsDao.findById(id);
		boardsPS.updateBoards(updateDto);
		boardsDao.update(boardsPS);
	}
	
	
	public void 삭제하기(Integer id) {
		boardsDao.deleteById(id);
	}
	
	
	
}
