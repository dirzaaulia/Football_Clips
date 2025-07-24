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
            Triple(
                "Premier League",
                "https://www.scorebat.com/embed/competition/england-premier-league/?token=MjExMDVfMTc1MzEwMzY0OV84ODVjNWE1Y2E3MTRlODBhYTAxOWU1YmE4ZDBmYjNiNWFmZDYyYjcx",
                "https://www.scorebat.com/embed/videofeed/competition/england-premier-league/?token=MjExMDVfMTc1MzEwNTM1OV81MmEzOGM5Y2ZjNTJmZjQxZTBlMWIyNDhlNTBkMzVmOWZhZmE2ZDE5"
            ),
            Triple(
                "La Liga",
                "https://www.scorebat.com/embed/competition/spain-la-liga/?token=MjExMDVfMTc1MzEwNTM1OV81MmEzOGM5Y2ZjNTJmZjQxZTBlMWIyNDhlNTBkMzVmOWZhZmE2ZDE5",
                "https://www.scorebat.com/embed/videofeed/competition/spain-la-liga/?token=MjExMDVfMTc1MzEwNTM1OV81MmEzOGM5Y2ZjNTJmZjQxZTBlMWIyNDhlNTBkMzVmOWZhZmE2ZDE5"
            ),
            Triple(
                "Serie A",
                "https://www.scorebat.com/embed/competition/italy-serie-a/?token=MjExMDVfMTc1MzEwNTM1OV81MmEzOGM5Y2ZjNTJmZjQxZTBlMWIyNDhlNTBkMzVmOWZhZmE2ZDE5",
                "https://www.scorebat.com/embed/videofeed/competition/italy-serie-a/?token=MjExMDVfMTc1MzEwNTM1OV81MmEzOGM5Y2ZjNTJmZjQxZTBlMWIyNDhlNTBkMzVmOWZhZmE2ZDE5",
            ),
            Triple(
                "Bundesliga",
                "https://www.scorebat.com/embed/competition/germany-bundesliga/?token=MjExMDVfMTc1MzEwNTM1OV81MmEzOGM5Y2ZjNTJmZjQxZTBlMWIyNDhlNTBkMzVmOWZhZmE2ZDE5",
                "https://www.scorebat.com/embed/videofeed/competition/germany-bundesliga/?token=MjExMDVfMTc1MzEwNTM1OV81MmEzOGM5Y2ZjNTJmZjQxZTBlMWIyNDhlNTBkMzVmOWZhZmE2ZDE5"
            ),
            Triple(
                "Ligue 1",
                "https://www.scorebat.com/embed/competition/france-ligue-1/?token=MjExMDVfMTc1MzEwNTM1OV81MmEzOGM5Y2ZjNTJmZjQxZTBlMWIyNDhlNTBkMzVmOWZhZmE2ZDE5",
                "https://www.scorebat.com/embed/videofeed/competition/france-ligue-1/?token=MjExMDVfMTc1MzEwNTM1OV81MmEzOGM5Y2ZjNTJmZjQxZTBlMWIyNDhlNTBkMzVmOWZhZmE2ZDE5"
            ),
            Triple(
                "Champions League",
                "https://www.scorebat.com/embed/league/uefa-champions-league/?token=MjExMDVfMTc1MzEwMzY0OV84ODVjNWE1Y2E3MTRlODBhYTAxOWU1YmE4ZDBmYjNiNWFmZDYyYjcx",
                "https://www.scorebat.com/embed/videofeed/league/uefa-champions-league/?token=MjExMDVfMTc1MzEwMzY0OV84ODVjNWE1Y2E3MTRlODBhYTAxOWU1YmE4ZDBmYjNiNWFmZDYyYjcx"
            ),
            Triple(
                "Europa League",
                "https://www.scorebat.com/embed/league/uefa-europa-league/?token=MjExMDVfMTc1MzEwNTM1OV81MmEzOGM5Y2ZjNTJmZjQxZTBlMWIyNDhlNTBkMzVmOWZhZmE2ZDE5",
                "https://www.scorebat.com/embed/videofeed/league/uefa-europa-league/?token=MjExMDVfMTc1MzEwNTM1OV81MmEzOGM5Y2ZjNTJmZjQxZTBlMWIyNDhlNTBkMzVmOWZhZmE2ZDE5"
            ),
            Triple(
                "Conference League",
                "https://www.scorebat.com/embed/league/uefa-conference-league/?token=MjExMDVfMTc1MzEwNTM1OV81MmEzOGM5Y2ZjNTJmZjQxZTBlMWIyNDhlNTBkMzVmOWZhZmE2ZDE5",
                "https://www.scorebat.com/embed/videofeed/league/uefa-conference-league/?token=MjExMDVfMTc1MzEwNTM1OV81MmEzOGM5Y2ZjNTJmZjQxZTBlMWIyNDhlNTBkMzVmOWZhZmE2ZDE5"
            )
        )
        adapter.submitList(competition)
    }

    private fun onCompetitionClicked(competition: Triple<String, String, String>) {
        val result = competition
        setFragmentResult(
            "competitionKey",
            bundleOf(
                "competitionNameKey" to result.first,
                "competitionUrlSecondKey" to result.second,
                "competitionUrlThirdKey" to result.third
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