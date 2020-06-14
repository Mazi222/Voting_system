package pl.edu.agh.votingapp.view.results

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.enums.Anchor
import com.anychart.enums.Position
import com.anychart.enums.ScaleStackMode
import kotlinx.coroutines.launch
import pl.edu.agh.votingapp.R
import pl.edu.agh.votingapp.VotingType
import pl.edu.agh.votingapp.database.AppDatabase
import pl.edu.agh.votingapp.database.entities.Answer
import pl.edu.agh.votingapp.votings.*
import pl.edu.agh.votingapp.votings.exceptions.QuorumNotReachedException

class VotingResultsActivity : AppCompatActivity() {

    private val TAG = "BallotBull"

    private lateinit var db: AppDatabase
    private lateinit var vote: BaseVoting

    private lateinit var winnersText: TextView
    private lateinit var winnersTableLayout: TableLayout
    private lateinit var resultMessage: TextView
    private lateinit var anyChartView: AnyChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voting_results)

        winnersText = findViewById(R.id.winnersText)
        winnersTableLayout = findViewById(R.id.winnersTableLayout)
        resultMessage = findViewById(R.id.resultMessage)
        anyChartView = findViewById(R.id.anyChartView)

        val votingId = intent.getLongExtra("VOTING_ID", 0L)
        val votingName = intent.getStringExtra("VOTING_NAME")
        val votingType: VotingType = intent.extras?.get("VOTING_TYPE") as VotingType

        title = "Results - $votingName"

        db = AppDatabase.getInstance(applicationContext)

        vote = when (votingType) {
            VotingType.MAJORITY_VOTE -> MajorityVote(db)
            VotingType.BORDA_COUNT -> BordaCount(db)
            VotingType.FIRST_PAST_THE_POST -> FirstPastThePostVoting(db)
            VotingType.SINGLE_NON_TRANSFERABLE_VOTE -> SingleNonTransferableVote(db)
            else -> MajorityVote(db)
        }

        val barChart = getBarChart()
        val data: MutableList<DataEntry> = ArrayList()

        lifecycleScope.launch {

            try {
                /**************************************** MOCK START *********************
                val winners = arrayListOf(
                Answer(answerContent = "elo elo elo elo elo elo elo elo elo", count = 10L),
                Answer(answerContent = "hej hej", count = 7L),
                Answer(answerContent = "jou jou", count = 4L)
                )
                 **************************** MOCK END ***********************************/

                Log.d(TAG, "Getting winners...")
                val winners = vote.getWinner(votingId)
                prepareWinnersTable(winners, votingType)
            } catch (e: QuorumNotReachedException) {
                Log.d(TAG, "Quorum not reached!")
                winnersText.visibility = View.GONE
                winnersTableLayout.visibility = View.GONE
                resultMessage.text = getString(R.string.quorum_not_reached_msg)
                resultMessage.visibility = View.VISIBLE
            }

            /********************************** MOCK START *******************************
            var counter = 20
            vote.getResults(votingId).forEach {
            data.add(ValueDataEntry(it.answerContent, counter))
            counter -= 3
            }
             ***************************** MOCK END **************************************/

            vote.getResults(votingId).forEach {
                data.add(ValueDataEntry(it.answerContent, it.count))
            }

            val seriesName = when (votingType) {
                VotingType.BORDA_COUNT -> "Points"
                else -> "Votes"
            }
            val series = barChart.bar(data)
            series.name(seriesName)
            series.tooltip()
                .position(Position.RIGHT_CENTER)
                .anchor(Anchor.LEFT_CENTER)
        }
        anyChartView.setChart(barChart)
    }

    override fun onDestroy() {
        super.onDestroy()

        anyChartView.clear()
    }

    private fun prepareWinnersTable(winners: List<Answer>, votingType: VotingType) {
        val tableTextSize = 40F

        for (i in -1 until winners.size) {

            val posText = TextView(this@VotingResultsActivity)
            posText.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            posText.gravity = Gravity.START
            posText.setPadding(10, 15, 0, 15)
            posText.setTextSize(TypedValue.COMPLEX_UNIT_PX, tableTextSize)
            if (i == -1) {
                posText.text = "Pos."
                posText.setBackgroundColor(Color.parseColor("#f0f0f0"))
            } else {
                posText.setBackgroundColor(Color.parseColor("#f8f8f8"))
                posText.text = "${i + 1}."
            }


            val answerContentText = TextView(this@VotingResultsActivity)
            answerContentText.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            answerContentText.gravity = Gravity.START
            answerContentText.setPadding(10, 15, 0, 15)
            answerContentText.setTextSize(TypedValue.COMPLEX_UNIT_PX, tableTextSize)
            if (i == -1) {
                answerContentText.text = "Answer"
                answerContentText.setBackgroundColor(Color.parseColor("#f7f7f7"))
            } else {
                answerContentText.setBackgroundColor(Color.parseColor("#ffffff"))
                answerContentText.text = winners[i].answerContent
            }


            val voteCountText = TextView(this@VotingResultsActivity)
            voteCountText.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            voteCountText.gravity = Gravity.START
            voteCountText.setPadding(10, 15, 0, 15)
            voteCountText.setTextSize(TypedValue.COMPLEX_UNIT_PX, tableTextSize)
            if (i == -1) {
                voteCountText.setBackgroundColor(Color.parseColor("#f0f0f0"))
                voteCountText.text = when (votingType) {
                    VotingType.BORDA_COUNT -> "Points"
                    else -> "Votes"
                }
            } else {
                voteCountText.setBackgroundColor(Color.parseColor("#f8f8f8"))
                voteCountText.text = winners[i].count.toString()
            }

            val tableRow = TableRow(this@VotingResultsActivity)
            tableRow.id = i + 1
            val tableRowParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )
            tableRowParams.setMargins(0, 0, 0, 0)
            tableRow.setPadding(0, 0, 0, 0)
            tableRow.layoutParams = tableRowParams

            tableRow.addView(posText)
            tableRow.addView(answerContentText)
            tableRow.addView(voteCountText)

            winnersTableLayout.addView(tableRow, tableRowParams)

            if (i > -1) {
                val tableRowSep = TableRow(this@VotingResultsActivity)
                val tableRowSepParams = TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
                )
                tableRowSepParams.setMargins(0, 0, 0, 0)
                tableRowSep.layoutParams = tableRowSepParams

                val sepView = TextView(this@VotingResultsActivity)
                val sepViewParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                sepViewParams.span = 4
                sepView.layoutParams = sepViewParams
                sepView.setBackgroundColor(Color.parseColor("#d9d9d9"))
                sepView.height = 1

                tableRowSep.addView(sepView)
                winnersTableLayout.addView(tableRowSep, tableRowSepParams)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getBarChart(): Cartesian {
        val barChart = AnyChart.bar()
        barChart.margin(0, 8, 0, 8)
        barChart.animation(true)
        barChart.yScale().stackMode(ScaleStackMode.VALUE)
        barChart.yScale().ticks().allowFractional(false)
        barChart.yAxis(0).title("Number of votes")
        barChart.xAxis(0).title("Answers")
        barChart.xAxis(0).labels().enabled(false)
        return barChart
    }
}