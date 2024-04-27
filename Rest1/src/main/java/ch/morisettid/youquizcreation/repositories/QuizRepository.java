package ch.morisettid.youquizcreation.repositories;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ch.morisettid.youquizcreation.model.Quiz;

// This will be AUTO IMPLEMENTED by Spring into a Bean called SkieurRepository
// CRUD refers Create, Read, Update, Delete

public interface QuizRepository extends CrudRepository<Quiz, Integer> {
    List<Quiz> findAllByUsername(String username);
}