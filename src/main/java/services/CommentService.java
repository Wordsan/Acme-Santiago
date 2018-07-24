
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	private CommentRepository			commentRepository;

	/* SERVICES */
	@Autowired
	private UserService					userService;
	@Autowired
	private ConfigurationSystemService	csService;
	@Autowired
	private AdministratorService		administratorService;


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
		Assert.notNull(this.administratorService.getAdminByUserAccountId(LoginService.getPrincipal().getId()));
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
	public List<Comment> tabooComments() {
		List<Comment> all;
		final List<Comment> res = new ArrayList<Comment>();
		all = this.commentRepository.findAll();
		final String[] tabooWords = this.csService.get().getTabooWords().toLowerCase().split(",");
		for (final Comment c : all)
			for (final String s : tabooWords)
				if ((c.getText().toLowerCase().contains(s) || c.getTitle().toLowerCase().contains(s)))
					res.add(c);
		return res;
	}
}
