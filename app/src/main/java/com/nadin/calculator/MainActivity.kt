package com.nadin.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.nadin.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CalculatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CalculatorViewModel::class.java]

        viewModel.arithmeticProcess.observe(this) {
            binding.arithmeticProcessTextview.text = it
        }

        viewModel.result.observe(this) {
            binding.resultTextview.text = it
            binding.resultTextview.visibility = if (it.isNotBlank()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        viewModel.history.observe(this) {
            val spinner = binding.spinnerHistory
            val adapter = ArrayAdapter(
                this@MainActivity,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, it
            )

            spinner.adapter = adapter
            spinner.onItemClickListener = object : AdapterView.OnItemSelectedListener,
                AdapterView.OnItemClickListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                }
            }
        }
        setupKeyClickListeners()

    }

    private fun setupKeyClickListeners() {
        binding.apply {
            button0.setOnClickListener(::onKeyClicked)
            button1.setOnClickListener(::onKeyClicked)
            button2.setOnClickListener(::onKeyClicked)
            button3.setOnClickListener(::onKeyClicked)
            button4.setOnClickListener(::onKeyClicked)
            button5.setOnClickListener(::onKeyClicked)
            button6.setOnClickListener(::onKeyClicked)
            button7.setOnClickListener(::onKeyClicked)
            button8.setOnClickListener(::onKeyClicked)
            button9.setOnClickListener(::onKeyClicked)
            buttonPlus.setOnClickListener(::onKeyClicked)
            buttonMinus.setOnClickListener(::onKeyClicked)
            buttonMul.setOnClickListener(::onKeyClicked)
            buttonDiv.setOnClickListener(::onKeyClicked)
            buttonDot.setOnClickListener(::onKeyClicked)
            buttonRightPrances.setOnClickListener(::onKeyClicked)
            buttonLeftPrances.setOnClickListener(::onKeyClicked)
            buttonC.setOnClickListener { viewModel.onClear() }
            buttonEqual.setOnClickListener { viewModel.calculateResult() }
        }
    }

    private fun onKeyClicked(view: View) {
        val keyText = (view as Button).text.toString()
        viewModel.updateArithmeticProcess(keyText)
    }
}