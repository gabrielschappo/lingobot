package com.example.lingobot.repository;

import com.example.lingobot.model.ExercicioLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExercicioRepository extends JpaRepository<ExercicioLog, Long> {
}
