package pl.edu.agh.votingapp.view.vote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.edu.agh.votingapp.R

class BordaCountAdapter(private val myDataset: List<AnswerListElement>) :
    RecyclerView.Adapter<BordaCountAdapter.MyViewHolder>() {


    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val candidateName: TextView = view.findViewById(R.id.bc_candidate_name) as TextView
        val seekBar: SeekBar = view.findViewById(R.id.seekBar) as SeekBar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val cell = LayoutInflater.from(parent.context)
            .inflate(R.layout.borda_count_list_item, parent, false)

        return MyViewHolder(cell)
    }

    override fun getItemCount() = myDataset.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.candidateName.text = myDataset[position].name
        holder.seekBar.max = myDataset.size     // seekBar ma mieć tyle opcji ilu jest kandydatow
    }

}

