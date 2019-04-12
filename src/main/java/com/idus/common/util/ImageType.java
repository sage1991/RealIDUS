package com.idus.common.util;

public enum ImageType {
	
	USERTHUMBNAIL,
	PIECEIMAGE,
	PIECEDETAILIMAGE,
	POSTIMAGE;

	@Override
	public String toString() {
		switch(this) {
		case USERTHUMBNAIL : 
			return "thumbnail/";
		case PIECEIMAGE : 
			return "piece/pieceImage/";
		case PIECEDETAILIMAGE : 
			return "piece/pieceDetail/";
		case POSTIMAGE : 
			return "post/postDetail/";
		default :
			return "ect/";
		}
	}
	
}