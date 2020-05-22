package pl.edu.agh.votingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass.
 */
class CreateVotingChooseTypeFragment : Fragment(R.layout.fragment_create_voting_choose_type) {

    private val adapter = MyListAdapter()
    private var tracker: SelectionTracker<Long>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rv = RecyclerView(context!!)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter

        adapter.listData = VotingType.values()
        adapter.notifyDataSetChanged()

        tracker = SelectionTracker.Builder(
            "mySelection",
            rv,
            StableIdKeyProvider(rv),
            MyItemDetailsLookup(rv),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything()
        ).build()

        adapter.tracker = tracker

        return rv
    }

    class MyListAdapter : RecyclerView.Adapter<MyViewHolder>() {

        var listData: Array<VotingType> = arrayOf()
        var tracker: SelectionTracker<Long>? = null

        init {
            setHasStableIds(true)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val listItem = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false) as View

            return MyViewHolder(listItem)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            tracker?.let {
                holder.bind(listData[position], it.isSelected(position.toLong()))
            }
        }

        override fun getItemCount() = listData.size

        override fun getItemId(position: Int): Long = position.toLong()

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descTextView: TextView = itemView.findViewById(R.id.descTextView)
        private val relativeLayout: RelativeLayout = itemView.findViewById(R.id.relativeLayout)

        fun bind(votingType: VotingType, isActivated: Boolean = false) {
            titleTextView.text = votingType.type
            descTextView.text = votingType.description
            itemView.isActivated = isActivated
            relativeLayout.setOnClickListener { view ->
                Toast.makeText(
                    view.context,
                    "Selected: " + votingType.type,
                    Toast.LENGTH_LONG
                ).show()

//                val activity = fragment.activity as CreateVotingActivity
//                activity.newVoting.type = votingType
            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }
    }

    class MyItemDetailsLookup(private val recyclerView: RecyclerView) :
        ItemDetailsLookup<Long>() {
        override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(e.x, e.y)
            if (view != null) {
                return (recyclerView.getChildViewHolder(view) as MyViewHolder).getItemDetails()
            }
            return null
        }
    }

}
