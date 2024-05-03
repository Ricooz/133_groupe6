/*
  But : contrôleur de la page home
  Auteur : Richoz Matteo
  Date :   29.04.2024 / V1.0
*/

class HomeCtrl {
  constructor(vueService) {
    this.vueService = vueService;

    this.nouveauQuizElementHTML = `<div class=" basis-5/12 scale-x-110 mx-auto max-w-4xl px-4 sm:px-6 lg:px-8 bg-zinc-900 hover:bg-zinc-800 drop-shadow-2xl border-2 hover:border-blue-700 border-zinc-700 rounded-xl my-10 pt-8 pb-4 flex flex-col projet">
      <div class="py-2 flex flex-row justify-between">
          <p class="sm:text-2xl text-lg font-bold text-gray-100 nom">None</p>
          <p class="sm:text-xl text-lg font-bold text-gray-500 createur"><span class="text-gray-400">par
              </span>User</p>
      </div>
      <div class="py-2">
          <p class="sm:text-lg text-sm text-gray-300 text-justify description">Une simple description</p>
      </div>
      <div class="py-2 flex flex-row justify-between items-center">
          <p class="sm:text-2xl px-3 text-lg font-bold text-indigo-800"><span class="text-gray-300 questions">0</span> Questions</i></p>
          <div class="like flex items-center pr-3">
              <p class="sm:text-2xl px-3 text-lg font-bold text-gray-400"><i class="fa-solid fa-star" style="color: #FFD43B;"></i><span class="text-gray-300 pl-3 likes">0</span></p>
          </div>
      </div>
    </div>`;
  }

  estAutorise() {
    return true;
  }

  load() {
    chargerQuizzes((data) => {
      $("#quizzes").html("");
      data.forEach((elementQuiz) => {
        let quiz = Quiz.fromJSON(elementQuiz);
        this.nouveauElementQuiz(quiz);
      });
      $(".like").click((event) => {
        const $target = $(event.currentTarget);
        let pkQuiz = $target.parent().parent().attr("id");
        liker(pkQuiz, () => {}, (jqXHR) => {
          if (jqXHR.status === 403) {
            this.vueService.afficherErreur("Connectez vous pour pouvoir liker", () => { });
          } else if (jqXHR.status === 200) {
            getQuiz(pkQuiz, (data) => {
              console.log(data.likes);
              $target.find(".likes").text(data.likes);
            }, (jqXHR) => {
              console.log("Quiz pas trouvé");
            });
          }
        });
      });
    });
  }

  nouveauElementQuiz(quiz) {
    var elementQuiz = $(this.nouveauQuizElementHTML).clone();

    elementQuiz.attr("id", quiz.getPkQuiz());
    elementQuiz.find(".nom").text(quiz.getNom());
    elementQuiz.find(".description").text(quiz.getDescription());
    elementQuiz.find(".createur").text(quiz.getUsername())
    elementQuiz.find(".questions").text(quiz.getQuestions().length);
    elementQuiz.find(".likes").text(quiz.getLikes());

    $("#quizzes").append(elementQuiz);
  }
}
