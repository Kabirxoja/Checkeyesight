package uz.kabir.checkeyesight.colorblindness

import uz.kabir.checkeyesight.R


object List{

    fun getQuestionColorBlindness():ArrayList<QuestionColorBlindness>{

        val questionList1 = ArrayList<QuestionColorBlindness>()

        questionList1.add(QuestionColorBlindness(R.drawable.cb_2,"2"))
        questionList1.add(QuestionColorBlindness(R.drawable.cb_27,"27"))
        questionList1.add(QuestionColorBlindness(R.drawable.cb_42,"42"))
        questionList1.add(QuestionColorBlindness(R.drawable.cb_6,"6"))
        questionList1.add(QuestionColorBlindness(R.drawable.cb_74,"74"))

        return questionList1
    }
}
