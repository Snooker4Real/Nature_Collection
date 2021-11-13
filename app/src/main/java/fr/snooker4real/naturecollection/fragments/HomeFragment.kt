package fr.snooker4real.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.snooker4real.naturecollection.MainActivity
import fr.snooker4real.naturecollection.PlantModel
import fr.snooker4real.naturecollection.R
import fr.snooker4real.naturecollection.adapter.PlantAdapter
import fr.snooker4real.naturecollection.adapter.PlantItemDecoration

class HomeFragment(
    private val context : MainActivity
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater?.inflate(R.layout.fragment_home, container, false)

        //créer une liste qui va stocker ces plantes
        val plantList = arrayListOf<PlantModel>()

        // enregistrer une première plante dans notre liste (pissenlit)
        plantList.add(
            PlantModel(
                "Pissenlit",
                "jaune soleil",
                "https://cdn.pixabay.com/photo/2014/05/01/17/51/flower-335662_960_720.jpg",
                false
            )
        )

        plantList.add(
            PlantModel(
                "Rose",
                "ca pique un peu",
                "https://cdn.pixabay.com/photo/2018/11/08/12/02/rose-3802424_960_720.jpg",
                false
            )
        )

        plantList.add(
            PlantModel(
                "Cactus",
                "ca pique un beaucoup",
                "https://cdn.pixabay.com/photo/2015/06/08/20/50/cactus-802478_960_720.jpg",
                true
            )
        )

        plantList.add(
            PlantModel(
                "Tulipe",
                "c'est beau",
                "https://cdn.pixabay.com/photo/2017/04/23/20/36/tulips-2254970_960_720.jpg",
                false
            )
        )

        // récupérer le recycler view
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = PlantAdapter(context, plantList,R.layout.item_horizontal_plant)

        //récupérer le second recycler view
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = PlantAdapter(context, plantList,R.layout.item_vertical_plant)
        verticalRecyclerView.addItemDecoration(PlantItemDecoration())

        return view
    }

}