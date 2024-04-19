package ch.richozm.youquizplay.repository;

import org.springframework.data.repository.CrudRepository;

import ch.richozm.youquizplay.model.UserQuiz;

// This will be AUTO IMPLEMENTED by Spring into a Bean called SkieurRepository
// CRUD refers Create, Read, Update, Delete

public interface UserQuizRepository extends CrudRepository<UserQuiz, Integer> {

}