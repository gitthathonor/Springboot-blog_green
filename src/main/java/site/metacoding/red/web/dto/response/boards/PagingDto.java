package site.metacoding.red.web.dto.response.boards;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PagingDto {
	private Integer blockPage; // 전체 페이지 중에 현재 페이지가 어디인지 1~5 => 1, 6~10 => 2...
	private Integer blockCount; // 상수, 한 블록에 몇 개의 숫자를 띄울 것인지
	private Integer startPageNum; // 블록의 첫번째 숫자 -> ex)if(blockCount == 5) => 1, 6, 11...
	private Integer lastPageNum; // 블록의 마지막 숫자 -> ex)if(blockCount == 5) => 5, 10, 15...
	private Integer totalCount; // 게시글 전체 갯수
	private Integer totalPage; // 페이지 전체 갯수
	private Integer currentPage; // 현재 페이지
	private boolean isLast; // true -> next disabled
	private boolean isFirst; // true -> prev disabled
}
