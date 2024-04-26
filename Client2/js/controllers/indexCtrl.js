class IndexCtrl {
    constructor() {
      // Charge le service de vue
      let nomFichierHTML = window.location.pathname.substring(window.location.pathname.lastIndexOf("/") + 1);
  
      chargerUtilisateurInfos((data) => {
        this.vueService = new VueService(this, nomFichierHTML, new Utilisateur(data.pk_utilisateur, data.nom, data.estAdmin));
      }, () => {
        this.vueService = new VueService(this, nomFichierHTML);
      });
    }
  }
  
  $().ready(function () {
    const indexCtrl = new IndexCtrl();
  });
  