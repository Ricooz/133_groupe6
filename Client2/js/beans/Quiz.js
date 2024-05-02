class Quiz {
    constructor(pkQuiz, nom, description, username, questions) {
        this.pkQuiz = pkQuiz || null;
        this.nom = nom || '';
        this.description = description || '';
        this.username = username || '';
        this.likes = 0;
        this.questions = questions || [];
    }

    static fromJSON(quizJSON) {
        let questions = [];
        if (quizJSON.questions.length > 0) {
            quizJSON.questions.forEach(questionJSON => {
                questions.push(Question.fromJSON(questionJSON));
            });
        }
        return new Quiz(quizJSON.pkQuiz, quizJSON.nom, quizJSON.description, quizJSON.username, questions);
    }

    // Getters and setters...
    getPkQuiz() {
        return this.pkQuiz;
    }

    setPkQuiz(pkQuiz) {
        this.pkQuiz = pkQuiz;
    }

    getNom() {
        return this.nom;
    }

    setNom(nom) {
        this.nom = nom;
    }

    getDescription() {
        return this.description;
    }

    setDescription(description) {
        this.description = description;
    }

    getUsername() {
        return this.username;
    }

    setUsername(username) {
        this.username = username;
    }

    getLikes() {
        return this.likes;
    }

    setLikes(likes) {
        this.likes = likes;
    }

    getQuestions() {
        return this.questions;
    }

    setQuestions(questions) {
        this.questions = questions;
    }
}
