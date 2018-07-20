
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CommentRepository;
import domain.Comment;

@Service
@Transactional
public class CommentService {

	/* REPOSITORIES */
	@Autowired
	private CommentRepository	commentRepository;


	/* SERVICES */

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
		this.commentRepository.delete(comment);
	}

	/* OTHERS */
}
