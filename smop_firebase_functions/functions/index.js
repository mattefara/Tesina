  const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);


// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
exports.helloWorld = functions.https.onRequest((request, response) => {
  console.log("La funzione hello world è partita")
  response.send("Hello from Firebase!");
});

exports.like = functions.https.onCall((data, context) => {
  const str = data.text;
  console.log(str)
  const example = admin.database().ref("query2").orderByChild("name")
  var products = []
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
      })
      return products;
  })
  console.log("xxxxx",x)
  return x;
})

exports.hourly_job =
  functions.pubsub.topic('hourly-tick').onPublish((event) => {
    console.log("This job is ran every hour!")
  });