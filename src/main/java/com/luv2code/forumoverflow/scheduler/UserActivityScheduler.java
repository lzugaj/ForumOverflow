package com.luv2code.forumoverflow.scheduler;

import com.luv2code.forumoverflow.domain.Post;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.UserStatus;
import com.luv2code.forumoverflow.service.PostService;
import com.luv2code.forumoverflow.service.UserService;
import com.luv2code.forumoverflow.service.UserStatusService;
import com.luv2code.forumoverflow.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by lzugaj on Friday, March 2020
 */

@Slf4j
@Component
public class UserActivityScheduler {

	private final UserService userService;

	private final UserStatusService userStatusService;

	private final PostService postService;

	@Autowired
	public UserActivityScheduler(final UserService userService, final UserStatusService userStatusService,
								 final PostService postService) {
		this.userService = userService;
		this.userStatusService = userStatusService;
		this.postService = postService;
	}

	@Scheduled(cron = "0 0 12 * * ?")
	public void checkIfUserWasActiveInLastSixMonths() {
		List<User> users = userService.findAll();
		for (User user : users) {
			List<Post> posts = postService.findAllByUsername(user.getUsername());
			for (Post post : posts) {
				if (post.getCreatedDate().isBefore(LocalDateTime.now().minusMonths(6))
						&& user.getUserStatus().getName().equals(Constants.ACTIVE)) {
					UserStatus userStatus = userStatusService.findByName(Constants.INACTIVE);
					userService.updateUserStatus(user, userStatus);
					log.info("Change status for User `{}` to `{}`", user.getUsername(), user.getUserStatus().getName());

					// TODO: Slanje maila
				}
			}
		}
	}
}
