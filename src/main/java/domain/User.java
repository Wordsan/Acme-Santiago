
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class User extends DomainEntity {

	private Collection<User>	followedUsers;


	public Collection<User> getFollowedUsers() {
		return this.followedUsers;
	}

	public void setFollowedUsers(final Collection<User> followedUsers) {
		this.followedUsers = followedUsers;
	}

}
