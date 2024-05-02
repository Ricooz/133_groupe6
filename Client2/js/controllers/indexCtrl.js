/*
  But : contrôleur principale
  Auteur : Richoz Matteo
  Date :   29.04.2024 / V1.0
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
