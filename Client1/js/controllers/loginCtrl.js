/*
  But : contrôleur de la vue login
  Auteur : Morisetti David
  Date :   01.03.2023 / V1.0
*/

class LoginCtrl {
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

      connecterUtilisateur(nom, motDePasse, (data) => {
        this.vueService.utilisateurConnecte(data.username);
        this.vueService.changerVue("home", true);
      }, () => {
        this.vueService.afficherErreur("Nom d'utilisateur ou mot passe invalide.");
      });
    });
  }
}
