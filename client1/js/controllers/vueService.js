/*
  But : service de la vue
  Auteur : Morisetti David
  Date :   23.02.2023 / V1.0
*/

class VueService {
  constructor(indexCtrl, premierVue, utilisateur) {
    this.indexCtrl = indexCtrl;

    if (utilisateur) {
      this.utilisateurConnecte(utilisateur);
    } else {
      this.utilisateurDeconnecte();
    }

    this.fichierHTML = "";
    this.fichiersHTML = {
      home: "",
      projets: "",
      login: "",
      signin: "",
      admin: "",
    };

    this.controllers = {
      home: "",
      projets: "",
      login: "",
      signin: "",
      admin: "",
    };

    this.chargerFichiersJS();
    this.chargerFichiersHTML(premierVue);

    window.addEventListener('popstate', (event) => {
      this.changerVue(event.state ? event.state.page : 'home');
    });
  }

  chargerFichiersHTML(premierVue) {
    // Traduit aucune vue en home
    if (premierVue === "") {
      premierVue = "home";
    }

    let requests = [];
    let root = window.location.pathname.substring(0, window.location.pathname.lastIndexOf("/") + 1);
    for (let [key, value] of Object.entries(this.fichiersHTML)) {
      let request = $.get(root + key + ".html", (data) => {
        this.fichiersHTML[key] = data;
      });
      requests.push(request);
    }
    $.when(...requests).done(() => {
      this.changerVue(premierVue);
    });
  }

  chargerFichiersJS() {
    this.controllers["home"] = new HomeCtrl(this);
    this.controllers["projets"] = new ProjetsCtrl(this);
    this.controllers["login"] = new LoginCtrl(this);
    this.controllers["signin"] = new SigninCtrl(this);
    this.controllers["admin"] = new AdminCtrl(this);
  }

  changerVue(vue) {
    if (this.fichiersHTML[vue] !== null && this.fichierHTML !== vue) {
      let root = window.location.pathname.substring(0, window.location.pathname.lastIndexOf("/") + 1);
      if (this.controllers[vue].estAutorise()) {
        if (vue === "home") {
          $("#content").html(this.fichiersHTML[vue]);
          history.pushState({ page: vue }, vue, root);
        } else {
          $("#content").html(this.fichiersHTML[vue]);
          history.pushState({ page: vue }, vue, root + vue);
        }
      } else {
        this.controllers[vue].pasAutorise();
      }

      this.fichierHTML = vue;
      this.controllers[vue].load();
    }
  }

  utilisateurConnecte(utilisateur) {
    if (this.indexCtrl.utilisateur !== null && this.indexCtrl.utilisateur !== undefined) {
      return
    }
    this.indexCtrl.utilisateur = utilisateur;

    let htmlGauche = '<a href="#" class="g-gray-900 text-white rounded-md px-4 py-4 text-xl font-medium homeButton" aria-current="page">Home</a><a href="#" class="text-gray-300 hover:bg-gray-700 hover:text-white rounded-md px-4 py-3 text-xl font-medium projetsButton">Mes projets</a>';
    if (utilisateur.isAdmin()) {
      htmlGauche += '<a href="#" class="text-gray-300 hover:bg-gray-700 hover:text-white rounded-md px-4 py-3 text-xl font-medium adminButton">Administrateur</a>';
    }
    $(".boutonsGauche").html(htmlGauche);

    $(".homeButton").click((event) => {
      event.preventDefault();
      this.changerVue("home");
    });
    $(".projetsButton").click((event) => {
      event.preventDefault();
      this.changerVue("projets");
    });
    if (utilisateur.isAdmin()) {
      $(".adminButton").click((event) => {
        event.preventDefault();
        this.changerVue("admin");
      });
    }

    $(".boutonsDroit").html('<div class="block rounded-md px-4 py-3 text-xl font-medium text-gray-500">' + utilisateur.getNom() + '</div><a href="#" class="block rounded-md px-4 py-3 text-xl font-medium text-gray-300 hover:bg-gray-700 deconnecterButton">Deconnexion</a>');
    $(".deconnecterButton").click((event) => {
      event.preventDefault();
      deconnecterUtilisateur((data) => {
        this.utilisateurDeconnecte();
        this.changerVue("home");
      }, (jqXHR) => {
        this.afficherErreur(jqXHR.responseJSON.message, () => { });
      });
    });
  }

