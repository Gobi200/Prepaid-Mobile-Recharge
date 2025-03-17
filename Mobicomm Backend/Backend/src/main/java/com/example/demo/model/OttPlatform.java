package com.example.demo.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OttPlatform {
	
	 @Id
	 private String ottId;
	 private String PlatformName;
	 private String icons;
	 
	 @ManyToMany(mappedBy = "ottPlans", cascade = CascadeType.PERSIST)
	 @JsonIgnoreProperties("ottPlans")
	 private List<Plan> plan;

}
