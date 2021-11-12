const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();

exports.androidPushNotification = functions.firestore
    .document("notifications/{docId}").onCreate(
        (snapshot, context) =>{
          admin.messaging().sendToTopic("notification_jhb", {
            notification: {
              title: "Peridot Request",
              body: "Urgent! Need a pad please.",
            },
          });
        }
    );
