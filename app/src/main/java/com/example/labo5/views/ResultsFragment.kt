package com.example.labo5.views


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.example.labo5.R
import com.example.labo5.databases.SurveyDataBase
import com.example.labo5.viewmodels.ResultsViewModel
import com.example.labo5.databinding.FragmentResultsBinding
import com.example.labo5.viewmodels.QuestionsViewModel

/**
 * A simple [Fragment] subclass.
 */
class ResultsFragment : Fragment() {
    private lateinit var binding: FragmentResultsBinding
    private lateinit var viewModelResults: ResultsViewModel
    private lateinit var viewModelQuestions: QuestionsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        //ViewModel
        viewModelResults = ViewModelProvider(activity!!).get(ResultsViewModel::class.java)
        viewModelQuestions = ViewModelProvider(activity!!).get(QuestionsViewModel::class.java)
        //Menu
        setHasOptionsMenu(true)


        binding.resultsAverage = viewModelResults.getSurveyRating().value
        binding.surveysQuantity = viewModelResults.getSurveyQuantity()


        //New survey
        binding.buttonNewSurvey.setOnClickListener{
            newSurvey()
        }
        //Show results
        binding.buttonSeeResults.setOnClickListener{
            seeAllResults()
        }


        return binding.root
    }

    //Generates new survey
    private fun newSurvey(){
        view!!.findNavController().navigate(R.id.action_resultsFragment_to_nav_home)
    }

    //See all the previous results
    private fun seeAllResults(){
        view!!.findNavController().navigate(R.id.action_resultsFragment_to_seeResultsFragment)
    }
    //Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.results_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Db
        val db = SurveyDataBase(context)

        if (item.itemId == 0) { //Delete polls
            viewModelResults.rating.value = 0.0F
            viewModelResults.quantity = 0
            db.deleteSurvey()
        } else if (item.itemId == 1) { //Delete questions
            viewModelQuestions.addedQuestions = ArrayList()
        } else if (item.itemId == 2) { //Delete answers
            viewModelResults.answersList = ArrayList()
        }

        return super.onOptionsItemSelected(item)
    }


}
