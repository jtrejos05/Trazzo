const {
    onDocumentCreated,
    onDocumentUpdated,
} = require("firebase-functions/v2/firestore")
const logger = require("firebase-functions/logger")
const admin = require("firebase-admin")

admin.initializeApp()
const db = admin.firestore()

exports.updateUserInfoInObras = onDocumentUpdated(
    "users/{userId}",
    async (event) =>{
        const userId = event.params.userId
        const before = event.data.before.data()
        const after = event.data.after.data()
        if(before.fotousuario === after.fotousuario && before.nombre === after.nombre){
            logger.log("No se actualizó la información que se muestra en tweet");
            return
        }
        const obras = await db.collection("obras").where("artistaId", "==", userId).get()
        const batch = db.batch()
        obras.forEach((obra) => {
            batch.update(obra.ref, {"artista.fotousuario": after.fotousuario, "artista.nombre": after.nombre})
        })
        await batch.commit()
        logger.log("Se actualizó la información que se muestra en tweet");
    }
);

exports.sendLikeNotification = onDocumentCreated(
    "obras/{obraId}/likes/{userId}", 
    async (event) => {
    const userId = event.params.userId
    const obraId = event.params.obraId

    

    const obraDoc = await db.collection("obras").doc(obraId).get()
    const obraData = obraDoc.data();
    const titulo = obraData.titulo
    const user = obraData.artistaId;

    const userDoc = await db.collection("users").doc(user).get()
    const userData = userDoc.data();
    const userToken = userData.fcmtoken;
    const nombre = await (await db.collection("users").doc(userId).get()).data().nombre
    
    if(userToken){
        const message = {
            token: userToken,
            notification: {
                title: "Nuevo Like",
                body: `${nombre} le a gustado tu obra ${titulo}`,
            }
        }
        try{
            await admin.messaging().send(message)
            logger.log("Notificación enviada con éxito");
        }catch(error){
            logger.error("Error al enviar la notificación", error);
        }
    }else{
        logger.log(`El usuario ${user} no tiene un token de notificación registrado`);
    }
 })

