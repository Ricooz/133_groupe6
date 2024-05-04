/*
  But : contrôleur de la vue creation
  Auteur : Morisetti David
  Date :   01.03.2023 / V1.0
*/

class CreationCtrl {
  constructor(vueService) {
    this.vueService = vueService;

    this.baseHTML = `<div class="mx-auto flex flex-col w-full max-w-7xl px-4 pt-6 pb-3 sm:px-6 lg:px-8">
        <div class="text-2xl font-bold text-white p-2">Titre</div>
        <textarea class="w-full text-xl text-gray-300 p-1 border-2 border-zinc-700 bg-zinc-900 rounded-xl titre" rows="1" placeholder="titre"></textarea>
    </div>
    <div class="mx-auto flex flex-col w-full max-w-7xl px-4 py-3 sm:px-6 lg:px-8">
        <div class="text-2xl font-bold text-white p-2">Description</div>
        <textarea class="w-full text-xl text-gray-300 p-1 border-2 border-zinc-700 bg-zinc-900 rounded-xl description" rows="1" placeholder="description"></textarea>
    </div>
    <div class="mx-auto flex flex-col w-full max-w-7xl px-4 py-3 sm:px-6 lg:px-8">
        <div class="flex flex-row justify-between items-center reponse">
            <div class="text-2xl font-bold text-white p-2">Questions</div>
            <button type="button" class="bg-green-700 hover:bg-green-600 text-white text-md font-bold rounded-xl my-2 ml-2 px-5 h-10 buttonNouvelleQuestion"><i class="fa-solid fa-plus" style="color: #ffffff;"></i></button>
        </div>
        
        <div id="questionsContainer" class="border-2 border-zinc-700 bg-zinc-900 rounded-xl flex flex-col w-full p-3">
            
        </div>
    </div>
    <div class="w-full flex flex-row justify-center my-12 h-20 items-around">
        <button type="button" class="bg-green-700 hover:bg-green-600 text-white text-md font-bold rounded-xl mx-10 w-1/6 buttonSave"><i class="fa-solid fa-floppy-disk" style="color: #ffffff;"></i></button>
        <button type="button" class="bg-red-800 hover:bg-red-700 text-white text-md font-bold rounded-xl mx-10 w-1/6 buttonCancel"><i class="fa-solid fa-x" style="color: #ffffff;"></i></button>
    </div>`;
    this.nouvelleQuestionHTML = `<div class="w-full border-2 border-zinc-700 bg-zinc-800 rounded-xl flex flex-col w-full p-2 my-2 question">
      <div class="flex flex-row justify-between items-center mb-4">
          <textarea class="w-full text-gray-300 p-2 border-2 border-zinc-700 bg-zinc-900 rounded-xl questionTitre" rows="1" placeholder="question"></textarea>
          <button type="button" class="bg-green-700 hover:bg-green-600 text-white text-md font-bold rounded-xl my-2 ml-2 px-4 h-10 buttonNouvelleReponse"><i class="fa-solid fa-plus" style="color: #ffffff;"></i></button>
          <button type="button" class="bg-red-800 hover:bg-red-700 text-white text-md font-bold rounded-xl my-2 ml-2 px-4 h-10 buttonDeleteQuestion"><i class="fa-solid fa-trash" style="color: #ffffff;"></i></button>
      </div>
    </div>`;
    this.nouvelleReponseHTML = `<div class="flex flex-row items-center reponse">
      <textarea class="w-full text-gray-300 p-1 border-2 border-zinc-700 bg-zinc-900 rounded-xl my-1 reponseTitre" rows="1" placeholder="reponse"></textarea>
      <button type="button" class="bg-zinc-800 border-2 border-blue-700 text-white text-md font-bold rounded-xl ml-2 px-3 h-9 buttonCorrecte"><i class="fa-solid fa-check" style="color: #27272a;"></i></button>
      <button type="button" class="bg-red-800 hover:bg-red-700 text-white text-md font-bold rounded-xl ml-2 px-3 h-9 buttonDeleteReponse"><i class="fa-solid fa-trash" style="color: #ffffff;"></i></button>
    </div>`;
    this.buttonChecked = `<button type="button" class="bg-blue-700 border-2 border-blue-700 text-white text-md font-bold rounded-xl ml-2 pr-3 pl-3 h-9 buttonCorrecte"><i class="fa-solid fa-check" style="color: #ffffff;"></i></button>`;
    this.buttonUnchecked = `<button type="button" class="bg-zinc-800 border-2 border-blue-700 text-white text-md font-bold rounded-xl ml-2 px-3 h-9 buttonCorrecte"><i class="fa-solid fa-check" style="color: #27272a;"></i></button>`;
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
    let currentQuiz = $("#content").data("currentQuiz");
    let elementQuiz = $(this.baseHTML).clone();
    $("#quiz").html(elementQuiz);

    if (currentQuiz !== null && currentQuiz !== undefined) {
      $(".titre").text(currentQuiz.getNom());
      $(".description").text(currentQuiz.getDescription());

      currentQuiz.getQuestions().forEach(question => {
        let elementQuestion = $(this.nouvelleQuestionHTML).clone();
        elementQuestion.find(".questionTitre").text(question.getNom())

        question.getReponses().forEach(reponse => {
          let elementReponse = $(this.nouvelleReponseHTML).clone();
          elementReponse.find(".reponseTitre").text(reponse.getNom())

          let elementCorrect = elementReponse.find(".buttonCorrecte");
          if (reponse.isCorrect()) {
            elementCorrect.replaceWith(this.buttonChecked);
            elementCorrect.data("correcte", true);
          } else {
            elementCorrect.replaceWith(this.buttonUnchecked);
            elementCorrect.data("correcte", false);
          }

          elementQuestion.append(elementReponse);
        });

        $("#questionsContainer").append(elementQuestion);
      });
    }

    $(".bouttonDelete").click((event) => {
      if (currentQuiz) {
        deleteElement(currentQuiz.getPkQuiz(), "quiz", () => {
          this.vueService.changerVue("home");
        }, () => {
          this.vueService.changerVue("home");
        });
      } else {
        this.vueService.changerVue("home");
      }
    });

    $(".buttonSave").click((event) => {
      let nom = $(".titre").val();
      let description = $(".description").val();
    });

    $(".buttonCancel").click((event) => {
      this.vueService.changerVue("home");
    });

    $(".buttonNouvelleQuestion").click((event) => {
      let elementQuestion = $(this.nouvelleQuestionHTML).clone();
      $("#questionsContainer").append(elementQuestion);
    });

    elementQuiz.on("click", ".buttonDeleteQuestion", (event) => {
      $(event.currentTarget).parent().parent().remove()
    });

    elementQuiz.on("click", ".buttonNouvelleReponse", (event) => {
      let elementReponse = $(this.nouvelleReponseHTML).clone();
      $(event.currentTarget).parent().parent().append(elementReponse);
    });

    elementQuiz.on("click", ".buttonDeleteReponse", (event) => {
      $(event.currentTarget).parent().remove()
    });

    elementQuiz.on("click", ".buttonCorrecte", (event) => {
      if ($(event.currentTarget).hasClass("bg-zinc-800")) { // Est unchecked
        $(event.currentTarget).replaceWith(this.buttonChecked);
        $(event.currentTarget).data("correcte", true);
      } else {
        $(event.currentTarget).replaceWith(this.buttonUnchecked);
        $(event.currentTarget).data("correcte", false);
      }
    });
  }
}
