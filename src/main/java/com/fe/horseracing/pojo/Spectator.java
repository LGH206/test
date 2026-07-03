package com.fe.horseracing.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "spectator")
public class Spectator extends User {

	public Spectator() {
		super();
	}
}
