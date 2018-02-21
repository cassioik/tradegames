package br.com.systom.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name="ads" )
public class Ad {
	@Id
	@GeneratedValue
	private Long id;
	
	private String description;
	private int type;
	
	@ManyToOne
	@JoinColumn(name = "games_id")
	private Game game;
	
	@ManyToOne
	@JoinColumn(name = "users_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Ad [id=" + id + ", description=" + description + ", type=" + type + ", game=" + game + ", user=" + user
				+ "]";
	}
}
