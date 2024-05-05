/*
 * Controller de la page de jeux.
 *
 * @author Richoz Matteo
 * @version 1.0 / 02.05.2024
 */

class JouerCtrl {
  constructor(vueService) {
    this.vueService = vueService;

    this.titreQuestionnaire = `<div class="basis-5/12 scale-x-110 mx-auto max-w-4xl px-4 sm:px-6 lg:px-8 bg-zinc-900 drop-shadow-2xl border-2 border-zinc-700 rounded-xl my-10 pt-8 pb-4 flex flex-col">
    <p class="sm:text-2xl text-lg font-bold text-gray-100 text-center titreQuestionnaire">Titre</p>
    </div>`

    this.nouvelleQuestionHTML = `<div class=" basis-5/12 scale-x-110 mx-auto max-w-4xl px-4 sm:px-6 lg:px-8 bg-zinc-900 hover:bg-zinc-800 drop-shadow-2xl border-2 hover:border-blue-700 border-zinc-700 rounded-xl my-10 pt-8 pb-4 flex flex-col quiz">
        <div class="py-2 flex flex-row justify-between">
            <p class="sm:text-2xl text-lg font-bold text-gray-100 question">Question</p>
        </div>
        <div id="reponses%num%" class="py-2">
            
        </div>
      </div>`;

    this.nouvelleReponseHTML = `<div class="flex flex-row items-center">
      <p class="w-full text-gray-300 p-1 border-2 border-zinc-700 bg-zinc-900 rounded-xl my-1 reponse" rows="1"></p>
      <button type="button" class="bg-zinc-800 border-2 border-blue-700 text-white text-md font-bold rounded-xl ml-2 px-3 h-9 buttonCorrecte"><i class="fa-solid fa-check" style="color: #27272a;"></i></button>
    </div>`;

    this.buttonChecked = `<button type="button" class="bg-blue-700 border-2 border-blue-700 text-white text-md font-bold rounded-xl ml-2 pr-3 pl-3 h-9 buttonCorrecte"><i class="fa-solid fa-check" style="color: #ffffff;"></i></button>`;
    this.buttonUnchecked = `<button type="button" class="bg-zinc-800 border-2 border-blue-700 text-white text-md font-bold rounded-xl ml-2 px-3 h-9 buttonCorrecte"><i class="fa-solid fa-check" style="color: #27272a;"></i></button>`;

    this.buttonSoumettre = 
    `<div class="w-full flex flex-row justify-center my-12 h-20 items-around> 
      <button type="button" class="bg-red-800 hover:bg-red-700 text-white text-md font-bold rounded-xl mx-10 w-1/6"><i class="fa-solid" style="color: #ffffff;"></i></button>
      <button type="button" class="bg-green-700 hover:bg-green-600 text-white text-md font-bold rounded-xl mx-10 w-1/6 buttonValidate"><i class="fa-solid fa-floppy-disk" style="color: #ffffff;"></i></button>
      <button type="button" class="bg-red-800 hover:bg-red-700 text-white text-md font-bold rounded-xl mx-10 w-1/6 buttonCancel"><i class="fa-solid fa-x" style="color: #ffffff;"></i></button>
    </div>`;
  }

  estAutorise() {
    return this.vueService.indexCtrl.username !== null;
  }

  pasAutorise() {
    this.vueService.afficherErreur("Pas d'utilisateur authentifié.", () => {
      this.vueService.changerVue("login");
    });
  }

  load(params) {
    getQuiz(params.pkQuiz, (data) => {
      let quiz = Quiz.fromJSON(data);
      var titreQuestionnaire = $(this.titreQuestionnaire).clone();
      titreQuestionnaire.find(".titreQuestionnaire").text(quiz.getNom());
      $("#quiz").append(titreQuestionnaire);

      for (let index = 0; index < quiz.getQuestions().length; index++) {
        this.nouvelleQuestion(quiz, index);
        for (let question = 0;question < quiz.getQuestions()[index].getReponses().length;question++) {
          if (quiz.getQuestions()[index].getReponses().length > 0) {
            this.nouvelleReponse(quiz.getQuestions()[index], question, index);
          }
        }
      }
      let elementSoumettre = $(this.buttonSoumettre).clone();
      $("#quiz").append(elementSoumettre);
    });

    setTimeout(() => {
      $(".buttonCancel").click(() => {
        this.vueService.changerVue("home");
      });
    }, 1000);

    setTimeout(() => {
      $(".buttonValidate").click(() => {
        getPoints(params.pkQuiz, (data) => {
          
        });
        this.vueService.changerVue("home");
      });
    }, 1000);
  }

  nouvelleQuestion(quiz, numQuestion) {
    var elementQuizz = $(this.nouvelleQuestionHTML.replace("%num%", numQuestion)).clone();
    elementQuizz.find(".question").text(quiz.getQuestions()[numQuestion].getNom());
    $("#quiz").append(elementQuizz);
  }

  nouvelleReponse(question, numReponse, numQuestion) {
    var elementQuestion = $(this.nouvelleReponseHTML).clone();
    elementQuestion.find(".reponse").text(question.getReponses()[numReponse].getNom());
    $(`#reponses${numQuestion}`).append(elementQuestion);

    elementQuestion.on("click", ".buttonCorrecte", (event) => {
      if ($(event.currentTarget).hasClass("bg-zinc-800")) {
        $(event.currentTarget).replaceWith(this.buttonChecked);
        $(event.currentTarget).data("correcte", true);
      } else {
        $(event.currentTarget).replaceWith(this.buttonUnchecked);
        $(event.currentTarget).data("correcte", false);
      }
    });
  }
}
