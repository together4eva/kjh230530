package org.kosta.product.model;

public class Pagination {
	private int startPage;
	private int endPage;
	private boolean previousPageGroup;
	private boolean nextPageGroup;
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPreviousPageGroup() {
		return previousPageGroup;
	}
	public void setPreviousPageGroup(boolean previousPageGroup) {
		this.previousPageGroup = previousPageGroup;
	}
	public boolean isNextPageGroup() {
		return nextPageGroup;
	}
	public void setNextPageGroup(boolean nextPageGroup) {
		this.nextPageGroup = nextPageGroup;
	}
	
}
