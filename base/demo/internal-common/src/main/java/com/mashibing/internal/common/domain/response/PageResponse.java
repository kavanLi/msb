package com.mashibing.internal.common.domain.response;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Joyce Huang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private long pageNo;
	private long pageSize;
	private Integer totalPage;
	private long total;
	private List<T> dataList;

	public PageResponse(long pageNo, long pageSize, long total, List<T> dataList) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.total = total;
		this.totalPage = totalPage();
		this.dataList = dataList;
	}

	private Integer totalPage() {
		if (total == 0 || pageSize == 0) {
			return 1;
		}
		return (int) Math.ceil((float) total / pageSize);
	}

}
