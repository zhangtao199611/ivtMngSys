package com.example.ivt_mng_sys.Util;

public class PageModel {

	public int pageSize;// 每页数量
	public int currentPage;// 当前页
	public int allCount;// 总数
	public int start;//开始
	public PageModel(int pageSize, int currentPage, int totalCount) {
		super();
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.allCount = totalCount;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public PageModel() {
		super();
	}



	public int getStart() {
		return (this.currentPage - 1) * this.pageSize;
	}

	@Override
	public String toString() {
		return "PageModel [pageSize=" + pageSize + ", currentPage=" + currentPage + ", allCount=" + allCount + "]";
	}

	public int getEnd() {
		return pageSize * (currentPage - 1)  + pageSize;
	}

	// 鑾峰緱椤电爜鏁�
	public int getAllPage() {
	  	return (this.allCount - 1) / this.pageSize + 1;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getAllCount() {
		return allCount;
	}

	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
public static void main(String[] args) {
	 
	
}
}
