package br.com.precopoint.PrecoPoint.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "logging")
public class Logging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_ID")
    private int eventId;
    @Column(name = "USER")
    private String user;
    @Column(name = "EVENT_DATE")
    private String eventDate;
    @Column(name = "LEVEL")
    private String level;
    @Column(name = "LOGGER")
    private String logger;
    @Column(name = "MSG")
    private String msg;
    @Column(name = "THROWABLE")
    private String throwable;
}