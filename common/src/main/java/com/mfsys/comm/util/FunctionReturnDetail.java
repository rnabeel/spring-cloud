package com.mfsys.comm.util;


import com.mfsys.comm.constant.FunctionReturnType;
import com.mfsys.comm.constant.TransactionMessageCode;

public class FunctionReturnDetail<T> extends FunctionReturn{

	private T[] arguments;
	
	
	
	public FunctionReturnDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FunctionReturnDetail(FunctionReturnType type, TransactionMessageCode messageCode , T[] arguments) {
		super(type, messageCode);
		this.arguments = arguments;
	}

	public T[] getArguments() {
		return arguments;
	}

	
}
