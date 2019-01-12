const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });


/*    -----For Practice Purpose------ :)
exports.myMethod = functions.database.ref('/users/{id}').onUpdate((change, context) => {
    const after = change.after.val();
    const address = 'something weird';
    const timeEdited = Date.now();
    return change.after.ref.update({address, timeEdited})
});
*/



let arr = [];
exports.onUserCreate = functions.database.ref('/users/{id}').onCreate((snapshot, context) => {
    const userCreationTime = Date.now();
    arr.push(userCreationTime);
    arr.sort();
    if(arr.length > 10)
    {
        arr.shift();
    }
    snapshot.ref.parent.parent.update({count:count});
    return snapshot.ref.update({userCreationTime:userCreationTime})
});
