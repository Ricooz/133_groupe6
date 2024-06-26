/*
  But : contrôleur de la vue creation
  Auteur : Morisetti David
  Date :   01.03.2023 / V1.0
*/

class CreationCtrl {
  constructor(vueService) {
    this.vueService = vueService;
    this.elementsToRemove = {
      questions: [],
      reponses: []
    };

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
        elementQuestion.data("pk", question.getPkQuestion());

        question.getReponses().forEach(reponse => {
          let elementReponse = $(this.nouvelleReponseHTML).clone();
          elementReponse.find(".reponseTitre").text(reponse.getNom())
          elementReponse.data("pk", reponse.getPkReponse());

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
      let elementsQuestion = $(".question").clone(true);
      let elementsReponse = $(".reponse").clone(true);
      let quizNom = $(".titre").val();
      let quizDescription = $(".description").val();

      let saveQuestionsPromise = new Promise((resolve, reject) => {
        if (currentQuiz && quizNom) { // UPDATE if the quiz already exists and has a name
          modifierQuiz(currentQuiz.getPkQuiz(), quizNom, quizDescription, (data) => {
            resolve(this.saveQuestions(elementsQuestion, elementsReponse, currentQuiz.getPkQuiz()));
          });
        } else if (quizNom) { // ADD if the quiz is new and has a name
          rajouterQuiz(quizNom, quizDescription, (data) => {
            resolve(this.saveQuestions(elementsQuestion, elementsReponse, data.pkQuiz));
          });
        } else {
          reject(new Error("Quiz does not exist or does not have a name"));
        }
      });

      saveQuestionsPromise.then(() => {
        this.vueService.changerVue("home");
      }).catch((error) => {
        console.error(error);
      });
    });

    $(".buttonCancel").click((event) => {
      this.vueService.changerVue("home");
    });

    $(".buttonNouvelleQuestion").click((event) => {
      let elementQuestion = $(this.nouvelleQuestionHTML).clone();
      $("#questionsContainer").append(elementQuestion);
    });

    elementQuiz.on("click", ".buttonDeleteQuestion", (event) => {
      let questionElement = $(event.currentTarget).parent().parent()

      // Si la question éxistait déjà
      let pkQuestion = questionElement.data("pk");
      if (pkQuestion) {
        this.elementsToRemove["questions"].push(pkQuestion);
      }
      questionElement.remove()
    });

    elementQuiz.on("click", ".buttonNouvelleReponse", (event) => {
      let elementReponse = $(this.nouvelleReponseHTML).clone();
      $(event.currentTarget).parent().parent().append(elementReponse);
    });

    elementQuiz.on("click", ".buttonDeleteReponse", (event) => {
      let reponseElement = $(event.currentTarget).parent()

      // Si la réponse éxistait déjà
      let pkReponse = reponseElement.data("pk");
      if (pkReponse) {
        this.elementsToRemove["reponses"].push(pkReponse);
      }
      reponseElement.remove()
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

  saveQuestions(elementsQuestion, elementsReponse, pkQuiz) {
    let promises = [];

    this.elementsToRemove["questions"].forEach(pkQuestion => {
      let promise = new Promise((resolve, reject) => {
        deleteElement(pkQuestion, "question", resolve, resolve);
      });

      promises.push(promise);
    });

    this.elementsToRemove["reponses"].forEach(pkReponse => {
      let promise = new Promise((resolve, reject) => {
        deleteElement(pkReponse, "reponse", resolve, resolve);
      });

      promises.push(promise);
    });

    elementsQuestion.each(function (index, elementQuestion) {
      elementQuestion = $(elementQuestion);

      let pkQuestion = elementQuestion.data("pk");
      let questionNom = elementQuestion.find(".questionTitre").val();

      let promise = new Promise((resolve, reject) => {
        if (pkQuestion && questionNom) {
          modifierQuestion(pkQuestion, questionNom, (data) => {
            let reponsePromises = [];

            elementQuestion.find(".reponse").each(function () {
              let elementReponse = $(this)

              let pkReponse = elementReponse.data("pk");
              let reponseNom = elementReponse.find(".reponseTitre").val();
              let correct = elementReponse.find(".buttonCorrecte").hasClass("bg-blue-700");

              let reponsePromise = new Promise((resolve, reject) => {
                if (pkReponse && reponseNom) {
                  modifierReponse(pkReponse, reponseNom, correct, resolve);
                } else if (reponseNom) {
                  rajouterReponse(reponseNom, correct, data.pkQuestion, resolve);
                }
              });

              reponsePromises.push(reponsePromise);
            });

            Promise.all(reponsePromises).then(resolve);
          });
        } else if (questionNom) {
          rajouterQuestion(elementQuestion.find(".questionTitre").val(), pkQuiz, (data) => {
            let reponsePromises = [];

            elementQuestion.find(".reponse").each(function () {
              let elementReponse = $(this)

              let pkReponse = elementReponse.data("pk");
              let reponseNom = elementReponse.find(".reponseTitre").val();
              let correct = elementReponse.find(".buttonCorrecte").hasClass("bg-blue-700");

              let reponsePromise = new Promise((resolve, reject) => {
                if (pkReponse && reponseNom) {
                  modifierReponse(pkReponse, reponseNom, correct, resolve);
                } else if (reponseNom) {
                  rajouterReponse(reponseNom, correct, data.pkQuestion, resolve);
                }
              });

              reponsePromises.push(reponsePromise);
            });

            Promise.all(reponsePromises).then(resolve);
          });
        }
      });

      promises.push(promise);
    });

    return Promise.all(promises);
  }
}
