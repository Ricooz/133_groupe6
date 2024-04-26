/*
  But : contrôleur de la vue projets
  Auteur : Morisetti David
  Date :   01.03.2023 / V1.0
*/

class ProjetsCtrl {
  constructor(vueService) {
    this.vueService = vueService;
  }

  estAutorise() {
    return this.vueService.indexCtrl.utilisateur !== null && this.vueService.indexCtrl.utilisateur !== undefined;
  }
  
  pasAutorise() {
    this.vueService.afficherErreur("Pas d'utilisateur authentifié.", () => {
      this.vueService.changerVue("login");
    })
  }

  load() {
    chargerProjets(
      "utilisateur", "",
      (data) => {
        $("#projets").html("");
        data.forEach((elementP) => {
          let projet = Projet.fromJSON(elementP);
          this.nouveauElementProjet(projet);
        });
        $("#projets").append(`<button type="button" class="block mx-auto w-full max-w-7xl px-4 sm:px-6 lg:px-8 bg-green-600 hover:bg-green-700 text-white text-xl font-bold rounded-xl p-3 my-3 buttonNouveauProjet">Nouveau projet</button>`);

        $(".buttonNouveauProjet").click((event) => {
          let bouttons = $(event.currentTarget).parent();
          let projet = bouttons.parent();

          creerProjet("", "", (data) => {
            let projet = Projet.fromJSON(data);
            this.nouveauElementProjet(projet, true);
          }, (jqXHR) => {
            this.vueService.afficherErreur(jqXHR.responseJSON.message);
          })
        });
        this.setupBouttons("projets");
      })
  }

