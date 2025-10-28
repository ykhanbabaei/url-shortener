package org.softmind.urlshortener.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Entity
@Table(indexes = { @Index(columnList = "code", unique = true)})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlShortener {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    @URL
    String url;

    @Column(unique = true, nullable = false)
    String code;

    LocalDate date;
}
