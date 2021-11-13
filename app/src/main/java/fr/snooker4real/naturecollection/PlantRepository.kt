package fr.snooker4real.naturecollection

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.snooker4real.naturecollection.PlantRepository.Singleton.databaseRef
import fr.snooker4real.naturecollection.PlantRepository.Singleton.downloadUri
import fr.snooker4real.naturecollection.PlantRepository.Singleton.plantList
import fr.snooker4real.naturecollection.PlantRepository.Singleton.storageReference
import java.util.*

class PlantRepository {

    object Singleton {
        // donner le lien pour accéder au bucket
        private val BUCKET_URL: String = "gs://naturecollection-77799.appspot.com/"

        // se connecter à notre espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        // se connecter à la référence "plants"
        val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

        // créer une liste qui va contenir nos plantes
        val plantList = arrayListOf<PlantModel>()

        // contenir le lien de l'image courante
        var downloadUri: Uri? = null
    }

    fun updateData(callback: () -> Unit) {
        // absorber les données depuis la databaseRef -> liste de plantes
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // retirer les anciennes
                plantList.clear()
                for (ds in snapshot.children) {
                    //Construire un objet plante
                    val plant = ds.getValue(PlantModel::class.java)
                    // vérifier que la plante n'est pas nulle
                    if (plant != null) {
                        // ajouter la plante à notre liste
                        plantList.add(plant)
                    }
                }
                // actionner le callback
                callback()
            }
            override fun onCancelled(snapshot: DatabaseError) {
            }
        })
    }

    // créer une fonction pour envoyer des fichiers sur le storage
    fun uploadImage(file: Uri, callback: () -> Unit) {
        // vérifier que le fichier n'est pas null
        if (file != null) {
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)

            // démarrer la tache d'envoie
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                // s'il y a eu un problème lors de l'envoie du fichier
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                return@Continuation ref.downloadUrl
            }).addOnCompleteListener { task ->
                // vérifier si tout a bien fonctionné
                if (task.isSuccessful) {
                    // récupérer l'image en ligne sur le bucket
                    downloadUri = task.result
                    callback()
                }
            }
        }
    }

    // mettre à jour un objet plante en bdd
    fun updatePlant(plant: PlantModel) = databaseRef.child(plant.id).setValue(plant)

    //insérer une nouvelle plante en bdd
    fun insertPlant(plant: PlantModel) = databaseRef.child(plant.id).setValue(plant)

    // supprimer une plante de la base
    fun deletePlant(plant: PlantModel) = databaseRef.child(plant.id).removeValue()


}