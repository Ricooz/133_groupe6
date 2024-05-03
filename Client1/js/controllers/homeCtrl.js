/*
  But : contrôleur de la page home
  Auteur : Morisetti David
  Date :   01.03.2023 / V1.0
*/

class HomeCtrl {
  constructor(vueService) {
    this.vueService = vueService;

    this.nouveauQuizElementHTML = `<div class="basis-11/12 lg:basis-5/12 px-4 sm:px-6 lg:px-8 bg-zinc-900 drop-shadow-2xl border-2 border-zinc-700 rounded-xl my-10 pt-8 pb-4 flex flex-col quiz">
      <div class="py-2 flex flex-row justify-between items-center">
          <p class="sm:text-2xl text-lg font-bold text-gray-100 nom">None</p>
          <p class="sm:text-xl text-lg font-bold text-gray-300 createur"><span class="text-gray-400">par
              </span>User</p>
      </div>
      <div class="py-2">
          <p class="sm:text-lg text-sm text-gray-300 text-justify description">Une simple description</p>
      </div>
      <div class="py-2 flex flex-row justify-between items-center">
          <p class="sm:text-2xl px-3 text-lg font-bold text-indigo-800"><span
                  class="text-gray-300 questions">0</span> Questions</i></p>
          <div class="flex items-center">
              <p class="sm:text-2xl px-3 text-lg font-bold text-gray-400"><i class="fa-solid fa-star" style="color: #FFD43B;"></i><span class="text-gray-300 pl-3 likes">0</span></p>
              <button type="button"
                class="bg-violet-900 hover:bg-violet-700  text-white sm:text-2xl text-lg font-bold rounded-xl p-4 bouttonModifier"><i
                    class="fa-solid fa-pen-to-square" style="color: #ffffff;"></i></button>
          </div>
      </div>
    </div>`;
  }

  estAutorise() {
    return true;
  }

  load() {
    if (this.vueService.indexCtrl.username !== null) {
      chargerQuizzes(this.vueService.indexCtrl.username, (data) => {
        let bouttonNouveauQuiz = $(".bouttonNouveauQuiz");
        bouttonNouveauQuiz.show();
        bouttonNouveauQuiz.click(() => {
          this.vueService.changerVue("creation");
          this.vueService.controllers["creation"].loadNewQuiz();
        });

        $("#quizzes").html("");
        data.forEach((elementQuiz) => {
          let quiz = Quiz.fromJSON(elementQuiz);
          this.nouveauElementQuiz(quiz);
        });

        $("bouttonModifier").click(() => {
          this.vueService.changerVue("creation");
          this.vueService.controllers["creation"].loadQuiz(quiz);
        });
      });
    } else {
      $(".bouttonNouveauQuiz").hide();
      $("#quizzes").html(`<div class="text-gray-500 text-center py-8 mt-16 text-2xl font-bold">Vous devez vous connecter pour créer ou voir vos quiz</div>`);
    }
  }

  nouveauElementQuiz(quiz) {
    var elementQuiz = $(this.nouveauQuizElementHTML).clone();
    
    elementQuiz.attr("id", quiz.getPkQuiz());
    elementQuiz.find(".nom").text(quiz.getNom());
    elementQuiz.find(".description").text(quiz.getDescription());
    elementQuiz.find(".createur").text(quiz.getUsername())
    elementQuiz.find(".questions").text(quiz.getQuestions().length)
    elementQuiz.find(".likes").text(quiz.getLikes());

    $("#quizzes").append(elementQuiz);
  }
}