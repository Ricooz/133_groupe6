/*
  But : contrôleur de la vue signup
  Auteur : Richoz Matteo
  Date :   02.05.2024 / V1.0
*/

class SignupCtrl {
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
            this.vueService.utilisateurConnecte(data.username);
            this.vueService.changerVue("home", true);
          }, () => {
            this.vueService.afficherErreur("Nom d'utilisateur déja utilisé.");
          });
        } else {
          $("#password").val("");
          $("#passwordConfirm").val("");
          this.vueService.afficherErreur("Mots de passe non identiques.");
        }
      });
    }
  }