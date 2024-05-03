/*
  But : contrôleur de la vue creation
  Auteur : Morisetti David
  Date :   01.03.2023 / V1.0
*/

class CreationCtrl {
  constructor(vueService) {
    this.vueService = vueService;
  }

  estAutorise() {
    return this.vueService.indexCtrl.username !== null;
  }
  
  pasAutorise() {
    this.vueService.afficherErreur("Pas d'utilisateur authentifié.", () => {
      this.vueService.changerVue("login");
    })
  }

  load() {
    
  }

  loadNewQuiz() {
    
  }

  loadQuiz() {

  }
}
