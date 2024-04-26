class Question {
    constructor(pkQuestion, nom, reponses) {
        this.pkQuestion = pkQuestion;
        this.nom = nom;
        this.reponses = reponses;
    }
    
    // Getters and setters
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
