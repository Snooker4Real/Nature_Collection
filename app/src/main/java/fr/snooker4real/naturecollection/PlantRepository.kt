package fr.snooker4real.naturecollection

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.snooker4real.naturecollection.PlantRepository.Singleton.databaseRef
import fr.snooker4real.naturecollection.PlantRepository.Singleton.plantList

class PlantRepository {

    object Singleton {
        // se connecter à la référence "plants"
        val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

        // créer une liste qui va contenir nos plantes
        val plantList = arrayListOf<PlantModel>()
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

    // mettre à jour un objet plante en bdd
    fun updatePlant(plant: PlantModel) = databaseRef.child(plant.id).setValue(plant)

    // supprimer une plante de la base
    fun deletePlant(plant: PlantModel) = databaseRef.child(plant.id).removeValue()

}