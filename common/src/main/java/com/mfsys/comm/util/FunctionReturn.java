package com.mfsys.comm.util;


import com.mfsys.comm.constant.FunctionReturnType;
import com.mfsys.comm.constant.TransactionMessageCode;

public class FunctionReturn {

	protected int returnCode;
	protected String messageCode;
	
	public FunctionReturn() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FunctionReturn(FunctionReturnType type, TransactionMessageCode messageCode) {
		this.returnCode = type.getCode();
		this.messageCode = messageCode.getCode();
	}

	public int getReturnCode() {
		return returnCode;
	}
	
	public String getMessageCode() {
		return this.messageCode;
	}
	
}
