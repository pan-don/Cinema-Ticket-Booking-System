package com.project.mvc.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name="admin")
public class Admin extends Person {

}
