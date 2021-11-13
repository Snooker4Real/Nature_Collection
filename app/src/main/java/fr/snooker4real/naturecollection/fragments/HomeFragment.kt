package fr.snooker4real.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.snooker4real.naturecollection.MainActivity
import fr.snooker4real.naturecollection.PlantRepository.Singleton.plantList
import fr.snooker4real.naturecollection.R
import fr.snooker4real.naturecollection.adapter.PlantAdapter
import fr.snooker4real.naturecollection.adapter.PlantItemDecoration

class HomeFragment(
    private val context : MainActivity
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater
            ?.inflate(R.layout.fragment_home, container, false)

        // récupérer le recycler view
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = PlantAdapter(context, plantList.filter { !it.liked },R.layout.item_horizontal_plant)

        //récupérer le second recycler view
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = PlantAdapter(context, plantList,R.layout.item_vertical_plant)
        verticalRecyclerView.addItemDecoration(PlantItemDecoration())

        return view
    }

}