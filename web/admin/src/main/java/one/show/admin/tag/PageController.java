package one.show.admin.tag;



import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Norman711
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PageController {

	protected int currentPage = 1; // 当前页

	protected int totalPages = 0; // 总页数

	protected int pageSize = 20;// 每页20条数据
	
	protected int totalRows = 0; // 总数据数

	// protected int pageStartRow = currentPage * pageSize;// 每页的起始数
	//
	// protected int pageEndRow = currentPage * (pageSize + 1); // 每页显示数据的终止数

	protected int pageStartRow = 0;

	protected int pageEndRow = 0;

	protected boolean hasNextPage = false; // 是否有下一页

	protected boolean hasPreviousPage = false; // 是否有前一页

	private int previousPage, nextPage;// 保存上一页和下一页的值

	private String querySqlTemp = null; // 分页时临时保存查询的Sql条件

	Collection allPageNum = new ArrayList();

//	public void flip() {
//
//		totalPages = (totalRows - 1) / pageSize + 1;
//
//		/*
//		 * allPageNum.clear();
//		 * 
//		 * for (int i = 0; i < totalPages; i++) {
//		 * allPageNum.add(String.valueOf(i + 1)); }
//		 */
//
//		pageStartRow = pageSize * (currentPage - 1) + 1;
//		pageEndRow = pageSize * currentPage;
//
//		if (currentPage > 1) {
//			hasPreviousPage = true;
//		}
//
//		if (currentPage < totalPages) {
//			hasNextPage = true;
//		}
//	}

	/**
	 * 
	 */
	public PageController() {
		super();
	}

	public void update() {

		totalPages = (totalRows - 1) / pageSize + 1;
		if(totalPages == 1 || totalPages == 0) {
		    currentPage = 1;
		}
		
		for (int i = 0; i < totalPages; i++) {
			allPageNum.add(String.valueOf(i + 1));
		}

		pageStartRow = pageSize * (currentPage - 1);

		if (totalRows < pageSize) {
			pageEndRow = pageStartRow + totalRows;
		} else if (currentPage == totalPages) {
			pageEndRow = pageStartRow + (totalRows - (totalPages - 1) * pageSize);
		} else {
			pageEndRow = pageStartRow + pageSize;
		}

		if ((currentPage > 1) && (totalPages > 1)) {
			hasPreviousPage = true;
			previousPage = currentPage - 1;
		}else{
			hasPreviousPage = false;
			previousPage = currentPage;
		}

		if ((currentPage < totalPages) && (totalPages > 1)) {
			hasNextPage = true;
			nextPage = currentPage + 1;
		}else{
			hasNextPage = false;
			nextPage = currentPage;
		}
		
	}

	public String getLinkStr() {

		StringBuffer sb = new StringBuffer();
		sb.append("<< ");
		sb.append("");
		sb.append(" >>");

		return sb.toString();
	}

	// ArrayList arrayList;
	// Iterator it;

	/**
	 * @return Returns the currentPage.
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            The currentPage to set.
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return Returns the hasNextPage.
	 */
	public boolean isHasNextPage() {
		return hasNextPage;
	}

	/**
	 * @param hasNextPage
	 *            The hasNextPage to set.
	 */
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	/**
	 * @return Returns the hasPreviousPage.
	 */
	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	/**
	 * @param hasPreviousPage
	 *            The hasPreviousPage to set.
	 */
	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	/**
	 * @return Returns the pageEndRow.
	 */
	public int getPageEndRow() {
		return pageEndRow;
	}

	/**
	 * @param pageEndRow
	 *            The pageEndRow to set.
	 */
	public void setPageEndRow(int pageEndRow) {
		this.pageEndRow = pageEndRow;
	}

	/**
	 * @return Returns the pageSize.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            The pageSize to set.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return Returns the pageStartRow.
	 */
	public int getPageStartRow() {
		return pageSize * (currentPage - 1);
	}

	/**
	 * @param pageStartRow
	 *            The pageStartRow to set.
	 */
	public void setPageStartRow(int pageStartRow) {
		this.pageStartRow = pageStartRow;
	}

	/**
	 * @return Returns the totalPages.
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * @param totalPages
	 *            The totalPages to set.
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return Returns the totalRows.
	 */
	public int getTotalRows() {
		return totalRows;
	}

	/**
	 * @param totalRows
	 *            The totalRows to set.
	 */
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	/**
	 * @return Returns the allPageNum.
	 */
	public Collection getAllPageNum() {
		return allPageNum;
	}

	/**
	 * @param allPageNum
	 *            The allPageNum to set.
	 */
	public void setAllPageNum(Collection allPageNum) {
		this.allPageNum = allPageNum;
	}

	/**
	 * @return Returns the nextPage.
	 */
	public int getNextPage() {
		return nextPage;
	}

	/**
	 * @param nextPage
	 *            The nextPage to set.
	 */
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	/**
	 * @return Returns the previousPage.
	 */
	public int getPreviousPage() {
		return previousPage;
	}

	/**
	 * @param previousPage
	 *            The previousPage to set.
	 */
	public void setPreviousPage(int previousPage) {
		this.previousPage = previousPage;
	}

	/**
	 * @return Returns the querySqlTemp.
	 */
	public String getQuerySqlTemp() {
		return querySqlTemp;
	}

	/**
	 * @param querySqlTemp
	 *            The querySqlTemp to set.
	 */
	public void setQuerySqlTemp(String querySqlTemp) {
		this.querySqlTemp = querySqlTemp;
	}

	/**
	 * 获得分页语句
	 * 
	 * @param strQuery
	 *            String
	 * @param page
	 *            PageController
	 * @return String
	 */
	public String getQuerySqlByPage(String strQuery) {
		String querySql = null;
		querySql = "SELECT b.* from (select @rownum:=@rownum+1 as rownum,a.* FROM("
				+ strQuery
				+ ") a )b where b.rownum > "
				+ getPageStartRow()
				+ " and b.rownum <=" + getPageEndRow();

		return querySql;
	}

}
