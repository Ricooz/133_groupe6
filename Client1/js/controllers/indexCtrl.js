/*
  But : contrôleur principale
  Auteur : Morisetti David
  Date :   23.02.2023 / V1.0
*/

class IndexCtrl {
  constructor() {
    // Charge le service de vue
    let nomFichierHTML = window.location.pathname.substring(window.location.pathname.lastIndexOf("/") + 1);
    
    chargerUtilisateurInfos((data) => {
      this.vueService = new VueService(this, nomFichierHTML, data.username);
    }, (jqXHR) => {
      this.vueService = new VueService(this, nomFichierHTML);
    });
  }
}

$().ready(function () {
  const indexCtrl = new IndexCtrl();
});
