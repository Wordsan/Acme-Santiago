
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Comment;
import domain.Hike;
import domain.Route;
import domain.User;
import repositories.CommentRepository;
import security.LoginService;

@Service
@Transactional
public class CommentService {

	/* REPOSITORIES */
	@Autowired
	private CommentRepository commentRepository;

	/* SERVICES */
	@Autowired
	private UserService userService;
	@Autowired
	private ConfigurationSystemService csService;
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private RouteService routeService;
	@Autowired
	private HikeService hikeService;

	@Autowired
	private Validator validator;

	/* CONSTRUCTOR */
	public CommentService() {
		super();
	}

	/* CRUD */

	public Comment create(final Route route, final Hike hike) {
		Assert.notNull(this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId()));
		final Comment c = new Comment();
		final User u = this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId());
		if (route != null) {
			c.setRoute(route);
		} else {
			c.setHike(hike);
		}

		c.setWriteMoment(new Date(System.currentTimeMillis() - 10));
		c.setRate(0);
		c.setOwner(u);

		return c;
	}

	public Comment save(final Comment comment) {
		Assert.notNull(comment);
		Assert.notNull(this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId()));
		Assert.isTrue(this.userService.getUserByUserAccountId(LoginService.getPrincipal().getId())
				.equals(comment.getOwner()));

		final Comment saved = this.commentRepository.save(comment);

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
		for (final Comment c : all) {
			for (final String s : tabooWords) {
				if (!res.contains(c)) {
					if ((c.getText().toLowerCase().contains(s) || c.getTitle().toLowerCase().contains(s))) {
						res.add(c);
					}
				}
			}
		}
		return res;
	}

	public void flush() {
		this.commentRepository.flush();
	}

	public Comment reconstruct(final Comment comment, final BindingResult binding) {
		Comment result;

		if (comment.getId() != 0) {
			result = this.commentRepository.findOne(comment.getId());
		} else {
			result = this.create(comment.getRoute(), comment.getHike());
		}

		result.setPictures(comment.getPictures());
		result.setRate(comment.getRate());
		result.setText(comment.getText());
		result.setTitle(comment.getTitle());

		this.validator.validate(result, binding);

		Assert.isTrue(!binding.hasErrors());

		return result;
	}

}
