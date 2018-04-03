const functions = require('firebase-functions');
//const database = require('firebase-database');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

exports.funzione1 = functions.https.onCall((data, context) => {
    return "Funziono";
});

exports.funzione2 = functions.https.onCall((data, context) => {
    return {
        funziono : "true",
        funzionoAncora : "true"
    };
});

exports.funzione3 = functions.https.onCall((data, context) => {
    return {
        funziono : ["Sì","No","Forse"]
    };
});

exports.funzione4 = functions.https.onCall((data, context) => {
    return {
        array : ["Sì","No","Forse"],
        boolean : true,
        string: "vero",
        oggetto : {
            ogg1 : 1,
            ogg2 : "Messaggio",
            ogg3 : true
        },
        numero : 1
    };
});

exports.like = functions.https.onCall((data, context) => {
    console.log(database)
})
