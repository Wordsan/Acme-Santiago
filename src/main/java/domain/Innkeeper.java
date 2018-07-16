
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Innkeeper extends Actor {

	/* RELATIONSHIPS */
	private Collection<Inn>	inns;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "user")
	public Collection<Inn> getInns() {
		return this.inns;
	}

	public void setInns(final Collection<Inn> inns) {
		this.inns = inns;
	}
}
