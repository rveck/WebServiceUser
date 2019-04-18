package br.com.rvs.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User implements Serializable{

	private static final long serialVersionUID = -1153842634127193624L;

	@Id    
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="login", nullable=false, unique=true)
	private String login;
	
	@Column(name="pass", nullable=false)
	private String pass;
	
	public User() {
		
	}
	
	public User(String name, String login, String pass) {	
		this.name = name;
		this.login = login;
		this.pass = pass;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	@Override
    public String toString() {
        return String.format(
                "User[id=%d, name='%s', login='%s', pass='%s']",
                id, name, login, pass);
    }
	
	
}
