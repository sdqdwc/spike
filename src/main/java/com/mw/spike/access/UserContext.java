package com.mw.spike.access;

import com.mw.spike.domain.SpikeUser;

public class UserContext {

	//当前线程绑定的
	private static ThreadLocal<SpikeUser> userHolder = new ThreadLocal<SpikeUser>();
	
	public static void setUser(SpikeUser user) {
		userHolder.set(user);
	}
	
	public static SpikeUser getUser() {
		return userHolder.get();
	}

	public static void remove(){
		userHolder.remove();
	}


}
