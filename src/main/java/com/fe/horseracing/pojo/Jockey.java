package com.fe.horseracing.pojo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "jockeys")
@PrimaryKeyJoinColumn(name = "user_Id")
public class Jockey extends User {

    @Column(name = "license_number", nullable = false, unique = true, length = 50)
    private String licenseNumber;

    @Column(name = "nationality", length = 50)
    private String nationality;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "weight")
    private double weight;

    @Column(name = "experience_years")
    private int experienceYears;

    @Column(name = "total_races")
    private int totalRaces;

    @Column(name = "total_wins")
    private int totalWins;

    @Column(name = "profile_image_url", length = 255)
    private String profileImageUrl;

    @Column(name = "jockey_status", nullable = false, length = 20)
    private String jockeyStatus; 

    @OneToMany(mappedBy = "jockey")
    private List<Violation> violations;
    
    public Jockey() {
        super();
        this.jockeyStatus = "AVAILABLE";
        this.totalRaces = 0;
        this.totalWins = 0;
    }

    public Jockey(String userName, String password, String email, String firstName, String lastName, String phoneNumber, String licenseNumber, String nationality, LocalDate dateOfBirth, double weight, int experienceYears) {
        super();
        this.licenseNumber = licenseNumber;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
        this.weight = weight;
        this.experienceYears = experienceYears;
        this.jockeyStatus = "AVAILABLE";
        this.totalRaces = 0;
        this.totalWins = 0;
    }

    // Getters & Setters
    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public int getTotalRaces() {
        return totalRaces;
    }

    public void setTotalRaces(int totalRaces) {
        this.totalRaces = totalRaces;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getJockeyStatus() {
        return jockeyStatus;
    }

    public void setJockeyStatus(String jockeyStatus) {
        this.jockeyStatus = jockeyStatus;
    }

    public List<Violation> getViolations() {
		return violations;
	}

	public void setViolations(List<Violation> violations) {
		this.violations = violations;
	}

	@Transient
    public double getWinRate() {
        if (totalRaces == 0) {
            return 0.0;
        }
        return ((double) totalWins / totalRaces) * 100.0;
    }
}
