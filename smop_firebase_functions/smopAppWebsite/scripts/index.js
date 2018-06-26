document.addEventListener('DOMContentLoaded', function() {
    // // 🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥
    // // The Firebase SDK is initialized and available here!
    //
    // firebase.auth().onAuthStateChanged(user => { });
    // firebase.database().ref('/path/to/ref').on('value', snapshot => { });
    // firebase.messaging().requestPermission().then(() => { });
    // firebase.storage().ref('/path/to/ref').getDownloadURL().then(() => { });
    //
    // // 🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥🔥

    try {
      let app = firebase.app();
      console.log("OK!");
     
    } catch (e) {
      console.error(e);
      console.log("ERRORE!");
    }

    $("#login").click(function(){
        const email = $("#username").val();
        const password = $("#password").val();
        console.log(email, password)
        firebase.auth().signInWithEmailAndPassword(email, password).catch(function(error) {
          // Handle Errors here.
          var errorCode = error.code;
          var errorMessage = error.message;
          // ...
          console.log(errorCode, errorMessage);
        });
    })

    $(".send").click(function(){
      pr = {
        name:  $("#name").val(),
        brand: $("#brand").val(),
        barcode: $("#barcode").val(),
        price: parseFloat($("#price").val()),
        ingredients: $("#ingredients").val(),
        discount: parseFloat($("#discount").val()),
        description: $("#description").val()
      }

      firebase.database().ref("products/" + pr.barcode).set(pr)

      console.log(pr)
      
    })


    firebase.auth().onAuthStateChanged(function(user) {
      console.log(user);
      if (user) {
        // User is signed in.

        var displayName = user.displayName;
        var email = user.email;
        var emailVerified = user.emailVerified;
        var photoURL = user.photoURL;
        var isAnonymous = user.isAnonymous;
        var uid = user.uid;
        var providerData = user.providerData;

        // ...
      } else {
        // User is signed out.
        // ...
      }
    })
  });