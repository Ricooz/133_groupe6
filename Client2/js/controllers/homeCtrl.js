/*
  But : contrôleur de la page home
  Auteur : Richoz Matteo
  Date :   29.04.2024 / V1.0
*/

class HomeCtrl {
  constructor(vueService) {
    this.vueService = vueService;
  }

  load() {
    chargerQuizzes("", 0, (data) => {
      $("#quizzes").html("")
      data.forEach((elementP) => {
        let quiz = Quiz.fromJSON(elementP);
        this.nouveauElementProjet(quiz);
      });
      if (data.length === 0) {
        $("#quizzes").append(`<div class="mx-auto w-full max-w-7xl px-4 sm:px-6 lg:px-8 text-gray-500 text-xl text-center font-bold rounded-xl p-3 my-5">Pas de quizzes trouvés</button>`);
      } else {
        $("#quizzes").append(`<button type="button" class="block mx-auto w-full max-w-7xl px-4 sm:px-6 lg:px-8 bg-blue-800 hover:bg-blue-900 text-white text-xl font-bold rounded-xl p-3 my-3 buttonCharger">Charger plus</button>`);
      }

      this.setupBouttons("quizzes");


    }, (jqXHR) => {
      this.vueService.afficherErreur(jqXHR.responseJSON.message, () => { });
    });
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
              <div class="py-1 min-h-6">
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