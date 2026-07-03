package com.fe.horseracing.pojo;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "horse_Owners")
public class HorseOwner extends User {
	
	@OneToMany(mappedBy = "owner")
	private List<Horse> horses;
	
	public HorseOwner() {
		super();
	}
	
	public List<Horse> getHorses() {
	    return horses;
	}

	public void setHorses(List<Horse> horses) {
	    this.horses = horses;
	}
}
