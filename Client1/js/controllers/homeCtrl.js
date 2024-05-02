/*
  But : contrôleur de la page home
  Auteur : Morisetti David
  Date :   01.03.2023 / V1.0
*/

class HomeCtrl {
  constructor(vueService) {
    this.vueService = vueService;

    this.newQuizElementHTML = `<div
      class="mx-auto max-w-4xl px-4 sm:px-6 lg:px-8 bg-zinc-900 drop-shadow-2xl border-2 border-zinc-700 rounded-xl my-10 pt-8 pb-4 flex flex-col projet">
      <div class="py-2 flex flex-row justify-between">
          <p class="sm:text-2xl text-lg font-bold text-gray-100 nom">None</p>
          <p class="sm:text-xl text-lg font-bold text-gray-500 createur"><span class="text-gray-400">par
              </span>User</p>
      </div>
      <div class="py-2">
          <p class="sm:text-lg text-sm text-gray-300 text-justify description">Une simple description</p>
      </div>
      <div class="py-2 flex flex-row justify-between">
          <div class=" bg-zinc-800 border-2 border-zinc-700 rounded-xl px-4 py-2 flex items-center">
              <p class="sm:text-1xl px-3 text-lg font-bold text-gray-400 likes"><span class="text-gray-300">0</span><i
                      class="fa-solid fa-star pl-3" style="color: #FFD43B;"></i></p>
              <p class="sm:text-2xl px-3 text-xl font-bold text-gray-600 likes">/</p>
              <p class="sm:text-1xl px-3 text-lg font-bold text-gray-400 questions"><span
                      class="text-gray-300">0</span><i class="fa-solid fa-question fa-lg pl-3"
                      style="color: #c80000;"></i></p>
          </div>
          <button type="button"
              class="bg-violet-900 text-white sm:text-2xl text-lg font-bold rounded-xl p-4 buttonFonctionnalites"><i
                  class="fa-solid fa-pen-to-square" style="color: #ffffff;"></i></button>
      </div>
    </div>`;
  }

  estAutorise() {
    return true;
  }

  load() {
    if (this.vueService.indexCtrl.username !== null) {
      chargerQuizzes(this.vueService.indexCtrl.username, (data) => {
        $("#quizzes").html("");
        data.forEach((elementQuiz) => {
          let quiz = Quiz.fromJSON(elementQuiz);
          console.log(quiz)
          this.nouveauElementQuiz(quiz);
        });
      });
    } else {
      $("#quizzes").html(`<div class="text-gray-500 py-8 mt-16 text-2xl font-bold">Vous devez vous connecter pour créer ou voir vos quiz</div>`);
    }

    /*chargerProjets("", 0, (data) => {
      $("#projets").html("")
      data.forEach((elementP) => {
        let projet = Projet.fromJSON(elementP);
        this.nouveauElementProjet(projet);
      });
      if (data.length === 0) {
        $("#projets").append(`<div class="mx-auto w-full max-w-7xl px-4 sm:px-6 lg:px-8 text-gray-500 text-xl text-center font-bold rounded-xl p-3 my-5">Pas de projets trouvés</button>`);
      } else {
        $("#projets").append(`<button type="button" class="block mx-auto w-full max-w-7xl px-4 sm:px-6 lg:px-8 bg-blue-800 hover:bg-blue-900 text-white text-xl font-bold rounded-xl p-3 my-3 buttonCharger">Charger plus</button>`);
      }

      this.setupBouttons("projets");

      $(".buttonCharger").click((event) => {
        chargerProjets("", $("#projets").children(".projet").length, (data) => {
          data.forEach((elementP) => {
            let projet = Projet.fromJSON(elementP);
            this.nouveauElementProjet(projet, true);
          });
          if (data.length < 10) {
            $(event.currentTarget).hide();
          }
        }, (jqXHR) => {
          this.vueService.afficherErreur(jqXHR.responseJSON.message, () => { });
        });
      });
    }, (jqXHR) => {
      this.vueService.afficherErreur(jqXHR.responseJSON.message, () => { });
    });*/
  }

  setupBouttons(idBase) {
    let base = $("#" + idBase);

    base.find(".buttonFonctionnalites").click(function (event) {
      let fonctionnalites = $(this).parent().siblings(".fonctionnalites");
      if (fonctionnalites.is(":visible")) {
        $(this).text("Voir les fonctionnalitées");
      } else {
        $(this).text("Ne plus voir les fonctionnalitées");
      }
      fonctionnalites.slideToggle(500);
    });
  }

  nouveauElementQuiz(quiz) {
    
  }

  nouveauElementProjet(projet, creerApres) {
    let htmlProjet = `<div id="${projet.getPkProjet()}" class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8 bg-white border-2 border-gray-300 rounded-xl my-10 py-8 flex flex-col projet">
        <div class="py-2 flex flex-row justify-between">
          <p id="project-name" class="sm:text-2xl text-lg font-bold">${projet.getTitre()}</p>
          <p id="project-creator" class="sm:text-xl text-lg font-bold text-gray-500"><span class="text-gray-400">par </span>${projet.getUtilisateur()}</p>
        </div>
        <div class="py-2">
          <p id="descriptionProjet" class="sm:text-lg text-sm  text-justify">${projet.getDescription()}</p>
        </div>`;

    if (projet.getFonctionnalites().length > 0) {
      let estPremier = true;
      htmlProjet += `<div class="bg-gray-300 border-2 border-gray-400 rounded-xl flex flex-col hidden fonctionnalites">`;
      projet.getFonctionnalites().forEach(fonctionnalite => {
        if (!estPremier) {
          htmlProjet += `<div class="px-3">
              <div class="border border-gray-600 w-full"></div>
            </div>`;
        } else {
          estPremier = false;
        }

        htmlProjet += `<div class="p-2 flex flex-row justify-between">
            <p id="nomFonctionnalite" class="my-auto sm:text-lg text-base  font-bold text-gray-800 text-center w-2/12 px-1.5">${fonctionnalite.getTitre()}</p>
            <div class="py-1">
              <div class="border border-gray-600 h-full"></div>
            </div>
            <p id="descriptionFonctionnalite" class="my-auto sm:text-lg text-sm text-gray-600 text-justify w-10/12 px-1.5">${fonctionnalite.getDescription()}</p>
          </div>`;
      });
      htmlProjet += `</div ><div class="pt-5 flex justify-center">
          <button type="button" class="w-full bg-gray-600 text-white sm:text-xl text-base font-bold rounded-xl py-2 px-3 buttonFonctionnalites">Voir les fonctionnalitées</button>
        </div>
    </div >`;
    }
    if (creerApres) {
      $("#projets").children().last().before(htmlProjet)
    } else {
      $("#projets").append(htmlProjet);
    }
  }
}