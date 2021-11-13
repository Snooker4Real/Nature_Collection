package fr.snooker4real.naturecollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.snooker4real.naturecollection.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // charger notre PlantRepository
        val repo = PlantRepository()

        // Mettre à jour la liste de plantes
        repo.updateData {
            // Injecter le fragment dans notre boite (fragment container)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, HomeFragment(this))
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }
}