class Question {
    constructor(pkQuestion, nom, reponses) {
        this.pkQuestion = pkQuestion || null;
        this.nom = nom || '';
        this.reponses = reponses || [];
    }

    static fromJSON(questionJSON) {
        let reponses = [];
        if (questionJSON.reponses.length > 0) {
            questionJSON.reponses.forEach(reponseJSON => {
                reponses.push(Reponse.fromJSON(reponseJSON));
            });
        }
        return new Question(questionJSON.pkQuestion, questionJSON.nom, reponses);
    }

    // Getters and setters...
    getPkQuestion() {
        return this.pkQuestion;
    }

    setPkQuestion(pkQuestion) {
        this.pkQuestion = pkQuestion;
    }

    getNom() {
        return this.nom;
    }

    setNom(nom) {
        this.nom = nom;
    }

    getReponses() {
        return this.reponses;
    }

    setReponses(reponses) {
        this.reponses = reponses;
    }
}
