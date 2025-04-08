package uz.kabir.checkeyesight.astigmatism

import uz.kabir.checkeyesight.R

object List{

    fun getListAstigmatism():ArrayList<QuestionAstigmatism>{

        val questionList1 = ArrayList<QuestionAstigmatism>()

        questionList1.add( QuestionAstigmatism(R.drawable.astigmatism1,"1"))
        questionList1.add( QuestionAstigmatism(R.drawable.astigmatism2,"1"))
        questionList1.add( QuestionAstigmatism(R.drawable.astigmatism3,"1"))
        questionList1.add( QuestionAstigmatism(R.drawable.astigmatism4,"1"))

        questionList1.shuffle()

        return questionList1
    }


    data class QuestionAstigmatism(var imageQuestions: Int, var answer:String)


}