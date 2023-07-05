package haza.demo.domain;

import java.sql.Date;

public class WorkList {
	private Long workNo;
	private Date date;
	private String work;
	private String memo;
	private Boolean workyn = false;
	

	public void setWorkyn(Boolean workyn) {
		this.workyn = workyn;
	}

	public WorkList() {}
	
	public WorkList(String work, String memo, boolean workyn) {
		this.work = work;
		this.memo = memo;
		this.workyn = workyn;
	}

	public final String getWork() {
		return work;
	}

	public final void setWork(String work) {
		this.work = work;
	}

	public final String getMemo() {
		return memo;
	}

	public final void setMemo(String memo) {
		this.memo = memo;
	}
	public Boolean getWorkyn() {
		return workyn;
	}

	public final boolean isWorkyn() {
		return workyn;
	}

	public void setWorkyn(boolean workyn) {
		this.workyn = workyn;
	}

	public Long getWorkNo() {
		return workNo;
	}

	public void setWorkNo(Long workNo) {
		this.workNo = workNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
