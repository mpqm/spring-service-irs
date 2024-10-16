package com.example.common.domain.resume.repository;

import com.example.common.domain.resume.model.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TrainingRepository extends JpaRepository<Training, Long> {
    @Query("SELECT t FROM Training t WHERE t.resumeInfo.idx = :resumeInfoIdx")
    List<Training> findAllByResumeInfoIdx(Long resumeInfoIdx);
}