package com.dirzaaulia.footballclips.ui.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.dirzaaulia.footballclips.R
import com.dirzaaulia.footballclips.databinding.FragmentDialogCompetitionBinding
import com.dirzaaulia.footballclips.databinding.FragmentViewerBinding
import com.dirzaaulia.footballclips.ui.home.adapter.ClipAdapter

private const val ARG_PARAM1 = "param1"

class DialogCompetition : DialogFragment() {

    private lateinit var binding: FragmentDialogCompetitionBinding
    private lateinit var adapter: CompetitionAdapter

    private var currentCompetition: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentCompetition = it.getString(ARG_PARAM1)
            adapter = CompetitionAdapter(currentCompetition.orEmpty(), this::onCompetitionClicked)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogCompetitionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        binding.recyclerView.adapter = adapter

        val competition = listOf(
            Pair("Premier League", "<iframe src=\"https://www.scorebat.com/embed/competition/england-premier-league/?token=MjExMDVfMTczMTI2NzE2NV83MDkzYWI3MWU4MjdlZTFhYTVmM2NjMGQ5NmY0ODE5NzA5OTczMGY2\" frameborder=\"0\" width=\"600\" height=\"760\" allowfullscreen allow='autoplay; fullscreen' style=\"width:100%;height:760px;overflow:hidden;display:block;\" class=\"_scorebatEmbeddedPlayer_\"></iframe><script>(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; js.src = 'https://www.scorebat.com/embed/embed.js?v=arrv'; fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'scorebat-jssdk'));</script>"),
            Pair("La Liga", "<iframe src=\"https://www.scorebat.com/embed/competition/spain-la-liga/?token=MjExMDVfMTczMTI2NzE2NV83MDkzYWI3MWU4MjdlZTFhYTVmM2NjMGQ5NmY0ODE5NzA5OTczMGY2\" frameborder=\"0\" width=\"600\" height=\"760\" allowfullscreen allow='autoplay; fullscreen' style=\"width:100%;height:760px;overflow:hidden;display:block;\" class=\"_scorebatEmbeddedPlayer_\"></iframe><script>(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; js.src = 'https://www.scorebat.com/embed/embed.js?v=arrv'; fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'scorebat-jssdk'));</script>"),
            Pair("Serie A", "<iframe src=\"https://www.scorebat.com/embed/competition/italy-serie-a/?token=MjExMDVfMTczMTI2NzE2NV83MDkzYWI3MWU4MjdlZTFhYTVmM2NjMGQ5NmY0ODE5NzA5OTczMGY2\" frameborder=\"0\" width=\"600\" height=\"760\" allowfullscreen allow='autoplay; fullscreen' style=\"width:100%;height:760px;overflow:hidden;display:block;\" class=\"_scorebatEmbeddedPlayer_\"></iframe><script>(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; js.src = 'https://www.scorebat.com/embed/embed.js?v=arrv'; fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'scorebat-jssdk'));</script>"),
            Pair("Bundesliga", "<iframe src=\"https://www.scorebat.com/embed/competition/germany-bundesliga/?token=MjExMDVfMTczMTI2NzE2NV83MDkzYWI3MWU4MjdlZTFhYTVmM2NjMGQ5NmY0ODE5NzA5OTczMGY2\" frameborder=\"0\" width=\"600\" height=\"760\" allowfullscreen allow='autoplay; fullscreen' style=\"width:100%;height:760px;overflow:hidden;display:block;\" class=\"_scorebatEmbeddedPlayer_\"></iframe><script>(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; js.src = 'https://www.scorebat.com/embed/embed.js?v=arrv'; fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'scorebat-jssdk'));</script>"),
            Pair("Ligue 1", "<iframe src=\"https://www.scorebat.com/embed/competition/france-ligue-1/?token=MjExMDVfMTczMTI2NzE2NV83MDkzYWI3MWU4MjdlZTFhYTVmM2NjMGQ5NmY0ODE5NzA5OTczMGY2\" frameborder=\"0\" width=\"600\" height=\"760\" allowfullscreen allow='autoplay; fullscreen' style=\"width:100%;height:760px;overflow:hidden;display:block;\" class=\"_scorebatEmbeddedPlayer_\"></iframe><script>(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; js.src = 'https://www.scorebat.com/embed/embed.js?v=arrv'; fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'scorebat-jssdk'));</script>"),
        )
        adapter.submitList(competition)
    }

    private fun onCompetitionClicked(competition: Pair<String, String>) {
        val result = competition
        // Use the Kotlin extension in the fragment-ktx artifact.
        setFragmentResult(
            "competitionKey",
            bundleOf(
                "competitionNameKey" to result.first,
                "competitionUrlKey" to result.second
            )
        )
        dialog?.dismiss()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            DialogCompetition().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}