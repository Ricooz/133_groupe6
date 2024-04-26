/*
 * Couche de services HTTP (worker).
 *
 * @author Morisetti David
 * @version 1.0 / 23.02.2023
 */

var BASE_URL = "http://localhost:8080/projet/serveur/";

/**
 * Fonction permettant de demander la liste des pays au serveur.
 * @param {type} String categorie de projet.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function chargerProjets(categorie, offset, successCallback, errorCallback) {
  if (categorie !== "") {
    $.ajax({
      type: "GET",
      dataType: "json",
      data: "categorie=" + categorie,
      url: BASE_URL + "projetManager.php",
      success: successCallback,
      error: errorCallback,
    });
  } else {
    $.ajax({
      type: "GET",
      dataType: "json",
      data: "offset=" + offset,
      url: BASE_URL + "projetManager.php",
      success: successCallback,
      error: errorCallback,
    });
  }
}

/**
 * Fonction permettant de créer un projet.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function creerProjet(titre, description, successCallback, errorCallback) {
  $.ajax({
    type: "POST",
    dataType: "json",
    data: {
      titre: titre,
      description: description
    },
    url: BASE_URL + "projetManager.php",
    success: successCallback,
    error: errorCallback,
  });
}

/**
 * Fonction permettant de modifier les infos un projet.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function modifierProjetInfos(projet, successCallback, errorCallback) {
  $.ajax({
    type: "PUT",
    dataType: "json",
    data: {
      pk_projet: projet.getPkProjet(),
      titre: projet.getTitre(),
      description: projet.getDescription()
    },
    url: BASE_URL + "projetManager.php",
    success: successCallback,
    error: errorCallback,
  });
}

/**
 * Fonction permettant de modifier la demande de public d'un projet.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function modifierProjetDemandePublic(pk_projet, demandePublic, successCallback, errorCallback) {
  $.ajax({
    type: "PUT",
    dataType: "json",
    data: {
      pk_projet: pk_projet,
      demandePublic: demandePublic
    },
    url: BASE_URL + "projetManager.php",
    success: successCallback,
    error: errorCallback,
  });
}

/**
 * Fonction permettant de modifier public d'un projet.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function modifierProjetPublic(pk_projet, estPublic, successCallback, errorCallback) {
  $.ajax({
    type: "PUT",
    dataType: "json",
    data: {
      pk_projet: pk_projet,
      public: estPublic
    },
    url: BASE_URL + "projetManager.php",
    success: successCallback,
    error: errorCallback,
  });
}

/**
 * Fonction permettant de supprimer un projet.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function supprimerProjet(pk_projet, successCallback, errorCallback) {
  $.ajax({
    type: "DELETE",
    dataType: "json",
    data: {
      pk_projet: pk_projet
    },
    url: BASE_URL + "projetManager.php",
    success: successCallback,
    error: errorCallback,
  });
}

/**
 * Fonction permettant de rajouter une fonctionnalite.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function ajouterFonctionnalite(pk_projet, titre, description, successCallback, errorCallback) {
  $.ajax({
    type: "POST",
    dataType: "json",
    data: {
      pk_projet: pk_projet,
      titre: titre,
      description: description
    },
    url: BASE_URL + "fonctionnaliteManager.php",
    success: successCallback,
    error: errorCallback,
  });
}

/**
 * Fonction permettant de modifier une fonctionnalite.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function modifierFonctionnalite(fonctionnalite, successCallback, errorCallback) {
  $.ajax({
    type: "PUT",
    dataType: "json",
    data: {
      pk_fonctionnalite: fonctionnalite.getPkFonctionnalite(),
      titre: fonctionnalite.getTitre(),
      description: fonctionnalite.getDescription()
    },
    url: BASE_URL + "fonctionnaliteManager.php",
    success: successCallback,
    error: errorCallback,
  });
}

/**
 * Fonction permettant de supprimer une fonctionnalite.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function supprimerFonctionnalite(pk_fonctionnalite, successCallback, errorCallback) {
  $.ajax({
    type: "DELETE",
    dataType: "json",
    data: {
      pk_fonctionnalite: pk_fonctionnalite
    },
    url: BASE_URL + "fonctionnaliteManager.php",
    success: successCallback,
    error: errorCallback,
  });
}


/**
 * Fonction permettant de demander les infos de l'utilisateur.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function chargerUtilisateurInfos(successCallback, errorCallback) {
  $.ajax({
    type: "POST",
    dataType: "json",
    data: {
      action: "getUtilisateurInfos",
    },
    url: BASE_URL + "loginManager.php",
    success: successCallback,
    error: errorCallback,
  });
}

/**
 * Fonction permettant de se connecter.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function connecterUtilisateur(nom, motDePasse, successCallback, errorCallback) {
  $.ajax({
    type: "POST",
    dataType: "json",
    data: {
      action: "connecter",
      nom: nom,
      motDePasse: motDePasse,
    },
    url: BASE_URL + "loginManager.php",
    success: successCallback,
    error: errorCallback,
  });
}

/**
 * Fonction permettant de se deconnecter.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function deconnecterUtilisateur(successCallback, errorCallback) {
  $.ajax({
    type: "POST",
    dataType: "json",
    data: {
      action: "deconnecter",
    },
    url: BASE_URL + "loginManager.php",
    success: successCallback,
    error: errorCallback,
  });
}

/**
 * Fonction permettant d'enregistrer un utilisateur.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function enregistrerUtilisateur(nom, motDePasse, successCallback, errorCallback) {
  $.ajax({
    type: "POST",
    dataType: "json",
    data: {
      action: "enregistrer",
      nom: nom,
      motDePasse: motDePasse,
    },
    url: BASE_URL + "loginManager.php",
    success: successCallback,
    error: errorCallback,
  });
}
