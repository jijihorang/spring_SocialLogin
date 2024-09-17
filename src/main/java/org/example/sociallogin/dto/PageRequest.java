package org.example.sociallogin.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageRequest {

	private int page = 1;
	
	private int size = 10;

	private String type;
	private String keyword;

	public void setType(final String type) {
		this.type = type;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	public String[] getArr() {

		if(type == null || keyword == null || keyword.trim().length() == 0){
			return new String[0];
		}

		return type.split("");
	}


	public void setPage(int page) {
		this.page = page <= 0 ? 1 : page;
	}

	public void setSize(int size) {
		this.size = size <= 10 ? 10 : size >= 100 ? 100: size ;
	}
	
	public int getSkip() {
		
		return (this.page - 1) * 10;
	}
	
	
}
