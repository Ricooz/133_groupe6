/*
 * Controller de la page de jeux.
 *
 * @author Richoz Matteo
 * @version 1.0 / 02.05.2024
 */

class JouerCtrl {
    constructor(vueService) {
        this.vueService = vueService;

        this.nouvelleQuestionHTML = `<div class=" basis-5/12 scale-x-110 mx-auto max-w-4xl px-4 sm:px-6 lg:px-8 bg-zinc-900 hover:bg-zinc-800 drop-shadow-2xl border-2 hover:border-blue-700 border-zinc-700 rounded-xl my-10 pt-8 pb-4 flex flex-col quiz">
        <div class="py-2 flex flex-row justify-between">
            <p class="sm:text-2xl text-lg font-bold text-gray-100 question">Question</p>
        </div>
        <div id="reponses" class="py-2">
            
        </div>
      </div>`;

      this.nouvelleReponseHTML = `<div class="basis-5/12 scale-x-110 mx-auto max-w-4xl px-4 sm:px-6 lg:px-8 bg-zinc-900 hover:bg-zinc-800 drop-shadow-2xl border-2 hover:border-blue-700 border-zinc-700 rounded-xl my-10 pt-8 pb-4 flex flex-col">
      <p class="sm:text-lg text-sm text-gray-300 text-justify reponse">Reponse</p>
      </div>`;
    }

    estAutorise() {
        return this.vueService.indexCtrl.username !== null;
    }

    pasAutorise() {
        this.vueService.afficherErreur("Pas d'utilisateur authentifié.", () => {
            this.vueService.changerVue("login");
        })
    }

    load() {
        chargerQuizzes((data) => {
            data.forEach((elementQuiz) => {
                let quiz = Quiz.fromJSON(elementQuiz);
                if (quiz.getPkQuiz() === 14) {
                    for (let index = 0; index < quiz.getQuestions().length; index++) {
                        this.nouvelleQuestion(quiz, index);
                        //console.log("Num question : " + quiz.getQuestions()[index].getNom());
                        //console.log("Nombre de réponses : " + quiz.getQuestions()[index].getReponses().length);
                        console.log(quiz.getQuestions()[index].getNom());
                        for (let question = 0; question < quiz.getQuestions()[index].getReponses().length; question++) {
                            //console.log("Réponse : " + quiz.getQuestions()[index].getReponses()[question].getNom());
                            if(quiz.getQuestions()[index].getReponses().length != 0) {
                                this.nouvelleReponse(quiz.getQuestions()[index], question);
                            }
                        }
                    }
                }
            });
        });
    }

    nouvelleQuestion(quiz, numQuestion) {
        var elementQuizz = $(this.nouvelleQuestionHTML).clone();
        elementQuizz.find(".question").text(quiz.getQuestions()[numQuestion].getNom());
        elementQuizz.data;
        $("#quiz").append(elementQuizz);
    }

    nouvelleReponse(question, numReponse) {
        var elementQuestion = $(this.nouvelleReponseHTML).clone();
        console.log(question);
        console.log(numReponse);
        elementQuestion.find(".response").text(question.getReponses()[numReponse].getNom());
        elementQuestion.data;

        $("#reponses").append(elementQuestion);
    }
}