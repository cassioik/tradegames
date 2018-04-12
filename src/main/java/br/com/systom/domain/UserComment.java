package br.com.systom.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name="comments" )
public class UserComment {
	@Id
	@GeneratedValue
	private Long id;
	
	private String comment;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	
	@ManyToOne
	@JoinColumn(name = "ads_id")
	private Ad ad;
	
	@ManyToOne
	@JoinColumn(name = "users_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public LocalDateTime getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}

	public Ad getAd() {
		return ad;
	}

	public void setAd(Ad ad) {
		this.ad = ad;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserComment [id=" + id + ", comment=" + comment + ", created_at=" + created_at + ", updated_at="
				+ updated_at + ", ad=" + ad + ", user=" + user + "]";
	}
}
