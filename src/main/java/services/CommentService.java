
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import security.LoginService;
import domain.Comment;
import domain.User;

@Service
@Transactional
public class CommentService {

	/* REPOSITORIES */
	@Autowired
	private CommentRepository	commentRepository;

	/* SERVICES */
	@Autowired
	private UserService			userService;


	/* CONSTRUCTOR */
	public CommentService() {
		super();
	}

	/* CRUD */

	public Collection<Comment> findAll() {
		return this.commentRepository.findAll();
	}

	public Comment findOne(final int commentID) {
		return this.commentRepository.findOne(commentID);
	}

	public void delete(final Comment comment) {
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains("ADMIN"));
		Assert.notNull(comment);
		final User u = comment.getOwner();

		if (u.getHikeComments().contains(comment))
			u.getHikeComments().remove(comment);
		else
			u.getRouteComments().remove(comment);

		this.userService.save(u);
		this.commentRepository.delete(comment);
	}

	/* OTHERS */
}
