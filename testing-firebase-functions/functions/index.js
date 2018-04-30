const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

//Eseguire le funzioni localmente
//firebase serve --only functions

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
    /*const example = admin.database().ref("query2").orderByChild("barcode")
    console.log("Example",example)
    example.on('value', function (snapshot) {
        snapshot.forEach(function(childSnapShot){
            console.log("SnapShot",childSnapShot.val().barcode)
        })
    })*/
    const str = data.text;
    console.log(str)
    const example = admin.database().ref("query2").orderByChild("name")
    var products = [] 
    //console.log("Example",example)
    const x =  example.once('value').then( function (snapshot) {
        snapshot.forEach(function(childSnapShot){
            const resChild = childSnapShot.val()
            const name = resChild.name
            
            if (name.substring(0, str.length).toUpperCase() === str.toUpperCase()){
                products.push({
                    barcode : resChild.barcode,
                    name : name,
                    quantity :resChild.quantity
                })
            }

            

            //console.log("SnapShot",childSnapShot.val().barcode)
        })
        //console.log("Prodotti", products)
        return products;

    })
    console.log("xxxxx",x)
    return x;
})

exports.likeWeb = functions.https.onRequest((request, response) => {
   const str = "s"

   const example = admin.database().ref("query2").orderByChild("name")
    var products = [] 
    //console.log("Example",example)
    return example.on('value', function (snapshot) {
        snapshot.forEach(function(childSnapShot){
            const resChild = childSnapShot.val()
            const name = resChild.name
            
            if (name.substring(0, str.length).toUpperCase() === str.toUpperCase()){
                products.push({
                    barcode : resChild.barcode,
                    name : name,
                    quantity :resChild.quantity
                })
            }

            

            //console.log("SnapShot",childSnapShot.val().barcode)
        })
        console.log("Prodotti", products)
        return products;

    })
})
