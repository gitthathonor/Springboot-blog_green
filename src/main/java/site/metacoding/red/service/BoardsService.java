package site.metacoding.red.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.boards.Likes;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.web.dto.request.boards.UpdateDto;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.boards.LikeDto;
import site.metacoding.red.web.dto.response.boards.MainDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;

//Transaction을 관리해준다.
@Service
@RequiredArgsConstructor
public class BoardsService {
	
	private final BoardsDao boardsDao;
	
	public void 글쓰기(WriteDto writeDto, Users principal) {
		boardsDao.insert(writeDto.toEntity(principal.getId()));
	}
	
	public PagingDto 게시글목록보기(Integer page, String keyword) {
		
		if (page == null) {
			page = 0;
		}
		
		int startNum = page * 5;
		
		List<MainDto> boardsList = boardsDao.findAll(startNum, keyword);
		PagingDto pagingDto = boardsDao.paging(page, keyword);
		
		if (boardsList.size() == 0) {
			pagingDto.setLast(true);
			pagingDto.setFirst(true);
		}
		pagingDto.makeBlockInfo(keyword);
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
	
	@Transactional(rollbackFor = RuntimeException.class)
	public void 삭제하기(Integer id) {
		boardsDao.findById(id);
		boardsDao.deleteById(id);
	}
	
	// 좋아요
	public LikeDto 게시글좋아요(Likes likes) {
		System.out.println("service 시작");
		boardsDao.insertLike(likes);
		return boardsDao.showLikeCount(likes.getUsersId(), likes.getBoardsId());
	}
	
	// 좋아요 취소
}
