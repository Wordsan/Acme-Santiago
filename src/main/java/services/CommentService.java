
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import security.LoginService;
import domain.Comment;
import domain.Hike;
import domain.Route;
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
	@Autowired
	private RouteService				routeService;
	@Autowired
	private HikeService					hikeService;


	/* CONSTRUCTOR */
	public CommentService() {
		super();
	}

	/* CRUD */

	public Comment create() {
		Assert.notNull(this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId()));
		final Comment c = new Comment();
		final User u = this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId());

		c.setWriteMoment(new Date(System.currentTimeMillis() - 10));
		c.setRate(0);
		c.setOwner(u);

		return c;
	}

	public Comment save(final Comment comment) {
		Assert.notNull(comment);
		Assert.notNull(this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId()));
		Assert.isTrue(this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId()).equals(comment.getOwner()));
		final User u = comment.getOwner();
		final Hike h = comment.getHike();
		final Route r = comment.getRoute();

		final Comment saved = this.commentRepository.save(comment);

		if ((h == null) && (r != null)) {
			Assert.isTrue(r.getCreator().equals(u));

			u.getRouteComments().add(saved);
			r.getComments().add(saved);
			this.routeService.save(r);
		} else if ((h != null) && (r == null)) {
			Assert.isTrue(h.getRoute().getCreator().equals(u));

			u.getHikeComments().add(saved);
			h.getComments().add(saved);
			this.hikeService.save(h);
		}

		this.userService.save(u);

		return saved;
	}
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
		final Hike h = comment.getHike();
		final Route r = comment.getRoute();

		if ((h == null) && (r != null)) {
			u.getRouteComments().remove(comment);

			r.getComments().remove(comment);
			this.routeService.save(r);
		} else if ((h != null) && (r == null)) {
			u.getHikeComments().remove(comment);

			h.getComments().remove(comment);
			this.hikeService.save(h);
		}

		this.userService.save(u);
		this.commentRepository.delete(comment);
	}

	/* OTHERS */
	public List<Comment> tabooComments() {
		Assert.notNull(this.administratorService.getAdminByUserAccountId(LoginService.getPrincipal().getId()));
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

	public void flush() {
		this.commentRepository.flush();
	}
}