  setupBouttons(idBase) {
    let base = $("#" + idBase);

    base.find(".buttonModifier").click((event) => {
      let bouttons = $(event.currentTarget).parent();
      let projet = bouttons.parent();

      projet.find("textarea").prop("readonly", false);

      $(event.currentTarget).hide();
      projet.find(".buttonNouvelleFonctionnalite").show();
      bouttons.find(".buttonSauvegarder").show();
      bouttons.find(".buttonSupprimer").show();
      $(".buttonSupprimerFonctionnalite").show();
    });

    base.find(".buttonSauvegarder").click((event) => {
      let bouttons = $(event.currentTarget).parent();
      let projetModifie = this.creerProjetDeElement($(event.currentTarget).parent().parent());
      modifierProjetInfos(
        projetModifie,
        (data) => {
          bouttons.parent().find("textarea").prop("readonly", true);

          let projet = Projet.fromJSON(data);
        }, (jqXHR) => {
          this.vueService.afficherErreur(jqXHR.responseJSON.message, () => { });
        });

      let nouvelleFonctionnalites = bouttons.parent().find(".fonctionnalites").children().filter(function () {
        return this.id.includes('FNew');
      });
      nouvelleFonctionnalites.each((index, elementFonctionnalite) => {
        elementFonctionnalite = $(elementFonctionnalite);
        ajouterFonctionnalite(projetModifie.getPkProjet(), elementFonctionnalite.find(".titre").val(), elementFonctionnalite.find(".description").val(), (data) => {

        }, (jqXHR) => {
          this.vueService.afficherErreur(jqXHR.responseJSON.message, () => { });
        });
      });

      let fonctionnaliteModifies = bouttons.parent().find(".fonctionnalites").children().filter(function () {
        return this.id.includes('F') && !this.id.includes('New') && !$(this).is(":hidden");
      });
      fonctionnaliteModifies.each((index, elementFonctionnalite) => {
        elementFonctionnalite = $(elementFonctionnalite);
        modifierFonctionnalite(new Fonctionnalite(elementFonctionnalite.data("fonctionnalite").getPkFonctionnalite(), elementFonctionnalite.find(".titre").val(), elementFonctionnalite.find(".description").val()), (data) => {

        }, (jqXHR) => {
          this.vueService.afficherErreur(jqXHR.responseJSON.message, () => { });
        });
      });

      let fonctionnaliteSupprimer = bouttons.parent().find(".fonctionnalites").children().filter(function () {
        return $(this).is(":hidden") && this.id.includes('F');
      });
      fonctionnaliteSupprimer.each((index, elementFonctionnalite) => {
        elementFonctionnalite = $(elementFonctionnalite);
        supprimerFonctionnalite(elementFonctionnalite.data("fonctionnalite").getPkFonctionnalite()), (data) => {

        }, (jqXHR) => {
          this.vueService.afficherErreur(jqXHR.responseJSON.message, () => { });
        }
      });

      $(event.currentTarget).hide();
      bouttons.parent().find(".buttonNouvelleFonctionnalite").hide();
      bouttons.find(".buttonSupprimer").hide();
      bouttons.find(".buttonModifier").show();
      bouttons.parent().find(".buttonSupprimerFonctionnalite").hide();
    });

    base.find(".buttonNouvelleFonctionnalite").click((event) => {
      $(event.currentTarget).parent().before(`<div class="px-3">
                                      <div class="border border-gray-400 w-full"></div>
                                     </div>
                                     <div id="FNew" class="p-2 flex flex-row justify-between">
                                      <textarea class="my-auto h-24 text-lg font-bold text-gray-700 text-center bg-gray-200 w-2/12 px-1 mx-1.5 border-2 border-gray-500 rounded-xl titre" rows="1" placeholder="Titre"></textarea>
                                      <div class="py-1">
                                        <div class="border border-gray-400 h-full"></div>
                                      </div>
                                      <textarea class="text-xl text-gray-500 text-justify bg-gray-200 w-10/12 p-1 mx-1.5 border-2 border-gray-500 rounded-xl description" rows="3" placeholder="Description"></textarea>
                                      <button type="button" class="bg-red-700 hover:bg-red-800 text-white text-xl font-bold rounded-xl p-2 mx-1.5 buttonSupprimerFonctionnalite">Supprimer</button>
                                     </div>`);
      base.find(".buttonSupprimerFonctionnalite").click((event) => {
        let fonctionnaliteElement = $(event.currentTarget).parent();
        if (fonctionnaliteElement.prev().is(":visible")) {
          fonctionnaliteElement.prev().hide();
        } else {
          if (!fonctionnaliteElement.next().hasClass("nouvelleFonctionnalite")) {
            fonctionnaliteElement.next().hide();
          }
        }

        if (fonctionnaliteElement.attr('id') == "FNew") {
          fonctionnaliteElement.remove();
        } else {
          fonctionnaliteElement.hide();
        }
      });
    });

    base.find(".buttonSupprimerFonctionnalite").click((event) => {
      let fonctionnaliteElement = $(event.currentTarget).parent();
      if (fonctionnaliteElement.prev().is(":visible")) {
        fonctionnaliteElement.prev().hide();
      } else {
        if (!fonctionnaliteElement.next().hasClass("nouvelleFonctionnalite")) {
          fonctionnaliteElement.next().hide();
        }
      }

      if (fonctionnaliteElement.attr('id') == "FNew") {
        fonctionnaliteElement.remove();
      } else {
        fonctionnaliteElement.hide();
      }
    });

    base.find(".buttonSupprimer").click((event) => {
      let pkProjet = $(event.currentTarget).parent().parent().data("projet").getPkProjet()
      supprimerProjet(pkProjet, (data) => {
        $("#" + pkProjet).remove();
      }, (jqXHR) => {
        this.vueService.afficherErreur(jqXHR.responseJSON.message, () => { });
      })
    });

    base.find(".buttonPublic").click((event) => {
      let projetElement = $(event.currentTarget).parent().parent();
      let pkProjet = projetElement.data("projet").getPkProjet()
      modifierProjetPublic(pkProjet, true, (data) => {
        projetElement.find(".buttonPublic").hide();
        projetElement.find(".buttonPrive").show();
      }, (jqXHR) => {
        this.vueService.afficherErreur(jqXHR.responseJSON.message, () => { });
      })
    });

    base.find(".buttonPrive").click((event) => {
      let projetElement = $(event.currentTarget).parent().parent();
      let pkProjet = projetElement.data("projet").getPkProjet()
      modifierProjetPublic(pkProjet, false, (data) => {
        if (this.vueService.indexCtrl.utilisateur.isAdmin()) {
          projetElement.find(".buttonPublic").show();
        } else {
          projetElement.find(".buttonDemandePublic").show();
        }
        projetElement.find(".buttonPrive").hide();
      }, (jqXHR) => {
        this.vueService.afficherErreur(jqXHR.responseJSON.message, () => { });
      })
    });

    base.find(".buttonDemandePublic").click((event) => {
      let projetElement = $(event.currentTarget).parent().parent();
      let pkProjet = projetElement.data("projet").getPkProjet()
      modifierProjetDemandePublic(pkProjet, true, (data) => {
        projetElement.find(".buttonAnnulerDemandePublic").show();
        projetElement.find(".buttonDemandePublic").hide();
      }, (jqXHR) => {
        this.vueService.afficherErreur(jqXHR.responseJSON.message, () => { });
      })
    });

    base.find(".buttonAnnulerDemandePublic").click((event) => {
      let projetElement = $(event.currentTarget).parent().parent();
      let pkProjet = projetElement.data("projet").getPkProjet()
      modifierProjetDemandePublic(pkProjet, false, (data) => {
        projetElement.find(".buttonAnnulerDemandePublic").hide();
        projetElement.find(".buttonDemandePublic").show();
      }, (jqXHR) => {
        this.vueService.afficherErreur(jqXHR.responseJSON.message, () => { });
      })
    });
  }

