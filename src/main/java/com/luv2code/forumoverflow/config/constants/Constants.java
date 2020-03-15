package com.luv2code.forumoverflow.config.constants;

import org.springframework.http.MediaType;

/**
 * Created by lzugaj on Friday, February 2020
 */

public final class Constants {

	public static final String MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE = "application/vnd.com.luv2code.api.v1+json";

	public static final String MEDIA_TYPE_FORUM_OVERFLOW_API_V1_UTF8_VALUE = MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE + ";charset=UTF-8";

	public static final MediaType FORUM_OVERFLOW_API_V1 = MediaType.valueOf(MEDIA_TYPE_FORUM_OVERFLOW_API_V1_UTF8_VALUE);

	public static final MediaType FORUM_OVERFLOW_API_V1_UTF8 = MediaType.valueOf(MEDIA_TYPE_FORUM_OVERFLOW_API_V1_UTF8_VALUE);

	private Constants() {
		// Private default constructor
	}
}

