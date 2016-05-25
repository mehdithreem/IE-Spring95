package ir.stocks.domain;

public class DepositRequest {
	private String userId;
	private Integer amount;
	private Integer reqID;
	private Status status;

	public DepositRequest(String _id, Integer _amount) {
		userId = _id;
		amount = _amount;
		status = Status.PENDING;
	}

	public String getId() {
		return userId;
	}

	public Integer getAmount() {
		return amount;
	}

	public Integer getReqID() {
		return reqID;
	}

	public void setReqID(Integer reqID) {
		this.reqID = reqID;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}