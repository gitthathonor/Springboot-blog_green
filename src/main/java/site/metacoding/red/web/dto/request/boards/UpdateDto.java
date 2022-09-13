package site.metacoding.red.web.dto.request.boards;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import site.metacoding.red.domain.boards.Boards;

@Getter
@Setter
@AllArgsConstructor
public class UpdateDto {
	private String title;
	private String content;
	
	public Boards toEntity(Integer id) {
		Boards boards = new Boards(id, this.title, this.content);
		return boards;
	}
	
}