  nouveauElementProjet(projet, creerApres) {
    let htmlProjet = `<div id="${projet.getPkProjet()}" class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8 bg-white border-2 border-gray-300 rounded-xl my-10 py-8 flex flex-col">
        <div class="py-2 flex flex-row justify-between">
          <textarea class="w-full text-2xl font-bold p-1 border-2 border-gray-400 bg-gray-100 rounded-xl project-name" rows="1" placeholder="Titre" readonly>${projet.getTitre()}</textarea>
        </div>
        <div class="py-2">
          <textarea class="w-full text-md text-justify p-1 border-2 border-gray-400 bg-gray-100 rounded-xl descriptionProjet" rows="5" placeholder="Description" readonly>${projet.getDescription()}</textarea>
        </div>
        <div class="bg-gray-300 border-gray-400 rounded-xl flex flex-col fonctionnalites"></div>
        <div class="pt-5 flex justify-around buttons">
          <button type="button" class="w-full bg-blue-800 hover:bg-blue-900 text-white text-xl font-bold rounded-xl py-2 px-3 buttonModifier">Modifier le projet</button>
          <button type="button" class="w-full bg-green-600 hover:bg-green-700 text-white text-xl font-bold rounded-xl py-2 px-3 mr-2 hidden buttonSauvegarder">Sauvegarder le projet</button>
          <button type="button" class="w-full bg-red-700 hover:bg-red-800 text-white text-xl font-bold rounded-xl py-2 px-3 ml-2 hidden buttonSupprimer">Supprimer le projet</button>
        </div>
        <div class="pt-5 flex justify-around buttons">
          <button type="button" class="w-full bg-green-600 hover:bg-green-700 text-white text-xl font-bold rounded-xl py-2 px-3 hidden buttonPublic">Rendre public</button>
          <button type="button" class="w-full bg-red-700 hover:bg-red-800 text-white text-xl font-bold rounded-xl py-2 px-3 hidden buttonPrive">Rendre privé</button>
          <button type="button" class="w-full bg-green-600 hover:bg-green-700  text-white text-xl font-bold rounded-xl py-2 px-3 hidden buttonDemandePublic">Demander public</button>
          <button type="button" class="w-full bg-red-700 hover:bg-red-800 text-white text-xl font-bold rounded-xl py-2 px-3 hidden buttonAnnulerDemandePublic">Annuler demande</button>
        </div>
      </div >`;
    if (creerApres) {
      $("#projets").children().last().before(htmlProjet)
    } else {
      $("#projets").append(htmlProjet);
    }
    let projetElement = $(`#${projet.getPkProjet()}`);
    projetElement.data("projet", projet);
    let fonctionnalitesElement = projetElement.find(".fonctionnalites");

    let estPremier = true;
    projet.getFonctionnalites().forEach((fonctionnalite) => {
      if (!estPremier) {
        fonctionnalitesElement.append(`<div class="px-3">
                                  <div class="border border-gray-400 w-full"></div>
                                </div>`);
      } else {
        fonctionnalitesElement.addClass("border-2");
        estPremier = false;
      }
      fonctionnalitesElement.append(`<div id="F${fonctionnalite.getPkFonctionnalite()}" class="p-2 flex flex-row justify-between">
                                <textarea class="my-auto h-24 text-lg font-bold text-gray-700 text-center bg-gray-200 w-2/12 px-1 mx-1.5 border-2 border-gray-500 rounded-xl titre" rows="1" placeholder="Titre" readonly>${fonctionnalite.getTitre()}</textarea>
                                <div class="py-1">
                                  <div class="border border-gray-400 h-full"></div>
                                </div>
                                <textarea class="text-xl text-gray-500 text-justify bg-gray-200 w-10/12 p-1 mx-1.5 border-2 border-gray-500 rounded-xl description" rows="3" placeholder="Description" readonly>${fonctionnalite.getDescription()}</textarea>
                                <button type="button" class="bg-red-700 hover:bg-red-800 text-white text-xl font-bold rounded-xl p-2 mx-1.5 hidden buttonSupprimerFonctionnalite">Supprimer</button>
                              </div>`);
      $(`#F${fonctionnalite.getPkFonctionnalite()}`).data("fonctionnalite", fonctionnalite);
    });
    fonctionnalitesElement.append(`<div class="px-3 my-3 nouvelleFonctionnalite">
                                      <button type="button" class="w-full bg-blue-800 hover:bg-blue-900 text-white text-xl font-bold rounded-xl py-2 px-3 hidden buttonNouvelleFonctionnalite">Nouvelle fonctionnalite</button>
                                    </div>`);


    if (projet.isPublic()) {
      projetElement.find(".buttonPrive").show();
    } else {
      if (this.vueService.indexCtrl.utilisateur.isAdmin()) {
        projetElement.find(".buttonPublic").show();
      } else {
        if (projet.isDemandePublic()) {
          projetElement.find(".buttonAnnulerDemandePublic").show();
        } else {
          projetElement.find(".buttonDemandePublic").show();
        }
      }
    }

    if (creerApres) {
      this.setupBouttons(projet.getPkProjet());
    }
  }

  creerProjetDeElement(elementProjet) {
    let projet = elementProjet.data("projet");
    projet.setTitre(elementProjet.find(".project-name").val());
    projet.setDescription(elementProjet.find(".descriptionProjet").val());
    return projet;
  }
}
