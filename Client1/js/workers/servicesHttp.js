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

/**
 * Fonction permettant de demander le rajout d'un quiz au serveur.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function rajouterQuiz(nom, description, successCallback, errorCallback) {
  $.ajax({
    type: "POST",
    data: {
      nom: nom,
      description: description
    },
    url: BASE_URL + "quiz/add",
    xhrFields: {
      withCredentials: true
    },
    success: successCallback,
    error: errorCallback,
  });
}

/**
 * Fonction permettant de demander la modifcation d'un quiz au serveur.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function modifierQuiz(pk, nom, description, successCallback, errorCallback) {
  $.ajax({
    type: "PUT",
    data: {
      pk: pk,
      nom: nom,
      description: description
    },
    url: BASE_URL + "quiz/update",
    xhrFields: {
      withCredentials: true
    },
    success: successCallback,
    error: errorCallback,
  });
}

/**
 * Fonction permettant de demander le rajout d'une question au serveur.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function rajouterQuestion(nom, pkQuiz, successCallback, errorCallback) {
  $.ajax({
    type: "POST",
    data: {
      nom: nom,
      pkQuiz: pkQuiz
    },
    url: BASE_URL + "question/add",
    xhrFields: {
      withCredentials: true
    },
    success: successCallback,
    error: errorCallback,
  });
}

/**
 * Fonction permettant de demander la modifcation d'une question au serveur.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function modifierQuestion(pkQuestion, nom, successCallback, errorCallback) {
  $.ajax({
    type: "PUT",
    data: {
      pkQuestion: pkQuestion,
      nom: nom
    },
    url: BASE_URL + "question/update",
    xhrFields: {
      withCredentials: true
    },
    success: successCallback,
    error: errorCallback,
  });
}

/**
 * Fonction permettant de demander le rajout d'une réponse au serveur.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function rajouterReponse(nom, correct, pkQuestion, successCallback, errorCallback) {
  $.ajax({
    type: "POST",
    data: {
      nom: nom,
      correct: correct,
      pkQuestion: pkQuestion
    },
    url: BASE_URL + "reponse/add",
    xhrFields: {
      withCredentials: true
    },
    success: successCallback,
    error: errorCallback,
  });
}

/**
 * Fonction permettant de demander la modifcation d'une réponse au serveur.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function modifierReponse(pkReponse, nom, correct, successCallback, errorCallback) {
  $.ajax({
    type: "PUT",
    data: {
      pkReponse: pkReponse,
      nom: nom,
      correct: correct
    },
    url: BASE_URL + "reponse/update",
    xhrFields: {
      withCredentials: true
    },
    success: successCallback,
    error: errorCallback,
  });
}

/**
 * Fonction permettant de demander de supprimer un element au serveur.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function deleteElement(pk, element, successCallback, errorCallback) {
  $.ajax({
    type: "DELETE",
    data: {
      pk: pk
    },
    url: BASE_URL + element + "/delete",
    xhrFields: {
      withCredentials: true
    },
    success: successCallback,
    error: errorCallback,
  });
}

function getQuiz(pkQuiz, successCallback, errorCallback) {
  $.ajax({
    type: "GET",
    dataType: "json",
    xhrFields: {
      withCredentials: true
    },
    url: BASE_URL + "quiz/get/" + pkQuiz,
    success: successCallback,
    error: errorCallback,
  });
}