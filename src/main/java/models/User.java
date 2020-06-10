package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class User extends BaseDomain{
	@NotEmpty
	@Size(min=3)
	private String name;
	@NotEmpty
	@Email
	@Column(name="email",unique=true)
	private String email;
	
    public User() {}
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
