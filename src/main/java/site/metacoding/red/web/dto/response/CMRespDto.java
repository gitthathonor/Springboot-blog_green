package site.metacoding.red.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class  CMRespDto<T> {
	private Integer code; // 1 정상, -1 실패
	private String msg;
	private T data;
}
