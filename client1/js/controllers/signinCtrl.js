/*
  But : contrôleur de la vue signin
  Auteur : Morisetti David
  Date :   01.03.2023 / V1.0
*/

class SigninCtrl {
  constructor(vueService) {
    this.vueService = vueService;
  }

  estAutorise() {
    return true;
  }

  load() {
    $("form").on("submit", (e) => {
      e.preventDefault();
      var nom = $("#nom").val();
      var motDePasse = $("#password").val();
      var confirmationMotDePasse = $("#passwordConfirm").val();

      // Annule si la confirmation du mot de passe n'est pas egal
      if (motDePasse === confirmationMotDePasse) {
        enregistrerUtilisateur(nom, motDePasse, (data) => {
          this.vueService.utilisateurConnecte(new Utilisateur(data.pk_utilisateur, data.nom, data.estAdmin));
          this.vueService.changerVue("projets");
        }, (jqXHR) => {
          this.vueService.afficherErreur(jqXHR.responseJSON.message);
        });
      } else {
        $("#password").val("");
        $("#passwordConfirm").val("");
        this.vueService.afficherErreur("Mots de passe non identiques.");
      }
    });
  }
}