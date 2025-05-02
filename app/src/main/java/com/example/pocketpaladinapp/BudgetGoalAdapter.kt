package com.example.pocketpaladinapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BudgetGoalAdapter : RecyclerView.Adapter<BudgetGoalAdapter.BudgetGoalViewHolder>() {

    private var goals: List<BudgetGoal> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetGoalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_budget_goal, parent, false)
        return BudgetGoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: BudgetGoalViewHolder, position: Int) {
        val goal = goals[position]
        holder.bind(goal)
    }

    override fun getItemCount(): Int = goals.size

    fun submitList(newGoals: List<BudgetGoal>) {
        goals = newGoals
        notifyDataSetChanged()
    }

    class BudgetGoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMonth: TextView = itemView.findViewById(R.id.tvMonth)
        private val tvMin: TextView = itemView.findViewById(R.id.tvMin)
        private val tvMax: TextView = itemView.findViewById(R.id.tvMax)

        fun bind(goal: BudgetGoal) {
            tvMonth.text = "Month: ${goal.month}"
            tvMin.text = "Min: R${goal.minGoal}"
            tvMax.text = "Max: R${goal.maxGoal}"
        }
    }
}
