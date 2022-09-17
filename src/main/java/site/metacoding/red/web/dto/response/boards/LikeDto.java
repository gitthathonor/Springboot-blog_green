package site.metacoding.red.web.dto.response.boards;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDto {
	private Integer usersId;
	private Integer boardsId;
	private boolean isLike;
}
