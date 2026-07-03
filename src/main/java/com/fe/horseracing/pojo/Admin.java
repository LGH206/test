package com.fe.horseracing.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin extends User {
	
	public Admin() {
		super();
	}

}