  utilisateurDeconnecte() {
    if (this.indexCtrl.utilisateur === null) {
      return
    }
    this.indexCtrl.utilisateur = null;

    $(".boutonsGauche").html('<a href="#" class="g-gray-900 text-white rounded-md px-4 py-4 text-xl font-medium homeButton" aria-current="page">Home</a>');
    $(".homeButton").click((event) => {
      event.preventDefault();
      this.changerVue("home");
    });

    $(".boutonsDroit").html('<a href="#" class="block rounded-md px-4 py-3 text-xl font-medium text-gray-400 hover:bg-gray-700 hover:text-white enregistrerButton">S\'enregistrer</a><a href="#" class="block rounded-md px-4 py-3 text-xl font-medium text-gray-400 hover:bg-gray-700 hover:text-white connecterButton">Se connecter</a>');
    $(".connecterButton").click((event) => {
      event.preventDefault();
      this.changerVue("login");
    });
    $(".enregistrerButton").click((event) => {
      event.preventDefault();
      this.changerVue("signin");
    });
  }

  afficherSucces(message, callback) {
    $("#popup").hide().html('<div class="fixed inset-0 flex items-center justify-center z-50 pointer-events-none hidden"><div class="rounded-lg bg-gray-50 px-16 py-14 bg-opacity-95 pointer-events-auto"><div class="flex justify-center"><div class="rounded-full bg-green-200 p-6"><div class="flex h-16 w-16 items-center justify-center rounded-full bg-green-500 p-4"><svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="h-8 w-8 text-white"><path stroke-linecap="round" stroke-linejoin="round" d="M4.5 12.75l6 6 9-13.5" /></svg></div></div></div><h3 class="my-4 text-center text-3xl font-semibold text-gray-700">Succès</h3><p class="w-[230px] text-center font-normal text-gray-600">' + message + '</p><button id="buttonPopupOK" class="mx-auto mt-10 block rounded-xl border-4 border-transparent bg-orange-400 px-6 py-3 text-center text-base font-medium text-orange-100 outline-8 hover:outline hover:duration-300">OK</button></div></div>');
    $("#popup").fadeIn("slow");
    $("#buttonPopupOK").click((event) => {
      event.preventDefault();
      if (callback !== null && typeof callback === 'function') {
        callback();
      }
      
      $("#popup").fadeOut("slow", () => {
        $("#popup").html('');
      });
    });
  }

  afficherErreur(message, callback) {
    $("#popup").hide().html('<div class="fixed inset-0 flex items-center justify-center z-50 pointer-events-none"><div class="rounded-lg bg-gray-50 px-16 py-14 bg-opacity-95 pointer-events-auto"><div class="flex justify-center"><div class="rounded-full bg-red-200 p-6"><div class="flex h-16 w-16 items-center justify-center rounded-full bg-red-500 p-4"><svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="h-8 w-8 text-white"><path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12" /></svg></div></div></div><h3 class="my-4 text-center text-3xl font-semibold text-gray-700">Echec</h3><p class="w-[230px] text-center font-normal text-gray-600">' + message + '</p><button id="buttonPopupOK" class="mx-auto mt-10 block rounded-xl border-4 border-transparent bg-orange-400 px-6 py-3 text-center text-base font-medium text-orange-100 outline-8 hover:outline hover:duration-300">OK</button></div></div>');
    $("#popup").fadeIn("slow");
    $("#buttonPopupOK").click((event) => {
      event.preventDefault();
      if (callback !== null && typeof callback === 'function') {
        callback();
      }
      
      $("#popup").fadeOut("slow", () => {
        $("#popup").html('');
      });
    });
  }
}
