
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.CommentRepository;
import domain.Comment;

@Component
@Transactional
public class StringToCommentConverter implements Converter<String, Comment> {

	@Autowired
	private CommentRepository	commentRepository;


	@Override
	public Comment convert(final String source) {
		final Comment comment;
		int id;
		try {
			id = Integer.valueOf(source);
			comment = this.commentRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return comment;
	}

}
