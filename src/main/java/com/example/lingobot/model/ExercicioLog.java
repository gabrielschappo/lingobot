package com.example.lingobot.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exercicio_log") // Boa prática nomear a tabela em minúsculo no Postgres
public class ExercicioLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // columnDefinition="TEXT" é vital no Postgres para strings muito longas
    // (se o feedback passar de 255 chars)
    @Column(columnDefinition = "TEXT")
    private String fraseOriginal;

    @Column(columnDefinition = "TEXT")
    private String respostaUsuario;

    @Column(columnDefinition = "TEXT")
    private String feedbackIA;

    private String nota;

    private LocalDateTime dataHora = LocalDateTime.now();

    // Getters e Setters (Obrigatórios para o JPA funcionar)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFraseOriginal() { return fraseOriginal; }
    public void setFraseOriginal(String fraseOriginal) { this.fraseOriginal = fraseOriginal; }
    public String getRespostaUsuario() { return respostaUsuario; }
    public void setRespostaUsuario(String respostaUsuario) { this.respostaUsuario = respostaUsuario; }
    public String getFeedbackIA() { return feedbackIA; }
    public void setFeedbackIA(String feedbackIA) { this.feedbackIA = feedbackIA; }
    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}