package pl.edu.agh.votingapp

enum class VotingType(
    val type: String,
    val description: String
) {

    MAJORITY_VOTE("Majority vote", "Majority vote description"),
    BORDA_COUNT("Borda Count", "Borda Count description"),
    FIRST_PAST_THE_POST("First-past-the-post voting", "First-past-the-post voting description"),
    TWO_ROUND_SYSTEM("Two-round system", "Two-round system description"),
    SINGLE_NON_TRANSFERABLE_VOTE(
        "Single non-transferable vote",
        "Single non-transferable vote description"
    ),
    NONE("", "");

}