/*
 * Couche de services HTTP (worker).
 *
 * @author Morisetti David
 * @version 1.0 / 29.04.2024
 */

var BASE_URL = "https://backend-6.emf4you.ch/";

/**
 * Fonction permettant de demander les infos de l'utilisateur.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function chargerUtilisateurInfos(successCallback, errorCallback) {
  $.ajax({
    type: "GET",
    dataType: "json",
    url: BASE_URL + "user",
    xhrFields: {
      withCredentials: true
    },
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
      username: nom,
      password: motDePasse,
    },
    url: BASE_URL + "user/login",
    xhrFields: {
      withCredentials: true
    },
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
    url: BASE_URL + "user/logout",
    xhrFields: {
      withCredentials: true
    },
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
      username: nom,
      password: motDePasse,
    },
    url: BASE_URL + "user/register",
    xhrFields: {
      withCredentials: true
    },
    success: successCallback,
    error: errorCallback,
  });
}

/**
 * Fonction permettant de demander la liste des quiz d'un utilisateur au serveur.
 * @param {type} String categorie de projet.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function chargerQuizzes(username, successCallback, errorCallback) {
  $.ajax({
    type: "GET",
    dataType: "json",
    url: BASE_URL + "quiz/user/" + username,
    success: successCallback,
    error: errorCallback,
  });
}