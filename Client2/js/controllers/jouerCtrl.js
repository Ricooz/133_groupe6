/*
 * Controller de la page de jeux.
 *
 * @author Richoz Matteo
 * @version 1.0 / 02.05.2024
 */

class JouerCtrl {
  constructor(vueService) {
    this.vueService = vueService;

    this.titreQuestionnaire = `<div class="basis-5/12 scale-x-110 mx-auto max-w-4xl px-4 sm:px-6 lg:px-8 bg-zinc-900 drop-shadow-2xl border-2 border-zinc-700 rounded-xl my-10 py-4 flex flex-col">
    <p class="sm:text-2xl text-lg font-bold text-gray-100 text-center titreQuestionnaire">Titre</p>
    </div>`

    this.nouvelleQuestionHTML = `<div class=" basis-5/12 scale-x-110 mx-auto max-w-4xl px-4 sm:px-6 lg:px-8 bg-zinc-900 hover:bg-zinc-800 drop-shadow-2xl border-2 hover:border-blue-700 border-zinc-700 rounded-xl my-10 pt-8 pb-4 flex flex-col questionElement">
        <div class="py-2 flex flex-row justify-between">
            <p class="sm:text-2xl text-lg font-bold text-gray-100 question">Question</p>
        </div>
        <div id="reponses%num%" class="py-2">
            
        </div>
      </div>`;

    this.nouvelleReponseHTML = `<div class="flex flex-row items-center reponseElement">
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
    let elementQuiz = $("#quiz");

    if (params) {
      getQuiz(params.pkQuiz, (data) => {
        let quiz = Quiz.fromJSON(data);
        var titreQuestionnaire = $(this.titreQuestionnaire).clone();
        titreQuestionnaire.find(".titreQuestionnaire").text(quiz.getNom());
        elementQuiz.append(titreQuestionnaire);

        for (let index = 0; index < quiz.getQuestions().length; index++) {
          this.nouvelleQuestion(quiz, index);
          for (let question = 0; question < quiz.getQuestions()[index].getReponses().length; question++) {
            if (quiz.getQuestions()[index].getReponses().length > 0) {
              this.nouvelleReponse(quiz.getQuestions()[index], question, index);
            }
          }
        }
        let elementSoumettre = $(this.buttonSoumettre).clone();
        elementQuiz.append(elementSoumettre);
      }, () => {
        this.vueService.afficherErreur("Id du quiz invalide.", () => {
          this.vueService.changerVue("home");
        });
      });
    } else {
      this.vueService.changerVue("home");
    }

    elementQuiz.on("click", ".buttonCancel", (event) => {
      this.vueService.changerVue("home");
    });

    elementQuiz.on("click", ".buttonValidate", (event) => {
      let questions = [];

      $(".questionElement").each((index, element) => {
        let elementQuestion = $(element);
        let question = elementQuestion.data("question");

        elementQuestion.find(".reponseElement").each((index, element) => {
          let elementReponse = $(element);
          let reponse = elementReponse.data("reponse");

          if (elementReponse.find(".buttonCorrecte").hasClass("bg-zinc-800")) {
            reponse.setCorrect(false);
          } else {
            reponse.setCorrect(true);
          }
        });
        questions.push(question);
      });

      getPoints(params.pkQuiz, questions, (data) => {
        this.vueService.afficherScore(data + " points", () => {
          this.vueService.changerVue("home");
        });
      }, () => {
        this.vueService.afficherErreur("Problème durant le calcule des points.", () => {
          this.vueService.changerVue("home");
        });
      });
    });
  }

  nouvelleQuestion(quiz, numQuestion) {
    let elementQuestion = $(this.nouvelleQuestionHTML.replace("%num%", numQuestion)).clone();
    let question = quiz.getQuestions()[numQuestion];

    elementQuestion.find(".question").text(question.getNom());
    elementQuestion.data("question", question)
    $("#quiz").append(elementQuestion);
  }

  nouvelleReponse(question, numReponse, numQuestion) {
    let elementReponse = $(this.nouvelleReponseHTML).clone();
    let reponse = question.getReponses()[numReponse];

    elementReponse.find(".reponse").text(reponse.getNom());
    elementReponse.data("reponse", reponse)
    $(`#reponses${numQuestion}`).append(elementReponse);

    elementReponse.on("click", ".buttonCorrecte", (event) => {
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
