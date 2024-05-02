class Reponse {
    constructor(pkReponse, nom, correct) {
        this.pkReponse = pkReponse || null;
        this.nom = nom || '';
        this.correct = correct || false;
    }

    static fromJSON(reponseJSON) {
        return new Reponse(reponseJSON.pkReponse, reponseJSON.nom, reponseJSON.correct);
    }

    // Getters and setters...
    getPkReponse() {
        return this.pkReponse;
    }

    setPkReponse(pkReponse) {
        this.pkReponse = pkReponse;
    }

    getNom() {
        return this.nom;
    }

    setNom(nom) {
        this.nom = nom;
    }

    isCorrect() {
        return this.correct;
    }

    setCorrect(correct) {
        this.correct = correct;
    }
}
