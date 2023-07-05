package haza.demo.domain;

public class Member {

	private Long memberNo;
	private String name;
	private String id;
	private String password;
	
	public Member() {}
	
	public Member(String name, String id, String password) {
		this.name = name;
		this.id = id;
		this.password = password;
	}
	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public Long getmemberNo() {
		return memberNo;
	}

	public void setmemberNo(Long memberNo) {
		this.memberNo = memberNo;
	}
	
	
}
