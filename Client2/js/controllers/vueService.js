/*
  But : service de la vue
  Auteur : Richoz Matteo
  Date :   29.04.2024 / V1.0
*/

class VueService {
  constructor(indexCtrl, premierVue, username) {
    this.indexCtrl = indexCtrl;
    this.indexCtrl.username = null;
    this.root = window.location.pathname.substring(0, window.location.pathname.lastIndexOf("/") + 1);
    this.root = this.root.slice(0, -1);

    if (username) {
      this.utilisateurConnecte(username);
    } else {
      this.utilisateurDeconnecte(true);
    }

    this.fichierHTML = "";
    this.fichiersHTML = {
      home: "",
      jouer: "",
      login: "",
      signup: "",
    };

    this.controllers = {
      home: "",
      jouer: "",
      login: "",
      signup: "",
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
    this.controllers["jouer"] = new JouerCtrl(this);
    this.controllers["login"] = new LoginCtrl(this);
    this.controllers["signup"] = new SignupCtrl(this);
  }

  changerVue(vue, reload) {
    if (this.fichierHTML !== vue || reload === true) { // Si cette vu n'est pas déja chargé
      if (this.fichiersHTML[vue] === undefined) { // Si cette vue n'existe pas
        vue = "home";
      }

      if (this.controllers[vue].estAutorise()) {
        if (vue === "home") {
          $("#content").html(this.fichiersHTML[vue]);
          history.pushState({ page: vue }, vue, this.root);
        } else {
          $("#content").html(this.fichiersHTML[vue]);
          history.pushState({ page: vue }, vue, this.root + "/" + vue);
        }
      } else {
        this.controllers[vue].pasAutorise();
      }

      this.fichierHTML = vue;
      this.controllers[vue].load();
    }
  }

  utilisateurConnecte(username) {
    if (this.indexCtrl.username !== null) {
      return;
    }
    this.indexCtrl.usernmae = username;

    let htmlGauche = '<a href="#" class="g-gray-900 text-white rounded-md px-4 py-4 text-xl font-medium homeButton" aria-current="page">Home</a><a href="#" class="text-gray-300 hover:bg-gray-700 hover:text-white rounded-md px-4 py-3 text-xl font-medium projetsButton">Mes projets</a>';
    $(".boutonsGauche").html(htmlGauche);

    $(".homeButton").click((event) => {
      event.preventDefault();
      this.changerVue("home");
    });
    $(".jouerButton").click((event) => {
      event.preventDefault();
      this.changerVue("jouer");
    });

    $(".boutonsDroit").html('<div class="block rounded-md px-4 py-3 text-xl font-medium text-gray-500">' + username + '</div><a href="#" class="block rounded-md px-4 py-3 text-xl font-medium text-gray-300 hover:bg-gray-700 deconnecterButton">Deconnexion</a>');
    $(".deconnecterButton").click((event) => {
      event.preventDefault();
      deconnecterUtilisateur((data) => {
        this.utilisateurDeconnecte();
        this.changerVue("home");
      }, () => {
        this.utilisateurDeconnecte();
        this.changerVue("home");
      });
    });
  }

  utilisateurDeconnecte(firstLoad) {
    if (this.indexCtrl.username === null && !firstLoad) {
      return
    }
    this.indexCtrl.username = null;

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
      this.changerVue("signup");
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
