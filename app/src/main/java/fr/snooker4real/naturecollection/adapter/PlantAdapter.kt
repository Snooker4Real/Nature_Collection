package fr.snooker4real.naturecollection.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.snooker4real.naturecollection.*

class PlantAdapter(
    val context: MainActivity,
    private val plantList: List<PlantModel>,
    private val layoutId: Int
) : RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    // boite pour ranger tous les composants à contrôler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
        val plantName: TextView? = view.findViewById(R.id.name_item)
        val plantDescription: TextView? = view.findViewById(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // récuperer les informations de la plante
        val currentPlant = plantList[position];

        // récupérer le repository
        val repo = PlantRepository()

        // utiliser glide pour récupérer l'image à partir de son lien -> composant
        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(holder.plantImage)

        // mettre à jour le nom de la plante
        holder.plantName?.text = currentPlant.name

        // mettre à jour la description de la plante
        holder.plantDescription?.text = currentPlant.description

        // vérifier si la plante a été liké ou non
        if (currentPlant.liked) {
            holder.starIcon.setImageResource(R.drawable.ic_star)
        } else {
            holder.starIcon.setImageResource(R.drawable.ic_unstar)
        }

        // rajouter une intéraction sur cette étoile
        holder.starIcon.setOnClickListener{
            // inverser si le bouton est liked ou non
            currentPlant.liked = !currentPlant.liked
            //metre à jour l'objet plante
            repo.updatePlant(currentPlant)
        }
        // interaction lors du click sur une plante
        holder.itemView.setOnClickListener{
            // afficher la popup
            PlantPopup(this, currentPlant).show()
        }
    }

    override fun getItemCount(): Int = plantList.size
}