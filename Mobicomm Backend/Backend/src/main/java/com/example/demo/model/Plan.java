package com.example.demo.model;


import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
    @Id
    private String planId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false, referencedColumnName = "categoryId")
//    @JsonBackReference
    @JsonIgnoreProperties({"plans"})
    private Category category;

    private String planPrice;
    private String validity;
    private String voice;
    private String data;
    private String message;
    private String tag;
    private String description;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "plan_ott_mapping",
        joinColumns = @JoinColumn(name = "plan_id"),
        inverseJoinColumns = @JoinColumn(name = "ott_id")
    )
    @JsonIgnoreProperties("plan")  // Ignore "plan" field inside OttPlatform
    private Set<OttPlatform> ottPlans;

}
